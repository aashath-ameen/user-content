// ReviewService.java
package com.example.usercontent.service;

import com.example.usercontent.model.Review;
import com.example.usercontent.model.Restaurant;
import com.example.usercontent.model.Menu;
import com.example.usercontent.model.User;
import com.example.usercontent.repository.ReviewRepository;
import com.example.usercontent.repository.RestaurantRepository;
import com.example.usercontent.repository.MenuRepository;
import com.example.usercontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    public Review createReview(Review review) {
        if (review.getRestaurant() != null && review.getMenu() != null && review.getUser() != null) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(review.getRestaurant().getId());
            Optional<Menu> menu = menuRepository.findById(review.getMenu().getId());
            Optional<User> user = userRepository.findById(review.getUser().getId());

            if (restaurant.isPresent() && menu.isPresent() && user.isPresent()) {
                review.setRestaurant(restaurant.get());
                review.setMenu(menu.get());
                review.setUser(user.get());
                return reviewRepository.save(review);
            } else {
                throw new RuntimeException("Invalid Restaurant, Menu, or User ID");
            }
        } else {
            throw new RuntimeException("Restaurant, Menu, and User must be provided");
        }
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByRestaurantId(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByMenuId(Long menuId) {
        return reviewRepository.findByMenuId(menuId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setText(reviewDetails.getText());
                    review.setRating(reviewDetails.getRating());
                    review.setTimestamp(reviewDetails.getTimestamp());
                    review.setRestaurant(reviewDetails.getRestaurant());
                    review.setMenu(reviewDetails.getMenu());
                    review.setUser(reviewDetails.getUser());
                    return reviewRepository.save(review);
                }).orElseThrow(() -> new RuntimeException("Review not found with id " + id));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
