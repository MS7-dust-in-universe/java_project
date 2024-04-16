package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Parameter;

public class scenecontroller {
    private Stage primaryStage;

    public void backbyaslide(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../detailofanimal.fxml"));
        Parent root = loader.load();
        scenecontroller2 controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.getScene().setRoot(root);
    }

    public void updateconfimation(ActionEvent actionEvent) {
    }

    public void getmeditwoname(ActionEvent actionEvent) {
    }

    public void getmedithreename(ActionEvent actionEvent) {
    }

    public void getmedioneamount(ActionEvent actionEvent) {
    }

    public void getmeditwoamount(ActionEvent actionEvent) {
    }

    public void getmedithreeamount(ActionEvent actionEvent) {
    }

    public void getfoodpackamount(ActionEvent actionEvent) {
    }

    public void getmediname(ActionEvent actionEvent) {
    }

    public void setPrimayStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
