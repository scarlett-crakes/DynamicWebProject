import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Review {
    private UUID reviewId;
    private UUID clientId;
    private UUID trainerId;
    private int rating;
    private String comment;

    public Review(UUID clientId, UUID trainerId, int rating, String comment) {
        if (clientId == null || trainerId == null) {
            throw new IllegalArgumentException("Client ID and Trainer ID cannot be null.");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (comment == null || comment.length() > 50) {
            throw new IllegalArgumentException("Comment must not be null and must not exceed 50 characters.");
        }
        this.reviewId = UUID.randomUUID(); 
        this.clientId = clientId;
        this.trainerId = trainerId;
        this.rating = rating;
        this.comment = comment;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        if (comment == null || comment.length() > 50) {
            throw new IllegalArgumentException("Comment must not be null and must not exceed 50 characters.");
        }
        this.comment = comment;
    }

    //Review Implementation - For CLIENT ONLY
    //placeholder values used for the db connection (Not yet implemented)
    public boolean addReview(Connection dbConnection) {
        if (dbConnection == null) {
            System.err.println("No Database connection.");
            return false;
        }

        String sql = "INSERT INTO Review (review_ID, client_ID, trainer_ID, Rating, Comment) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = dbConnection.prepareStatement(sql)) {
            statement.setObject(1, reviewId);  
            statement.setObject(2, clientId); 
            statement.setObject(3, trainerId);
            statement.setInt(4, rating);     
            statement.setString(5, comment); 

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            System.err.println("Could not save the review to the database: " + e.getMessage());
            return false;
        }
    }
}
