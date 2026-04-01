package lk.wedalk.reviews.repository;

import java.util.List;
import java.util.Optional;
import lk.wedalk.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ReviewRepository.java — Review Data Access Layer
 *
 * <p>Data access for reviews — supports lookup by worker, seeker, and rating calculations.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRevieweeId(Long revieweeId);

    List<Review> findByReviewerId(Long reviewerId);

    Optional<Review> findByRequestIdAndReviewerId(Long requestId, Long reviewerId);

    boolean existsByRequestIdAndReviewerId(Long requestId, Long reviewerId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.reviewee.id = :revieweeId")
    Double findAverageRatingByRevieweeId(@Param("revieweeId") Long revieweeId);
}
