package com.de.n26.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto implements Serializable {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;
}
