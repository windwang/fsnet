package fr.univartois.ili.fsnet.trayDesktop.controls;

import fr.univartois.ili.fsnet.trayDesktop.model.Options.LANG;
import fr.univartois.ili.fsnet.trayDesktop.model.WSConnector;

/**
 * The controleur of the application
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class WSControl {

    private final WSConnector model;

    public WSControl(WSConnector model) {
        this.model = model;
    }

    /**
     * Change the configuration of the application.
     * @param wsurl
     * @param fsnetUrl
     * @param login
     * @param password
     * @param lang
     * @param lag
     */
    public void changeConfig(String wsurl, String fsnetUrl, String login, String password, LANG lang, int lag) {
        model.changeConfig(wsurl, fsnetUrl, login, password, lang, lag);
    }

    /**
     * Ping the webservice to check if there are news.
     */
    public void checkWS() {
        model.checkWS();
    }
}
