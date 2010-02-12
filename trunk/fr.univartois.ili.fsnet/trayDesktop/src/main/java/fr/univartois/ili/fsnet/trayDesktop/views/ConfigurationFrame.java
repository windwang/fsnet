package fr.univartois.ili.fsnet.trayDesktop.views;

import fr.univartois.ili.fsnet.trayDesktop.TrayLauncher;
import fr.univartois.ili.fsnet.trayDesktop.controls.WSControl;
import fr.univartois.ili.fsnet.trayDesktop.model.Options;
import fr.univartois.ili.fsnet.trayDesktop.model.WSListener;
import fr.univartois.ili.fsnet.trayDesktop.model.WSMessage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Create the configuration frame
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ConfigurationFrame implements WSListener {

    private final JFrame frame;
    private final ConfigurationPanel cpanel;
    private final ResourceBundle trayi18n = TrayLauncher.getBundle();
    private final JLabel labelLoading;
    private JButton validateButton;
    private final WSControl control;
    private boolean waitForValid;

    public ConfigurationFrame(WSControl control) {
        frame = new JFrame(trayi18n.getString("CONFIGURATION"));
        cpanel = new ConfigurationPanel();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cpanel.getPanel(), BorderLayout.CENTER);

        JPanel validatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon loading = TrayLauncher.getImageIcon("/ressources/loading.gif");
        if (loading != null) {
            labelLoading = new JLabel(loading);
        } else {
            labelLoading = new JLabel();
        }
        labelLoading.setVisible(false);
        validatePanel.add(labelLoading);
        validatePanel.add(getTestButton());
        validatePanel.add(getValidateButton());
        validatePanel.add(getCancelButton());
        panel.add(validatePanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        this.control = control;
    }

    /**
     * Test the configuration with threads and timeout
     */
    private void tryValidate() {
        labelLoading.setVisible(true);
        frame.setEnabled(false);
        new Thread() {

            @Override
            public void run() {
                //TODO Refactor please
                control.testConfig();
            }
        }.start();
    }

    /**
     * Hide the loadingButton and enable the frame back
     */
    private void terminateValidation() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                labelLoading.setVisible(false);
                frame.setEnabled(true);
            }
        });
    }

    private JButton getValidateButton() {
        validateButton = new JButton(trayi18n.getString("VALIDER"));
        validateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Options.setWSUrl(cpanel.getWSUrl());
                Options.setFsnetUrl(cpanel.getFsnetUrl());
                Options.setLogin(cpanel.getLogin());
                Options.setPassword(cpanel.getPassword());
                Options.setLanguage(cpanel.getLanguage());
                Options.setLag(cpanel.getLag());
                tryValidate();

            }
        });
        return validateButton;
    }

    private JButton getCancelButton() {
        JButton but = new JButton(trayi18n.getString("ANNULER"));
        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        return but;
    }

    private JButton getTestButton() {
        JButton but = new JButton(trayi18n.getString("TESTER"));
        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                waitForValid = true;
                tryValidate();
            }
        });
        return but;
    }

    /**
     * Show the configuration frame
     */
    public void show() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void onNewMessages(WSMessage message) {
        
    }

    @Override
    public void onError(WSMessage message) {
        if (waitForValid) {
            waitForValid = false;
        }
        validateButton.setEnabled(false);
        JOptionPane.showMessageDialog(frame, trayi18n.getString("NOCONNECTION"),
                trayi18n.getString("ERROR"), JOptionPane.ERROR_MESSAGE);
        terminateValidation();
    }

    @Override
    public void onConnection(WSMessage message) {
        if (waitForValid) {
            waitForValid = false;
            validateButton.setEnabled(true);
            JOptionPane.showMessageDialog(frame, trayi18n.getString("VALIDCONFIGURATION"),
                    trayi18n.getString("SUCCESS"), JOptionPane.INFORMATION_MESSAGE);
            terminateValidation();
        } else {
            frame.dispose();
            TrayLauncher.reload();
        }
    }
}
