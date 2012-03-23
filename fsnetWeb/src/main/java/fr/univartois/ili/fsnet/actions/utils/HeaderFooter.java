package fr.univartois.ili.fsnet.actions.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
	public void onEndPage (PdfWriter writer, Document document) {
        com.itextpdf.text.Rectangle rect = writer.getBoxSize("art");
        PdfPTable table=new PdfPTable(2);
		PdfPCell cell = new PdfPCell (new Paragraph ("odd header",new Font(Font.FontFamily.TIMES_ROMAN, 18,
    			Font.BOLD, BaseColor.DARK_GRAY)));
		table.addCell(cell);
        
		 Font FONT = new Font(FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));
		
        switch(writer.getPageNumber() % 2) {
        case 0:
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, new Phrase("even header",new Font(Font.FontFamily.TIMES_ROMAN, 18,
                			Font.BOLD, BaseColor.DARK_GRAY)),
                    rect.getRight(), rect.getTop(), 0);
             
            break;
        case 1:
        	
//            ColumnText.showTextAligned(writer.getDirectContent(),
//                    Element.ALIGN_LEFT, new Phrase("odd header",new Font(Font.FontFamily.TIMES_ROMAN, 18,
//                			Font.BOLD, BaseColor.DARK_GRAY)),
//                    rect.getLeft(), rect.getTop(), 0);
        	 ColumnText.showTextAligned(writer.getDirectContentUnder(),
                     Element.ALIGN_CENTER, new Phrase("FSNET", FONT),
                     297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
            break;
        }
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("page %d", writer.getPageNumber())),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }

}
