package org.example.day31jpaexercisecapstone1.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Check(constraints = "price > 0 and total_sold >=0 and total_revenue >=0")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @NotEmpty(message = "Product Name Cannot Be Empty")
    @Size(min = 3,message = "Product Name have to be 3 length or more")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name;

    @NotNull(message = "price must not be null")
    @Positive(message = "Must Be Positive Number")
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotNull(message = "categoryId must not be null")
    @Column(columnDefinition = "int not null")
    private Integer category_id;

    @PositiveOrZero
    @Column(columnDefinition = "int")
    private Integer total_sold;

    @PositiveOrZero
    @Column(columnDefinition = "double")
    private Double total_revenue;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "datetime")
    private LocalDate last_sold_date;

    @Pattern(
            regexp = "^[a-zA-Z0-9]+:[a-zA-Z]+:[a-zA-Z0-9]+$",
            message = "Product code must follow the format: productName:color:code"
    )
    @NotEmpty(message = "Product code Must Not be Empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String product_code;

}
