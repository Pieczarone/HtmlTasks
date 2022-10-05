package org.example.Service;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Reader {

    public void readJExcel(String fileLocation) {
        Map<Integer, List<String>> data = new HashMap<>();
        try {
            DateFormat formatter = new SimpleDateFormat("HH");
            DateFormat formatterOutput = new SimpleDateFormat("HH:mm");
            WorkbookSettings ws = new WorkbookSettings();
            ws.setSuppressWarnings(true);
            Workbook workbook = Workbook.getWorkbook(new File(fileLocation),ws);
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            int columns = sheet.getColumns();
            String stringDate = sheet.getCell(0,2).getContents();
            stringDate = stringDate.substring(stringDate.indexOf("-") + 2);
            Date date;
            Calendar calendar = Calendar.getInstance();
            for (int i = 5; i < rows; i++) {
                if(i==30){
                    break;
                }
                data.put(i-5, new ArrayList<String>());
                for (int j = 0; j < columns; j++) {
                    data.get(i-5)
                            .add(sheet.getCell(j, i)
                                    .getContents().replace("\n", "").split("\\(")[0]);
                }
            }
            for (int i =0;i<data.get(i).size()-1;i++){
                for (int j=0;j<data.size()-1;j++){
                    date = formatter.parse(data.get(j+1).get(0));
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR_OF_DAY, -1);
                    System.out.println(data.get(0).get(i+1)+";"+stringDate+" "+formatterOutput.format(calendar.getTime())+";"+data.get(j+1).get(i+1));
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (BiffException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }
    public void getStorms() throws IOException {
        String stormName="UNNAMED";
        int max = 0;
        int maxOfYear=0;
        int year=2015;
        BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\hurdat2-nepac-1949-2016-041317.txt"));
        List lines = new ArrayList();
        for(String line = br.readLine();line != null;line = br.readLine()) {
            String[] fields = line.split(",");
            lines.add(fields);
        }
        String[][] strings = (String[][]) lines.toArray(new String[lines.size()][]);
        for(int i=0;i<strings.length;i++) {
            if(strings[i][0].contains("EP")||strings[i][0].contains("CP")){
                if (max!=0){
                    System.out.println("Storm: "+stormName.trim()+" Max knots: "+max);
                }
                max=0;
                stormName = strings[i][1];
            }
            else if(Integer.parseInt(strings[i][0])>=20150000){
                if(Integer.parseInt(strings[i][0].substring(0,4))==year+1){
                    System.out.println();
                    System.out.println("Max knots for year " + year + ": " +maxOfYear);
                    System.out.println();
                    maxOfYear=0;
                    year++;
                }
                max=Math.max(max,Integer.parseInt(strings[i][6].trim()));
                maxOfYear=Math.max(maxOfYear,Integer.parseInt(strings[i][6].trim()));
                if(strings.length==i+1){
                    System.out.println();
                    System.out.println("Max knots for year " + year + ": " +maxOfYear);
                    System.out.println();
                }

            }

        }
    }
}

