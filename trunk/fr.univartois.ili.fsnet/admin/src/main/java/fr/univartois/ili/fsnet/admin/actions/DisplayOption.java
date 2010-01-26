/*
 *  Copyright (C) 2010 Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univartois.ili.fsnet.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.ModuleUtils;

import fr.univartois.ili.fsnet.admin.utils.FSNetConfiguration;

/**
 * Put options in requestScope and forward user to Options.jsp
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class DisplayOption extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("OptionsForm", getServerOptions(request));
		return mapping.findForward("success");
	}

	/**
	 * Get options in preferences file
	 * 
	 * @param request
	 * @return a dynaActionForm dynamically instanciated
	 */
	private DynaActionForm getServerOptions(HttpServletRequest request) {
		FSNetConfiguration conf = FSNetConfiguration.getInstance();

		ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(
				request, getServlet().getServletContext());
		FormBeanConfig formConfig = moduleConfig
				.findFormBeanConfig("OptionsForm");
		DynaActionFormClass dynaClass = DynaActionFormClass
				.createDynaActionFormClass(formConfig);
		DynaActionForm myForm = null;
		try {
			myForm = (DynaActionForm) dynaClass.newInstance();
			myForm.set("hote", conf.getFrom());
			if (conf.isTLSEnabled()) {
				myForm.set("enableTLS", "on");
			} else {
				myForm.set("enableTLS", "");
			}
			if (conf.isSSLEnabled()) {
				myForm.set("enableSSL", "on");
			} else {
				myForm.set("enableSSL", "");
			}
			if (conf.isAuthenticationEnabled()) {
				myForm.set("authenticate", "on");
				myForm.set("username", conf.getUsername());
				myForm.set("motdepasse", conf.getPassword());
			} else {
				myForm.set("authenticate", "");
				myForm.set("username", "");
				myForm.set("motdepasse", "");
			}
			myForm.set("serveursmtp", conf.getSMTPHost());
			myForm.set("port", conf.getSMTPPort());			
			myForm.set("adressefsnet", conf.getFSNetWebAddress());
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return myForm;
	}
}
