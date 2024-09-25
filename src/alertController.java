import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class alertController {
    public AnchorPane alertPane;
    public Label alertTxt;
    public Button showmoreBtn;
    public String animalTag;
    private Stage stage;
    private Scene scene;

    public void setAnimalTag(String animalTag) {
        this.animalTag = animalTag;
        alertTxt.setText("Alert " + animalTag);
    }

    public void showmoreOnAction(ActionEvent actionEvent) throws IOException {
       /* FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ALertDetails.fxml"));
        AnchorPane detailsPane = loader.load();
        AlertDetailsController detailsController = loader.getController();
        detailsController.setAnimalTag(animalTag);
        // Display the details GUI, e.g. in a new window or popup
        Stage detailsStage = new Stage();
        detailsStage.setScene(new Scene(detailsPane));
        detailsStage.show();
        */
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AlertDetails.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        AlertDetailsController detailsController = (AlertDetailsController)fxmlLoader.getController();
        detailsController.setAnimalTag(this.animalTag);
        this.stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }
}
