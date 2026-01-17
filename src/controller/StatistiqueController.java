package controller;

import dao.StatistiqueDAO;
import java.util.Map;

public class StatistiqueController {
    private StatistiqueDAO statistiqueDAO;

    public StatistiqueController() {
        statistiqueDAO = new StatistiqueDAO();
    }

    public Map<String, Integer> livresPopulaires() {
        return statistiqueDAO.livresPopulaires();
    }

    public Map<String, Integer> empruntsParFiliere() {
        return statistiqueDAO.empruntsParFiliere();
    }

    // <CHANGE> Nouvelle méthode pour la fréquentation par jour
    public Map<String, Integer> frequentationParJour() {
        return statistiqueDAO.frequentationParJour();
    }
}