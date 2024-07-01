import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;

public class Desk5Controller {
    public ImageView animalimg;
    public Label animaltag;
    public Label animaldescTxt;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public String animalTag;

    public void imgselection(MouseEvent mouseEvent) {
    }

    public void confirmation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Desk6.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
      }


    public void backbyaslide(ActionEvent actionEvent) throws IOException {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("ButtonForm.fxml"));
        root = loader.load();
        ButtonFormController buttonformcontroller = loader.getController();
        buttonformcontroller.displayName2(username);

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }


    public void setAnimalTag(String animalTag) {
        this.animalTag = animalTag;
        animaltag.setText(animalTag);
        retrieveAnimalDetails();
    }


    private void retrieveAnimalDetails() {
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

                animaldescTxt.setText(desc.toString());
            } else {
                animaldescTxt.setText("No prescription found for animal " + animalTag);
            }

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn!= null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

}