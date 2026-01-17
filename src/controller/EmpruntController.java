package controller;

import dao.EmpruntDAO;
import model.Emprunt;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmpruntController {

    private EmpruntDAO empruntDAO;

    public EmpruntController() {
        empruntDAO = new EmpruntDAO();
    }

    // Ajouter un emprunt
    public boolean ajouterEmprunt(Emprunt e) {
        return empruntDAO.ajouter(e);
    }

    // Retour de livre avec calcul pénalité
    public boolean retourLivre(Emprunt e) {
        // Calcul pénalité si retour tardif
        Date today = e.getDateRetourEffective();
        Date prevue = e.getDateRetourPrevue();

        if (today.after(prevue)) {
            long diff = today.getTime() - prevue.getTime();
            long daysLate = diff / (1000 * 60 * 60 * 24);
            e.setPenalite(daysLate * 2.0); // Exemple : 2 unités par jour de retard
        } else {
            e.setPenalite(0);
        }

        return empruntDAO.retourLivre(e);
    }

    // Historique des emprunts
    public List<Emprunt> listerEmprunts() {
        return empruntDAO.lister();
    }
    
 // <CHANGE> Ajouter cette méthode pour supprimer un emprunt
    public void supprimerEmprunt(int id) {
        empruntDAO.supprimer(id);
    }
}
