package fr.univartois.ili.fsnet.admin.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;

public class ActionOptions extends Action {

    private static final Logger log = Logger.getLogger(ActionOptions.class.getName());

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse arg3) throws Exception {
        DynaActionForm dynaform = (DynaActionForm) form;
        try {
            setServerOptions(
                    (String) dynaform.get("serveursmtp"),
                    (String) dynaform.get("hote"),
                    (String) dynaform.get("motdepasse"),
                    (String) dynaform.get("adressefsnet"),
                    (Integer) dynaform.get("port"));
        } catch (BackingStoreException e) {
            log.log(Level.SEVERE, "Impossible d'enregistrer les preferences.");
            ActionErrors errors = new ActionErrors();
            errors.add("errors.optionSave", new ActionMessage("errors.optionSave"));

        }
        return mapping.findForward("continue");
    }

    /**
     * Set mail server options in preference file
     * @param host
     * @param password
     * @param adresseFsnet
     * @param port
     * @throws BackingStoreException
     */
    public void setServerOptions(String serverSmtp, String host, String password, String adresseFsnet, Integer port)
            throws BackingStoreException {

        Preferences appPref = Preferences.userNodeForPackage(ActionOptions.class);
        appPref.put("serveursmtp", serverSmtp);
        appPref.put("hote", host);
        appPref.put("motdepasse", password);
        appPref.put("adressefsnet", adresseFsnet);
        appPref.putInt("port", port);
        appPref.flush();
    }
}
