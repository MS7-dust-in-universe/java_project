import javafx.animation.ParallelTransition;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ButtonFormController {
    public Button bearBtn;
    public TextField docTextField;
    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane scenePane;
    private Labeled Usernametxt;



    public void bearOnAction(javafx.event.ActionEvent actionEvent) throws IOException {

    }


    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginForm.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void displayName1(String username) {
        docTextField.setText("Doctor");
    }

    public void displayName(String username) {
        docTextField.setText(username);
    }

    public void displayName2(String username) {
        docTextField.setText("ZooKeeper");
    }

    public void dearOnAction(ActionEvent actionEvent) throws IOException {
        String username = docTextField.getText();
        if("Doctor".equals(username)){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Desk3.fxml"));
            Parent root = fxmlLoader.load();
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }else if("ZooKeeper".equals(username)){
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Desk16.fxml")));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.show();

        }else{
            System.out.println("");
        }
    }
}

