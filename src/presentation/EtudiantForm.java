package presentation;

import controller.EtudiantController;
import model.Etudiant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EtudiantForm extends JFrame {

    private EtudiantController etudiantController;
    private JTextField txtNom, txtPrenom, txtFiliere, txtEmail;
    private JTable tableEtudiants;
    private int selectedEtudiantId = -1;

    public EtudiantForm() {
        setTitle("Gestion des Étudiants");
        setSize(800, 600);
        setLocationRelativeTo(null);

        etudiantController = new EtudiantController();

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Ajouter / Modifier Étudiant"));
        panelForm.setPreferredSize(new Dimension(800, 200));

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtFiliere = new JTextField();
        txtEmail = new JTextField();

        panelForm.add(new JLabel("Nom :"));
        panelForm.add(txtNom);
        panelForm.add(new JLabel("Prénom :"));
        panelForm.add(txtPrenom);
        panelForm.add(new JLabel("Filière :"));
        panelForm.add(txtFiliere);
        panelForm.add(new JLabel("Email :"));
        panelForm.add(txtEmail);

        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener((ActionEvent e) -> ajouterEtudiant());
        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener((ActionEvent e) -> modifierEtudiant());
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener((ActionEvent e) -> supprimerEtudiant());

        panelButtons.add(btnAjouter);
        panelButtons.add(btnModifier);
        panelButtons.add(btnSupprimer);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        tableEtudiants = new JTable();
        tableEtudiants.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tableEtudiants.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedEtudiantId = (int) tableEtudiants.getValueAt(selectedRow, 0);
                    txtNom.setText((String) tableEtudiants.getValueAt(selectedRow, 1));
                    txtPrenom.setText((String) tableEtudiants.getValueAt(selectedRow, 2));
                    txtFiliere.setText((String) tableEtudiants.getValueAt(selectedRow, 3));
                    txtEmail.setText((String) tableEtudiants.getValueAt(selectedRow, 4));
                }
            }
        });

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(tableEtudiants), BorderLayout.CENTER);

        listerEtudiants();
    }

    private void ajouterEtudiant() {
        Etudiant e = new Etudiant();
        e.setNom(txtNom.getText());
        e.setPrenom(txtPrenom.getText());
        e.setFiliere(txtFiliere.getText());
        e.setEmail(txtEmail.getText());

        if (etudiantController.ajouterEtudiant(e)) {
            JOptionPane.showMessageDialog(this, "Étudiant ajouté !");
            listerEtudiants();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur !");
        }
    }

    private void modifierEtudiant() {
        if (selectedEtudiantId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant à modifier !");
            return;
        }

        Etudiant e = new Etudiant();
        e.setId(selectedEtudiantId);
        e.setNom(txtNom.getText());
        e.setPrenom(txtPrenom.getText());
        e.setFiliere(txtFiliere.getText());
        e.setEmail(txtEmail.getText());

        if (etudiantController.modifierEtudiant(e)) {
            JOptionPane.showMessageDialog(this, "Étudiant modifié !");
            listerEtudiants();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification !");
        }
    }

    private void supprimerEtudiant() {
        if (selectedEtudiantId == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant à supprimer !");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet étudiant ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (etudiantController.supprimerEtudiant(selectedEtudiantId)) {
                JOptionPane.showMessageDialog(this, "Étudiant supprimé !");
                listerEtudiants();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression !");
            }
        }
    }

    private void clearForm() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtFiliere.setText("");
        txtEmail.setText("");
        selectedEtudiantId = -1;
        tableEtudiants.clearSelection();
    }

    private void listerEtudiants() {
        List<Etudiant> etudiants = etudiantController.listerEtudiants();
        String[] columns = {"ID", "Nom", "Prénom", "Filière", "Email"};
        Object[][] data = new Object[etudiants.size()][5];

        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant e = etudiants.get(i);
            data[i][0] = e.getId();
            data[i][1] = e.getNom();
            data[i][2] = e.getPrenom();
            data[i][3] = e.getFiliere();
            data[i][4] = e.getEmail();
        }

        tableEtudiants.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }
}
