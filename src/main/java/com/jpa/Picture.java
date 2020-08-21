package com.jpa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="picture")
public class Picture {
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String url;
    private String author;
    private String category;
    private String getTime;
}
