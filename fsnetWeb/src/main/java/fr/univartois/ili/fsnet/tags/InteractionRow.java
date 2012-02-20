package fr.univartois.ili.fsnet.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Interaction;

public class InteractionRow extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> unreadInteractionsId;
	private int currentInteractionId;

	public InteractionRow() {

	}

	public List<Integer> getUnreadInteractionsId() {
		return unreadInteractionsId;
	}

	public void setUnreadInteractionsId(List<Integer> unreadInteractionsId) {
		this.unreadInteractionsId = unreadInteractionsId;
	}

	public int getCurrentInteractionId() {
		return currentInteractionId;
	}

	public void setCurrentInteractionId(int currentInteractionId) {
		this.currentInteractionId = currentInteractionId;
	}

	@Override
	public int doStartTag() throws JspException {


		JspWriter writer = pageContext.getOut();
		try {
			if(unreadInteractionsId.contains(currentInteractionId)){
				writer.println("<tr class='notReed'>");
			}else{
				writer.println("<tr>");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.println("</tr>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

}
