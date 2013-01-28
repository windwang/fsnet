package fr.univartois.ili.fsnet.tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ChangeAttributeTag  extends SimpleTagSupport { 

	private String id;
	private String property;
	private String value;

	public ChangeAttributeTag() {}

	public String getId() {
		return id;
	}

	public String getProperty() {
		return property;
	}

	public String getValue() {
		return value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException {
		JspWriter out = getJspContext().getOut();
			
		try {
			out.print("<script type=\"text/javascript\">");
			out.print("document.getElementById('" + id + "')." + property + "='" + value + "';");
			out.println("</script>");
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}
}
