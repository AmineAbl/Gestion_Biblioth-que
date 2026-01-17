package dao;

import model.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO {

    private Connection connection;

    public CategorieDAO() {
        // Connexion à la base via ConnexionDB
        connection = ConnexionDB.getConnection();
    }

    //  Ajouter une catégorie
    public boolean ajouter(Categorie categorie) {
        String sql = "INSERT INTO categorie (nom) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categorie.getNom());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Lister toutes les catégories
    public List<Categorie> lister() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Categorie c = new Categorie();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                categories.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    //  Trouver par ID
    public Categorie trouverParId(int id) {
        String sql = "SELECT * FROM categorie WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("id"), rs.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  Modifier une catégorie
    public boolean modifier(Categorie categorie) {
        String sql = "UPDATE categorie SET nom = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categorie.getNom());
            ps.setInt(2, categorie.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Supprimer une catégorie
    public boolean supprimer(int id) {
        String sql = "DELETE FROM categorie WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
