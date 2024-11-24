import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.Connection;

public class Client extends User {
    private UUID clientID;
    private String goals;
    private String workoutPlan;

    public Client(UUID userID, String username, String passwordHash, String email, String role, UUID clientID, String goals, String workoutPlan) {
        super(userID, username, passwordHash, email, role);
        this.clientID = clientID;
        this.goals = goals;
        this.workoutPlan = workoutPlan;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getWorkoutPlan() {
        return workoutPlan;
    }

    public void setWorkoutPlan(String workoutPlan) {
        this.workoutPlan = workoutPlan;
    }
    
    //Review Implementation - For CLIENT ONLY
    //placeholder values used for the db connection (Not yet implemented)
   public boolean addReview(UUID trainerID, int rating, String comment, Connection dbConnection) {
	   //Validating input
	   if (dbConnection == null) {
		    System.err.println("No Database connection.");
		    return false;
		}

	   if(rating < 1 || rating > 5) {
		   System.err.println("Invalid rating: " + rating + ".");
		   return false;
	   }
	   
	   if(comment == null || comment.length() > 50) {
		   System.err.println("Comment must not be empty or larger than 50 characters.");
		   return false;
	   }
	   
	   if(trainerID == null) {
		   System.err.println("Please specifiy a trainer to review.");
		   return false;
	   }
	   String sql = "INSERT INTO Review (review_ID, client_ID, trainer_ID, Rating, Comment) VALUES (?, ?, ?, ?, ?)";
	   try(PreparedStatement statement = dbConnection.prepareStatement(sql)){
		   statement.setObject(1, UUID.randomUUID());
		   statement.setObject(2, this.clientID);
		   statement.setObject(3, trainerID);
		   statement.setInt(4, rating);
		   statement.setString(5,comment);
		   
		   int rowsAffected = statement.executeUpdate();
		   return rowsAffected > 0;
	   }catch (SQLException e) {
		   System.err.println("Could not insert the review: " + e.getMessage());
		   return false;
	   } catch (Exception e) {
		   System.err.println("Unexpected error occurred.");
		   return false;
	   }
   }
}

