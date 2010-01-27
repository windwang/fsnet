package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.admin.utils.FSNetConfiguration;

public class ConfigureFSNet extends MappingDispatchAction {

	private static final int DEFAULT_SMTP_PORT = 25;

	public ActionForward editMailConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		DynaActionForm dynaform = (DynaActionForm) form; // NOSONAR
		try {
			dynaform.set("MailFrom", conf.getFrom());
			if (conf.isTLSEnabled()) {
				dynaform.set("enableTLS", "on");
			} else {
				dynaform.set("enableTLS", "");
			}
			if (conf.isSSLEnabled()) {
				dynaform.set("enableSSL", "on");
			} else {
				dynaform.set("enableSSL", "");
			}
			if (conf.isAuthenticationEnabled()) {
				dynaform.set("enableAuthentication", "on");
				dynaform.set("SMTPUsername", conf.getUsername());
				dynaform.set("SMTPPassword", conf.getPassword());
			} else {
				dynaform.set("enableAuthentication", "");
				dynaform.set("SMTPUsername", "");
				dynaform.set("SMTPPassword", "");
			}
			dynaform.set("SMTPPort", conf.getSMTPPort());
			dynaform.set("SMTPHost", conf.getSMTPHost());
			dynaform.set("FSNetWebURL", conf.getFSNetWebAddress());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("success");
	}

	public ActionForward saveMailConfiguration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
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
		if ("".equals(dynaform.get("enableAuthentication"))) {
			conf.disableAuthentication();
		} else {
			conf.enableAuthentication((String) dynaform.get("SMTPUsername"),
					(String) dynaform.get("SMTPPassword"));
		}

		conf.setFrom((String) dynaform.get("MailFrom"));
		conf.setSMTPHost((String) dynaform.get("SMTPHost"));
		int port;
		try {
			port = Integer.parseInt((String) dynaform.get("SMTPPort"));
		} catch (NumberFormatException e) {
			port = DEFAULT_SMTP_PORT;
		}
		conf.setSMTPPort(port);

		conf.setFSNetWebAddress((String) dynaform.get("FSNetWebURL"));
		conf.setMailConfigured(true);
		conf.save();

		return mapping.findForward("success");
	}

}
