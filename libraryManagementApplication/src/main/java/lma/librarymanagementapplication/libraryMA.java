package lma.librarymanagementapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class libraryMA extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(libraryMA.class.getResource("dashboardoutside.fxml"));

        Parent root = fxmlLoader. load();

        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {

            x = event.getSceneX();
            y = event.getSceneY();

        });

        root.setOnMouseDragged((MouseEvent event) -> {

            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("LibraryManagementApplication");
        stage.setScene(scene);
        stage.show();

        dashboardController dbcontroller = fxmlLoader.getController();
        dbcontroller.setHostServices(getHostServices());

    }

    public static void main(String[] args) {
        launch();
    }
}
