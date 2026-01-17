package presentation;

import controller.EmpruntController;
import controller.EtudiantController;
import controller.LivreController;
import model.Emprunt;
import model.Etudiant;
import model.Livre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmpruntForm extends JFrame {

    private EmpruntController empruntController;
    private EtudiantController etudiantController;
    private LivreController livreController;

    private JComboBox<Etudiant> cbEtudiants;
    private JComboBox<Livre> cbLivres;
    private JSpinner dateEmpruntSpinner;
    private JSpinner dateRetourSpinner;
    private JTable tableEmprunts;
    
    private static final double PENALITE_PAR_JOUR = 2.0; // 2 DH par jour de retard

    private List<Emprunt> listeEmprunts;

    public EmpruntForm() {
        setTitle("Gestion des Emprunts");
        setSize(900, 700);
        setLocationRelativeTo(null);

        empruntController = new EmpruntController();
        etudiantController = new EtudiantController();
        livreController = new LivreController();

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Ajouter un Emprunt"));
        panelForm.setPreferredSize(new Dimension(800, 200));

        cbEtudiants = new JComboBox<>();
        List<Etudiant> etudiants = etudiantController.listerEtudiants();
        for (Etudiant e : etudiants) cbEtudiants.addItem(e);

        cbLivres = new JComboBox<>();
        List<Livre> livres = livreController.listerLivres();
        for (Livre l : livres) cbLivres.addItem(l);

        dateEmpruntSpinner = new JSpinner(new SpinnerDateModel());
        dateRetourSpinner = new JSpinner(new SpinnerDateModel());

        panelForm.add(new JLabel("Étudiant :"));
        panelForm.add(cbEtudiants);
        panelForm.add(new JLabel("Livre :"));
        panelForm.add(cbLivres);
        panelForm.add(new JLabel("Date Emprunt :"));
        panelForm.add(dateEmpruntSpinner);
        panelForm.add(new JLabel("Date Retour Prévue :"));
        panelForm.add(dateRetourSpinner);

        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("Ajouter Emprunt");
        btnAjouter.addActionListener((ActionEvent e) -> ajouterEmprunt());
        
        JButton btnRetourner = new JButton("Retourner Livre");
        btnRetourner.setBackground(new Color(34, 139, 34));
        btnRetourner.addActionListener((ActionEvent e) -> retournerLivre());
        
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setBackground(new Color(220, 53, 69));
        btnSupprimer.addActionListener((ActionEvent e) -> supprimerEmprunt());
        
        panelButtons.add(btnAjouter);
        panelButtons.add(btnRetourner);
        panelButtons.add(btnSupprimer);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Information"));
        JLabel lblInfo = new JLabel("Pénalité de retard : " + PENALITE_PAR_JOUR + " DH par jour. Sélectionnez un emprunt et cliquez sur 'Retourner Livre' pour enregistrer le retour.");
        panelInfo.add(lblInfo);

        tableEmprunts = new JTable();
        tableEmprunts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(panelInfo, BorderLayout.NORTH);
        panelCenter.add(new JScrollPane(tableEmprunts), BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);

        listerEmprunts();
    }

    private void ajouterEmprunt() {
        Emprunt e = new Emprunt();
        e.setEtudiant((Etudiant) cbEtudiants.getSelectedItem());
        e.setLivre((Livre) cbLivres.getSelectedItem());
        e.setDateEmprunt((Date) dateEmpruntSpinner.getValue());
        e.setDateRetourPrevue((Date) dateRetourSpinner.getValue());

        if (empruntController.ajouterEmprunt(e)) {
            JOptionPane.showMessageDialog(this, "Emprunt ajouté !");
            listerEmprunts();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur !");
        }
    }

    private void retournerLivre() {
        int selectedRow = tableEmprunts.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt.", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Emprunt emprunt = listeEmprunts.get(selectedRow);
        
        if (emprunt.getDateRetourEffective() != null) {
            JOptionPane.showMessageDialog(this, "Ce livre a déjà été retourné.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Confirmer le retour du livre ?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            emprunt.setDateRetourEffective(new Date());
            
            if (empruntController.retourLivre(emprunt)) {
                String message = "Livre retourné avec succès !";
                if (emprunt.getPenalite() > 0) {
                    message += "\nPénalité de retard : " + emprunt.getPenalite() + " DH";
                }
                JOptionPane.showMessageDialog(this, message, "Retour effectué", JOptionPane.INFORMATION_MESSAGE);
                listerEmprunts();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors du retour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerEmprunt() {
        int selectedRow = tableEmprunts.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt.", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Emprunt emprunt = listeEmprunts.get(selectedRow);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cet emprunt ?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            empruntController.supprimerEmprunt(emprunt.getId());
            JOptionPane.showMessageDialog(this, "Emprunt supprimé !");
            listerEmprunts();
        }
    }

    private void listerEmprunts() {
        listeEmprunts = empruntController.listerEmprunts();
        String[] columns = {"ID", "Étudiant", "Livre", "Date Emprunt", "Date Retour Prévue", "Date Retour", "Pénalité (DH)", "Statut"};
        Object[][] data = new Object[listeEmprunts.size()][8];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < listeEmprunts.size(); i++) {
            Emprunt e = listeEmprunts.get(i);
            data[i][0] = e.getId();
            data[i][1] = e.getEtudiant().getNom() + " " + e.getEtudiant().getPrenom();
            data[i][2] = e.getLivre().getTitre();
            data[i][3] = e.getDateEmprunt() != null ? dateFormat.format(e.getDateEmprunt()) : "";
            data[i][4] = e.getDateRetourPrevue() != null ? dateFormat.format(e.getDateRetourPrevue()) : "";
            data[i][5] = e.getDateRetourEffective() != null ? dateFormat.format(e.getDateRetourEffective()) : "";
            data[i][6] = e.getPenalite();
            
            if (e.getDateRetourEffective() != null) {
                data[i][7] = "Retourné";
            } else if (e.getDateRetourPrevue() != null && new Date().after(e.getDateRetourPrevue())) {
                data[i][7] = "En retard";
            } else {
                data[i][7] = "En cours";
            }
        }

        tableEmprunts.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
