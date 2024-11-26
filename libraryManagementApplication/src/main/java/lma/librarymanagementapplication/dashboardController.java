package lma.librarymanagementapplication;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dashboardController implements Initializable {

    private Person currentUser;

    @FXML
    private AnchorPane introPage;

    @FXML
    private Label intro1;

    @FXML
    private Label intro2;

    @FXML
    private Label intro6;

    @FXML
    private Label intro7;

    @FXML
    private Label intro8;

    @FXML
    private ImageView intro3;

    @FXML
    private ImageView intro4;

    @FXML
    private ImageView intro5;

    /**
     * Hàm tạo animation.
     */
    public void playIntroAnimation() {

        List<Node> nodes = Arrays.asList(intro1, intro2, intro6, intro7, intro8, intro3, intro4, intro5);

        for (Node node : nodes) {

            // Hiệu ứng
            addFadeEffect(node);

            if (node != null) {
                node.setTranslateY(-200);

                TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), node);
                transition.setFromY(-200);
                transition.setToY(0);
                transition.setCycleCount(1);
                transition.setAutoReverse(false);

                transition.play();
            }
        }
    }

    /**
     * Tạo hiệu ứng mờ dần.
     * @param node
     */
    private void addFadeEffect(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.play();
    }

    @FXML
    private Label user;

    @FXML
    private Button signindashboard;

    public void setFullname(String fullname) {
        user.setText(fullname);
        user.setVisible(true);
        signindashboard.setVisible(false);
    }

    /**
     * Đăng nhập thư viện.
     * @throws IOException
     */
    public void signindashboard() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryMA.class.getResource("signin.fxml"));

        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        signindashboard.getScene().getWindow().hide();

        signinController controller = fxmlLoader.getController();
        String fullname = controller.login();
        controller.setHostServices(hostServices);

        if (fullname != null) {
            setFullname(fullname);
            user.setVisible(true);
            signindashboard.setVisible(false);
        }
        if (currentUser instanceof staffClass) {
            openPersonalPage.setVisible(false);
            openPersonalPage1.setVisible(true);
        }
    }

    @FXML
    private Button signoutdashboard;

    /**
     * Đăng xuất khỏi thư viện.
     * @throws IOException
     */
    @FXML
    public void signoutdashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryMA.class.getResource("dashboardoutside.fxml"));

        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        signoutdashboard.getScene().getWindow().hide();
    }

    @FXML
    private ChoiceBox<?> date;

    @FXML
    private HBox documents;

    @FXML
    private Button documents_btn;

    /**
     * Chuyển đổi giữa các trang bên trên.
     */
    @FXML
    public void documents_btn() {

        documents.setOnMouseExited(event -> documents.setVisible(false));
        documents_btn.setOnMouseClicked(event -> {
            documents.setVisible(true);
            mainPage.setVisible(true);
            introPage.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            inventory.setVisible(false);
            link.setVisible(false);
            addBookPage.setVisible(false);

            documents_btn.getStyleClass().add("selected");
            mainPage_btn.getStyleClass().remove("selected");
            link_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
        });

        mainPage.setStyle("-fx-background-color:linear-gradient(to bottom right, #344250, #3c6382)");
        personalPage.setStyle("-fx-background-color:transparent");
        borrowedBooks.setStyle("-fx-background-color:transparent");
        addBooks.setStyle("-fx-background-color:transparent");
        inventory.setStyle("-fx-background-color:transparent");
        introPage.setStyle("-fx-background-color:transparent");
        addBookPage.setStyle("-fx-background-color:transparent");

    }

    @FXML
    private Button intro_btn;

    public void intro_btn() {

        intro_btn.setOnMouseEntered(event -> {
            documents.setVisible(false);
            link.setVisible(false);
        });
        intro_btn.setOnMouseClicked(event -> {
            playIntroAnimation();
            introPage.setVisible(true);
            documents.setVisible(false);
            link.setVisible(false);
            mainPage.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            inventory.setVisible(false);
            addBookPage.setVisible(false);

            intro_btn.getStyleClass().add("selected");
            mainPage_btn.getStyleClass().remove("selected");
            link_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        });

        mainPage.setStyle("-fx-background-color:transparent");
        personalPage.setStyle("-fx-background-color:transparent");
        borrowedBooks.setStyle("-fx-background-color:transparent");
        addBooks.setStyle("-fx-background-color:transparent");
        inventory.setStyle("-fx-background-color:transparent");
        addBookPage.setStyle("-fx-background-color:transparent");

    }

    @FXML
    private Button link_btn;

    @FXML
    private HBox link;

    public void link_btn() {

        link_btn.setOnMouseEntered(event -> documents.setVisible(false));
        link.setOnMouseExited(event -> link.setVisible(false));
        link_btn.setOnMouseClicked(event -> {
            mainPage.setVisible(true);
            link.setVisible(true);
            documents.setVisible(false);
            introPage.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            inventory.setVisible(false);
            addBookPage.setVisible(false);

            link_btn.getStyleClass().add("selected");
            mainPage_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        });

        mainPage.setStyle("-fx-background-color:linear-gradient(to bottom right, #344250, #3c6382)");
        personalPage.setStyle("-fx-background-color:transparent");
        borrowedBooks.setStyle("-fx-background-color:transparent");
        addBooks.setStyle("-fx-background-color:transparent");
        inventory.setStyle("-fx-background-color:transparent");
        introPage.setStyle("-fx-background-color:transparent");
        addBookPage.setStyle("-fx-background-color:transparent");

    }

    private final String url1 = "https://uet.vnu.edu.vn/";

    public void openLink() {

        try {
            Desktop.getDesktop().browse(new URI(url1));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private final String url2 = "file:///C:/Users/PC/Downloads/sach-giao-khoa-toan-12-tap-1-ket-noi-tri-thuc-voi-cuoc-song.pdf";

    public void watchFull() {

        try {
            Desktop.getDesktop().browse(new URI(url2));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private Button mainPage_btn;

    public void mainPage_btn() {

        mainPage_btn.setOnMouseEntered(event -> {
            documents.setVisible(false);
            link.setVisible(false);
        });
        mainPage_btn.setOnMouseClicked(event -> {
            mainPage.setVisible(true);
            introPage.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            inventory.setVisible(false);
            addBookPage.setVisible(false);

            mainPage_btn.getStyleClass().add("selected");
            link_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        });

        mainPage.setStyle("-fx-background-color:linear-gradient(to bottom right, #344250, #3c6382)");
        personalPage.setStyle("-fx-background-color:transparent");
        borrowedBooks.setStyle("-fx-background-color:transparent");
        addBooks.setStyle("-fx-background-color:transparent");
        inventory.setStyle("-fx-background-color:transparent");
        introPage.setStyle("-fx-background-color:transparent");
        addBookPage.setStyle("-fx-background-color:transparent");

    }

    @FXML
    private Button openPersonalPage;

    @FXML
    private Button openPersonalPage1;

    @FXML
    private Button openBorrowedBooks;

    @FXML
    private Button openAddBooks;

    @FXML
    private Button openInventory;

    @FXML
    private Button openNewBooks;

    @FXML
    private AnchorPane mainPage;

    @FXML
    private AnchorPane personalPage;

    @FXML
    private AnchorPane borrowedBooks;

    @FXML
    private AnchorPane addBooks;

    @FXML
    private AnchorPane inventory;

    @FXML
    private AnchorPane addBookPage;

    @FXML
    private AnchorPane manageUserPage;

    /**
     * Chuyển đổi giữa các trang bên trái.
     * @param event
     */
    @FXML
    public void switchPage(ActionEvent event) {
            if(currentUser instanceof userClass) {
                if (currentUser instanceof staffClass) {
                    try {
                        showMangageUserTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    manageUserPage.setVisible(true);
                    personalPage.setVisible(false);
                    mainPage.setVisible(false);
                    addBooks.setVisible(false);
                    borrowedBooks.setVisible(false);
                    inventory.setVisible(false);
                    introPage.setVisible(false);
                    addBookPage.setVisible(false);

                    manageUserPage.setStyle("-fx-background-color:linear-gradient(from 0.0% 24.6914% to 100.0% 100.0%, #5353af 0.0%, #d3d3ff 100.0%)");
                    mainPage.setStyle("-fx-background-color:transparent");
                    personalPage.setStyle("-fx-background-color:transparent");
                    addBooks.setStyle("-fx-background-color:transparent");
                    inventory.setStyle("-fx-background-color:transparent");
                    introPage.setStyle("-fx-background-color:transparent");
                    addBookPage.setStyle("-fx-background-color:transparent");
                    borrowedBooks.setStyle("-fx-background-color:transparent");
                } else {
                    personalPage.setVisible(true);
                    manageUserPage.setVisible(false);
                    mainPage.setVisible(false);
                    addBooks.setVisible(false);
                    borrowedBooks.setVisible(false);
                    inventory.setVisible(false);
                    introPage.setVisible(false);
                    addBookPage.setVisible(false);

                    personalPage.setStyle("-fx-background-color:linear-gradient(from 0.0% 24.6914% to 100.0% 100.0%, #af5353 0.0%, #ffd3d3 100.0%)");
                    mainPage.setStyle("-fx-background-color:transparent");
                    borrowedBooks.setStyle("-fx-background-color:transparent");
                    addBooks.setStyle("-fx-background-color:transparent");
                    inventory.setStyle("-fx-background-color:transparent");
                    introPage.setStyle("-fx-background-color:transparent");
                    addBookPage.setStyle("-fx-background-color:transparent");
                    manageUserPage.setStyle("-fx-background-color:transparent");

                    mainPage_btn.getStyleClass().remove("selected");
                    link_btn.getStyleClass().remove("selected");
                    intro_btn.getStyleClass().remove("selected");
                    documents_btn.getStyleClass().remove("selected");
                }
        }

        else if (event.getSource() == openBorrowedBooks) {
            borrowedBooks.setVisible(true);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            mainPage.setVisible(false);
            addBooks.setVisible(false);
            inventory.setVisible(false);
            introPage.setVisible(false);
            addBookPage.setVisible(false);

            borrowedBooks.setStyle("-fx-background-color: #fff");
            mainPage.setStyle("-fx-background-color:transparent");
            personalPage.setStyle("-fx-background-color:transparent");
            addBooks.setStyle("-fx-background-color:transparent");
            inventory.setStyle("-fx-background-color:transparent");
            introPage.setStyle("-fx-background-color:transparent");
            addBookPage.setStyle("-fx-background-color:transparent");

            mainPage_btn.getStyleClass().remove("selected");
            link_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        }

        else if (event.getSource() == openAddBooks) {
            addBooks.setVisible(true);
            borrowedBooks.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            mainPage.setVisible(false);
            inventory.setVisible(false);
            introPage.setVisible(false);
            addBookPage.setVisible(false);

            addBooks.setStyle("-fx-background-color:lightblue");
            borrowedBooks.setStyle("-fx-background-color:transparent");
            mainPage.setStyle("-fx-background-color:transparent");
            personalPage.setStyle("-fx-background-color:transparent");
            inventory.setStyle("-fx-background-color:transparent");
            introPage.setStyle("-fx-background-color:transparent");
            addBookPage.setStyle("-fx-background-color:transparent");

            mainPage_btn.getStyleClass().remove("selected");
            link_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        }

        else if (event.getSource() == openInventory) {
            inventory.setVisible(true);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            mainPage.setVisible(false);
            introPage.setVisible(false);
            addBookPage.setVisible(false);

            inventory.setStyle("-fx-background-color:lightyellow");
            addBooks.setStyle("-fx-background-color:transparent");
            borrowedBooks.setStyle("-fx-background-color:transparent");
            mainPage.setStyle("-fx-background-color:transparent");
            personalPage.setStyle("-fx-background-color:transparent");
            introPage.setStyle("-fx-background-color:transparent");
            addBookPage.setStyle("-fx-background-color:transparent");

            mainPage_btn.getStyleClass().remove("selected");
            link_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        }

        else if (event.getSource() == openNewBooks) {
            addBookPage.setVisible(true);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            mainPage.setVisible(false);
            introPage.setVisible(false);
            inventory.setVisible(false);

            addBookPage.setStyle("-fx-background-color:white");
            inventory.setStyle("-fx-background-color:transparent");
            addBooks.setStyle("-fx-background-color:transparent");
            borrowedBooks.setStyle("-fx-background-color:transparent");
            mainPage.setStyle("-fx-background-color:transparent");
            personalPage.setStyle("-fx-background-color:transparent");
            introPage.setStyle("-fx-background-color:transparent");

            mainPage_btn.getStyleClass().remove("selected");
            link_btn.getStyleClass().remove("selected");
            intro_btn.getStyleClass().remove("selected");
            documents_btn.getStyleClass().remove("selected");
        }
    }

    @FXML
    private ChoiceBox<Integer> personal_date;

    @FXML
    private TextField personal_email;

    @FXML
    private CheckBox personal_female;

    @FXML
    private TextField personal_fullname;

    @FXML
    private Button personal_imageInsert;

    @FXML
    private ImageView personal_imageView;

    @FXML
    private CheckBox personal_male;

    @FXML
    private ChoiceBox<Integer> personal_month;

    @FXML
    private TextField personal_password;

    @FXML
    private TextField personal_MSV;

    @FXML
    private TextField personal_username;

    @FXML
    private Button personal_update;

    @FXML
    private Label personal_message;

    @FXML
    private ChoiceBox<Integer> personal_year;

    public void setCurrentUser(Person user) {
        this.currentUser = user;
        initializePersonalInfo();
    }

    /**
     * Trang cá nhân.
     */
    @FXML
    private void initializePersonalInfo() {

        if (currentUser != null) {
            personal_fullname.setText(currentUser.getFullname());
            personal_MSV.setText(currentUser.getMSV());
            personal_email.setText(currentUser.getEmail());
            personal_username.setText(currentUser.getUsername());
            personal_password.setText(currentUser.getPassword());


            String birthdateString = currentUser.getBirthdate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthdate = LocalDate.parse(birthdateString, formatter);

            personal_date.setValue(birthdate.getDayOfMonth());
            personal_month.setValue(birthdate.getMonthValue());
            personal_year.setValue(birthdate.getYear());

            if (currentUser.getGender().equals("Nam")) {
                personal_male.setSelected(true);
            } else {
                personal_female.setSelected(true);
            }
        }

        for (int day = 1; day <= 31; day++) {
            personal_date.getItems().add(day);
        }

        for (int month = 1; month <= 12; month++) {
            personal_month.getItems().add(month);
        }

        for (int year = 1920; year <= 2024; year++) {
            personal_year.getItems().add(year);
        }

        personal_male.setOnAction(event -> {
            if (personal_male.isSelected()) {
                personal_female.setSelected(false);
            }
        });

        personal_female.setOnAction(event -> {
            if (personal_female.isSelected()) {
                personal_male.setSelected(false);
            }
        });

        personal_update.setOnAction(event -> {
            try {
                updatePersonalInfo();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Cập nhật thông tin cá nhân.
     */
    public void updatePersonalInfo() throws SQLException {
        Connection connectDB = Database.connectDB();

        if (personal_fullname.getText().isEmpty() || personal_email.getText().isEmpty()
                || personal_MSV.getText().isEmpty()
                || personal_password.getText().isEmpty()
                || personal_date.getValue() == null
                || personal_month.getValue() == null
                || personal_year.getValue() == null) {
            personal_message.setText("Nhập đầy đủ thông tin giúp mình");
            return;
        }

        String fullname = personal_fullname.getText();
        String email = personal_email.getText();
        String MSV = personal_MSV.getText();
        String password = personal_password.getText();

        int day = personal_date.getValue();
        int month = personal_month.getValue();
        int year = personal_year.getValue();

        LocalDate birthdate = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthdate = birthdate.format(formatter);

        String gender = "";
        if (personal_male.isSelected()) {
            gender = "Nam";
        } else if (personal_female.isSelected()) {
            gender = "Nữ";
        } else {
            personal_message.setText("Chọn giới tính ik");
            return;
        }

        String username = currentUser.getUsername();

        String updateQuery = "UPDATE users SET fullname = ?, email = ?, MSV = ?, password = ?, birthdate = ?, gender = ? WHERE username = ?";

        try (PreparedStatement statement = connectDB.prepareStatement(updateQuery)) {
            statement.setString(1, fullname);
            statement.setString(2, email);
            statement.setString(3, MSV);
            statement.setString(4, password);
            statement.setString(5, formattedBirthdate);
            statement.setString(6, gender);
            statement.setString(7, username);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                personal_message.setText("Cập nhật thành công");

                Person currentUser = personalInformationManager.getCurrentUser();
                if (currentUser != null) {
                    currentUser.setFullname(fullname);
                    currentUser.setEmail(email);
                    currentUser.setMSV(MSV);
                    currentUser.setPassword(password);
                    currentUser.setBirthdate(formattedBirthdate);
                    currentUser.setGender(gender);

                    personalInformationManager.setCurrentUser((userClass)currentUser);
                }

            } else {
                personal_message.setText("Cập nhật thất bại");
            }

        } catch (Exception e) {
            personal_message.setText("Lỗi gì đó rồi");
            e.printStackTrace();
        }
    }

    @FXML
    private Label book_title;

    @FXML
    private ImageView addBooks_image;

    @FXML
    private AnchorPane addBooks_form;

    @FXML
    private TableView<addBooksClass> addBooks_tableView;

    @FXML
    private TableColumn<addBooksClass, String> col_title;

    @FXML
    private TableColumn<addBooksClass, String> col_author;

    @FXML
    private TableColumn<addBooksClass, String> col_previewLink;

    @FXML
    private TableColumn<addBooksClass, String> col_ID;

    @FXML
    private TableColumn<addBooksClass, String> col_publishedDate;

    @FXML
    private TableColumn<addBooksClass, String> col_comment;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    /**
     * Thêm sách.
     * @return
     */
    public ObservableList<addBooksClass> dataList() throws SQLException {
        ObservableList<addBooksClass> listBooks = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";
        connect = Database.connectDB();

        try {
            addBooksClass aBooks;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                String publishedDate = result.getString("PublishedDate");
                String formattedDate = "Unknown";

                if (publishedDate != null && !publishedDate.isEmpty()) {
                    try {
                        if (publishedDate.matches("\\d{4}")) {
                            // yyyy
                            formattedDate = LocalDate.parse(publishedDate + "-01-01").format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        } else if (publishedDate.matches("\\d{4}-\\d{2}")) {
                            // yyyy-MM
                            YearMonth yearMonth = YearMonth.parse(publishedDate, DateTimeFormatter.ofPattern("yyyy-MM"));
                            formattedDate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        } else if (publishedDate.matches("\\d{2}-\\d{4}")) {
                            // MM-yyyy
                            YearMonth yearMonth = YearMonth.parse(publishedDate, DateTimeFormatter.ofPattern("MM-yyyy"));
                            formattedDate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        } else if (publishedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            // yyyy-MM-dd
                            formattedDate = LocalDate.parse(publishedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        } else if (publishedDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                            // dd-MM-yyyy
                            formattedDate = LocalDate.parse(publishedDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Lỗi phân tích ngày: " + publishedDate);
                    }
                }

                aBooks = new addBooksClass(
                        result.getString("Title"),
                        result.getString("Author"),
                        formattedDate,
                        result.getInt("ID"),
                        result.getString("Image"),
                        result.getString("PreviewLink"),
                        result.getString("Comments")
                );

                listBooks.add(aBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listBooks;
    }

    private ObservableList<addBooksClass> listBook;

    /**
     * Lấy thông tin sách.
     */
    public void showAddBooks() throws SQLException {

        listBook = dataList();

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_previewLink.setCellValueFactory(new PropertyValueFactory<>("previewLink"));
        col_publishedDate.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("comments"));

        addBooks_tableView.setItems(listBook);
    }

    @FXML
    private ListView<addBooksClass> newBooks;

    /**
     * Hiển thị sách mới ở trang chủ.
     */
    public void displayNewAddBooks() throws SQLException {

        String sql = "SELECT * FROM books ORDER BY ID DESC LIMIT 5";
        Connection connect = Database.connectDB();

        try (PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {
            newBooks.setCellFactory(listView -> new ListCell<>() {
                private ImageView imageView1 = new ImageView();
                private Button detailButton1 = new Button("Xem chi tiết");
                private Button takeButton1 = new Button("Mượn sách");
                private HBox hBox1 = new HBox(5);

                {
                    hBox1.getChildren().addAll(imageView1, detailButton1, takeButton1);
                }

                @Override
                protected void updateItem(addBooksClass book, boolean empty) {
                    super.updateItem(book, empty);

                    if (empty || book == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(book.toString());
                        setGraphic(hBox1);

                        if (book.getImage() != null && !book.getImage().isEmpty()) {
                            Image image = new Image(book.getImage(), 50, 75, true, true);
                            imageView1.setImage(image);
                        } else {
                            imageView1.setImage(null);
                        }

                        detailButton1.setOnAction(e -> {
                            if (book.getPreviewLink() != null && !book.getPreviewLink().isEmpty()) {
                                openWebPage(book.getPreviewLink());
                            }
                        });

                        takeButton1.setOnAction(e -> {
                            try {
                                takeButton1(book);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                    }
                }
            });

            while (result.next()) {
                String title = result.getString("Title");
                String author = result.getString("Author");
                String publishedDate = result.getString("PublishedDate");
                int ID = result.getInt("ID");
                String image = result.getString("Image");
                String previewLink = result.getString("PreviewLink");
                String comments = result.getString("Comments");

                addBooksClass book = new addBooksClass(title, author, publishedDate, ID, image, previewLink, comments);
                newBooks.getItems().add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy sách mới ở trang chủ.
     * @param selectedBook
     */
    public void takeButton1(addBooksClass selectedBook) throws SQLException {
        if (currentUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Yêu cầu đăng nhập");
            alert.setHeaderText(null);
            alert.setContentText("Bạn cần đăng nhập để lấy sách.");
            alert.showAndWait();
            return;
        }

        LocalDate currentBorrowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String sqlCheck = "SELECT * FROM users_books WHERE user_MSV = ? AND book_ID = ?";
        String sqlUpdate = "UPDATE users_books SET borrow_date = ?, return_date = NULL " +
                "WHERE user_MSV = ? AND book_ID = ? AND return_date IS NOT NULL";
        String sqlInsert = "INSERT INTO users_books (user_MSV, book_ID, borrow_date) VALUES (?, ?, ?)";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sqlCheck);
            prepare.setString(1, currentUser.getMSV());
            prepare.setInt(2, selectedBook.getID());
            result = prepare.executeQuery();

            if (result.next()) {
                prepare = connect.prepareStatement(sqlUpdate);
                prepare.setString(1, formatter.format(currentBorrowDate));
                prepare.setString(2, currentUser.getMSV());
                prepare.setInt(3, selectedBook.getID());
                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sách đã được mượn");
                    alert.setHeaderText(null);
                    alert.setContentText("Sách này đã được mượn bởi người dùng.");
                    alert.showAndWait();
                    return;
                }
            } else {
                prepare = connect.prepareStatement(sqlInsert);
                prepare.setString(1, currentUser.getMSV());
                prepare.setInt(2, selectedBook.getID());
                prepare.setString(3, formatter.format(currentBorrowDate));
                prepare.executeUpdate();
            }

            // Cập nhật giao diện
            takenListBooks.add(selectedBook);

            String uri = selectedBook.getImage();
            image = new Image(uri, 170, 202, false, true);
            addBooks_image1.setImage(image);
            bookNameTaken.setText(selectedBook.getTitle());
            authorTaken.setText(selectedBook.getAuthor());
            dayTaken.setText(currentBorrowDate.format(formatter));

            col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_previewLink1.setCellValueFactory(new PropertyValueFactory<>("previewLink"));
            col_publishedDate1.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
            col_ID1.setCellValueFactory(new PropertyValueFactory<>("ID"));

            addBooks_tableView1.setItems(takenListBooks);

            col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author2.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_publishedDate2.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
            col_borrowDate.setCellValueFactory(cellData -> new SimpleStringProperty(dayTaken.getText()));
            col_returnDate.setCellValueFactory(cellData -> new SimpleStringProperty(""));
            col_ID2.setCellValueFactory(new PropertyValueFactory<>("ID"));

            inventory.setVisible(true);
            addBooks.setVisible(false);
            borrowedBooks.setVisible(false);
            personalPage.setVisible(false);
            manageUserPage.setVisible(false);
            mainPage.setVisible(false);
            introPage.setVisible(false);

            inventory.setStyle("-fx-background-color:lightyellow");
            addBooks.setStyle("-fx-background-color:transparent");
            borrowedBooks.setStyle("-fx-background-color:transparent");
            mainPage.setStyle("-fx-background-color:transparent");
            personalPage.setStyle("-fx-background-color:transparent");
            introPage.setStyle("-fx-background-color:transparent");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Image image;

    /**
     * Hiển thị sách.
     */
    public void selectShowBooks() {

        addBooksClass bookData = addBooks_tableView.getSelectionModel().getSelectedItem();

        if (bookData == null) {
            return;
        }

        book_title.setText(bookData.getTitle());

        String uri = bookData.getImage();

        image = new Image(uri, 170, 202, false, true);

        addBooks_image.setImage(image);
    }

    @FXML
    private ImageView addBooks_image1;

    @FXML
    private TableView<addBooksClass> addBooks_tableView1;

    @FXML
    private TableColumn<addBooksClass, String> col_title1;

    @FXML
    private TableColumn<addBooksClass, String> col_author1;

    @FXML
    private TableColumn<addBooksClass, String> col_previewLink1;

    @FXML
    private TableColumn<addBooksClass, String> col_ID1;

    @FXML
    private TableColumn<addBooksClass, String> col_publishedDate1;

    @FXML
    private Label bookNameTaken;

    @FXML
    private Label authorTaken;

    @FXML
    private Label dayTaken;

    @FXML
    private Button takeBook;

    @FXML
    private TableView<borrowedBooksClass> addBooks_tableView2;

    @FXML
    private TableColumn<addBooksClass, String> col_title2;

    @FXML
    private TableColumn<addBooksClass, String> col_author2;

    @FXML
    private TableColumn<addBooksClass, String> col_ID2;

    @FXML
    private TableColumn<addBooksClass, String> col_publishedDate2;

    @FXML
    private TableColumn<addBooksClass, String> col_borrowDate;

    @FXML
    private TableColumn<addBooksClass, String> col_returnDate;

    @FXML
    private TableView<userClass> manageUserTable;

    @FXML
    private TableColumn<userClass, String> col_MSV;

    @FXML
    private TableColumn<userClass, String> col_fullName;

    @FXML
    private TableColumn<userClass, String> col_username;

    @FXML
    private TableColumn<userClass, String> col_password;

    @FXML
    private TableColumn<userClass, String> col_email;

    @FXML
    private TableColumn<userClass, String> col_birthDate;

    @FXML
    private TableColumn<userClass, String> col_gender;

    ObservableList<addBooksClass> takenListBooks = FXCollections.observableArrayList();

    /**
     * Cập nhật sách đã mượn của người dùng vào tủ sách cá nhân.
     */
    public void loadUserBooks() throws SQLException {
        String sql = "SELECT b.* FROM books b INNER JOIN users_books ub ON b.ID = ub.book_ID " +
                "WHERE ub.user_MSV = ? AND ub.return_date IS NULL";
        connect = Database.connectDB();

        takenListBooks.clear();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, currentUser.getMSV());
            result = prepare.executeQuery();

            String formattedDate = null;

            while (result.next()) {

                String publishedDateString = result.getString("PublishedDate");

                if (publishedDateString != null && !publishedDateString.isEmpty()) {
                    LocalDate publishedDate = LocalDate.parse(publishedDateString);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    formattedDate = publishedDate.format(formatter);
                }

                addBooksClass aBooks = new addBooksClass(
                        result.getString("Title"),
                        result.getString("Author"),
                        formattedDate,
                        result.getInt("ID"),
                        result.getString("Image"),
                        result.getString("PreviewLink"),
                        result.getString("Comments")
                );
                takenListBooks.add(aBooks);
            }

            col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_previewLink1.setCellValueFactory(new PropertyValueFactory<>("previewLink"));
            col_publishedDate1.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
            col_ID1.setCellValueFactory(new PropertyValueFactory<>("ID"));

            addBooks_tableView1.setItems(takenListBooks);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Mượn sách.
     * @param event
     */
    public void takeBook(ActionEvent event) throws SQLException {

        if (currentUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Yêu cầu đăng nhập");
            alert.setHeaderText(null);
            alert.setContentText("Bạn cần đăng nhập để lấy sách.");
            alert.showAndWait();
            return;
        }

        addBooksClass selectedBook = addBooks_tableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sách không tồn tại trong thư viện");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một sách để lấy thông tin.");
            alert.showAndWait();
            return;
        }

        LocalDate currentBorrowDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String sqlCheck = "SELECT * FROM users_books WHERE user_MSV = ? AND book_ID = ?";
        String sqlUpdate = "UPDATE users_books SET borrow_date = ?, return_date = NULL " +
                "WHERE user_MSV = ? AND book_ID = ? AND return_date IS NOT NULL";
        String sqlInsert = "INSERT INTO users_books (user_MSV, book_ID, borrow_date) VALUES (?, ?, ?)";
        connect = Database.connectDB();

        try {
            // Kiểm tra xem có trùng không
            prepare = connect.prepareStatement(sqlCheck);
            prepare.setString(1, currentUser.getMSV());
            prepare.setInt(2, selectedBook.getID());
            result = prepare.executeQuery();

            if (result.next()) {
                // Nếu trả sách thì cập nhật return_date về null
                prepare = connect.prepareStatement(sqlUpdate);
                prepare.setString(1, formatter.format(currentBorrowDate));
                prepare.setString(2, currentUser.getMSV());
                prepare.setInt(3, selectedBook.getID());
                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sách đã được mượn");
                    alert.setHeaderText(null);
                    alert.setContentText("Sách này đã được mượn bởi người dùng.");
                    alert.showAndWait();
                    return;
                }
            } else {
                // Thêm sách vào tủ cá nhân
                prepare = connect.prepareStatement(sqlInsert);
                prepare.setString(1, currentUser.getMSV());
                prepare.setInt(2, selectedBook.getID());
                prepare.setString(3, formatter.format(currentBorrowDate));
                prepare.executeUpdate();
            }

            takenListBooks.add(selectedBook);

            String uri = selectedBook.getImage();
            image = new Image(uri, 170, 202, false, true);
            addBooks_image1.setImage(image);
            bookNameTaken.setText(selectedBook.getTitle());
            authorTaken.setText(selectedBook.getAuthor());
            dayTaken.setText(currentBorrowDate.format(formatter));

            col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author1.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_previewLink1.setCellValueFactory(new PropertyValueFactory<>("previewLink"));
            col_publishedDate1.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
            col_ID1.setCellValueFactory(new PropertyValueFactory<>("ID"));

            addBooks_tableView1.setItems(takenListBooks);

            col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author2.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_publishedDate2.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
            col_borrowDate.setCellValueFactory(cellData -> new SimpleStringProperty(dayTaken.getText()));
            col_returnDate.setCellValueFactory(cellData -> new SimpleStringProperty(LocalDate.now().format(formatter)));
            col_ID2.setCellValueFactory(new PropertyValueFactory<>("ID"));

            borrowedBooksTable();

            if (event.getSource() == takeBook) {
                inventory.setVisible(true);
                addBooks.setVisible(false);
                borrowedBooks.setVisible(false);
                personalPage.setVisible(false);
                manageUserPage.setVisible(false);
                mainPage.setVisible(false);
                introPage.setVisible(false);

                inventory.setStyle("-fx-background-color:lightyellow");
                addBooks.setStyle("-fx-background-color:transparent");
                borrowedBooks.setStyle("-fx-background-color:transparent");
                mainPage.setStyle("-fx-background-color:transparent");
                personalPage.setStyle("-fx-background-color:transparent");
                introPage.setStyle("-fx-background-color:transparent");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private Label cmtMessage;

    @FXML
    private TextField comment;

    /**
     * Bình luận sách.
     */
    @FXML
    public void saveComment_btn(ActionEvent event) throws SQLException {
        if (comment == null || comment.getText().trim().isEmpty()) {
            cmtMessage.setText("Vui lòng bình luận!");
            return;
        }

        addBooksClass selectedBook = addBooks_tableView1.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            int bookID = selectedBook.getID();
            String newComment = comment.getText().trim();

            String sql = "UPDATE books SET Comments = ? WHERE ID = ?";
            connect = Database.connectDB();

            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, newComment);
                prepare.setInt(2, bookID);

                int rowsUpdated = prepare.executeUpdate();
                if (rowsUpdated > 0) {
                    cmtMessage.setText("Bình luận đã được cập nhật!");
                    selectedBook.setComments(newComment);
                    addBooks_tableView1.refresh();
                } else {
                    cmtMessage.setText("Không thể cập nhật bình luận!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (prepare != null) prepare.close();
                    if (connect != null) connect.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            cmtMessage.setText("Chọn sách để bình luận!");
        }
    }

    /**
     * Hiển thị thông tin sách trong tủ cá nhân.
     */
    @FXML
    private void displaySelectedBook() {

        addBooksClass selectedBook = addBooks_tableView1.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Hiển thị thông tin sách đã chọn từ bảng
            bookNameTaken.setText(selectedBook.getTitle());
            authorTaken.setText(selectedBook.getAuthor());
            dayTaken.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            comment.setText(selectedBook.getComments());

            String uri = selectedBook.getImage();
            image = new Image(uri, 170, 202, false, true);
            addBooks_image1.setImage(image);
        } else {
            // Xóa thông tin nếu không có sách được chọn
            bookNameTaken.setText("");
            authorTaken.setText("");
            dayTaken.setText("");
            comment.setText("");
            addBooks_image1.setImage(null);
        }
    }

    /**
     * Xem chi tiết sách đang mượn.
     */
    public void watchInfo() throws URISyntaxException, IOException {
        addBooksClass selectedBook = addBooks_tableView1.getSelectionModel().getSelectedItem();
        if ((selectedBook != null) && (selectedBook.getPreviewLink() != null)) {
            Desktop.getDesktop().browse(new URI(selectedBook.getPreviewLink()));
        }
    }

    /**
     * Trả sách.
     * @param event
     */
    public void returnBook(ActionEvent event) throws SQLException {
        addBooksClass selectedBook = addBooks_tableView1.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chọn sách để trả");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một sách để trả.");
            alert.showAndWait();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String returnDate = LocalDate.now().format(formatter);

        String sqlUpdate = "UPDATE users_books SET return_date = ? WHERE user_MSV = ? AND book_ID = ? AND return_date IS NULL";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sqlUpdate);
            prepare.setString(1, returnDate);
            prepare.setString(2, currentUser.getMSV());
            prepare.setInt(3, selectedBook.getID());
            int rowsUpdated = prepare.executeUpdate();

            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Trả sách thành công");
                alert.setHeaderText(null);
                alert.setContentText("Sách đã được trả thành công.");
                alert.showAndWait();

                bookNameTaken.setText("");
                authorTaken.setText("");
                dayTaken.setText("");
                addBooks_image1.setImage(null);

                addBooks_tableView1.getItems().remove(selectedBook);
                borrowedBooksTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi trả sách");
                alert.setHeaderText(null);
                alert.setContentText("Sách này không được ghi nhận là đã mượn hoặc đã được trả.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Sách đã từng mượn.
     */
    public void borrowedBooksTable() throws SQLException {
        String sql = "SELECT b.ID, b.Title, b.Author, b.PublishedDate, ub.borrow_date, ub.return_date " +
                "FROM books b "
                + "JOIN users_books ub ON b.ID = ub.book_ID "
                + "WHERE ub.user_MSV = ?";

        ObservableList<borrowedBooksClass> borrowedBooksList = FXCollections.observableArrayList();

        connect = Database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, currentUser.getMSV());
            result = prepare.executeQuery();

            SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            while (result.next()) {
                int id = result.getInt("ID");
                String title = result.getString("Title");
                String author = result.getString("Author");
                String publishedDate = "";
                Date dbPublishedDate = result.getDate("PublishedDate");
                if (dbPublishedDate != null) {
                    publishedDate = displayDateFormat.format(dbPublishedDate);
                }
                String borrowDate = result.getString("borrow_date");
                String returnDate = result.getString("return_date");

                borrowedBooksClass borrowedBook = new borrowedBooksClass(id, title, author, publishedDate, borrowDate, returnDate);
                borrowedBooksList.add(borrowedBook);
            }

            addBooks_tableView2.setItems(borrowedBooksList);

            col_ID2.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author2.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_publishedDate2.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
            col_borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            col_returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Thông tin người dùng thư viện (dành cho quản lý thư viện).
     * @throws SQLException
     */
    public void showMangageUserTable() throws SQLException {
        String sql = "SELECT MSV, fullname, username, password, email, birthdate, gender FROM users";

        ObservableList<userClass> userList = FXCollections.observableArrayList();
        connect = Database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            while (result.next()) {
                String msv = result.getString("MSV");
                String fullname = result.getString("fullname");
                String username = result.getString("username");
                String password = result.getString("password");
                String email = result.getString("email");
                String birthdate = result.getString("birthdate");
                String gender = result.getString("gender");

                userClass user = new userClass(fullname, email, msv, username, password, birthdate, gender);
                userList.add(user);
            }

            manageUserTable.setItems(userList);
            col_MSV.setCellValueFactory(new PropertyValueFactory<>("MSV"));
            col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
            col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            col_birthDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
            col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *  Xóa sách khỏi thư viện (admin).
     */
    public void deleteBookFromLibrary() {
        String deleteQuery = "DELETE FROM books WHERE ID = ?";
        addBooksClass selectedBook = addBooks_tableView.getSelectionModel().getSelectedItem();

        try {
            connect = Database.connectDB();
            prepare = connect.prepareStatement(deleteQuery);
            prepare.setInt(1, selectedBook.getID());
            prepare.executeUpdate();
            showAddBooks();
            loadUserBooks();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Xóa sách thành công");
            alert.showAndWait();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Database error", e);
            throw new RuntimeException("Không thể xóa sách, thử lại!");
        }
    }

    /**
     * Khởi chạy game.
     * @throws IOException
     */
    public void gameStart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(libraryMA.class.getResource("game.fxml"));

        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("bookFlip");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private Button searchBook_btn;

    @FXML
    private TextField searchField;

    @FXML
    private HostServices hostServices;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private Label selectedBookInfo;

    // Hiển thị sách được chọn.
    private void showSelectedBookDetails(addBooksClass book) {
        selectedBookInfo.setText(
                "Tiêu đề: " + book.getTitle() + "\n" +
                        "Tác giả: " + book.getAuthor() + "\n" +
                        "Ngày xuất bản: " + book.formatPublishedDate() + "\n" +
                        "ISBN: " + book.getISBN()
        );
    }

    private void openWebPage(String url) {
        if (hostServices != null) {
            hostServices.showDocument(url);
        }
    }

    @FXML
    private Button closedb;

    @FXML
    private Button hidedb;

    @FXML
    public void hidedb() {
        Stage stage = (Stage) hidedb.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void closedb() {
        System.exit(0);
    }

    @FXML
    private ListView<String> suggestionsListView;

    /**
     * Nút tìm kiếm.
     */
    public void searchBook_btn() {
        if(currentUser instanceof staffClass) {
            searchBook_btn.setOnMouseEntered(event -> link.setVisible(false));
            searchBook_btn.setOnMouseClicked(event -> {
                addBookPage.setVisible(true);
                link.setVisible(false);
                introPage.setVisible(false);
                personalPage.setVisible(false);
                manageUserPage.setVisible(false);
                addBooks.setVisible(false);
                borrowedBooks.setVisible(false);
                inventory.setVisible(false);
                mainPage.setVisible(false);
                suggestionsListView.setVisible(false);
            });

            addBookPage.setStyle("-fx-background-color:white");
            mainPage.setStyle("-fx-background-color:transparent");
            personalPage.setStyle("-fx-background-color:transparent");
            borrowedBooks.setStyle("-fx-background-color:transparent");
            addBooks.setStyle("-fx-background-color:transparent");
            inventory.setStyle("-fx-background-color:transparent");
            introPage.setStyle("-fx-background-color:transparent");

            // Ẩn danh sách đề xuất khi trống.
            String searchText = searchField.getText();
            if (searchText.isEmpty()) {
                suggestionsListView.setVisible(false);
            }
            searchBook(searchText);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cảnh báo!");
            alert.setHeaderText(null);
            alert.setContentText("Bạn cần phải là quản trị viên để truy cập chức năng này!");
            alert.showAndWait();
        }

    }

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    /**
     * Hàm tìm kiếm.
     * @param search
     */
    public void searchBook(String search) {
        if(currentUser instanceof staffClass) {
            executor.submit(() -> {
                try {
                    // Gọi API Google Books và xử lý kết quả trong luồng nền
                    String result = GoogleBookAPI.searchBook(search);
                    JsonObject json = JsonParser.parseString(result).getAsJsonObject();
                    JsonArray items = json.getAsJsonArray("items");

                    Platform.runLater(() -> displayBooks(items));

                } catch (Exception e) {
                    Platform.runLater(() -> {
                        e.printStackTrace();
                    });
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cảnh báo!");
            alert.setHeaderText(null);
            alert.setContentText("Bạn cần là quản trị viên để làm điều này");
            alert.showAndWait();
        }
    }


    @FXML
    private TextField searchTitle;

    @FXML
    private TextField searchAuthor;

    @FXML
    private TextField searchPublishedDate;

    @FXML
    private TextField searchID;

    // Phương thức tìm kiếm và hiển thị kết quả
    private void searchBooks(String title, String author, String publishedDate, int id) {

        if (currentUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng đăng nhập để tìm kiếm!");
        }

        // Tạo danh sách kết quả dựa trên điều kiện tìm kiếm
        ObservableList<addBooksClass> filteredBooks = FXCollections.observableArrayList();

        for (addBooksClass book : listBook) {
            boolean matchesTitle = (title == null || title.isEmpty() || book.getTitle().toLowerCase().contains(title.toLowerCase()));
            boolean matchesAuthor = (author == null || author.isEmpty() || book.getAuthor().toLowerCase().contains(author.toLowerCase()));
            boolean matchesPublishedDate = (publishedDate == null || publishedDate.isEmpty() || book.getPublishedDate().contains(publishedDate));
            boolean matchesID = (id == -1 || book.getID() == id);

            // Nếu tất cả điều kiện phù hợp, thêm sách vào danh sách kết quả
            if (matchesTitle && matchesAuthor && matchesPublishedDate && matchesID) {
                filteredBooks.add(book);
            }
        }

        // Cập nhật bảng với danh sách kết quả
        addBooks_tableView.setItems(filteredBooks);
    }


    private boolean isSelectingSuggestion = false;

    /**
     * Cập nhật các đề xuất khi tìm kiếm.
     * @param query
     */
    public void updateSuggestions(String query) {
        // Kiểm tra nếu chuỗi tìm kiếm trống, ẩn danh sách gợi ý
        if (query.isEmpty()) {
            suggestionsListView.setVisible(false);
            return;
        }

        Callable<ObservableList<String>> fetchSuggestionsTask = () -> {

            String result = GoogleBookAPI.searchBook(query);
            JsonObject json = JsonParser.parseString(result).getAsJsonObject();
            JsonArray items = json.getAsJsonArray("items");

            ObservableList<String> suggestions = FXCollections.observableArrayList();
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    JsonObject volumeInfo = items.get(i).getAsJsonObject().getAsJsonObject("volumeInfo");
                    String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "No Title";
                    suggestions.add(title);
                }
            }
            return suggestions;
        };

        Future<ObservableList<String>> future = executor.submit(fetchSuggestionsTask);

        executor.execute(() -> {
            try {
                ObservableList<String> suggestions = future.get();

                Platform.runLater(() -> {
                    if (!isSelectingSuggestion && !searchField.getText().isEmpty()) {
                        suggestionsListView.setItems(suggestions);
                        suggestionsListView.setVisible(!suggestions.isEmpty());
                    }
                });
            } catch (Exception e) {
                // Ẩn danh sách gợi ý và in lỗi khi xảy ra ngoại lệ
                Platform.runLater(() -> suggestionsListView.setVisible(false));
                e.printStackTrace();
            }
        });

        // Xử lý sự kiện khi người dùng chọn một gợi ý
        suggestionsListView.setOnMouseClicked(event -> {
            if (!suggestionsListView.getSelectionModel().isEmpty()) {
                String selectedSuggestion = suggestionsListView.getSelectionModel().getSelectedItem();

                isSelectingSuggestion = true;

                // Cập nhật giao diện và thực hiện tìm kiếm
                Platform.runLater(() -> {
                    searchField.setText(selectedSuggestion);
                    addBookPage.setVisible(true);
                    link.setVisible(false);
                    introPage.setVisible(false);
                    personalPage.setVisible(false);
                    manageUserPage.setVisible(false);
                    addBooks.setVisible(false);
                    borrowedBooks.setVisible(false);
                    inventory.setVisible(false);
                    mainPage.setVisible(false);
                    suggestionsListView.setVisible(false);

                    addBookPage.setStyle("-fx-background-color:white");
                    mainPage.setStyle("-fx-background-color:transparent");
                    personalPage.setStyle("-fx-background-color:transparent");
                    borrowedBooks.setStyle("-fx-background-color:transparent");
                    addBooks.setStyle("-fx-background-color:transparent");
                    inventory.setStyle("-fx-background-color:transparent");
                    introPage.setStyle("-fx-background-color:transparent");

                    searchBook(selectedSuggestion);
                });

                Platform.runLater(() -> isSelectingSuggestion = false);
            }
        });
    }

    @FXML
    private ListView<addBooksClass> listView;

    /**
     * Thêm sách từ API vào database.
     * @param book
     */
    public void addBooktoLibrary(addBooksClass book) throws SQLException {
        book.addBooktoDatabase();
        showAddBooks();
    }

    /**
     * Hiển thị sách tìm kiếm từ API.
     * @param items
     */
    public void displayBooks(JsonArray items) {
        listView.getItems().clear();
        selectedBookInfo.setText("");

        executor.submit(() -> {
            try {
                List<addBooksClass> books = new ArrayList<>();

                for (int i = 0; i < items.size(); i++) {
                    JsonObject volumeInfo = items.get(i).getAsJsonObject().getAsJsonObject("volumeInfo");

                    String title = volumeInfo.get("title").getAsString();
                    String authors = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").get(0).getAsString() : "Unknown";
                    String publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "Unknown";
                    String isbn = volumeInfo.has("industryIdentifiers") ? volumeInfo.getAsJsonArray("industryIdentifiers").get(0).getAsJsonObject().get("identifier").getAsString() : "N/A";
                    String image = volumeInfo.has("imageLinks") ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : "";
                    String previewLink = volumeInfo.has("previewLink") ? volumeInfo.get("previewLink").getAsString() : "";
                    String infoLink = volumeInfo.has("infoLink") ? volumeInfo.get("infoLink").getAsString() : "";

                    books.add(new addBooksClass(title, authors, publishedDate, isbn, image, previewLink, infoLink));
                }

                Platform.runLater(() -> {
                    listView.getItems().addAll(books);
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    selectedBookInfo.setText("Error loading books");
                });
                e.printStackTrace();
            }
        });
    }

    /**
     * Khi không ô tìm kiếm nào được dùng thì trả về bảng gốc.
     */
    private void addSearchFieldListeners() {
        ChangeListener<String> searchFieldListener = (observable, oldValue, newValue) -> {
            searchBooks(
                    searchTitle.getText(),
                    searchAuthor.getText(),
                    searchPublishedDate.getText(),
                    parseSearchID()
            );
        };
        searchTitle.textProperty().addListener(searchFieldListener);
        searchAuthor.textProperty().addListener(searchFieldListener);
        searchPublishedDate.textProperty().addListener(searchFieldListener);
        searchID.textProperty().addListener((observable, oldValue, newValue) -> {
            int searchId = parseSearchID(); // Chuyển đổi giá trị searchID
            searchBooks(searchTitle.getText(), searchAuthor.getText(), searchPublishedDate.getText(), searchId);
        });
    }

    /**
     * Phương thức để chuyển đổi giá trị searchID từ String sang Integer
     */
    private int parseSearchID() {
        try {
            if (searchID.getText().isEmpty()) {
                return -1;
            }
            return Integer.parseInt(searchID.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi nhập liệu");
            alert.setHeaderText(null);
            alert.setContentText("ID phải là số nguyên!");
            alert.showAndWait();
            return -1;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Trang cá nhân
        initializePersonalInfo();

        // Hiển thị sách được thêm vào thư viện
        try {
            showAddBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Tự động trả về khi các ô tìm kiếm trống
        addSearchFieldListeners();

        // Hiển thị sách thêm vào ở trang chủ;
        try {
            displayNewAddBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Hiển thị sách trong inventory
        addBooks_tableView1.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                displaySelectedBook();
            }
        });

        // Xử lý gợi ý
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Ngắt lắng nghe nếu đang chọn gợi ý
            if (isSelectingSuggestion) {
                return;
            }
            if (newValue.isEmpty()) {
                suggestionsListView.setVisible(false); // Ẩn danh sách nếu không có gì nhập
            } else {
                updateSuggestions(newValue); // Lấy danh sách gợi ý
            }
        });

        listView.setCellFactory(new Callback<ListView<addBooksClass>, ListCell<addBooksClass>>() {
            @Override
            public ListCell<addBooksClass> call(ListView<addBooksClass> listView) {
                return new ListCell<addBooksClass>() {
                    private ImageView imageView = new ImageView();
                    private Button detailButton = new Button("Xem chi tiết");
                    private Button buyButton = new Button("Mua sách");
                    private Button addButton = new Button("Thêm sách vào thư viện");
                    private HBox hBox = new HBox(10);

                    {
                        hBox.getChildren().addAll(imageView, detailButton, buyButton, addButton);
                    }

                    @Override
                    protected void updateItem(addBooksClass book, boolean empty) {
                        super.updateItem(book, empty);

                        if (empty || book == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(book.toString());

                            if (!book.getImage().isEmpty()) {
                                Image image = new Image(book.getImage(), 50, 75, true, true);
                                imageView.setImage(image);
                                setGraphic(hBox);
                            }

                            detailButton.setOnAction(e -> {
                                if (!book.getPreviewLink().isEmpty()) {
                                    openWebPage(book.getPreviewLink());
                                }
                            });

                            buyButton.setOnAction(e -> {
                                if (!book.getInfoLink().isEmpty()) {
                                    openWebPage(book.getInfoLink());
                                }
                            });

                            addButton.setOnAction(e -> {
                                if (book != null) {
                                    try {
                                        addBooktoLibrary(book);
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            });

                            setOnMouseClicked(event -> {
                                if (event.getClickCount() == 1 && book != null) {
                                    showSelectedBookDetails(book);
                                }
                            });
                        }
                    }
                };
            }
        });
    }
}
