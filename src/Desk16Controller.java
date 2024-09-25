import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Desk16Controller {
    public AnchorPane Viewed;
    public ListView<String> animalTagsListView;
    public TextField animaltagTxt;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private Labeled docTextField;
    private String username;

    public void submitOnAction(ActionEvent actionEvent) throws IOException {
        String enteredAnimalTag = animaltagTxt.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Desk5.fxml"));
        Parent root = loader.load();
        Desk5Controller controller = loader.getController();
        controller.setAnimalTag(enteredAnimalTag);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void displayName2(String username){
        docTextField.setText("Doctor");
    }

    public void initialize() {
        ObservableList<String> animalTags = getAnimalTags();
        animalTagsListView.setItems(animalTags);
    }

    public ObservableList<String> getAnimalTags() {
        ObservableList<String> animalTags = FXCollections.observableArrayList();

        // Create a database connection
        Connection conn = LoginFormController.connectDB();

        // Create a statement to retrieve the animal tags
        String getAnimalTagsQuery = "SELECT animal_tag FROM prescription";
        Statement stmt = null;
        ResultSet result = null;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(getAnimalTagsQuery);

            // Iterate over the result set and add the animal tags to the list
            while (result.next()) {
                String animalTag = result.getString("animal_tag");
                animalTags.add(animalTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return animalTags;
    }
}


