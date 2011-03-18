package fr.univartois.ili.fsnet.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import fr.univartois.ili.fsnet.entities.SocialGroup;

public class GetSocialGroupInfosTag extends SimpleTagSupport {

	private SocialGroup socialGroup;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		out.print("<a href=\"DisplayGroup.do?idGroup=");
		out.print(socialGroup.getId());
		out.print("\">");
		out.print(socialGroup.getName());
		out.print("</a>");

	}

	public SocialGroup getSocialGroup() {
		return socialGroup;
	}

	public void setSocialGroup(SocialGroup socialGroup) {
		this.socialGroup = socialGroup;
	}

}
