package fr.univartois.ili.fsnet.trayDesktop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.xml.namespace.QName;

import fr.univartois.ili.fsnet.webservice.Info;
import fr.univartois.ili.fsnet.webservice.InfoService;

/**
 * Create the configuration frame
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ConfigurationFrame {

    private final JFrame frame;
    private final ConfigurationPanel cpanel;
    private final ResourceBundle trayi18n = TrayLauncher.getBundle();
    private final JLabel lab;
    private JButton validateButton;

    public ConfigurationFrame() {
        frame = new JFrame(trayi18n.getString("CONFIGURATION"));
        cpanel = new ConfigurationPanel();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cpanel.getPanel(), BorderLayout.CENTER);

        JPanel validatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon loading = TrayLauncher.getImageIcon("/ressources/loading.gif");
        if (loading != null) {
            lab = new JLabel(loading);
        } else {
            lab = new JLabel();
        }
        lab.setVisible(false);
        validatePanel.add(lab);
        validatePanel.add(getTestButton());
        validatePanel.add(getValidateButton());
        validatePanel.add(getCancelButton());
        panel.add(validatePanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
    }

    /**
     * Test the configuration with threads and timeout
     */
    private void tryValidate() {
        lab.setVisible(true);
        frame.setEnabled(false);
        new Thread() {

            @Override
            public void run() {
                //TODO Refactor please
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<Boolean> future = executor.submit(new Callable<Boolean>() {

                    public Boolean call() {
                        return validateConfig();
                    }
                });
                boolean valid = false;
                try {
                    valid = future.get(5, TimeUnit.SECONDS);
                } catch (Exception ex) {
                    Logger.getLogger(ConfigurationFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (valid) {
                    validateButton.setEnabled(true);
                    configurationSuccess();
                } else {
                    validateButton.setEnabled(false);
                    configurationFailed();
                }
            }
        }.start();
    }

    /**
     * Test the current configuration
     * @return true if valid, false otherwise
     */
    private boolean validateConfig() {
        try {
            String url = cpanel.getWSUrl();
            String login = cpanel.getLogin();
            String password = cpanel.getPassword();
            InfoService infoService = new InfoService(new URL(url),
                    new QName("http://webservice.fsnet.ili.univartois.fr/", "InfoService"));
            Info infoPort = infoService.getInfoPort();
            infoPort.getNewAnnouncementCount(login, password);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ConfigurationFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Show success message
     */
    private void configurationSuccess() {
        JOptionPane.showMessageDialog(frame, trayi18n.getString("VALIDCONFIGURATION"),
                trayi18n.getString("SUCCESS"), JOptionPane.INFORMATION_MESSAGE);
        terminateValidation();
    }

    /**
     * Show error message
     */
    private void configurationFailed() {
        JOptionPane.showMessageDialog(frame, trayi18n.getString("NOCONNECTION"),
                trayi18n.getString("ERROR"), JOptionPane.ERROR_MESSAGE);
        terminateValidation();
    }

    /**
     * Hide the loadingButton and enable the frame back
     */
    private void terminateValidation() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lab.setVisible(false);
                frame.setEnabled(true);
            }
        });
    }

    private JButton getValidateButton() {
        validateButton = new JButton(trayi18n.getString("VALIDER"));
        validateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateConfig()) {
                    Options.setWSUrl(cpanel.getWSUrl());
                    Options.setFsnetUrl(cpanel.getFsnetUrl());
                    Options.setLogin(cpanel.getLogin());
                    Options.setPassword(cpanel.getPassword());
                    Options.setLanguage(cpanel.getLanguage());
                    Options.setLag(cpanel.getLag());
                    Options.saveOptions();
                    frame.dispose();
                    TrayLauncher.reload();
                } else {
                    validateButton.setEnabled(false);
                    
                    configurationFailed();
                }
            }
        });
        if (!validateConfig()) {
            validateButton.setEnabled(false);
        }
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
}
