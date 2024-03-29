package fr.univartois.ili.fsnet.trayDesktop.model;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Give informations about the configuration.
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public final class Options {

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
    public static String getLogin() {
        return login;
    }

    /**
     * Set the value of login
     *
     * @param login new value of login
     */
    public static void setLogin(String login) {
        Options.login = login;
    }
    private static String password;

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public static void setPassword(String password) {
        Options.password = password;
    }
    private static String url;

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public static String getWSUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public static void setWSUrl(String url) {
        Options.url = url;
    }
    private static LANG language;

    /**
     * Get the value of language
     *
     * @return the value of language
     */
    public static LANG getLanguage() {
        return language;
    }
    private static int lag;

    /**
     * Get the value of lag
     *
     * @return the value of lag
     */
    public static int getLag() {
        return lag;
    }

    /**
     * Set the value of lag
     *
     * @param lag new value of lag
     */
    public static void setLag(int lag) {
        Options.lag = lag;
    }

    /**
     *
     * @return the current locale
     */
    public static Locale getLocale() {
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
    public static void setLanguage(LANG language) {
        Options.language = language;
    }
    private static String fsneturl;

    public static String getFsneturl() {
		return fsneturl;
	}

	public static void setFsneturl(String fsneturl) {
		Options.fsneturl = fsneturl;
	}

	/**
     * Get the value of fsneturl
     *
     * @return the value of fsneturl
     */
    public static String getFsnetUrl() {
        return fsneturl;
    }

    /**
     * Set the value of fsneturl
     *
     * @param fsneturl new value of fsneturl
     */
    public static void setFsnetUrl(String fsneturl) {
        Options.fsneturl = fsneturl;
    }

    public static void loadOptions() {
        Preferences pref = Preferences.userNodeForPackage(Options.class);
        // TODO validate
        saved = pref.getBoolean("saved", false);
        login = pref.get("login", "");
        password = pref.get("password", "");
        url = pref.get("wsurl", "");
        fsneturl = pref.get("fsneturl", "");
        lag = pref.getInt("lag", 10);
        try {
            language = LANG.valueOf(pref.get("language", "English"));
        } catch (IllegalArgumentException e) {
            language = LANG.English;
        }
    }

    /**
     * Save options to filesystem
     */
    public static void saveOptions() {
        try {
            // TODO validate
            Preferences pref = Preferences.userNodeForPackage(Options.class);
            saved = true;
            pref.putBoolean("saved", true);
            pref.put("login", login);
            pref.put("password", password);
            pref.put("wsurl", url);
            pref.put("fsneturl", fsneturl);
            pref.put("language", language.toString());
            pref.putInt("lag", lag);
            pref.flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, "Unable to flush preferences", ex);
        }
    }

    /**
     *
     * @return true if already configured, false otherwise
     */
    public static boolean isConfigured() {
        return saved;
    }

    /**
     * Clear all option for the application
     */
    public static void clearOptions() {
        Preferences pref = Preferences.userNodeForPackage(Options.class);
        try {
            pref.clear();
        } catch (BackingStoreException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
