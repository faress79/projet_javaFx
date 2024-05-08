package tn.esprit.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import tn.esprit.models.user;
import tn.esprit.services.ServicePersonne;
import tn.esprit.test.HelloApplication;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserBack {


    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button event1;

    @FXML
    private Button logout;



    @FXML
    void eexit(ActionEvent event) {
        System.exit(0);

    }




//    @FXML
//    void eventadmin(MouseEvent event) {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AdminEvenment.fxml"));
//        try {
//            label.getScene().setRoot(fxmlLoader.load());
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//
//    }

    @FXML
    void event(ActionEvent event) {
        try {
            // Load the FXML file for AdminEvenment.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminEvenment.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Admin Evenment"); // Set the title of the new window
            stage.setScene(new Scene(root)); // Set the scene with the content loaded from AdminEvenment.fxml

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private final ServicePersonne servicePersonne = new ServicePersonne() {

        public void update(user user) {

        }
    };

    @FXML
    void initialize() {
        try {
            actualiserListeUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualiserListeUsers() throws SQLException {
        List<user> users = servicePersonne.getAllll();
        afficherUsersDansGrille(users);
    }


    private void afficherUsersDansGrille(List<user> users) {
        int column = 0;
        int row = 3;

        for (user user : users) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ListeUser.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ListeUser ListeUser = fxmlLoader.getController();
                ListeUser.getData(user);
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 1) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
