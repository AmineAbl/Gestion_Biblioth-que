package presentation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import controller.StatistiqueController;
import controller.LivreController;
import util.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

public class StatistiquesFrame extends JFrame {

    private StatistiqueController statController;
    private LivreController livreController;

    public StatistiquesFrame() {
        setTitle("Statistiques");
        setSize(1200, 800);
        setLocationRelativeTo(null);

        statController = new StatistiqueController();
        livreController = new LivreController();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnExportPDF = new JButton("Exporter Liste des Livres (PDF)");
        btnExportPDF.addActionListener(e -> exporterPDF());
        topPanel.add(btnExportPDF);

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        DefaultCategoryDataset datasetLivres = new DefaultCategoryDataset();
        Map<String, Integer> livresPop = statController.livresPopulaires();
        for (String titre : livresPop.keySet()) {
            datasetLivres.addValue(livresPop.get(titre), "Livres", titre);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Livres les plus populaires",
                "Titre",
                "Nombre d'emprunts",
                datasetLivres
        );

        ChartPanel chartPanelLivres = new ChartPanel(barChart);

        DefaultPieDataset datasetFiliere = new DefaultPieDataset();
        Map<String, Integer> empruntsFiliere = statController.empruntsParFiliere();
        for (String filiere : empruntsFiliere.keySet()) {
            datasetFiliere.setValue(filiere, empruntsFiliere.get(filiere));
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Emprunts par filière",
                datasetFiliere,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})",
                new DecimalFormat("0"),
                new DecimalFormat("0.0%")
        ));

        ChartPanel chartPanelFiliere = new ChartPanel(pieChart);

        DefaultCategoryDataset datasetFrequentation = new DefaultCategoryDataset();
        Map<String, Integer> frequentation = statController.frequentationParJour();
        for (String jour : frequentation.keySet()) {
            datasetFrequentation.addValue(frequentation.get(jour), "Visites", jour);
        }

        JFreeChart barChartFrequentation = ChartFactory.createBarChart(
                "Fréquentation par jour",
                "Jour",
                "Nombre de visites",
                datasetFrequentation
        );

        ChartPanel chartPanelFrequentation = new ChartPanel(barChartFrequentation);

        mainPanel.add(chartPanelLivres);
        mainPanel.add(chartPanelFiliere);
        mainPanel.add(chartPanelFrequentation);
        mainPanel.add(new JPanel());

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void exporterPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le PDF");
        fileChooser.setSelectedFile(new java.io.File("liste_livres.pdf"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            try {
                LivreController controller = new LivreController();
                PDFGenerator.genererLivresPDF(filePath, controller.listerLivres());
                JOptionPane.showMessageDialog(this, 
                    "PDF exporté avec succès !\n" + filePath, 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'export : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
