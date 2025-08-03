package org.example.day31jpaexercisecapstone1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "role in ('admin','customer') and balance >0")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @NotEmpty(message = "Username Cannot Be Empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String username;



    @NotEmpty(message = "User Password Cannot Be Empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$",message = "Password Must contain characters and digits, and a length of 6")
    @Column(columnDefinition = "varchar(250) not null")
    private String password;


    @NotEmpty(message = "User Email Cannot Be Empty")
    @Email(message = "Invalid Email Format")
    @Column(columnDefinition = "varchar(255) not null")
    private String email;

    @NotEmpty(message = "User Role Cannot Be Empty")
    @Pattern(regexp = "^(admin|customer)$",message = "Invalid Customer Role")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;


    @NotNull(message = "balance Cannot Be Empty")
    @PositiveOrZero(message = "Only Positive numbers")
    @Column(columnDefinition = "double")
    private Double balance;
}
