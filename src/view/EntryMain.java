package view;

import controller.EntryCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EntryMain extends Application {


    public static void main(String[] args) {
        launch(args);

    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        EntryCtrl controller = new EntryCtrl();
        loader.setController(controller);
        Parent parent = loader.load();
        stage.setScene(new Scene(parent));
        stage.setTitle("Лабораторная работа 4");
        stage.show();

    }
}
