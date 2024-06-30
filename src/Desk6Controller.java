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

public class Desk6Controller {
    public AnchorPane Update;
    public Button back;
    private Stage stage;
    private Scene scene;
    private Stage previousStage;
    private TransformationList<Object, Object> event;
    private InputMethodTextRun animaltagTxt;




    public void updateconfimation(ActionEvent actionEvent) {
        stage = (Stage)Update.getScene().getWindow();
        stage.close();

    }

    public void backbyaslide(ActionEvent actionEvent) throws IOException {


       
    }
}
