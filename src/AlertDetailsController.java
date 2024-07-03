import javafx.scene.control.Label;

import java.sql.*;

public class AlertDetailsController {
    public Label animalDetailsTxt;
    private String animalTag;

    public void setAnimalTag(String animalTag) {
        this.animalTag = animalTag;
        retrieveDetailsFromDatabase(animalTag);
    }

    private void retrieveDetailsFromDatabase(String animalTag) {
        Connection conn = LoginFormController.connectDB();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT p.description, m.med_name, m.dose_at_a_time, m.food_instructions, p.food_name, p.pack_at_a_time, p.no_of_times_per_day, p.no_of_days " +
                    "FROM prescription p " +
                    "JOIN med_det m ON p.prescription_id = m.prescription_id " +
                    "WHERE p.animal_tag =? ";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, animalTag);
            ResultSet resultSet = pstmt.executeQuery();

            StringBuilder desc = new StringBuilder("Description:\nThe " + animalTag + " has been prescribed with:\n");

            while (resultSet.next()) {
                String medName = resultSet.getString("med_name");
                int dose = resultSet.getInt("dose_at_a_time");
                String instruction = resultSet.getString("food_instructions");
                desc.append("  - ").append(medName).append(" with a dose of ").append(dose).append(" ").append(instruction).append(" the food.\n");
            }

            if (resultSet.previous()) {
                String description = resultSet.getString("description");
                String foodPack = resultSet.getString("food_name");
                int howManyTimes = resultSet.getInt("pack_at_a_time");
                int perday = resultSet.getInt("no_of_times_per_day");
                int noOfDays = resultSet.getInt("no_of_days");

                desc.append("The dosage needs to be taken for \n").append("  - ").append(perday).append(" times a day with ").append(noOfDays).append(" days. ").append("\n")
                        .append("The food pack recommended is ").append(foodPack).append("\nand it should be taken ").append(howManyTimes).append(" times a day.\n")
                        .append("The description of the prescription is: \n").append(description);

                animalDetailsTxt.setText(desc.toString());
            } else {
                animalDetailsTxt.setText("No prescription found for animal " + animalTag);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

    }
}
