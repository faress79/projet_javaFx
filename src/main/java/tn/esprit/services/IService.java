package tn.esprit.services;

import tn.esprit.models.Evenment;
import tn.esprit.models.Review;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;
  //  void modifier(T t) throws SQLException;

    void modifier(Integer id_event, Evenment evenment) throws SQLException;


    void modifierReview(Integer id_review, Review review) throws SQLException;


    void supprimerReview(int id_review) throws SQLException;

    void supprimer(int id_event) throws SQLException;

    List<T> recuperer() throws SQLException;


}
