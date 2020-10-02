package condoapp.models;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import condoapp.models.Document;
import condoapp.models.Item;
import condoapp.models.Parcel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ItemManagement {
    private ArrayList<Item> itemList, receivedItemList;



    public ItemManagement() {
        itemList=new ArrayList<>();
        receivedItemList =new ArrayList<>();
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public ArrayList<Item> getReceivedItemList() {
        return receivedItemList;
    }
}

