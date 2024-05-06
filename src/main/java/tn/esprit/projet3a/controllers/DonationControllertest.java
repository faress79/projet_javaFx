package tn.esprit.projet3a.controllers;

import tn.esprit.projet3a.models.Donation;
import tn.esprit.projet3a.services.DonationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.awt.*;
import java.sql.SQLException;

public class DonationControllertest {

    DonationService ds = new DonationService();

    @FXML
    public TableColumn<?, ?> ColAmount;

    @FXML
    public TableColumn<?, ?> ColEvent;

    @FXML
    public TableColumn<?, ?> ColID;

    @FXML
    public TableColumn<?, ?> ColMessage;

    @FXML
    public TableColumn<?, ?> ColUtilisateur;

    @FXML
    public Button btnAjout;


    @FXML
    public TextField tAmount;

    @FXML
    public TextField tEvent_id;

    @FXML
    public TextField tID;

    @FXML
    public TextField tMessage;

    @FXML
    public TextField tUtilisateur_id;



    @FXML
    void AjouterDonation(ActionEvent event) {
        if (validateInput()) {
            Donation d = new Donation(

                    Integer.parseInt(tUtilisateur_id.getText()),
                    Integer.parseInt(tAmount.getText()),
                    tMessage.getText()
            );
            try {
                ds.create(d);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setContentText("Donation insérée avec succès!");
                alert.show();
                clear();
            } catch (SQLException e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Erreur lors de l'insertion de la donation: " + e1.getMessage());
                alert.show();
                throw new RuntimeException(e1);
            }
        }
    }

    private boolean validateInput() {
        if ( tUtilisateur_id.getText().isEmpty() || tAmount.getText().isEmpty() || tMessage.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return false;
        }

        try {

            int utilisateurId = Integer.parseInt(tUtilisateur_id.getText());
            int amount = Integer.parseInt(tAmount.getText());
            // Add more specific validation rules as needed
            return true;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setContentText("Les champs ID de l'événement, ID de l'utilisateur et Montant doivent être des nombres entiers.");
            alert.show();
            return false;
        }
    }





    public void initData(Label eventLabel) {
        tEvent_id.setText(eventLabel.getText());  // Set the event ID text field with the text of the passed label
    }


    void clear(){

        tEvent_id.setText(null);
        tUtilisateur_id.setText(null);
        tAmount.setText(null);
        tMessage.setText(null);
        btnAjout.setDisable(false);

    }
    @FXML
    void clearField(){
        clear();
    }

}



