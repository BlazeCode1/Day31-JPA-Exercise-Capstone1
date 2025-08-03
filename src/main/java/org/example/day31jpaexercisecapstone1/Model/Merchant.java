package org.example.day31jpaexercisecapstone1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Check(constraints = "commission_rate >= 0")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;


    @NotEmpty(message = "Merchant Name Cannot Be Empty")
    @Size(min = 3,message = "Merchant Name Can Only ")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Column(columnDefinition = "double")
    private double commission_rate;

    @Column(columnDefinition = "boolean")
    private boolean isSuspended;
}
