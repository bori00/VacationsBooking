package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User implements IEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserType userType;

    @OneToMany(mappedBy = "", cascade = CascadeType.PERSIST)
    // lazy loading by default
    private Set<Booking> bookings;

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
