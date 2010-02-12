/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univartois.ili.fsnet.trayDesktop.model;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class WSMessage {

    private final String message;

    public WSMessage(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
