package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.LoginUser;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static Stage MainStage;
    @Override
    public void start(Stage stage) throws Exception {
        MainStage = stage;
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SignIn.fxml")));
        Scene scene = new Scene(parent);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Personal Finance");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void changeScene(String fxml, LoginUser user)throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Scene scene = new Scene(pane);
        MainStage.setUserData(user);
        MainStage.setScene(scene);
        MainStage.setTitle("Personal Finance");
        MainStage.show();
    }
}
