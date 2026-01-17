package dao;

import model.Categorie;
import model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private Connection connection;

    public LivreDAO() {
        connection = ConnexionDB.getConnection();
    }

    //  Ajouter un livre
    public boolean ajouter(Livre livre) {
        String sql = """
            INSERT INTO livre (titre, auteur, isbn, categorie_id, quantite)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getIsbn());
            ps.setInt(4, livre.getCategorie().getId());
            ps.setInt(5, livre.getQuantite());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Lister tous les livres
    public List<Livre> lister() {
        List<Livre> livres = new ArrayList<>();

        String sql = """
            SELECT l.*, c.id AS cid, c.nom AS cnom
            FROM livre l
            JOIN categorie c ON l.categorie_id = c.id
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Categorie c = new Categorie(
                        rs.getInt("cid"),
                        rs.getString("cnom")
                );

                Livre l = new Livre();
                l.setId(rs.getInt("id"));
                l.setTitre(rs.getString("titre"));
                l.setAuteur(rs.getString("auteur"));
                l.setIsbn(rs.getString("isbn"));
                l.setCategorie(c);
                l.setQuantite(rs.getInt("quantite"));

                livres.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    //  Modifier
    public boolean modifier(Livre livre) {
        String sql = """
            UPDATE livre
            SET titre = ?, auteur = ?, isbn = ?, categorie_id = ?, quantite = ?
            WHERE id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getIsbn());
            ps.setInt(4, livre.getCategorie().getId());
            ps.setInt(5, livre.getQuantite());
            ps.setInt(6, livre.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Supprimer
    public boolean supprimer(int id) {
        String sql = "DELETE FROM livre WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Livre getById(int id) {
        Livre livre = null;
        try {
            Connection con = ConnexionDB.getConnection();
            String sql = "SELECT * FROM livres WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                livre = new Livre();
                livre.setId(rs.getInt("id"));
                livre.setTitre(rs.getString("titre"));
                livre.setAuteur(rs.getString("auteur"));
                livre.setIsbn(rs.getString("isbn"));
                livre.setQuantite(rs.getInt("quantite"));
                // Récupérer catégorie si nécessaire
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livre;
    }
}
