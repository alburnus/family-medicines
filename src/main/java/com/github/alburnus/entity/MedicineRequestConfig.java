package com.github.alburnus.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class MedicineRequestConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_req_conf_generator")
    @SequenceGenerator(name = "medicine_req_conf_generator", sequenceName = "medicine_req_conf_seq", allocationSize = 1)
    private Long id;

    private String source;

    private String url;

    private int threshold;

    private String email;

    @ManyToOne
    private Medicine medicine;
}
