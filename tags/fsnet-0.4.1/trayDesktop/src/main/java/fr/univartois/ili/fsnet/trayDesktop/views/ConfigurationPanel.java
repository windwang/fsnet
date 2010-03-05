package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.event.KeyListener;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import fr.univartois.ili.fsnet.trayDesktop.TrayLauncher;
import fr.univartois.ili.fsnet.trayDesktop.model.Options;
import fr.univartois.ili.fsnet.trayDesktop.model.Options.LANG;

/**
 * Create and return a configuration panel for the trayIcon
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ConfigurationPanel {

    private final ResourceBundle trayi18n = TrayLauncher.getBundle();
    private final JPanel panel;
    private JTextField login;
    private JTextField password;
    private JTextField url;
    private JTextField fsneturl;
    private JComboBox language;
    private JSpinner lag;
    private final KeyListener al;

    public ConfigurationPanel(KeyListener al) {
        this.al = al;
        createFields();
        FormLayout layout = new FormLayout("right:max(40dlu;p), 4dlu, 90dlu",
                "p, 4dlu, p, 2dlu, p, 2dlu, p, 4dlu, p, 2dlu, p, 2dlu, p, 4dlu, p, 2dlu, p, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();

        builder.addSeparator(trayi18n.getString("IDENTIFICATION"), cc.xyw(1, 1, 3));
        builder.addLabel(trayi18n.getString("IDENTIFIANT"), cc.xy(1, 3));
        builder.add(login, cc.xy(3, 3));
        builder.addLabel(trayi18n.getString("PASSWORD"), cc.xy(1, 5));
        builder.add(password, cc.xy(3, 5));

        builder.addSeparator(trayi18n.getString("FSNET"), cc.xyw(1, 7, 3));
        builder.addLabel(trayi18n.getString("WSADRESSE"), cc.xy(1, 9));
        builder.add(url, cc.xy(3, 9));
        builder.addLabel(trayi18n.getString("FSNETADRESSE"), cc.xy(1, 11));
        builder.add(fsneturl, cc.xy(3, 11));

        builder.addSeparator(trayi18n.getString("MISC"), cc.xyw(1, 13, 3));
        builder.addLabel(trayi18n.getString("LANGUAGE"), cc.xy(1, 15));
        builder.add(language, cc.xy(3, 15));
        builder.addLabel(trayi18n.getString("LAG"), cc.xy(1, 17));
        builder.add(lag, cc.xy(3, 17));
        builder.addLabel(trayi18n.getString("MINUTES"), cc.xy(1, 18));
        panel = builder.getPanel();
    }

    /**
     *
     * @return the created JPanel
     */
    public final JPanel getPanel() {
        return panel;
    }

    private void createFields() {
        login = new JTextField(Options.getLogin());
        login.addKeyListener(al);
        password = new JPasswordField(Options.getPassword());
        password.addKeyListener(al);
        url = new JTextField(Options.getWSUrl());
        url.addKeyListener(al);
        fsneturl = new JTextField(Options.getFsnetUrl());
        fsneturl.addKeyListener(al);
        language = new JComboBox(LANG.values());
        language.setSelectedItem(Options.getLanguage());
        SpinnerModel model = new SpinnerNumberModel(Options.getLag(), 1, 120, 1);
        lag = new JSpinner(model);
    }

    /**
     *
     * @return the selectionned language
     */
    public LANG getLanguage() {
        return (LANG) language.getSelectedItem();
    }

    /**
     *
     * @return the input login
     */
    public String getLogin() {
        return login.getText();
    }

    /**
     *
     * @return the input password
     */
    public String getPassword() {
        return password.getText();
    }

    /**
     *
     * @return the input url
     */
    public String getWSUrl() {
        return url.getText();
    }

    /**
     *
     * @return lag time between two checks
     */
    public int getLag() {
        return (Integer) (lag.getModel().getValue());
    }

    /**
     *
     * @return the website url
     */
    public String getFsnetUrl() {
        return fsneturl.getText();
    }
}
