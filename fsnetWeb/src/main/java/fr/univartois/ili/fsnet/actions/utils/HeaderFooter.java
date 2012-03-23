package fr.univartois.ili.fsnet.actions.utils;


import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
	public void onEndPage (PdfWriter writer, Document document) {
        com.itextpdf.text.Rectangle rect = writer.getBoxSize("art");

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("Page %d", writer.getPageNumber())),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }

}
