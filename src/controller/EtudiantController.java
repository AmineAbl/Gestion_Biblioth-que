package controller;

import dao.EtudiantDAO;
import model.Etudiant;

import java.util.List;

public class EtudiantController {

    private EtudiantDAO etudiantDAO;

    public EtudiantController() {
        etudiantDAO = new EtudiantDAO();
    }

    public boolean ajouterEtudiant(Etudiant e) {
        return etudiantDAO.ajouter(e);
    }

    public boolean modifierEtudiant(Etudiant e) {
        return etudiantDAO.modifier(e);
    }

    public boolean supprimerEtudiant(int id) {
        return etudiantDAO.supprimer(id);
    }

    public List<Etudiant> listerEtudiants() {
        return etudiantDAO.lister();
    }
}
