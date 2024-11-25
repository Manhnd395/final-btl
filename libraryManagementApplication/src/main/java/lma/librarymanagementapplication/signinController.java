package lma.librarymanagementapplication;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class signinController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button close;

    @FXML
    private Button hide;

    private HostServices hostServices;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    /**
     * Đăng nhập thư viện.
     */

    public String login() throws IOException, SQLException {
        String fullname = null;
        connect = Database.connectDB();
        String verifyLogin = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            prepare = connect.prepareStatement(verifyLogin);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();

            Alert alert;

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                return null;
            } else {
                if (result.next()) {

                    userClass loggedInUser = new userClass(
                            result.getString("fullname"),
                            result.getString("email"),
                            result.getString("MSV"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("birthdate"),
                            result.getString("gender")
                    );

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Đăng nhập");
                    alert.setHeaderText(null);
                    alert.setContentText("Đăng nhập thành công");
                    alert.showAndWait();

                    login.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(libraryMA.class.getResource("dashboardoutside.fxml"));

                    Parent root = fxmlLoader.load();

                    dashboardController db = fxmlLoader.getController();

                    db.setCurrentUser(loggedInUser);
                    db.setFullname(loggedInUser.getFullname());
                    db.loadUserBooks();
                    db.setHostServices(hostServices);
                    db.borrowedBooksTable();

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();


                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Sai tên đăng nhập hoặc mật khẩu!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullname;
    }

    @FXML
    private Button signup;

    public void signup () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryMA.class.getResource("signup.fxml"));

        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void hide() {
        Stage stage = (Stage) hide.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    private AnchorPane backgroundSigninPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
