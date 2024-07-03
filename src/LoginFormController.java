import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    private static Connection connect;
    public TextField Usernametxt;
    public PasswordField Userpwd;
    public Label loginmessage;
    public Button btnlogin;

    // private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Parent root;
    private Stage stage;
    private Scene scene;

    //  private Statement statement;


    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "ijse");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

            if(Usernametxt.getText().isEmpty() || Userpwd.getText().isEmpty()){
               // alert.show();
                loginmessage.setText("Invalid Login. Please Try Again");
            }else {
                String checkData = "SELECT username, password FROM login WHERE "+" username = ? AND password = ?";
                connect = connectDB();
               try {
                   prepare = connect.prepareStatement(checkData);
                   prepare.setString(1, Usernametxt.getText());
                   prepare.setString(2, Userpwd.getText());

                   result = prepare.executeQuery();

                   if (result.next()) {
                       //Load next gui

                        String username = Usernametxt.getText();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ButtonForm.fxml"));
                        root = loader.load();
                        ButtonFormController buttonformcontroller = loader.getController();
                        buttonformcontroller.displayName(username);

                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                   } else {
                      // loginmessage.setText("Invalid Login. Please Try Again");
                           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Notifications.fxml"));
                           Parent root = fxmlLoader.load();
                           stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                           scene = new Scene(root);
                           stage.setScene(scene);
                           stage.show();
                   }
               }catch(Exception e){
                   e.printStackTrace();
               }
               

            }



}




}

