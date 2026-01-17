package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatistiqueDAO {

    private Connection connection;

    public StatistiqueDAO() {
        connection = ConnexionDB.getConnection();
    }

    // 📊 Livres les plus empruntés
    public Map<String, Integer> livresPopulaires() {
        Map<String, Integer> data = new HashMap<>();

        String sql = """
            SELECT l.titre, COUNT(*) AS total
            FROM emprunt e
            JOIN livre l ON e.livre_id = l.id
            GROUP BY l.titre
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("titre"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // 📊 Taux d’emprunt par filière
    public Map<String, Integer> empruntsParFiliere() {
        Map<String, Integer> data = new HashMap<>();

        String sql = """
            SELECT et.filiere, COUNT(*) AS total
            FROM emprunt e
            JOIN etudiant et ON e.etudiant_id = et.id
            GROUP BY et.filiere
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("filiere"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
 // <CHANGE> Ajouter cette méthode dans StatistiqueDAO
    public Map<String, Integer> frequentationParJour() {
        Map<String, Integer> frequentation = new LinkedHashMap<>();
        // Initialiser tous les jours à 0
        frequentation.put("Lundi", 0);
        frequentation.put("Mardi", 0);
        frequentation.put("Mercredi", 0);
        frequentation.put("Jeudi", 0);
        frequentation.put("Vendredi", 0);
        frequentation.put("Samedi", 0);
        frequentation.put("Dimanche", 0);

        String sql = "SELECT DAYOFWEEK(date_emprunt) as jour, COUNT(*) as total FROM emprunt GROUP BY DAYOFWEEK(date_emprunt)";
        
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int jourNum = rs.getInt("jour");
                int total = rs.getInt("total");
                
                // MySQL: 1=Dimanche, 2=Lundi, ..., 7=Samedi
                String jourNom = switch (jourNum) {
                    case 1 -> "Dimanche";
                    case 2 -> "Lundi";
                    case 3 -> "Mardi";
                    case 4 -> "Mercredi";
                    case 5 -> "Jeudi";
                    case 6 -> "Vendredi";
                    case 7 -> "Samedi";
                    default -> "Inconnu";
                };
                frequentation.put(jourNom, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frequentation;
    }
}
