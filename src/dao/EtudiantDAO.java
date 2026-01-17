package dao;

import model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    private Connection connection;

    public EtudiantDAO() {
        connection = ConnexionDB.getConnection();
    }

    //  Ajouter un étudiant
    public boolean ajouter(Etudiant e) {
        String sql = "INSERT INTO etudiant (nom, prenom, filiere, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getFiliere());
            ps.setString(4, e.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //  Lister les étudiants
    public List<Etudiant> lister() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Etudiant e = new Etudiant();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setFiliere(rs.getString("filiere"));
                e.setEmail(rs.getString("email"));
                etudiants.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return etudiants;
    }

    //  Modifier
    public boolean modifier(Etudiant e) {
        String sql = """
            UPDATE etudiant
            SET nom = ?, prenom = ?, filiere = ?, email = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, e.getNom());
            ps.setString(2, e.getPrenom());
            ps.setString(3, e.getFiliere());
            ps.setString(4, e.getEmail());
            ps.setInt(5, e.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //  Supprimer
    public boolean supprimer(int id) {
        String sql = "DELETE FROM etudiant WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
