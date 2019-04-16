package app;

import java.awt.Shape;
import java.awt.geom.*;
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
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daan Eekhof
 */
public class ShapeFileWriter {
    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    private final String standardFilePath = path.toString().replace("\\", "/");
    private final String savedItemPath = "/saveFile/";

    public void SaveShapeToFile(ArrayList<BaseShape> shapeList) throws Exception {
        String fileName = "savedshapes";
        new File(standardFilePath + "/saveFile").mkdirs();
        FileOutputStream fileStream = new FileOutputStream(
                new File(standardFilePath + savedItemPath + fileName + ".txt"));
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream, "windows-1252")) {
            for (BaseShape s : shapeList) {
                int width = s.getWidth();
                int height = s.getHeight();
                int shapeX = s.getX();
                int shapeY = s.getY();
                String stringText = "";
                
                for (BaseText text : s.textList) {
                    stringText += "ornament " + text.position + " \""+ text.text + "\"\n";
                }

                if (s instanceof Rectangle) {
                    outputStreamWriter
                            .write(stringText + "rectangle" + " " + shapeX + " " + shapeY + " " + width + " " + height + '\n');
                    System.out.println(stringText + "Rectangle: " + shapeX + " " + shapeY + " " + width + " " + height);
                } else if (s instanceof Circle) {
                    outputStreamWriter
                            .write(stringText + "ellipse" + " " + shapeX + " " + shapeY + " " + width + " " + height + '\n');
                    System.out.println(stringText + "Ellipse: " + shapeX + " " + shapeY + " " + width + " " + height);
                }
            }
            outputStreamWriter.close();
        }
    }

    public void LoadShapeFromFile() throws Exception {

        String fileName = "savedshapes";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(standardFilePath + savedItemPath + fileName + ".txt")), "ISO-8859-1"))) {

            String line = "";
            Map ornaments = new HashMap();
            
            while ((line = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("(.*?)\\s(\\d*)\\s(\\d*)\\s(\\d*)\\s(\\d*)").matcher(line);
                Matcher ornamentMatch = Pattern.compile("(.*?)\\s(.*?)\\s\\\"(.*?)\\\"").matcher(line);
                //r (.*?)\s(.*?)\s\"(.*?)\"
                //r old (\\w*)\\s(\\w*)\\s(\\\"\\w*\\\")
                if(line.startsWith("ornament")){
                    if(ornamentMatch.find()){
                        System.out.println("Place ornament in hasmap");
                        System.out.println("line = " + line);
                        System.out.println(ornamentMatch.group(1));
                    }
                }
                if (line.startsWith("rectangle")) {
                    if (matcher.find()) {
                        Shapes rectangle = new TestDecorator(new Rectangle());
                        rectangle.draw(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5)));
                        System.out.println(matcher.group(1));
                    }
                } else if (line.startsWith("ellipse")) {
                    if (matcher.find()) {
                        Shapes oval = new TestDecorator(new Circle());
                        oval.draw(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5)));
                    }
                }
            }
            br.close();
        }
    }
}