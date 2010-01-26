package fr.univartois.ili.fsnet.admin.tags.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class AbstractSingleLoopTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> infos = new HashMap<String, Object>();

    /**
     * Method to implement in subclass in order to send parameter(s) to the jsp
     *
     * @param infos
     *            The map that should be populated. Each <i>key</i> represents
     *            the parameter in page scope that can be retrieved by
     *            ${<i>key</i>} in jsp
     */
    public abstract void retrieveInfos(Map<String, Object> infos);

    @Override
    public final int doStartTag() throws JspException {
        retrieveInfos(infos);
        for (Map.Entry<String, Object> info : infos.entrySet()) {
            pageContext.setAttribute(info.getKey(), info.getValue(),
                    PageContext.PAGE_SCOPE);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public final int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public final int doEndTag() throws JspException {
        for (Map.Entry<String, Object> info : infos.entrySet()) {
            pageContext.removeAttribute(info.getKey(), PageContext.PAGE_SCOPE);
        }
        infos.clear();
        return SKIP_BODY;
    }
}
