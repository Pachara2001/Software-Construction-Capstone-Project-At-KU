package condo.models;

import java.util.ArrayList;

public class ItemManagement {
    private ArrayList<Item> itemList, receivedItemList;



    public ItemManagement() {
        itemList=new ArrayList<>();
        receivedItemList =new ArrayList<>();
    }

    public ArrayList<Item> searchItemByRoomNo(String roomNo,ArrayList<Item> itemList){
        ArrayList<Item> item = new ArrayList<>();
        if(roomNo.isEmpty()) return itemList;
        for(Item temp : itemList){
            if(temp.getRoomNo().equalsIgnoreCase(roomNo)) item.add(temp);
        }
        if(item.isEmpty()) throw new IllegalArgumentException("Item not found by this room number.");
        return item;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public ArrayList<Item> getReceivedItemList() {
        return receivedItemList;
    }
}

