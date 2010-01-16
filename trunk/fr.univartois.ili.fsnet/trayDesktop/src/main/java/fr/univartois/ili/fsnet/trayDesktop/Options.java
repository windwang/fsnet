package fr.univartois.ili.fsnet.trayDesktop;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Give informations about the configuration.
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class Options {

    public static enum LANG {

        English, Francais
    };
    private static boolean saved;

    private Options() {
    }
    private static String login;

    /**
     * Get the value of login
     *
     * @return the value of login
     */
    public static final String getLogin() {
        return login;
    }

    /**
     * Set the value of login
     *
     * @param login new value of login
     */
    public static final void setLogin(String login) {
        Options.login = login;
    }
    private static String password;

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public static final String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public static final void setPassword(String password) {
        Options.password = password;
    }
    private static String url;

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public static final String getUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public static final void setUrl(String url) {
        Options.url = url;
    }
    private static LANG language;

    /**
     * Get the value of language
     *
     * @return the value of language
     */
    public static final LANG getLanguage() {
        return language;
    }

    /**
     *
     * @return the current locale
     */
    public static final Locale getLocale() {
        switch (language) {
            case English:
                return new Locale("en");
            case Francais:
                return new Locale("fr");
        }
        return new Locale("en");
    }

    /**
     * Set the value of language
     *
     * @param language new value of language
     */
    public static final void setLanguage(LANG language) {
        Options.language = language;
    }

    public static final void loadOptions() {
        Preferences pref = Preferences.userNodeForPackage(Options.class);
        // TODO validate
        saved = pref.getBoolean("saved", false);
        login = pref.get("login", "");
        password = pref.get("password", "");
        url = pref.get("url", "");
        try {
            language = LANG.valueOf(pref.get("language", "English"));
        } catch (IllegalArgumentException e) {
            language = LANG.English;
        }
    }

    public static final void saveOptions() {
        try {
            // TODO validate
            Preferences pref = Preferences.userNodeForPackage(Options.class);
            saved = true;
            pref.putBoolean("saved", true);
            pref.put("login", login);
            pref.put("password", password);
            pref.put("url", url);
            pref.put("language", language.toString());
            pref.flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, "Impossible de flusher les preferences", ex);
        }
    }

    public static final boolean isConfigured() {
        return saved;
    }

    public static final void clearOptions() {
        Preferences pref = Preferences.userNodeForPackage(Options.class);
        try {
            pref.clear();
        } catch (BackingStoreException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
