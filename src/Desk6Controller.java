import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Desk6Controller {
    public AnchorPane Update;
    public Button back;
    public Label med1txt;
    public Label food1txt;
    public Label med3txt;
    public Label med2txt;
    public TextField amount1txt;
    public TextField amount3txt;
    public TextField amount2txt;
    public TextField amount1;
    private Stage stage;
    private Scene scene;
    private Stage previousStage;
    private TransformationList<Object, Object> event;
    private InputMethodTextRun animaltagTxt;
    public String animalTag;




    public void updateconfimation(ActionEvent actionEvent) {
        /*stage = (Stage)Update.getScene().getWindow();
        stage.close();

         */
        try {
            Connection conn = LoginFormController.connectDB();
            String query = "UPDATE prescription SET days_remains = days_remains - 1 WHERE animal_tag = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, animalTag);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stage = (Stage)Update.getScene().getWindow();
        stage.close();

    }

    public void backbyaslide(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Desk5.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setAnimalTag(String animalTag) {
        this.animalTag = animalTag;
        retrieveDetailsFromDatabase(animalTag);
    }

    private void retrieveDetailsFromDatabase(String animalTag) {
        Connection conn = LoginFormController.connectDB();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT  m.med_name, m.dose_at_a_time, p.food_name, p.pack_at_a_time, p.no_of_times_per_day, p.no_of_days" +
                    " FROM prescription p" +
                    " JOIN med_det m ON p.prescription_id = m.prescription_id " +
                    "WHERE p.animal_tag =? ";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, animalTag);
            ResultSet resultSet = pstmt.executeQuery();

            int medicineCount = 0;
            while (resultSet.next()) {
                String medName = resultSet.getString("med_name");
                int dose = resultSet.getInt("dose_at_a_time");
                int noOfTimesPerDay = resultSet.getInt("no_of_times_per_day");
                int noOfDays = resultSet.getInt("no_of_days");


                int medicineAmount = dose * noOfTimesPerDay;

                switch (medicineCount) {
                    case 0:
                        med1txt.setText(medName);
                        amount1txt.setText(String.valueOf(medicineAmount));
                        break;
                    case 1:
                        med2txt.setText(medName);
                        amount2txt.setText(String.valueOf(medicineAmount));
                        break;
                    case 2:
                        med3txt.setText(medName);
                        amount3txt.setText(String.valueOf(medicineAmount));
                        break;
                }

                medicineCount++;

            }

            resultSet.beforeFirst();
            while (resultSet.next()) {
                String foodPack = resultSet.getString("food_name");
                int howManyTimes = resultSet.getInt("pack_at_a_time");
                int perday = resultSet.getInt("no_of_times_per_day");
                int noOfDays = resultSet.getInt("no_of_days");

                int foodPackAmount = howManyTimes* perday;
                food1txt.setText(foodPack);
                amount1.setText(String.valueOf(foodPackAmount));
                break;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
