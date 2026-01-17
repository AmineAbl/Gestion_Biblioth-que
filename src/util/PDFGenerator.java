package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;


import java.io.FileOutputStream;
import java.util.List;
import model.Livre;

public class PDFGenerator {

    public static void genererLivresPDF(String filename, List<Livre> livres) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            Paragraph titre = new Paragraph("Liste des Livres",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE));
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.addCell("ID");
            table.addCell("Titre");
            table.addCell("Auteur");
            table.addCell("ISBN");
            table.addCell("Catégorie");

            for (Livre l : livres) {
                table.addCell(String.valueOf(l.getId()));
                table.addCell(l.getTitre());
                table.addCell(l.getAuteur());
                table.addCell(l.getIsbn());
                table.addCell(l.getCategorie().getNom());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
