package fr.univartois.ili.fsnet.tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SubstringTag extends SimpleTagSupport {
	
	private int beginIndex;
	
	private int endIndex;
	
	@Override
	public void doTag() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		getJspBody().invoke(writer);
		String content = writer.toString();
		if (content.length() > endIndex) {
			content = content.substring(beginIndex, endIndex);
		} else {
			content = content.substring(beginIndex, content.length());
		}
		getJspContext().getOut().print(content);
	}

	/**
	 * @return the beginIndex
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param beginIndex the beginIndex to set
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	

}
