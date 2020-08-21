package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PictureVO {
    @JsonIgnore
    private Long id;
    private String name;
    private String url;
    private String author;
    private String category;
    private String getTime;
}
