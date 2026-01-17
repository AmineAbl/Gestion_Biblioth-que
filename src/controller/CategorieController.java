package controller;

import dao.CategorieDAO;
import model.Categorie;

import java.util.List;

public class CategorieController {

    private CategorieDAO categorieDAO;

    public CategorieController() {
        categorieDAO = new CategorieDAO();
    }

    // Ajouter une catégorie
    public boolean ajouterCategorie(String nom) {
        Categorie c = new Categorie();
        c.setNom(nom);
        return categorieDAO.ajouter(c);
    }

    // Lister toutes les catégories
    public List<Categorie> listerCategories() {
        return categorieDAO.lister();
    }

    // Modifier une catégorie
    public boolean modifierCategorie(int id, String nom) {
        Categorie c = new Categorie();
        c.setId(id);
        c.setNom(nom);
        return categorieDAO.modifier(c);
    }

    // Supprimer une catégorie
    public boolean supprimerCategorie(int id) {
        return categorieDAO.supprimer(id);
    }

    // Trouver une catégorie par ID
    public Categorie trouverParId(int id) {
        return categorieDAO.trouverParId(id);
    }
}
