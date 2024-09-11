import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class multiplePanelsController {
    public AnchorPane main;
    public AnchorPane screen1;
    public AnchorPane screen2;
    public VBox vbox;
  


    @FXML
    private TableView<Prescription> tableView;

    @FXML
    private TableColumn<Prescription, String> animalTagColumn;

    @FXML
    private TableColumn<Prescription, String> foodNameColumn;

    @FXML
    private TableColumn<Prescription, String> descriptionColumn;


    @FXML
    private TextField deleteTextField;
    

    private ObservableList<Prescription> prescriptions = FXCollections.observableArrayList();

    private Stage stage;
    private Scene scene;


    public void messageOnAction(ActionEvent actionEvent) throws IOException {
       // actionEvent.consume();

        main.setVisible(true);
        screen1.setVisible(true);
        screen2.setVisible(false);
        List<String> animalTags = retrieveAnimalTagsFromDatabase();


        vbox.getChildren().clear();
        // Create a separate alert GUI for each animal tag
       /* ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setStyle("-fx-background-color:  #9BA88F;");

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        */


        for (String animalTag : animalTags) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("alert.fxml"));
            AnchorPane alertPane = loader.load();
            alertController alertController = loader.getController();
            alertController.setAnimalTag(animalTag);
            vbox.getChildren().add(alertPane);
        }
       // screen1.getChildren().add(scrollPane);
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

    public void recordOnAction(ActionEvent actionEvent) {
        main.setVisible(true);
        screen1.setVisible(false);
        screen2.setVisible(true);
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Desk3.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void initialize() {
        // Set up the table columns
        animalTagColumn.setCellValueFactory(new PropertyValueFactory<>("animalTag"));
        foodNameColumn.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));

        // Load data into the table
        if (tableView.getItems().isEmpty()) {
            loadData();
        }

        // Set the table's items
        tableView.setItems(prescriptions);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Prescription prescription = tableView.getSelectionModel().getSelectedItem();
                if (prescription != null) {
                    deleteTextField.setText(prescription.getAnimalTag());
                }
            }
        });

    }

    // Load data from the database
    private void loadData() {
        try ( Connection conn = LoginFormController.connectDB();
              Statement statement = conn.createStatement();
              ResultSet resultSet = statement.executeQuery("SELECT animal_tag, food_name, description  FROM prescription ")) {

            while (resultSet.next()) {
                String animalTag = resultSet.getString("animal_tag");
                String foodName = resultSet.getString("food_name");
                String desc = resultSet.getString("description");
                prescriptions.add(new Prescription(animalTag, foodName, desc));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void deleteOnAction (ActionEvent actionEvent){
            try {
                String prescriptionId = deleteTextField.getText();

                // Delete from prescription table
                Connection conn = LoginFormController.connectDB();
                PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM prescription WHERE animal_tag =?");
                preparedStatement.setString(1, prescriptionId);
                preparedStatement.executeUpdate();

                tableView.getItems().removeIf(prescription -> prescription.getAnimalTag().equals(prescriptionId));
                deleteTextField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public static class Prescription {
            private String animalTag;
            private String foodName;
            private String desc;


            public Prescription(String animalTag, String foodName, String desc) {
                this.animalTag = animalTag;
                this.foodName = foodName;
                this.desc = desc;
            }

            public String getAnimalTag() {
                return animalTag;
            }

            public String getFoodName() {
                return foodName;
            }

            public String getDesc() { return desc; }

        }


    }
