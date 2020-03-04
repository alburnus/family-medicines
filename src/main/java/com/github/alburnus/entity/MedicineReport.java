package com.github.alburnus.entity;

import com.github.alburnus.entity.report.ReportResult;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class MedicineReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_report_generator")
    @SequenceGenerator(name = "medicine_report_generator", sequenceName = "medicine_report_seq", allocationSize = 1)
    private Long id;

    private String response;

    private Long countedResult;

    private LocalDate reportDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private ReportResult reportResult;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private MedicineRequestConfig medicineRequestConfig;

    @NotNull
    @CreatedDate
    // FIXME should set time automatically
    private LocalDateTime createdDate;
}
