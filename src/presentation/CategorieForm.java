package presentation;

import controller.CategorieController;
import model.Categorie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategorieForm extends JFrame {
    private JTextField nomField;
    private JTable table;
    private DefaultTableModel tableModel;
    private CategorieController categorieController;
    private Categorie categorieSelectionnee;

    public CategorieForm() {
        categorieController = new CategorieController();
        initUI();
        chargerCategories();
    }

    private void initUI() {
        setTitle("Gestion des Catégories");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Catégorie"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nom
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nomField = new JTextField(20);
        formPanel.add(nomField, gbc);

        // Panel boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton ajouterBtn = new JButton("Ajouter");
        JButton modifierBtn = new JButton("Modifier");
        JButton supprimerBtn = new JButton("Supprimer");
        JButton reinitialiserBtn = new JButton("Réinitialiser");

        ajouterBtn.addActionListener(e -> ajouterCategorie());
        modifierBtn.addActionListener(e -> modifierCategorie());
        supprimerBtn.addActionListener(e -> supprimerCategorie());
        reinitialiserBtn.addActionListener(e -> reinitialiserFormulaire());

        buttonPanel.add(ajouterBtn);
        buttonPanel.add(modifierBtn);
        buttonPanel.add(supprimerBtn);
        buttonPanel.add(reinitialiserBtn);

        // Panel haut (formulaire + boutons)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nom"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectionnerCategorie();
            }
        });

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Liste des Catégories"));

        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
    }

    private void chargerCategories() {
        tableModel.setRowCount(0);
        List<Categorie> categories = categorieController.listerCategories();
        for (Categorie c : categories) {
            tableModel.addRow(new Object[]{c.getId(), c.getNom()});
        }
    }

    private void selectionnerCategorie() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            String nom = (String) tableModel.getValueAt(row, 1);

            categorieSelectionnee = new Categorie();
            categorieSelectionnee.setId(id);
            categorieSelectionnee.setNom(nom);

            nomField.setText(nom);
        }
    }

    private void ajouterCategorie() {
        String nom = nomField.getText().trim();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (categorieController.ajouterCategorie(nom)) {
            JOptionPane.showMessageDialog(this, "Catégorie ajoutée avec succès.");
            reinitialiserFormulaire();
            chargerCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierCategorie() {
        if (categorieSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie.", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nom = nomField.getText().trim();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (categorieController.modifierCategorie(categorieSelectionnee.getId(), nom)) {
            JOptionPane.showMessageDialog(this, "Catégorie modifiée avec succès.");
            reinitialiserFormulaire();
            chargerCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerCategorie() {
        if (categorieSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie.", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cette catégorie ?", 
            "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (categorieController.supprimerCategorie(categorieSelectionnee.getId())) {
                JOptionPane.showMessageDialog(this, "Catégorie supprimée avec succès.");
                reinitialiserFormulaire();
                chargerCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression. Vérifiez qu'aucun livre n'utilise cette catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reinitialiserFormulaire() {
        nomField.setText("");
        categorieSelectionnee = null;
        table.clearSelection();
    }
}
