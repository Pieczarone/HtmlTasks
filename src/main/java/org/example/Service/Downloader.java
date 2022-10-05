package org.example.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Downloader {

    public static Document downloadFromUrl(String fileUrl) throws IOException {
        Document doc = Jsoup.connect(fileUrl).get();
        return doc;
    }

    public static void downloadFromUrlHtmlUnit(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc.select("text"));
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_ESR);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.waitForBackgroundJavaScript(15000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage htmlPage = webClient.getPage(url);
        System.out.println(htmlPage.asXml());
    }


    public void downloadXlsFromUrl(String fileUrl,String outputUrl){
        try {
            URL url = new URL(fileUrl);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(outputUrl);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
