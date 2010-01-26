package fr.univartois.ili.fsnet.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import fr.univartois.ili.fsnet.admin.utils.FSNetConfiguration;

public class ModifyOptions extends Action {

	private static final int DEFAULT_SMTP_PORT = 25;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse arg3) throws Exception {
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		conf.reset();
		if ("".equals(dynaform.get("enableTLS"))) {
			conf.disableTLS();
		} else {
			conf.enableTLS();
		}
		if ("".equals(dynaform.get("enableSSL"))) {
			conf.disableSSL();
		} else {
			conf.enableSSL();
		}
		if ("".equals(dynaform.get("authenticate"))) {
			conf.disableAuthentication();
		} else {
			conf.enableAuthentication((String) dynaform.get("username"),
					(String) dynaform.get("motdepasse"));
		}

		conf.setFrom((String) dynaform.get("hote"));
		conf.setSMTPHost((String) dynaform.get("serveursmtp"));
		int port;
		try {
			port = Integer.parseInt((String) dynaform.get("port"));
		} catch (NumberFormatException e) {
			port = DEFAULT_SMTP_PORT;
		}
		conf.setSMTPPort(port);

		conf.setFSNetWebAddress((String) dynaform.get("adressefsnet"));
		conf.setMailConfigured(true);
		conf.save();

		return mapping.findForward("continue");
	}
}
