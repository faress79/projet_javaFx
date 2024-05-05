package tn.esprit.interfaces;

import tn.esprit.models.Reclamation;

import java.util.List;

public interface IReclamationService {
    int createReclamation(Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    int updateReclamation(Reclamation reclamation, int id);
    Reclamation getReclamationById(int id);
    int removeReclamation(int id);
}
