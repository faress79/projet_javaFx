package tn.esprit.pidev.interfaces;

import tn.esprit.pidev.models.Reclamation;

import java.util.List;

public interface IReclamationService {
    int createReclamation(Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    int updateReclamation(Reclamation reclamation, int id);
    Reclamation getReclamationById(int id);
    int removeReclamation(int id);
}
