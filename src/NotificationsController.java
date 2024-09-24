import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotificationsController {
    public VBox vbox;
    public AnchorPane notifications;
    private Stage stage;
    private Scene scene;


    @FXML
    public void initialize() throws IOException {

        List<String> animalTags = retrieveAnimalTagsFromDatabase();

        vbox.getChildren().clear();
        // Create a separate alert GUI for each animal tag

        for (String animalTag : animalTags) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("packageAlert.fxml"));
            AnchorPane alertPane = loader.load();
            packageAlertController alertController = loader.getController();
            alertController.setAnimalTag(animalTag);
            vbox.getChildren().add(alertPane);
        }
    }

    private List<String> retrieveAnimalTagsFromDatabase() {
        List<String> animalTags = new ArrayList<>();
        Connection conn = LoginFormController.connectDB();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT animal_tag FROM prescription");

            // Iterate over the result set and add the animal tags to the list
            while (rs.next()) {
                animalTags.add(rs.getString("animal_tag"));
            }
            // Close the result set and statement
            rs.close();
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return animalTags;

         }

    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
