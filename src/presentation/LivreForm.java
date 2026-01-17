package presentation;

import controller.CategorieController;
import controller.LivreController;
import model.Categorie;
import model.Livre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class LivreForm extends JFrame {

    private LivreController livreController;
    private CategorieController categorieController;

    private JTextField txtTitre, txtAuteur, txtISBN, txtQuantite;
    private JComboBox<Categorie> cbCategorie;
    private JTable tableLivres;
    private int selectedLivreId = -1;

    private JTextField txtRecherche;
    private JComboBox<String> cbCritereRecherche;
    private List<Livre> tousLesLivres;

    public LivreForm() {
        setTitle("Gestion des Livres");
        setSize(800, 650);
        setLocationRelativeTo(null);

        livreController = new LivreController();
        categorieController = new CategorieController();

        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRecherche.setBorder(BorderFactory.createTitledBorder("Rechercher"));
        
        cbCritereRecherche = new JComboBox<>(new String[]{"Titre", "Auteur", "ISBN", "Tous"});
        txtRecherche = new JTextField(20);
        JButton btnRechercher = new JButton("Rechercher");
        btnRechercher.addActionListener(e -> rechercherLivres());
        JButton btnReinitialiser = new JButton("Réinitialiser");
        btnReinitialiser.addActionListener(e -> {
            txtRecherche.setText("");
            listerLivres();
        });
        
        panelRecherche.add(new JLabel("Critère :"));
        panelRecherche.add(cbCritereRecherche);
        panelRecherche.add(new JLabel("Recherche :"));
        panelRecherche.add(txtRecherche);
   
 

        // Recherche en temps réel lors de la saisie
        txtRecherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechercherLivres();
            }
        });

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Ajouter / Modifier Livre"));
        panelForm.setPreferredSize(new Dimension(800, 200));

        txtTitre = new JTextField();
        txtAuteur = new JTextField();
        txtISBN = new JTextField();
        txtQuantite = new JTextField();

        cbCategorie = new JComboBox<>();
        List<Categorie> categories = categorieController.listerCategories();
        for (Categorie c : categories) cbCategorie.addItem(c);

        panelForm.add(new JLabel("Titre :"));
        panelForm.add(txtTitre);
        panelForm.add(new JLabel("Auteur :"));
        panelForm.add(txtAuteur);
        panelForm.add(new JLabel("ISBN :"));
        panelForm.add(txtISBN);
        panelForm.add(new JLabel("Quantité :"));
        panelForm.add(txtQuantite);
        panelForm.add(new JLabel("Catégorie :"));
        panelForm.add(cbCategorie);

        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener((ActionEvent e) -> ajouterLivre());
        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener((ActionEvent e) -> modifierLivre());
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener((ActionEvent e) -> supprimerLivre());
        panelButtons.add(btnAjouter);
        panelButtons.add(btnModifier);
        panelButtons.add(btnSupprimer);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelRecherche, BorderLayout.NORTH);
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        tableLivres = new JTable();
        tableLivres.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableLivres.getSelectedRow();
            if (selectedRow >= 0) {
                selectedLivreId = (int) tableLivres.getValueAt(selectedRow, 0);
                txtTitre.setText((String) tableLivres.getValueAt(selectedRow, 1));
                txtAuteur.setText((String) tableLivres.getValueAt(selectedRow, 2));
                txtISBN.setText((String) tableLivres.getValueAt(selectedRow, 3));
                txtQuantite.setText(String.valueOf(tableLivres.getValueAt(selectedRow, 4)));
            }
        });

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(tableLivres), BorderLayout.CENTER);

        listerLivres();
    }

    private void rechercherLivres() {
        String recherche = txtRecherche.getText().toLowerCase().trim();
        String critere = (String) cbCritereRecherche.getSelectedItem();
        
        if (recherche.isEmpty()) {
            afficherLivres(tousLesLivres);
            return;
        }
        
        List<Livre> resultats = tousLesLivres.stream()
            .filter(livre -> {
                switch (critere) {
                    case "Titre":
                        return livre.getTitre().toLowerCase().contains(recherche);
                    case "Auteur":
                        return livre.getAuteur().toLowerCase().contains(recherche);
                    case "ISBN":
                        return livre.getIsbn().toLowerCase().contains(recherche);
                    case "Tous":
                    default:
                        return livre.getTitre().toLowerCase().contains(recherche) ||
                               livre.getAuteur().toLowerCase().contains(recherche) ||
                               livre.getIsbn().toLowerCase().contains(recherche);
                }
            })
            .collect(Collectors.toList());
        
        afficherLivres(resultats);
    }

    private void afficherLivres(List<Livre> livres) {
        String[] columns = {"ID", "Titre", "Auteur", "ISBN", "Quantité", "Catégorie"};
        Object[][] data = new Object[livres.size()][6];

        for (int i = 0; i < livres.size(); i++) {
            Livre l = livres.get(i);
            data[i][0] = l.getId();
            data[i][1] = l.getTitre();
            data[i][2] = l.getAuteur();
            data[i][3] = l.getIsbn();
            data[i][4] = l.getQuantite();
            data[i][5] = l.getCategorie().getNom();
        }

        tableLivres.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }


    private void ajouterLivre() {
        try {
            String titre = txtTitre.getText();
            String auteur = txtAuteur.getText();
            String isbn = txtISBN.getText();
            int quantite = Integer.parseInt(txtQuantite.getText());
            Categorie categorie = (Categorie) cbCategorie.getSelectedItem();

            Livre l = new Livre();
            l.setTitre(titre);
            l.setAuteur(auteur);
            l.setIsbn(isbn);
            l.setQuantite(quantite);
            l.setCategorie(categorie);

            if (livreController.ajouterLivre(l)) {
                JOptionPane.showMessageDialog(this, "Livre ajouté avec succès !");
                listerLivres();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout !");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantité invalide !");
        }
    }

    private void modifierLivre() {
        if (selectedLivreId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à modifier !");
            return;
        }

        try {
            String titre = txtTitre.getText();
            String auteur = txtAuteur.getText();
            String isbn = txtISBN.getText();
            int quantite = Integer.parseInt(txtQuantite.getText());
            Categorie categorie = (Categorie) cbCategorie.getSelectedItem();

            Livre l = new Livre();
            l.setId(selectedLivreId);
            l.setTitre(titre);
            l.setAuteur(auteur);
            l.setIsbn(isbn);
            l.setQuantite(quantite);
            l.setCategorie(categorie);

            if (livreController.modifierLivre(l)) {
                JOptionPane.showMessageDialog(this, "Livre modifié avec succès !");
                listerLivres();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification !");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantité invalide !");
        }
    }

    private void supprimerLivre() {
        if (selectedLivreId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à supprimer !");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce livre ?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (livreController.supprimerLivre(selectedLivreId)) {
                JOptionPane.showMessageDialog(this, "Livre supprimé avec succès !");
                listerLivres();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression !");
            }
        }
    }

    private void clearForm() {
        txtTitre.setText("");
        txtAuteur.setText("");
        txtISBN.setText("");
        txtQuantite.setText("");
        cbCategorie.setSelectedIndex(0);
        selectedLivreId = -1;
    }

    private void listerLivres() {
        tousLesLivres = livreController.listerLivres();
        afficherLivres(tousLesLivres);
    }
}
