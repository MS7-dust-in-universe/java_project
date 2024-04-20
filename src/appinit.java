import controller.scenecontroller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class appinit extends Application {

    public static void main(String[] args) {
        //System.out.println("hi i am thuvarakan");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("databaseupdate.fxml"));
        Parent root = loader.load();
        scenecontroller controller = loader.getController();
        controller.setPrimayStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
