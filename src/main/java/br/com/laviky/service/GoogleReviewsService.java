package br.com.laviky.service;

import br.com.laviky.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleReviewsService {

    @Value("${laviky.google.api-key}")
    private String apiKey;

    @Value("${laviky.google.place-id}")
    private String placeId;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ReviewDTO> getReviews() {
        if (apiKey == null || apiKey.isBlank() || placeId == null || placeId.isBlank()) {
            log.warn("Google API Key or Place ID not configured. Returning empty list.");
            return Collections.emptyList();
        }

        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
                    .queryParam("place_id", placeId)
                    .queryParam("fields", "reviews")
                    .queryParam("key", apiKey)
                    .toUriString();

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !"OK".equals(response.get("status"))) {
                log.error("Google API error status: {}", response != null ? response.get("status") : "null");
                return Collections.emptyList();
            }

            Map<String, Object> result = (Map<String, Object>) response.get("result");
            if (result == null || !result.containsKey("reviews")) {
                return Collections.emptyList();
            }

            List<Map<String, Object>> reviewsList = (List<Map<String, Object>>) result.get("reviews");

            return reviewsList.stream()
                    .map(this::mapToReviewDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Exception calling Google Reviews API: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private ReviewDTO mapToReviewDTO(Map<String, Object> review) {
        return ReviewDTO.builder()
                .authorName((String) review.get("author_name"))
                .authorPhoto((String) review.get("profile_photo_url"))
                .rating((Integer) review.get("rating"))
                .text((String) review.get("text"))
                .relativeTime((String) review.get("relative_time_description"))
                .build();
    }
}
