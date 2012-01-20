package fr.univartois.ili.fsnet.trayDesktop.model;

/**
 * Simple message class used in WSListener
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class WSMessage {

    private final String message;

    public WSMessage(String message) {
        this.message = message;
    }


    /**
     *
     * @return the message string
     */
    public String getMessage() {
        return message;
    }
}
