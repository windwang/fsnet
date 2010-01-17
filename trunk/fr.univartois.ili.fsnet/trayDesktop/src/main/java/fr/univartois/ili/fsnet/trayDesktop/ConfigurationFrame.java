/*
 *  Copyright (C) 2010 Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univartois.ili.fsnet.trayDesktop;

import fr.univartois.ili.fsnet.webservice.NouvellesInformations;
import fr.univartois.ili.fsnet.webservice.NouvellesService;
import fr.univartois.ili.fsnet.webservice.NouvellesServiceLocator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Create the configuration frame
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class ConfigurationFrame {

    private final JFrame frame;
    private final ConfigurationPanel cpanel;
    private final ResourceBundle trayi18n = TrayLauncher.getBundle();

    public ConfigurationFrame() {
        frame = new JFrame(trayi18n.getString("CONFIGURATION"));
        cpanel = new ConfigurationPanel();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cpanel.getPanel(), BorderLayout.CENTER);

        JPanel validatePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        validatePanel.add(getTestButton());
        validatePanel.add(getCancelButton());
        validatePanel.add(getValidateButton());
        panel.add(validatePanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
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
                if (validateConfig()) {
                    JOptionPane.showMessageDialog(frame, trayi18n.getString("VALIDCONFIGURATION"),
                            trayi18n.getString("SUCCESS"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        return but;
    }

    private JButton getValidateButton() {
        JButton but = new JButton(trayi18n.getString("VALIDER"));
        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateConfig()) {
                    Options.setUrl(cpanel.getUrl());
                    Options.setLogin(cpanel.getLogin());
                    Options.setPassword(cpanel.getPassword());
                    Options.setLanguage(cpanel.getLanguage());
                    Options.saveOptions();
                    frame.dispose();
                    TrayLauncher.reload();
                }
            }
        });
        return but;
    }

    /**
     * Validate the given configuration
     * @return true if valid, false otherwise
     */
    private boolean validateConfig() {
        // TODO trouver une meilleur solution
        // il ne faudrait pas modifier Options, mais le webservice m'oblige a le faire...
        try {
            String url = cpanel.getUrl();
            String oldUrl = Options.getUrl();
            Options.setUrl(url);
            NouvellesService nv = new NouvellesServiceLocator();
            NouvellesInformations nvs = nv.getNouvellesInformationsPort();
            nvs.getNumberOfNewAnnonce();
            Options.setUrl(oldUrl);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, trayi18n.getString("NOCONNECTION"),
                    trayi18n.getString("ERROR"), JOptionPane.ERROR_MESSAGE);
        }
        // TODO authentication on webservice
        String login = cpanel.getLogin();
        String password = cpanel.getPassword();

        return false;
    }

    /**
     * Show the configuration frame
     */
    public void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
