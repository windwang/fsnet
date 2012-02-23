package fr.univartois.ili.fsnet.actions;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.facade.MeetingFacade;

/**
 * 
 * @author Ayoub AICH
 *
 */

public class GenerateCv extends MappingDispatchAction{

	//path of the file is defined on properties file
	private static String file = "../File.pdf";
	
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD, BaseColor.DARK_GRAY);
	
	private static Font particularFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.BLACK);
	
	private static Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14,
			Font.BOLD, BaseColor.WHITE);
	
	
	EntityManager em = PersistenceProvider.createEntityManager();
	
	
	//CV particulars
	/**
	 * 
	 * @param document
	 * @param meeting
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void addParticulars(Document document,Meeting meeting) throws DocumentException, MalformedURLException, IOException {
		
		Paragraph prefaceNom = new Paragraph(meeting.getAddress().getAddress()+" "+meeting.getAddress().getCity());

		Paragraph prefaceAdresse = new Paragraph("Adresse"+"",particularFont);
		
		Paragraph prefaceEmail = new Paragraph("email",particularFont);
		
		Paragraph prefaceTelephone = new Paragraph("téléphone",particularFont);
		
		
		document.add(prefaceNom);
		document.add(prefaceAdresse);
		document.add(prefaceEmail);
		document.add(prefaceTelephone);
		
		}
	
	
	//create title of document
	private static void addTitlePage(Document document)
			throws DocumentException {
		
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 3);
		// Lets write a big header
		preface.add(new Paragraph("Ingénieur d'études et développement", titleFont));
		preface.setAlignment(Element.ALIGN_CENTER);
		
		document.add(preface);
		
	}
	
	//add one empty line
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	
	
	//CV Picture
	public static void onPageEvent(Document document) throws MalformedURLException, IOException, DocumentException {
		document.open();
		Image img=Image.getInstance("/homelocal/aa/ILI4.JAVA/FSNET-NOTE/example.jpg");
		img.setAbsolutePosition(500,720);
	    document.add(img);
	    
	  }
	
	//CV separate
	
	public static void addseparate(Document document) throws DocumentException{
		
		
		PdfPTable table=new PdfPTable(2);
		PdfPCell cell = new PdfPCell (new Paragraph (""));
		cell.setColspan (2);
		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);
		table.setSpacingBefore(10);
		table.setWidthPercentage(100);
		document.add(table);
		
	}
	
	//add the first section
	public static void addFirstSection(Document document) throws DocumentException{
		
		 List overview = new List(false, 10);
		 overview.add("stage Atos");
		 overview.add("stage GFI");
		 overview.add("stage Proxiad");
		 document.add(styleSection("Expériences Professionnelles"));
		 document.add(overview);
		 
	}
	
	//add the second section
	public static void addSecondSection(Document document) throws DocumentException{
		
		
		 List overview = new List(false, 10);
		 overview.add(new ListItem("JAVA"));
		 overview.add("PHP");
		 overview.add("MYSQL");
	
		 document.add(styleSection("Compétences techniques"));
		 document.add(overview);
	}
	
	
	//add the tirth section
	public static void addtirthSection(Document document) throws DocumentException{
		
		 List overview = new List(false, 10);
		 overview.add(new ListItem("Licence Informatique"));
		 overview.add("Master 1 Informatique");
		 overview.add("Master 2 ILI");
		 
		 document.add(styleSection("Formation"));
		 document.add(overview);
	}
	
	//add the fourth section
	public static void addfourthSection(Document document) throws DocumentException{
		
		document.add(styleSection("Divers"));
	}
	
	
	//Style of Section
	public static PdfPTable styleSection(String string){
		
		
		PdfPTable table=new PdfPTable(2);
		PdfPCell cell = new PdfPCell (new Paragraph (string,sectionFont));
		cell.setColspan (2);
		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);
		table.setSpacingBefore(20);
		table.setWidthPercentage(110);
		
		return table;
	}
	
	
	/**
	 * 
	 * @param request
	 */
	public void generer(HttpServletRequest request){
		
		int id=Integer.parseInt(request.getParameter("idCv"));
		System.out.println("voilaaaaaaaaaaaaa"+id);
		
		MeetingFacade meetingFacade = new MeetingFacade(em);
		Meeting meeting = meetingFacade.getMeeting(id);
		
		
		Rectangle pageSize=new Rectangle(595,842);
		Document document = new Document(pageSize);
		PdfWriter writer;
		PdfContentByte under;
		try {
			Image img=Image.getInstance("/homelocal/aa/ILI4.JAVA/FSNET-NOTE/example.jpg");
			
			FileOutputStream f=new FileOutputStream(file);
			writer=PdfWriter.getInstance(document, f);
			
			
			document.open();
			img.setAbsolutePosition(11,11);
			under=writer.getDirectContent();
			under.addImage(img);
			onPageEvent(document);
			addParticulars(document,meeting);
			addTitlePage(document);
			addseparate(document);
			addFirstSection(document);
			addSecondSection(document);
			addtirthSection(document);
			addfourthSection(document);
			System.out.println(document.getPageSize());
			
			
			
			document.close();
			System.out.println("fichier crée"+writer.toString());
			
		   
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward test(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		
		generer(request);
		String filexport = "../File.pdf";
		FileInputStream filename = null;
		ByteArrayOutputStream bos = null;
		FileChannel src = null;
		OutputStream os = null;
		
		filename = new FileInputStream(filexport);
		src = filename.getChannel();
		bos = new ByteArrayOutputStream((int)src.size());
		byte[] tab = new byte [(int)src.size()];
		
		// copy the pdf on byte Array
		filename.read(tab);
		bos.write(tab);
		
		// Fill here your bos with the stream PDF
	    response.setContentType("text/html, application/pdf");
	    // get the output stream
	 	os = response.getOutputStream();
	  
	    // write the pdf
	    os.write(bos.toByteArray(), 0, bos.size());
	    
	    // force to empty the cache
	 			os.flush();
	  
	    // close stream
	 			os.close();
	 			bos.close();
	 			src.close();
	 			filename.close();
	 			
		return mapping.findForward("success");
		
	}
	
	
}
