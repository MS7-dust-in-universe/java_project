import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.input.InputMethodTextRun;
import javafx.stage.Stage;

import java.io.IOException;

public class Desk3Controller {


    private Stage stage;
    private Scene scene;
    private InputMethodTextRun Usernametxt;
    private Labeled docTextField;
    private String username;
    private Parent root;

    public void deleteRecord(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("multiplePanels.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void createRecord(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Desk4.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName1(String username){
        docTextField.setText("Doctor");
    }
    
    public void backBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ButtonForm.fxml"));
        root = loader.load();
        ButtonFormController buttonformcontroller = loader.getController();
        buttonformcontroller.displayName1(username);

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
