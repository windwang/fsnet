package fr.univartois.ili.fsnet.actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.TableHeader;
import fr.univartois.ili.fsnet.actions.utils.HeaderFooter;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.facade.CvFacade;

/**
 * @author Aich ayoub
 * 
 */
public class GenerateCv extends ActionSupport {
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 26,
			Font.BOLD, BaseColor.BLUE);
	
	private static Font NameFont = new Font(Font.FontFamily.TIMES_ROMAN,
			16, Font.BOLDITALIC, BaseColor.BLACK);

	private static Font particularFont = new Font(Font.FontFamily.TIMES_ROMAN,
			12, Font.NORMAL, BaseColor.BLACK);

	private static Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14,
			Font.BOLD, BaseColor.WHITE);

	private EntityManager em = PersistenceProvider.createEntityManager();

	public static String dateToString(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);

	}

	// CV particulars
	/**
	 * 
	 * @param document
	 * @param meeting
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void addParticulars(Document document, Curriculum curriculum)
			throws DocumentException, MalformedURLException, IOException {

		Paragraph prefaceNom = new Paragraph(curriculum.getMember()
				.getFirstName() + " " + curriculum.getMember().getSurname(),NameFont);

		Paragraph prefaceSex = new Paragraph(curriculum.getMember().getSex(),particularFont);

		Paragraph prefaceAdresse = new Paragraph(curriculum.getMember()
				.getAdress() + "", particularFont);

		Paragraph prefacePays = new Paragraph(curriculum.getMember().getTown(),
				particularFont);

		Paragraph prefacecodePostale = new Paragraph(
				Integer.toString(curriculum.getMember().getPostCode()),
				particularFont);

		Paragraph prefaceEmail = new Paragraph(
				curriculum.getMember().getMail(), particularFont);

		Paragraph prefaceTelephone = new Paragraph(curriculum.getMember()
				.getNumberPhone(), particularFont);

		Paragraph prefaceBirthday = new Paragraph(dateToString(curriculum
				.getMember().getBirthDate()), particularFont);

		Paragraph prefaceSituation = new Paragraph(curriculum.getMember()
				.getSituationFamilly(), particularFont);

		document.add(prefaceNom);
		document.add(prefaceSex);
		document.add(prefaceSituation);
		document.add(prefaceBirthday);
		document.add(prefaceAdresse);
		document.add(prefacecodePostale);
		document.add(prefacePays);
		document.add(prefaceEmail);
		document.add(prefaceTelephone);

	}

	// create title of document
	/**
	 * 
	 * @param document
	 * @param curriculum
	 * @throws DocumentException
	 */
	private static void addTitlePage(Document document, Curriculum curriculum)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 3);
		// Lets write a big header
		preface.add(new Paragraph(curriculum.getTitleCv(), titleFont));
		preface.setAlignment(Element.ALIGN_CENTER);

		document.add(preface);

	}

	// add one empty line
	/**
	 * 
	 * @param paragraph
	 * @param number
	 */
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	// CV Picture
	public static void onPageEvent(Document document)
			throws MalformedURLException, IOException, DocumentException {
		// TODO

	}

	// CV separate
	/**
	 * 
	 * @param document
	 * @throws DocumentException
	 */
	public static void addseparate(Document document) throws DocumentException {

		PdfPTable table = new PdfPTable(2);
		PdfPCell cell = new PdfPCell(new Paragraph(""));
		cell.setColspan(2);
		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);
		table.setSpacingBefore(10);
		table.setWidthPercentage(100);
		document.add(table);

	}

	// add the first section
	/**
	 * 
	 * @param document
	 * @param curriculum
	 * @throws DocumentException
	 */
	public static void addFirstSection(Document document, Curriculum curriculum)
			throws DocumentException {

		List overview = new List(false, 10);
		if(curriculum.getTrains().size()!=0){
			document.add(styleSection("Expériences Professionnelles"));
			for (int i = 0; i < curriculum.getTrains().size(); i++) {

				overview.add(curriculum.getTrains().get(i).getIdTraining()
						.getName()
						+ " à "
						+ curriculum.getTrains().get(i).getIdTraining()
								.getMyEst().getName()
						+ " du "
						+ dateToString(curriculum.getTrains().get(i)
								.getStartDate())
						+ " au "
						+ dateToString(curriculum.getTrains().get(i)
								.getEndDate()));

			}

			document.add(overview);
		}

	}

	// add the second section
	/**
	 * 
	 * @param document
	 * @param curriculum
	 * @throws DocumentException
	 */
	public static void addSecondSection(Document document, Curriculum curriculum)
			throws DocumentException {

		List overview = new List(false, 10);
		if(curriculum.getDegs().size()!=0){
			document.add(styleSection("Diplômes"));

			for (int i = 0; i < curriculum.getDegs().size(); i++) {
				overview.add("Niveau d'études: "+curriculum.getDegs().get(i).getDegree()
						.getStudiesLevel()+", Etablissement: "+
						curriculum.getDegs().get(i).getDegree().getEts().getName()
						+ " Dilplôme obtenu du "
						+ curriculum.getDegs().get(i).getStartDate()
						+ " au " + curriculum.getDegs().get(i).getEndDate());

			}
			document.add(overview);
		}
	}

	// add the tirth section
	/**
	 * 
	 * @param document
	 * @param curriculum
	 * @throws DocumentException
	 */
	public static void addtirthSection(Document document, Curriculum curriculum)
			throws DocumentException {

		List overview = new List(false, 10);

		if(curriculum.getMyFormations().size()!=0){
			document.add(styleSection("Formation"));

			for (int i = 0; i < curriculum.getMyFormations().size(); i++) {

				overview.add(curriculum.getMyFormations().get(i)
						.getIdFormation().getName()
						+ " obtenu à "
						+ curriculum.getMyFormations().get(i).getObtainedDate()
						+ " à "
						+ curriculum.getMyFormations().get(i).getIdFormation()
								.getEts().getName());

			}
			document.add(overview);
		}
	}

	// add the fourth section
	/**
	 * 
	 * @param document
	 * @param curriculum
	 * @throws DocumentException
	 */
	public static void addfifthSection(Document document, Curriculum curriculum)
			throws DocumentException {
		List overview = new List(false, 10);

		if(curriculum.getMember().getLanguages().size()!=0){
			document.add(styleSection("Langues"));

			@SuppressWarnings("rawtypes")
			Iterator iterator = curriculum.getMember().getLanguages()
					.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				overview.add(key + "     niveau: " + value);

			}

			document.add(overview);
		}

	}

	// add the fifth section
	/**
	 * 
	 * @param document
	 * @param curriculum
	 * @throws DocumentException
	 */
	public static void addfourthSection(Document document, Curriculum curriculum)
			throws DocumentException {
		List overview = new List(false, 10);
		if(curriculum.getHobs().size()!=0){
			document.add(styleSection("Loisirs"));
			for (int i = 0; i < curriculum.getHobs().size(); i++) {
				overview.add(curriculum.getHobs().get(i).getName());

			}
			document.add(overview);
		}
	}

	// Style of Section
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static PdfPTable styleSection(String string) {

		PdfPTable table = new PdfPTable(2);
		PdfPCell cell = new PdfPCell(new Paragraph(string, sectionFont));
		cell.setColspan(2);
		cell.setBackgroundColor(BaseColor.DARK_GRAY);
		table.addCell(cell);
		table.setSpacingBefore(20);
		table.setWidthPercentage(110);

		return table;
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
	public String download(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		long id = Integer.parseInt(request.getParameter("idCv"));
		CvFacade cvFacade = new CvFacade(em);
		Curriculum curriculum = cvFacade.getCurriculum(id);
		PdfWriter writer;
		try {

			String text = request.getParameter("text");
			if (text == null || text.trim().length() == 0) {
				text = "You didn't enter any text.";
			}

			Document document = new Document();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			writer=PdfWriter.getInstance(document, baos);
			writer.setBoxSize("art", new Rectangle(36, 54, 559, 810));
			HeaderFooter eventFooter=new HeaderFooter();
			writer.setPageEvent(eventFooter);
			
			TableHeader eventHeader = new TableHeader();
			writer.setPageEvent(eventHeader); 
			document.open();

			addParticulars(document, curriculum);
			addTitlePage(document, curriculum);
			addseparate(document);
			addFirstSection(document, curriculum);
			addSecondSection(document, curriculum);
			addtirthSection(document, curriculum);
			addfifthSection(document, curriculum);
			addfourthSection(document, curriculum);

			document.close();

			// setting some response headers
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ curriculum.getMember().getFirstName() + "_"
					+ curriculum.getMember().getSurname() + "_CV_"
					+ curriculum.getMember().getBirthDate() + ".pdf");
			// setting the content type
			response.setContentType("application/pdf");
			// the contentlength
			response.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

			return SUCCESS;
		} catch (DocumentException e) {
			throw new IOException(e.getMessage(),e);
		}
	}

}
