package ch.hearc.ig.guideresto.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author baudetc
 */
public class ReadFileProperties implements PropertyReader {

    @Override
    public Properties read(String filePath) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(filePath);
        Properties props = new Properties();
        props.load(fileReader);
        return props;
    }

    @Override
    public String find(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }
}
