import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class packageAlertController {
    public Button showmoreBtn;
    public Label alertTxt;
    public AnchorPane alertPane;
    private String animalTag;

    public void setAnimalTag(String animalTag) {
        this.animalTag = animalTag;
        alertTxt.setText(animalTag + " Package Details");
    }


    public void showmoreOnAction(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PackageDetails.fxml"));
            AnchorPane detailsPane = loader.load();
            PackageDetailsController detailsController = loader.getController();
            detailsController.setAnimalTag(animalTag);
            Scene scene = new Scene(detailsPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
