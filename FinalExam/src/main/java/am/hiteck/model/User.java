package am.hiteck.model;

import am.hiteck.model.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "verification_code")
    private String verificationCode;

    @JsonIgnore
    @Column(name = "password_token")
    private Integer passwordToken;

    @Column(name = "status")
    private String status;

    @Column(name = "role_id")
    @Enumerated(EnumType.ORDINAL)
    private Roles role;

    @Column(name = "salary")
    private int salary;
}
