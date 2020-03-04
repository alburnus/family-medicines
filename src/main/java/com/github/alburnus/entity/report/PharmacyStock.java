package com.github.alburnus.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyStock implements Serializable {

    private String pharmacy;

    private String location;

    private String stock;
}
