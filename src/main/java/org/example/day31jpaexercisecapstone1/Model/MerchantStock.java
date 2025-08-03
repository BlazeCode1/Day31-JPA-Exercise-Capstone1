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
public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull(message = "ProductID Cannot Be Empty")
    @Column(columnDefinition = "int not null")
    private Integer product_id;

    @NotNull(message = "merchantID Cannot Be Empty")
    @Column(columnDefinition = "int not null")
    private Integer merchant_id;

    @NotNull(message = "Stock cannot be empty")
    @Column(columnDefinition = "int not null")
    private Integer stock;

    @Column(columnDefinition = "int default 0")
    private Integer purchase_count;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "datetime")
    private LocalDate stock_added_date;

    @Column(columnDefinition = "boolean")
    private boolean is_clearance;


    @Column(columnDefinition = "double default 0")
    private double clearance_price = 0.0;



}
