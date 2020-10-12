package condo.service;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import condo.models.Document;
import condo.models.Item;
import condo.models.Parcel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadWriteItemCsv {
    private String dir;
    private String itemCsvFilePath;
    private String receivedItemCsvFilePath;
    private String itemFileName;
    private String receivedItemFileName;

    public ReadWriteItemCsv(String dir ,String itemFileName , String receivedItemFileName){
        this.dir=dir;
        this.itemFileName=itemFileName;
        this.receivedItemFileName =receivedItemFileName;
        itemCsvFilePath = (dir+ File.separator+itemFileName);
        receivedItemCsvFilePath = (dir+File.separator+receivedItemFileName);
        checkFilePath();
    }

    private void checkFilePath() {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(itemCsvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + itemFileName);
            }
        }

        file = new File(receivedItemCsvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + receivedItemFileName);
            }
        }
    }
    public void addItemList(ArrayList<Item> itemList) throws IOException, CsvValidationException {
        itemList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(itemCsvFilePath)).withCSVParser(csvParser).build();
        String [] item;
        while((item = reader.readNext())!=null) {

            if(item[0].equalsIgnoreCase("letter")){
                Item a = new Item(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
                itemList.add(a);
            }
            if(item[0].equalsIgnoreCase("document")){
                Item  a = new Document(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10]);
                itemList.add(a);
            }
            if(item[0].equalsIgnoreCase("parcel")){
                Item  a = new Parcel(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10],item[11]);
                itemList.add(a);
            }
        }
        reader.close();
    }
    public void updateItemCsv(ArrayList<Item> itemList) throws IOException {

        ArrayList <String []> itemStr = new ArrayList<>();
        CSVWriter writer = new CSVWriter(new FileWriter(itemCsvFilePath));
        for(Item itemUp : itemList){

            String[] q = null;
            if(itemUp.getType().equalsIgnoreCase("letter")){
                q=itemUp.getInformation();
            }
            if(itemUp.getType().equalsIgnoreCase("document")){
                Document temp = (Document)itemUp;
                q=temp.getInformation();            }
            if(itemUp.getType().equalsIgnoreCase("parcel")){
                Parcel temp = (Parcel)itemUp;
                q=temp.getInformation();            }
            itemStr.add(q);
        }
        writer.writeAll(itemStr);
        writer.close();
    }
    public void addReceivedItemList(ArrayList<Item> receivedItemList) throws IOException, CsvValidationException {
        receivedItemList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(receivedItemCsvFilePath)).withCSVParser(csvParser).build();
        String [] item;
        while((item = reader.readNext())!=null) {

            if(item[0].equalsIgnoreCase("letter")){
                Item  a = new Item(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9]);
                receivedItemList.add(a);
            }
            if(item[0].equalsIgnoreCase("document")){
                Item  a = new Document(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10]);
                receivedItemList.add(a);
            }
            if(item[0].equalsIgnoreCase("parcel")){
                Item  a = new Parcel(item[0],item[1],item[2],item[3],item[4],item[5],item[6],item[7],item[8],item[9],item[10],item[11]);
                receivedItemList.add(a);
            }
        }
        reader.close();
    }

    public void updateReceivedItemCsv(ArrayList<Item> receivedItemList) throws IOException {
        ArrayList<String []> receivedItemStr = new ArrayList<>();
        CSVWriter writer = new CSVWriter(new FileWriter(receivedItemCsvFilePath));
        for(Item itemUp : receivedItemList){

            String[] q = null;
            if(itemUp.getType().equalsIgnoreCase("letter")){
                q=itemUp.getInformation();
            }
            if(itemUp.getType().equalsIgnoreCase("document")){
                Document temp = (Document)itemUp;
                q=temp.getInformation();            }
            if(itemUp.getType().equalsIgnoreCase("parcel")){
                Parcel temp = (Parcel)itemUp;
                q=temp.getInformation();            }
            receivedItemStr.add(q);
        }
        writer.writeAll(receivedItemStr);
        writer.close();
    }
}
