import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Desk6Controller {
    public AnchorPane Update;
    public Button back;
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


       
    }

    public void setAnimalTag(String animalTag) {
        this.animalTag = animalTag;
    }
}
