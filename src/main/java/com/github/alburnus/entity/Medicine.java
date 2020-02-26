package com.github.alburnus.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Medicine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_generator")
    @SequenceGenerator(name = "medicine_generator", sequenceName = "medicine_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;
}
