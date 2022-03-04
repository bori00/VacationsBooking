package model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.stream.Stream;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserType userType;

    public User(String userName, String password, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public enum UserType {
        TravellingAgency("A"),
        VacaySeeker("V");

        private final String code;

        private UserType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        @Converter(autoApply = true)
        public static class UserTypeConverter implements AttributeConverter<UserType, String> {

            @Override
            public String convertToDatabaseColumn(UserType UserType) {
                if (UserType == null) {
                    return null;
                }
                return UserType.getCode();
            }

            @Override
            public UserType convertToEntityAttribute(String code) {
                if (code == null) {
                    return null;
                }

                return Stream.of(UserType.values())
                        .filter(c -> c.getCode().equals(code))
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new);
            }
        }
    }
}
