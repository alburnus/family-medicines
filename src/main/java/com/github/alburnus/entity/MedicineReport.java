package com.github.alburnus.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class MedicineReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_report_generator")
    @SequenceGenerator(name = "medicine_report_generator", sequenceName = "medicine_report_seq", allocationSize = 1)
    private Long id;

    private String response;

    private Long countedResult;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private MedicineRequestConfig medicineRequestConfig;

    @NotNull
    @CreatedDate
    // FIXME should set time automatically
    private LocalDateTime createdDate;
}
