package condoapp.models;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ItemManagement {
    private ArrayList<Item> itemList, receivedItemList;
    private ArrayList<String []> itemStr, receivedItemStr;
    private Item currentItem;


    public ItemManagement() {
        itemList=new ArrayList<>();
        itemStr=new ArrayList<>();
        receivedItemList =new ArrayList<>();
    }

    public void addItemList() throws IOException, CsvValidationException {
        itemList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\items.csv")).withCSVParser(csvParser).build();
        String [] items;
        while((items = reader.readNext())!=null) {

            if(items[0].equals("letter")){
                Item  a = new Item(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9]);
                itemList.add(a);
            }
            if(items[0].equals("document")){
                Item  a = new Document(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10]);
                itemList.add(a);
            }
            if(items[0].equals("parcel")){
                Item  a = new Parcel(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10],items[11]);
                itemList.add(a);
            }
        }
        reader.close();
    }
    public void updateItemCsv() throws IOException {
        itemStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\item.csv"));
        for(Item itemUp : itemList){

            String[] q = null;
            if(itemUp.getType().equals("letter")){
                q=itemUp.getInformation();
            }
            if(itemUp.getType().equals("document")){
                Document temp = (Document)itemUp;
                q=temp.getInformation();            }
            if(itemUp.getType().equals("parcel")){
                Parcel temp = (Parcel)itemUp;
                q=temp.getInformation();            }
            itemStr.add(q);
        }
        writer.writeAll(itemStr);
        writer.close();
    }
    public void addReceivedItemList() throws IOException, CsvValidationException {
        itemList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\itemsOut.csv")).withCSVParser(csvParser).build();
        String [] items;
        while((items = reader.readNext())!=null) {

            if(items[0].equals("letter")){
                Item  a = new Item(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9]);
                itemList.add(a);
            }
            if(items[0].equals("document")){
                Item  a = new Document(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10]);
                itemList.add(a);
            }
            if(items[0].equals("parcel")){
                Item  a = new Parcel(items[0],items[1],items[2],items[3],items[4],items[5],items[6],items[7],items[8],items[9],items[10],items[11]);
                itemList.add(a);
            }
        }
        reader.close();
    }

    public void updateReceivedItemCsv() throws IOException {
        receivedItemStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\receivedItem.csv"));
        for(Item itemUp : receivedItemList){

            String[] q = null;
            if(itemUp.getType().equals("letter")){
                q=itemUp.getInformation();
            }
            if(itemUp.getType().equals("document")){
                Document temp = (Document)itemUp;
                q=temp.getInformation();            }
            if(itemUp.getType().equals("parcel")){
                Parcel temp = (Parcel)itemUp;
                q=temp.getInformation();            }
            receivedItemStr.add(q);
        }
        writer.writeAll(receivedItemStr);
        writer.close();
    }


    }

