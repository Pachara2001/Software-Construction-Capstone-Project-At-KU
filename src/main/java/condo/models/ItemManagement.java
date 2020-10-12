package condo.models;

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

