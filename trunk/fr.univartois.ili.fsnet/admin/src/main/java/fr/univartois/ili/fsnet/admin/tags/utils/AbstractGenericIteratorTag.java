package fr.univartois.ili.fsnet.admin.tags.utils;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class AbstractGenericIteratorTag<T> extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String IS_LAST_VAR_NAME = "isLast";

	private static final String IS_FIRST_VAR_NAME = "isFirst";

	private String var;

	private Iterator<T> iterator;

	/**
	 * Subclass implementation must return an iterator that will be used to
	 * iterate results
	 * 
	 * @return an iterator
	 */
	protected abstract Iterator<T> initIterator();

	/**
	 * Should return a value that represents the variable name pushed in jsp
	 * 
	 * @return the default var name to put in page scope
	 */
	public abstract String getDefaultVarName();

	private boolean isFirst;

	@Override
	public final int doStartTag() throws JspException {
		int action = SKIP_BODY;
		isFirst = true;
		if (var == null) {
			var = getDefaultVarName();
		}
		iterator = initIterator();
		if (updateContext()) {
			action = EVAL_BODY_INCLUDE;
		}
		return action;
	}

	@Override
	public final int doAfterBody() throws JspException {
		int action = SKIP_BODY;
		if (updateContext()) {
			action = EVAL_BODY_AGAIN;
		}
		return action;
	}

	@Override
	public final int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		pageContext.removeAttribute(IS_FIRST_VAR_NAME);
		pageContext.removeAttribute(IS_LAST_VAR_NAME);
		return SKIP_BODY;
	}

	public final boolean updateContext() {
		boolean hasNextProposal = iterator.hasNext();
		if (hasNextProposal) {
			pageContext.setAttribute(var, iterator.next());
			if (iterator.hasNext()) {
				pageContext.setAttribute(IS_LAST_VAR_NAME, false);
			} else {
				pageContext.setAttribute(IS_LAST_VAR_NAME, true);
			}
			if (isFirst) {
				isFirst = false;
				pageContext.setAttribute(IS_FIRST_VAR_NAME, true);
			} else {
				pageContext.setAttribute(IS_FIRST_VAR_NAME, false);
			}
		}
		return hasNextProposal;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

}
