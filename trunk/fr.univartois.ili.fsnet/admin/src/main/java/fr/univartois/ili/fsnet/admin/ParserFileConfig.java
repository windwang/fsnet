/**
 * 
 */
package fr.univartois.ili.fsnet.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author romuald druelle
 * 
 */
public class ParserFileConfig {

    private File file;
    private static Logger logger = Logger.getLogger("fsnetadmin");

    public ParserFileConfig() {
        super();
    }

    public ParserFileConfig(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String[] parse() {
        String line = null;
        BufferedReader reader = null;
        String[] parameters = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
            parameters = line.split(" ");
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return parameters;
    }
}
