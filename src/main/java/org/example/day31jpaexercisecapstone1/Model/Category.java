package org.example.day31jpaexercisecapstone1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @NotEmpty(message = "Name Cannot Be Empty")
    @Size(min = 3,message = "Name length is at least 3 or above")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
}
