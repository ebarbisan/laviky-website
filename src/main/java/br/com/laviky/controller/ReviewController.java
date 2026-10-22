package br.com.laviky.controller;

import br.com.laviky.dto.ReviewDTO;
import br.com.laviky.service.GoogleReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final GoogleReviewsService googleReviewsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getReviews() {
        List<ReviewDTO> reviews = googleReviewsService.getReviews();

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("total", reviews.size());

        return ResponseEntity.ok(response);
    }
}
