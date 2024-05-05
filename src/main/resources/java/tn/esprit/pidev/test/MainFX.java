package tn.esprit.pidev.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // load the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Home.fxml"));
        // load fxml code in a sceen
        Parent root= loader.load();
        // put the fxml file in a sceene
        Scene scene = new Scene(root);
        // set a scene in stage
        stage.setScene(scene);
        stage.setTitle("Tazuri");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}