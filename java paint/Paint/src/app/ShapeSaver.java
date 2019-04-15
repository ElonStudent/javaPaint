package app;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Daan Eekhof
 */
public class ShapeSaver{
    
    private final String standardFilePath = Paths.get(System.getProperty("user.dir")).getParent().getParent().toString().replace("\\", "/");
    private final String parsedItemPath = "/Java Paint/saveFile/";
    private final String listItemPath = "/Big Data/List bestanden/";

    public void SaveShapeToFile() throws Exception{
        String fileName = "savedshapes";
        
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(standardFilePath + listItemPath + fileName + ".list")), "ISO-8859-1"));        
        FileOutputStream fileStream = new FileOutputStream(new File(standardFilePath + parsedItemPath + fileName + ".csv"));
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream, "windows-1252")) {

            String line = "";
            while ((line = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("([\\d.*]{1,10})\\s+([\\d]{1,9})\\s+([\\d.]{3})\\s+(?:\\\"?(.*)\\\"?)\\s+\\(([\\d]{4}|\\?{4})[\\w\\/]*?\\)\\s*(?:\\{(.*?)\\s?\\(?\\#?(\\d*)\\.?(\\d*)\\)?\\})?").matcher(line);
                if(matcher.find()) {
                    outputStreamWriter.write(matcher.group(1) + " | " + matcher.group(2) + " | " + matcher.group(3) + " | " + matcher.group(4) + " | " + matcher.group(5) + " | " + matcher.group(6) + " | " + matcher.group(7) + " | " + matcher.group(8) + '\n');
                }
            }
            outputStreamWriter.close();
        }
    }
}