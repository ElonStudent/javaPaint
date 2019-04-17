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
    private int groupNum = -1;

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
                String groupNumText = "";

                if(s.GetList() != null){
                    groupNum++;
                    String tabSpacer = "\t";
                    String repeatTab = tabSpacer.repeat(groupNum+1);
                    if(groupNum == 0){
                        groupNumText += "group" + " "+groupNum +"\n";
                        repeatTab = tabSpacer.repeat(groupNum+1);
                    }
                    else
                        groupNumText += repeatTab +"group" + " "+groupNum +"\n";
                    for (BaseText text : s.textList)
                        stringText += "ornament " + text.position + " \""+ text.text + "\"\n";
                    if (s instanceof Rectangle) {
                        outputStreamWriter
                                .write(groupNumText + repeatTab +stringText + "rectangle" + " " + shapeX + " " + shapeY + " " + width + " " + height + '\n');
                        System.out.println(stringText + "Rectangle: " + shapeX + " " + shapeY + " " + width + " " + height);
                    } else if (s instanceof Circle) {
                        outputStreamWriter
                                .write(groupNumText + repeatTab +stringText + "ellipse" + " " + shapeX + " " + shapeY + " " + width + " " + height + '\n');
                        System.out.println(stringText + "Ellipse: " + shapeX + " " + shapeY + " " + width + " " + height);
                    }
                    for (BaseShape b : s.GetList()){
                        for (BaseText text : b.textList) {
                            stringText += "ornament " + text.position + " \""+ text.text + "\"\n";
                        }
        
                        if (b instanceof Rectangle) {
                            outputStreamWriter
                                    .write(repeatTab +stringText + repeatTab +"rectangle" + " " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight() + '\n');
                            System.out.println(stringText + "Rectangle: " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight());
                        } else if (b instanceof Circle) {
                            outputStreamWriter
                                    .write(repeatTab +stringText + repeatTab +"ellipse" + " " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight() + '\n');
                            System.out.println(stringText + "Ellipse: " + b.getX() + " " + b.getY() + " " + b.getWidth() + " " + b.getHeight());
                        }
                    }
                }else if (s.GetList() == null){
                    for (BaseShape x : shapeList){
                        try{
                            for (BaseShape t : x.GetList()){
                                
                                if (t.GetList().contains(s) && t.GetList() != null){
                                    System.out.println("In a group");
                                }
                                else{
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

                            }
                        }catch(NullPointerException e){}
                    }
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
            String groupNumLoad = "";
            String ornamentPos = "";
            String ornamentText = "";
            List<BaseShape> tA = new ArrayList<BaseShape>();
            
            while ((line = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\t*(.*?)\\s(\\d*)\\s(\\d*)\\s(\\d*)\\s(\\d*)").matcher(line);
                Matcher ornamentMatch = Pattern.compile("\\t*(.*?)\\s(.*?)\\s\\\"(.*?)\\\"").matcher(line);
                Matcher groupMatch = Pattern.compile("\\t*(.*?)\\s(\\d*)").matcher(line);

                if(line.contains("group")){
                    if(groupMatch.find()){
                        System.out.println(groupMatch.group(2)); // number of group
                        groupNumLoad = groupMatch.group(2);
                    }
                }
                if(line.contains("ornament")){
                    if(ornamentMatch.find()){
                        System.out.println("Place ornament in hasmap");
                        System.out.println("line = " + line);
                        System.out.println(ornamentMatch.group(2)); //position
                        System.out.println(ornamentMatch.group(3)); // text
                        ornamentPos = ornamentMatch.group(2);
                        ornamentText = ornamentMatch.group(3);
                    }
                }
                if (line.contains("rectangle")) {
                    if (matcher.find()) {
                        BaseShape rectangle = new Rectangle();
                        rectangle.draw(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5)));
                        System.out.println(matcher.group(1));
                        tA.add(rectangle);

                        if(ornamentPos != "" && ornamentText != ""){
                            rectangle.addText(ornamentPos, ornamentText);
                            ornamentPos = "";
                            ornamentText = "";
                        }
                        if(groupNumLoad != ""){
                            for(BaseShape s : tA)
                                s.CreateList(rectangle);
                        }
                    }
                } else if (line.contains("ellipse")) {
                    if (matcher.find()) {
                        BaseShape oval = new Circle();
                        oval.draw(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(3)) + Integer.parseInt(matcher.group(5)));
                        if(ornamentPos != "" && ornamentText != ""){
                            oval.addText(ornamentPos, ornamentText);
                            ornamentPos = "";
                            ornamentText = "";
                        }
                        if(groupNumLoad != ""){
                            for(BaseShape s : tA)
                                s.CreateList(oval);
                        }
                    }
                }
            }
            br.close();
        }
    }
}