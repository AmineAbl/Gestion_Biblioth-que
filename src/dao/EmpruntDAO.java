package dao;

import model.Emprunt;
import model.Etudiant;
import model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpruntDAO {

    private Connection connection;

    public EmpruntDAO() {
        connection = ConnexionDB.getConnection();
    }

    //  Enregistrer un emprunt
    public boolean ajouter(Emprunt e) {
        String sql = """
            INSERT INTO emprunt
            (livre_id, etudiant_id, date_emprunt, date_retour_prevue)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, e.getLivre().getId());
            ps.setInt(2, e.getEtudiant().getId());
            ps.setDate(3, new java.sql.Date(e.getDateEmprunt().getTime()));
            ps.setDate(4, new java.sql.Date(e.getDateRetourPrevue().getTime()));

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //  Retour de livre + pénalité
    public boolean retourLivre(Emprunt e) {
        String sql = """
            UPDATE emprunt
            SET date_retour_effective = ?, penalite = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(e.getDateRetourEffective().getTime()));
            ps.setDouble(2, e.getPenalite());
            ps.setInt(3, e.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //  Historique des emprunts
    public List<Emprunt> lister() {
        List<Emprunt> emprunts = new ArrayList<>();

        String sql = """
            SELECT e.*, l.titre, et.nom, et.prenom
            FROM emprunt e
            JOIN livre l ON e.livre_id = l.id
            JOIN etudiant et ON e.etudiant_id = et.id
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Livre l = new Livre();
                l.setId(rs.getInt("livre_id"));
                l.setTitre(rs.getString("titre"));

                Etudiant et = new Etudiant();
                et.setId(rs.getInt("etudiant_id"));
                et.setNom(rs.getString("nom"));
                et.setPrenom(rs.getString("prenom"));

                Emprunt e = new Emprunt();
                e.setId(rs.getInt("id"));
                e.setLivre(l);
                e.setEtudiant(et);
                e.setDateEmprunt(rs.getDate("date_emprunt"));
                e.setDateRetourPrevue(rs.getDate("date_retour_prevue"));
                e.setDateRetourEffective(rs.getDate("date_retour_effective"));
                e.setPenalite(rs.getDouble("penalite"));

                emprunts.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emprunts;
    }
    
 // <CHANGE> méthode pour supprimer un emprunt
    public void supprimer(int id) {
        String sql = "DELETE FROM emprunt WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
