/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univartois.ili.fsnet.trayDesktop.model;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public interface WSListener {

    void onNewMessages(WSMessage message);
    //void onNewEvent(WsPrivateMessage message);
    //void onNewEvent(WsPrivateMessage message);

    void onError(WSMessage message);

    void onConnection(WSMessage message);
}
