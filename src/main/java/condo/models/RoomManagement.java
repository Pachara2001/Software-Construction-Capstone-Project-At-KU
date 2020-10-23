package condo.models;

import java.util.ArrayList;

public class RoomManagement {
    private ArrayList<Room> roomList;

    public RoomManagement() {
        roomList= new ArrayList<>();
    }

    public ArrayList<Room> searchRoomByResident(String name){
        ArrayList<Room> roomsFound = new ArrayList<>();
        for(Room room : roomList){
            if(room.getResident1().contains(name)||room.getResident2().contains(name)) roomsFound.add(room);
        }
        if(roomsFound.isEmpty()) throw new IllegalArgumentException("Not found this name on system.");
        return roomsFound;
    }



    public ArrayList<Room> getRoomList() {
        return roomList;
    }

}
