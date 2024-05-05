package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tn.esprit.models.user;
import tn.esprit.services.ServicePersonne;
import javafx.scene.layout.AnchorPane;


public class ListeUser  {

    private user user;



    @FXML
    private Button delete;


    @FXML
    private HBox actioncol;

    @FXML
    private Label nomcol;

    @FXML
    private Button edit;

    @FXML
    private Label emailcol;

    @FXML
    private Label prenomcol;

    @FXML
    private Label rolecole;


    public void getData(user userr) {
        this.user = userr;
        nomcol.setText(userr.getNom());
        prenomcol.setText(userr.getPrenom());
        rolecole.setText(userr.getRoles());
        emailcol.setText(userr.getEmail());
    }

    @FXML


    void delete(ActionEvent event) {
        if (user != null) {
            ServicePersonne servicePersonne = new ServicePersonne() {
                @Override
                public void update(tn.esprit.models.user user) {
                    // Implementation of update method
                }
            };
            try {
                // Perform user deletion
                servicePersonne.delete(user);

                // Update UI after successful deletion
                AnchorPane container = (AnchorPane) delete.getParent().getParent();
                Parent parent = container.getParent();
                if (parent instanceof Pane) {
                    Pane parentPane = (Pane) parent;
                    parentPane.getChildren().remove(container); // Remove anchor pane from its parent
                    System.out.println("AnchorPane removed."); // Debugging statement
                } else {
                    System.out.println("Parent of AnchorPane is not a Pane."); // Debugging statement
                }

                // Optionally, perform any additional UI updates or actions
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression du produit de la base de données : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun produit sélectionné.");
        }
    }






    private ObservableList<user> userList = FXCollections.observableArrayList();

//    private void initializeUsers() {
//        userList.add(new user("", "dddd", "Produit 1", "Catégorie 1", "Description 1", "image1.jpg", "10.0"));
//        userList.add(new user("", "ffffffff", "Produit 2", "Catégorie 2", "Description 2", "image2.jpg", "20.0"));
//    }


    @FXML
    void edit(ActionEvent event) {

    }


}