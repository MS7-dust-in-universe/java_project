import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Desk4Controller {
    public AnchorPane Desk4Form;
    public TextField identityTxt;
    public TextField descriptionTxt;
    public TextField med1Txt;
    public TextField med2Txt;
    public TextField med3Txt;
    public TextField foodTxt;
    public TextField packTxt;
    public TextField timesTxt;
    public TextField daysTxt;
    public TextField dose1Txt;
    public TextField dose2Txt;
    public TextField dose3Txt;
    public TextField food_ins1Txt;
    public TextField food_ins2Txt;
    public TextField food_ins3Txt;
    private Stage stage;
    private Scene scene;



    public void submitOnAction(ActionEvent actionEvent) {
        String identity = identityTxt.getText();
        String desc = descriptionTxt.getText();
        String med1 = med1Txt.getText();
        String med2 = med2Txt.getText();
        String med3 = med3Txt.getText();
        String dose1 = dose1Txt.getText();
        String dose2 = dose2Txt.getText();
        String dose3 = dose3Txt.getText();
        String ins1 = food_ins1Txt.getText();
        String ins2 = food_ins2Txt.getText();
        String ins3 = food_ins3Txt.getText();
        String foodname = foodTxt.getText();
        String pack = packTxt.getText();
        String no_time = timesTxt.getText();
        String no_days = daysTxt.getText();

        try {

            Connection conn = LoginFormController.connectDB();

            // Check if the animal exists in the animal table
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT 1 FROM animal WHERE animal_tag = '" + identity + "'");
            boolean animalExists = result.next();

            if (!animalExists) {
                // Insert the animal into the animal table
                String animalQuery = "INSERT INTO animal (animal_tag) VALUES (?)";
                PreparedStatement animalStmt = conn.prepareStatement(animalQuery);
                animalStmt.setString(1, identity);
                animalStmt.executeUpdate();
            }

            // Update the prescription table
            String prescriptionQuery = "INSERT INTO prescription (animal_tag, description,food_name,pack_at_a_time,no_of_times_per_day,no_of_days,days_remains) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement prescriptionStmt = conn.prepareStatement(prescriptionQuery);
            prescriptionStmt.setString(1, identity);
            prescriptionStmt.setString(2, desc);
            prescriptionStmt.setString(3,foodname);
            prescriptionStmt.setInt(4, Integer.parseInt(pack));
            prescriptionStmt.setInt(5, Integer.parseInt(no_time));
            prescriptionStmt.setInt(6,Integer.parseInt(no_days));
            prescriptionStmt.setInt(7,Integer.parseInt(no_days));
            prescriptionStmt.executeUpdate();

            // Get the prescription ID
            int prescriptionId = getLastInsertId(conn);

            // Update the med_det table
            String medDetQuery = "INSERT INTO med_det (prescription_id, med_name,dose_at_a_time,food_instructions) VALUES (?,?,?,?)";
            PreparedStatement medDetStmt = conn.prepareStatement(medDetQuery);
            medDetStmt.setInt(1, prescriptionId);
            medDetStmt.setString(2, med1);
            medDetStmt.setInt(3,Integer.parseInt(dose1));
            medDetStmt.setString(4,ins1);
            medDetStmt.executeUpdate();

            // Add more med_det rows if necessary
            if (!med2.isEmpty()) {
                medDetStmt.setString(2, med2);
                medDetStmt.setInt(3,Integer.parseInt(dose2));
                medDetStmt.setString(4,ins2);
                medDetStmt.executeUpdate();
            }
            if (!med3.isEmpty()) {
                medDetStmt.setString(2, med3);
                medDetStmt.setInt(3,Integer.parseInt(dose3));
                medDetStmt.setString(4,ins3);
                medDetStmt.executeUpdate();
            }


            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        identityTxt.setText("");
        descriptionTxt.setText("");
        med1Txt.setText("");
        med2Txt.setText("");
        med3Txt.setText("");
        dose1Txt.setText("");
        dose2Txt.setText("");
        dose3Txt.setText("");
        food_ins1Txt.setText("");
        food_ins2Txt.setText("");
        food_ins3Txt.setText("");
        foodTxt.setText("");
        packTxt.setText("");
        daysTxt.setText("");
        timesTxt.setText("");
    }

    private int getLastInsertId(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
        result.next();
        int id = result.getInt(1);
        return id;
    }


    public void backonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Desk3.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
