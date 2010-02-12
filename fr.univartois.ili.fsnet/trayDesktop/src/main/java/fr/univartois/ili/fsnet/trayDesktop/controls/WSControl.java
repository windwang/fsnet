/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univartois.ili.fsnet.trayDesktop.controls;

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
    

    public void testConfig() {
        model.verifyConfig();
    }
}
