package fr.univartois.ili.fsnet.tags;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PlaceHolderTag extends SimpleTagSupport {
	
	private String id;
	private String value;
	
	public PlaceHolderTag() {}
	
	public String getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void doTag() throws JspException {
		JspWriter out = getJspContext().getOut();
		
		try {
			out.print("<script type=\"text/javascript\">");
			out.print("document.getElementById('" + id + "').setAttribute('placeholder','" + value + "');");
			out.println("</script>");
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}
}
