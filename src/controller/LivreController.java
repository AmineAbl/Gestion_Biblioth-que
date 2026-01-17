package controller;

import dao.LivreDAO;
import model.Livre;

import java.util.List;

public class LivreController {

    private LivreDAO livreDAO;

    public LivreController() {
        livreDAO = new LivreDAO();
    }

    // Ajouter un livre
    public boolean ajouterLivre(Livre livre) {
        return livreDAO.ajouter(livre);
    }

    // Modifier un livre
    public boolean modifierLivre(Livre livre) {
        return livreDAO.modifier(livre);
    }

    // Supprimer un livre
    public boolean supprimerLivre(int id) {
        return livreDAO.supprimer(id);
    }

    // Lister les livres
    public List<Livre> listerLivres() {
        return livreDAO.lister();
    }
    
    public Livre getLivreById(int id) {
        return livreDAO.getById(id);
    }
}
