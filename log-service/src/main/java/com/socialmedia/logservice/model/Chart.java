package com.socialmedia.logservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "Chart")
@Getter
@Setter
@ToString
public class Chart {
    @Id
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;

    private String chartType;

    @Lob
    @Column(name = "chart_data", length = Integer.MAX_VALUE, nullable = false)
    private String chartData;

    @JsonCreator
    public Chart() {}

    public Chart(UUID id, String chartType, String chartData) {
        this.id = id;
        this.chartType = chartType;
        this.chartData = chartData;
    }
}