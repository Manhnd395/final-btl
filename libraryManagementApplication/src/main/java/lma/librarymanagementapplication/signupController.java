package lma.librarymanagementapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Hàm đăng ký thư viện.
 */
public class signupController implements Initializable {

    @FXML
    private Label message;

    @FXML
    private PasswordField signup_password;

    @FXML
    private PasswordField signup_passwordAgain;

    @FXML
    private Label confirmGender;

    @FXML
    private TextField signup_fullname;

    @FXML
    private TextField signup_username;

    @FXML
    private TextField signup_email;

    @FXML
    private Button signup_form;

    @FXML
    private ChoiceBox<Integer> signup_day;

    @FXML
    private ChoiceBox<Integer> signup_month;

    @FXML
    private ChoiceBox<Integer> signup_year;

    @FXML
    private CheckBox signup_male;

    @FXML
    private CheckBox signup_female;

    @FXML
    private TextField signup_MSV;

    /**
     * Hàm đăng ký chính.
     */
    @FXML
    public void signup_form() throws SQLException {
        Connection connectDB = Database.connectDB();

        if (signup_fullname.getText().isEmpty()
                || signup_username.getText().isEmpty()
                || signup_MSV.getText().isEmpty()
                || signup_email.getText().isEmpty()
                || signup_password.getText().isEmpty()
                || signup_day.getValue() == null
                || signup_month.getValue() == null
                || signup_year.getValue() == null) {
            message.setText("Nhập tất cả thông tin!");
            return;
        }

        String fullname = signup_fullname.getText();
        String email = signup_email.getText();
        String MSV = signup_MSV.getText();
        String username = signup_username.getText();
        String password = signup_password.getText();

        int day = signup_day.getValue();
        int month = signup_month.getValue();
        int year = signup_year.getValue();

        LocalDate birthdate = LocalDate.of(year, month, day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthdate = birthdate.format(formatter);

        String gender = "";
        if (signup_male.isSelected()) {
            gender = "Nam";
        } else if (signup_female.isSelected()) {
            gender = "Nữ";
        } else {
            confirmGender.setText("Vui lòng chọn giới tính");
            return;
        }

        String insertQuery = "INSERT INTO users(fullname, email, MSV, username, password, birthdate, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connectDB.prepareStatement(insertQuery)) {
            statement.setString(1, fullname);
            statement.setString(2, email);
            statement.setString(3, MSV);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.setString(6, formattedBirthdate);
            statement.setString(7, gender);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0 && signup_password.getText().equals(signup_passwordAgain.getText())) {

                userClass user = new userClass(fullname, email, MSV, username, password, formattedBirthdate, gender);

                personalInformationManager.setCurrentUser(user);

                signup_form.getScene().getWindow().hide();

            } else if (!signup_password.getText().equals(signup_passwordAgain.getText())) {
                message.setText("Mật khẩu không khớp");
            } else {
                message.setText("Đăng ký thất bại");
            }

        } catch (SQLException e) {
            message.setText("Email hoặc username đã tồn tại");
            e.printStackTrace();
        }
    }

    @FXML
    private AnchorPane backgroundPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String videoPath = "file:/C:/ảnh/video.mp4";
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(backgroundPane.getWidth());
        mediaView.setFitHeight(backgroundPane.getHeight());
        mediaView.setPreserveRatio(false);

        backgroundPane.getChildren().add(0, mediaView);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        dateChoices();

        signup_male.setOnAction(event -> {
            if (signup_male.isSelected()) {
                signup_female.setSelected(false);
            }
        });

        signup_female.setOnAction(event -> {
            if (signup_female.isSelected()) {
                signup_male.setSelected(false);
            }
        });

        // Cập nhật ngày dựa vào tháng và năm.
        signup_month.setOnAction(event -> updateDaysOnMonthYear());
        signup_year.setOnAction(event -> updateDaysOnMonthYear());
    }

    private void dateChoices() {
        for (int day = 1; day <= 31; day++) {
            signup_day.getItems().add(day);
        }

        for (int month = 1; month <= 12; month++) {
            signup_month.getItems().add(month);
        }

        for (int year = 1910; year <= 2024; year++) {
            signup_year.getItems().add(year);
        }
    }

    private void updateDaysOnMonthYear() {
        Integer month = signup_month.getValue();
        Integer year = signup_year.getValue();

        if (month == null || year == null) {
            return;
        }

        int days = getDays(month, year);

        /**
         * Ngày không tồn tại trong tháng thì xóa đi.
         */
        signup_day.getItems().clear();
        for (int day = 1; day <= days; day++) {
            signup_day.getItems().add(day);
        }
    }

    /**
     * Xử lí ngày theo tháng.
     * @param month
     * @param year
     * @return
     */
    private int getDays(int month, int year) {
        switch (month) {
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 31;
        }
    }

    /**
     * Kiểm tra năm nhuận.
     * @param year
     * @return
     */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

}
