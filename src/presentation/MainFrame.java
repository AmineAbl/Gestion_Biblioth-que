package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Gestion de Bibliothèque Universitaire");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Menu principal
        JMenuBar menuBar = new JMenuBar();

        JMenu menuGestion = new JMenu("Gestion");
        JMenuItem livreItem = new JMenuItem(new AbstractAction("Livres") {
            public void actionPerformed(ActionEvent e) {
                LivreForm livreForm = new LivreForm();
                livreForm.setVisible(true);
            }
        });
        JMenuItem etudiantItem = new JMenuItem(new AbstractAction("Étudiants") {
            public void actionPerformed(ActionEvent e) {
                EtudiantForm etudiantForm = new EtudiantForm();
                etudiantForm.setVisible(true);
            }
        });
        JMenuItem empruntItem = new JMenuItem(new AbstractAction("Emprunts") {
            public void actionPerformed(ActionEvent e) {
                EmpruntForm empruntForm = new EmpruntForm();
                empruntForm.setVisible(true);
            }
        });
        JMenuItem categorieItem = new JMenuItem(new AbstractAction("Catégories") {
            public void actionPerformed(ActionEvent e) {
                CategorieForm categorieForm = new CategorieForm();
                categorieForm.setVisible(true);
            }
        });
        JMenuItem statsItem = new JMenuItem(new AbstractAction("Statistiques") {
            public void actionPerformed(ActionEvent e) {
                StatistiquesFrame statsFrame = new StatistiquesFrame();
                statsFrame.setVisible(true);
            }
        });

        menuGestion.add(livreItem);
        menuGestion.add(etudiantItem);
        menuGestion.add(empruntItem);
        menuGestion.add(categorieItem);
        menuGestion.add(statsItem);

        menuBar.add(menuGestion);
        setJMenuBar(menuBar);

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon(getClass().getResource("/images/bibliotheque.jpg")).getImage();
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessiner l'image en arrière-plan (étirée pour remplir le panel)
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                
                // Ajouter un overlay semi-transparent pour améliorer la lisibilité
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Panel central avec texte
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue dans la Gestion de Bibliothèque");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sous-titre
        JLabel subLabel = new JLabel("Gérez vos livres, étudiants et emprunts facilement");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subLabel.setForeground(new Color(200, 200, 200));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(subLabel);

        backgroundPanel.add(textPanel);
        setContentPane(backgroundPanel);
    }
}
