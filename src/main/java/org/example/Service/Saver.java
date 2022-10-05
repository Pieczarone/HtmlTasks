package org.example.Service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Saver {
    private static Converter convert = new Converter();
    public void saveHtml(Document doc){
        try {
            String fileName = "src\\main\\resources\\table.html";
            FileWriter writer = new FileWriter(fileName);
            convert.removeComments(doc);
            Element tableElement = doc.select("table").first();
            writer.append(tableElement.outerHtml());
            writer.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void saveAsCleanData(Document doc){
        try {
            FileWriter writer = new FileWriter("src\\main\\resources\\tableClean.html");
            Element tableElement = doc.select("table").first();
            Elements tableHeaderEles = tableElement.select("thead tr th");
            for (int i = 0; i < tableHeaderEles.size(); i++) {
                writer.append(tableHeaderEles.get(i).text());
                if(i != tableHeaderEles.size() -1){
                    writer.append(',');
                }
            }
            writer.append('\n');
            Elements tableRowElements = tableElement.select(":not(thead) tr");
            for (int i = 0; i < tableRowElements.size(); i++) {
                Element row = tableRowElements.get(i);
                Elements rowItems = row.select("td");
                for (int j = 0; j < rowItems.size(); j++) {
                    writer.append(rowItems.get(j).text());
                    if(j != rowItems.size() -1){
                        writer.append(',');
                    }
                }
                writer.append('\n');
            }
            writer.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
