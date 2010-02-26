package fr.univartois.ili.fsnet.tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NoXMLTag extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		getJspBody().invoke(writer);
		String content = writer.toString().replaceAll("<.*?>", "");
		getJspContext().getOut().print(content);
	}
	
}
