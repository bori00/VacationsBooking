package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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

    @Column(unique = true, nullable = false, length = 15)
    @NotBlank(message = "Username cannot be blank!")
    @Size(min = 3, max = 15, message = "The username should have a length between 3 and 15")
    private String userName;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 3, max = 20, message = "The password should have a length between 3 and 15")
    private String password;

    @Column(nullable = false)
    @NotNull
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    // lazy loading by default
    private Set<@Valid Booking> bookings;

    public User(String userName, String password, UserType userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.bookings = new HashSet<>();
    }

    public enum UserType {
        TravellingAgency("A"),
        VacaySeeker("V");

        private final String code;

        UserType(String code) {
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
