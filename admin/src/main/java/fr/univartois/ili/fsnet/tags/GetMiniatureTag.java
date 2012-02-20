package fr.univartois.ili.fsnet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author FSNet
 * 
 */
public class GetMiniatureTag extends SimpleTagSupport {

	private SocialEntity socialEntity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if (socialEntity.getIsEnabled()) {
			out.print("<a href=\"DisplayProfile.do?id=");
			out.print(socialEntity.getId());
			out.print("\" class=\"miniature\">");
		}
		out.print("<img src=\"avatar/");
		out.print(socialEntity.getId());
		out.print(".png\"></img>");
		if (socialEntity.getIsEnabled()) {
			out.print("</a>");
		}
	}

	/**
	 * @return
	 */
	public SocialEntity getSocialEntity() {
		return socialEntity;
	}

	/**
	 * @param socialEntity
	 */
	public void setSocialEntity(SocialEntity socialEntity) {
		this.socialEntity = socialEntity;
	}

}
