package br.com.laviky.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private String authorName;
    private String authorPhoto;
    private int rating;
    private String text;
    private String relativeTime;
}
