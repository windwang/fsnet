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

import java.util.prefs.Preferences;
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

/**
 * Put options in requestScope and forward user to Options.jsp
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class DisplayOption extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("OptionsForm", getServerOptions(request));
        return mapping.findForward("success");
    }

    /**
     * Get options in preferences file
     * @param request
     * @return a dynaActionForm dynamically instanciated
     */
    private DynaActionForm getServerOptions(HttpServletRequest request) {
        Preferences appPref = Preferences.userNodeForPackage(ModifyOptions.class);

        ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request,
                getServlet().getServletContext());
        FormBeanConfig formConfig = moduleConfig.findFormBeanConfig("OptionsForm");
        DynaActionFormClass dynaClass = DynaActionFormClass.createDynaActionFormClass(formConfig);
        DynaActionForm myForm = null;
        try {
            myForm = (DynaActionForm) dynaClass.newInstance();
            myForm.set("hote", appPref.get("hote", ""));
            myForm.set("serveursmtp", appPref.get("serveursmtp", ""));
            myForm.set("motdepasse", appPref.get("motdepasse", ""));
            myForm.set("adressefsnet", appPref.get("adressefsnet", ""));
            myForm.set("port", appPref.getInt("port", 0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return myForm;
    }
}
