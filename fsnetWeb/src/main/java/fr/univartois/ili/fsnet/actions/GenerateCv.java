package fr.univartois.ili.fsnet.actions;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

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
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.facade.CvFacade;

/**
 * 
 * @author Ayoub AICH
 *
 */

public class GenerateCv extends MappingDispatchAction{
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	//path of the file is defined on properties file
	private static String file = "../File.pdf";
	
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD, BaseColor.DARK_GRAY);
	
	private static Font particularFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.BLACK);
	
	private static Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14,
			Font.BOLD, BaseColor.WHITE);
	
	
	EntityManager em = PersistenceProvider.createEntityManager();
	
	

	public static String dateToString(Date date) {
	return formatter.format(date);

	}
	//CV particulars
	/**
	 * 
	 * @param document
	 * @param meeting
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void addParticulars(Document document,Curriculum curriculum) throws DocumentException, MalformedURLException, IOException {
		
		Paragraph prefaceNom = new Paragraph(curriculum.getMember().getFirstName()+" "+curriculum.getMember().getSurname());

		Paragraph prefaceAdresse = new Paragraph(curriculum.getMember().getAdress()+"",particularFont);
		
		Paragraph prefaceEmail = new Paragraph(curriculum.getMember().getMail(),particularFont);
		
		Paragraph prefaceTelephone = new Paragraph(curriculum.getMember().getNumberPhone(),particularFont);
		
		Paragraph prefaceBirthday = new Paragraph(dateToString(curriculum.getMember().getBirthDate()),particularFont);
		
		Paragraph prefaceSituation = new Paragraph(curriculum.getMember().getNumberPhone(),particularFont);
		
		document.add(prefaceNom);
		document.add(prefaceSituation);
		document.add(prefaceBirthday);
		document.add(prefaceAdresse);
		document.add(prefaceEmail);
		document.add(prefaceTelephone);
		
		}
	
	
	//create title of document
	private static void addTitlePage(Document document,Curriculum curriculum)
			throws DocumentException {
		
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 3);
		// Lets write a big header
		preface.add(new Paragraph(curriculum.getTitleCv(), titleFont));
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
	public static void addFirstSection(Document document,Curriculum curriculum) throws DocumentException{
		
		 List overview = new List(false, 10);
		 
		 document.add(styleSection("Expériences Professionnelles"));
		 for(int i=0;i<curriculum.getTrains().size();i++){
		 	 
		 overview.add(curriculum.getTrains().get(i).getIdTraining().getName()+" à "+
				 curriculum.getTrains().get(i).getIdTraining().getMyEst().getName()+" du "+
				 dateToString(curriculum.getTrains().get(i).getStartDate())+
				 " au "+ dateToString(curriculum.getTrains().get(i).getEndDate()));
		 
		 }
		 document.add(overview);
		 
		 
	}
	
	//add the second section
	public static void addSecondSection(Document document,Curriculum curriculum) throws DocumentException{
		
		
		 List overview = new List(false, 10);
		 document.add(styleSection("Diplômes"));
		 
		 for(int i=0;i<curriculum.getDegs().size();i++){ 
		 overview.add(curriculum.getDegs().get(i).getDegree().getStudiesLevel()+" du "+
				 curriculum.getDegs().get(i).getStartDate()+" au "+
				 curriculum.getDegs().get(i).getEndDate());
		 
		 }
		 document.add(overview);
		 
	}
	
	
	//add the tirth section
	public static void addtirthSection(Document document,Curriculum curriculum) throws DocumentException{
		
		 List overview = new List(false, 10);
		 document.add(styleSection("Formation"));
		 
		 for(int i=0;i<curriculum.getMyFormations().size();i++){ 
			
			 overview.add(curriculum.getMyFormations().get(i).getIdFormation().getName()+" obtenu à "+
					 curriculum.getMyFormations().get(i).getObtainedDate()+" à "+
					 curriculum.getMyFormations().get(i).getIdFormation().getEts().getName()
					 );
			 
			 }
		 document.add(overview);
		
	}
	//add the fourth section
	
	public static void addfifthSection(Document document,Curriculum curriculum) throws DocumentException{
		 List overview = new List(false, 10);
		document.add(styleSection("Langues"));
		 
			 @SuppressWarnings("rawtypes")
			 Iterator iterator = curriculum.getMember().getLanguages().entrySet().iterator();
			 while (iterator.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry) iterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					overview.add(key+"     niveau: "+value) ; 
					
	         }
			 
		 document.add(overview);
	}
	
	//add the fifth section
	public static void addfourthSection(Document document,Curriculum curriculum) throws DocumentException{
		 List overview = new List(false, 10);
		document.add(styleSection("Loisirs"));
		 for(int i=0;i<curriculum.getHobs().size();i++){ 
			 overview.add(curriculum.getHobs().get(i).getName()
					 );
			 
			 } 
		 document.add(overview);
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
		
		long id=Integer.parseInt(request.getParameter("idCv"));
		
		
		CvFacade cvFacade = new CvFacade(em);
		Curriculum curriculum = cvFacade.getCurriculum(id);
		
		
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
			addParticulars(document,curriculum);
			addTitlePage(document,curriculum);
			addseparate(document);
			addFirstSection(document,curriculum);
			addSecondSection(document,curriculum);
			addtirthSection(document,curriculum);
			addfifthSection(document,curriculum);
			addfourthSection(document,curriculum);
			
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
