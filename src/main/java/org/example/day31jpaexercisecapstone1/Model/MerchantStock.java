package org.example.day31jpaexercisecapstone1.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "stock > 10")
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "ProductID Cannot Be Empty")
    @Column(columnDefinition = "int not null")
    private Integer product_id;

    @NotEmpty(message = "merchantID Cannot Be Empty")
    @Column(columnDefinition = "int not null")
    private Integer merchant_id;

    @NotNull(message = "Stock cannot be empty")
    @Min(value = 10,message = "Merchant Stock Has to be more than 10")
    @Column(columnDefinition = "int not null")
    private Integer stock;

    @Column(columnDefinition = "int")
    private Integer purchase_count;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "datetime")
    private LocalDate stock_added_date;

    @Column(columnDefinition = "boolean")
    private boolean is_clearance;

    @Column(columnDefinition = "double")
    private double clearance_price;


}
