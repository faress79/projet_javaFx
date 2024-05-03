package tn.esprit.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.models.user;
import tn.esprit.services.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserBack {


    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;



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
