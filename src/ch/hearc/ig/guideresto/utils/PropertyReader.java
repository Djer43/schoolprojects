package ch.hearc.ig.guideresto.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author baudetc
 */
public interface PropertyReader {

    public String find(String fileName);
    public Properties read(String filePath) throws FileNotFoundException, IOException;
    
}
