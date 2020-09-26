package condoapp.models;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class StaffAccount extends Account {
    private  String permission, attempt,picturePath,name,dateAndTimeStr;
    private LocalDateTime dateAndTime;
    private Item currentItem;



    public StaffAccount(String username, String password, String name, String permission, String dateAndTime, String attempt, String picturePath){
        super(username,password);
        this.permission=permission;
        this.dateAndTimeStr=dateAndTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateAndTime, formatter);
        this.dateAndTime=dateTime;
        this.attempt = attempt;
        this.picturePath=picturePath;
        this.name=name;

    }

    public String addRoom(String building, String floor, String roomNo, String type, String resident1, String resident2, ArrayList<Room> roomList){
        for(Room i : roomList){
            if(i.getRoomNo().equalsIgnoreCase(roomNo)) return "This room already have an information";

        }
            if(!building.equals(roomNo.substring(0,1)) )return "Room number not match with the building ";
            if(!floor.equals(roomNo.substring(1,2))) return "Room number not match with the floor";
            Room a = new Room(building, floor, roomNo, type, resident1, resident2);
            roomList.add(a);
            return "";
    }
    public  void searchItemByRoomNo(String roomNo,ArrayList<Item> itemList){
        for(Item temp : itemList){
            if(temp.getRoomNo().equalsIgnoreCase(roomNo)){
                currentItem=temp;
                break;
            }
        }
    }

    public void addItem(String type,String roomNo,String receiver,String sender , String size,String imagePath,String level, String company, String trackingNumber,ArrayList<Item> itemList){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        Item newItem= null;
        if(type.equalsIgnoreCase("letter")){
            newItem = new Item(type,formattedDateTime,roomNo,receiver,sender,size,imagePath,name,"01/01/0001 00:00:01","-");
        }

        if(type.equalsIgnoreCase("document")){
            newItem = new Document(type,formattedDateTime,roomNo,receiver,sender,size,imagePath,name,level,"01/01/0001 00:00:01","-");
        }
        if(type.equalsIgnoreCase("parcel")){
            newItem = new Parcel(type,formattedDateTime,roomNo,receiver, sender,size,imagePath,name,company,trackingNumber,"01/01/0001 00:00:01","-");
        }

        itemList.add(newItem);
    }

    public void removeItem(Item item  , ObservableList<Item> itemObservableList, ArrayList<Item> itemOutList){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        item.setReceiveDateAndTimeStr(formattedDateTime);
        item.setReceivedWithStaff(name);
        itemOutList.add(item);
        itemObservableList.remove(item);

    }
    public String getName() {
        return name;
    }

    public String getAttempt() {
        return attempt;
    }

    public String getDateAndTimeStr() {
        return dateAndTimeStr;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setDateAndTimeStr(String dateAndTimeStr) {
        this.dateAndTimeStr = dateAndTimeStr;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setAttempt(int attempt) {
        String a=String.valueOf(attempt);
        this.attempt = a;
    }

    @Override
    public String toString() {
        return "StaffAccount{" + "Username :"+ getUsername()+ "Password :"+ getPassword()+
                "permission='" + permission + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", total='" + attempt + '\'' +
                '}';
    }
}
