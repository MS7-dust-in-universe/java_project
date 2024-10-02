import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class PackageDetailsController {

    public Label medione;
    public Label meditwo;
    public Label medithree;
    public Label medioneamount;
    public Label meditwoamount;
    public Label medithreeamount;
    public Label foodpackamount;
    public Label foodpackamount3;
    public AnchorPane Update;
    private String animalTag;
    private Stage stage;
    private Scene scene;

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


                int medicineAmount = dose * noOfTimesPerDay * noOfDays;

                switch (medicineCount) {
                    case 0:
                        medione.setText(medName);
                        medioneamount.setText(String.valueOf(medicineAmount));
                        break;
                    case 1:
                        meditwo.setText(medName);
                        meditwoamount.setText(String.valueOf(medicineAmount));
                        break;
                    case 2:
                        medithree.setText(medName);
                        medithreeamount.setText(String.valueOf(medicineAmount));
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

                int foodPackAmount = howManyTimes* perday * noOfDays;
                foodpackamount.setText(foodPack);
                foodpackamount3.setText(String.valueOf(foodPackAmount));
                break;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    public void backbyaslide(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Notifications.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateconfimation(ActionEvent actionEvent) {
        stage = (Stage)Update.getScene().getWindow();
        stage.close();
    }
}
