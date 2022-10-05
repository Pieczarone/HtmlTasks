package org.example;

import org.example.Service.Downloader;
import org.example.Service.Reader;
import org.example.Service.Saver;
import java.io.IOException;
import static org.example.Service.Downloader.*;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Saver saver = new Saver();
        Downloader downloader = new Downloader();
        Reader reader = new Reader();
        // Task1:
        saver.saveHtml(downloadFromUrl("https://www.ote-cr.cz/en/statistics/electricity-imbalances?version=0&date=2022-09-26"));
        saver.saveAsCleanData(downloadFromUrl("https://www.ote-cr.cz/en/statistics/electricity-imbalances?version=0&date=2022-09-26"));
        // Task2:
        System.out.println("Task 2:");
        System.out.println();
        downloader.downloadXlsFromUrl("https://www.ote-cr.cz/pubweb/attachments/05_09_12/2022/month09/day26/Imbalances_26_09_2022_V0_EN.xls","src\\main\\resources\\tableXls.xls");
        reader.readJExcel("src\\main\\resources\\tableXls.xls");
        System.out.println();
//        // Task3 (Couldn't resolve it. I've left what i tried but whatever i was trying i couldn't get JS code that had O2 value):
//        downloadFromUrlHtmlUnit("https://www.okg.se/en");
          // Task4:
        System.out.println("Task 4: ");
        System.out.println();
        reader.getStorms();

    }
}
