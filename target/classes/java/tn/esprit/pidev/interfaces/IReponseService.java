package tn.esprit.pidev.interfaces;

import tn.esprit.pidev.models.Reponse;

import java.util.List;

public interface IReponseService {
    int createReponse(Reponse reponse);
    List<Reponse> getAllReponses();
    int updateReponse(Reponse reponse, int id);
    Reponse getReponseById(int id);
    int removeReponse(int id);
}
