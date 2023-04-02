package com.socialmedia.logservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Model class for personal log.
 */
@Entity
@Table(name = "PersonalLog")
@Getter
@Setter
@ToString
public class PersonalLog implements Comparable<PersonalLog> {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;

    @Column(name = "content", nullable = true)
    private String text;

    @Lob
    @Column(name = "image", nullable = true, columnDefinition = "mediumblob")
    private byte[] image;

    @Column(name = "username", nullable = false)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chart_id", referencedColumnName = "id")
    private Chart chart;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    public PersonalLog(UUID id, String text, byte[] image, String username, Chart chart, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.username = username;
        this.chart = chart;
        this.createdAt = createdAt;
    }

    @JsonCreator
    public PersonalLog() {}

    @JsonIgnore
    @Override
    public int compareTo(PersonalLog o) {
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
