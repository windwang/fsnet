package fr.univartois.ili.fsnet.trayDesktop.controls;

import fr.univartois.ili.fsnet.trayDesktop.model.Options.LANG;
import fr.univartois.ili.fsnet.trayDesktop.model.WSConnector;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class WSControl {

    private final WSConnector model;

    public WSControl(WSConnector model) {
        this.model = model;
    }

    public void changeConfig(String wSUrl, String fsnetUrl, String login, String password, LANG lANG, int language) {
        model.changeConfig(wSUrl, fsnetUrl, login, password, lANG, language);
    }

    public void checkWS() {
        model.checkWS();
    }
}
