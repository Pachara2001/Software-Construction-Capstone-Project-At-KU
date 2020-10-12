package condo.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


public class StaffAccount extends Account implements Sort {
    private  String permission, attempt,picturePath,name,dateAndTimeStr;
    private LocalDateTime dateAndTime;




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

    public void addRoom(String building, String floor, String roomNo, String type, String resident1, String resident2, ArrayList<Room> roomList){
        for(Room room : roomList){
            if(room.isRoomNoMatch(building+floor+roomNo)) throw new IllegalArgumentException( "This room already have an information.");
        }
            int floorInt = parseInt(floor);
        if(floorInt<1)  throw new IllegalArgumentException( "floor must more than 0");
        int room = parseInt(roomNo);
        if(roomNo.length()!=2||room<1||room>99) throw new IllegalArgumentException ("Room number must be between 01-99");
        roomNo=building+floor+roomNo;
        Room a = new Room(building, floor, roomNo, type, resident1, resident2);
        roomList.add(a);

    }

    public void addResident(String roomNo , String resident1 , String resident2,ArrayList<Room> roomList){
    int status = 0;
        for(Room room : roomList){
            if(room.isRoomNoMatch(roomNo)) {
                if(room.checkResident()) throw new IllegalArgumentException ("This room already have resident");
                if(room.getType().equals("One-Bedroom")&&!resident2.isEmpty()) throw new IllegalArgumentException("Resident 2 must be void. ");
                if(resident1.isEmpty()&&resident2.isEmpty()&&room.getType().equals("Two-Bedroom")) throw new IllegalArgumentException("At Least must have 1 resident.");
                if(resident1.isEmpty()&&!resident2.isEmpty()&&room.getType().equals("Two-Bedroom")) throw new IllegalArgumentException("At Least must have resident1 before resident2.");
                if(resident1.isEmpty()&&room.getType().equals("One-Bedroom")) throw new IllegalArgumentException ("At Least must have 1 resident.");
                else{
                    status++;
                    room.setResident1(resident1);
                    room.setResident2(resident2);
                }
            }

        }
       if(status==0) throw new IllegalArgumentException("Room not found !!");

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

    public void addItem(String type,String roomNo,String recipient,String sender , String size,String imagePath,String importance, String company, String trackingNumber,ArrayList<Item> itemList,ArrayList<Room> roomList){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        Item newItem= null;
        int findStatus=0;
        for(Room room : roomList ){
            if(room.isRoomNoMatch(roomNo)) {
                findStatus=1;
            if(!room.getResident1().equalsIgnoreCase(recipient)&&!room.getResident1().equalsIgnoreCase(recipient)) throw new IllegalArgumentException("Don't found this recipient in system");
            }
        }
        if(findStatus==0)throw new IllegalArgumentException("Not found this roomNo in system.");

        if(type.equalsIgnoreCase("Letter")){
            if(type.isEmpty()||roomNo.isEmpty()||recipient.isEmpty()||sender.isEmpty()||size.isEmpty()||imagePath.isEmpty()) throw new IllegalArgumentException ("Fill out the required information.");
            if(!importance.isEmpty())throw new IllegalArgumentException("Importance must be void.");
            if(!company.isEmpty()) throw new IllegalArgumentException ("Company must be void.");
            if(!trackingNumber.isEmpty()) throw new IllegalArgumentException ("Tracking number must be void.");
            newItem = new Item(type,formattedDateTime,roomNo,recipient,sender,size,imagePath,name,"01/01/0001 00:00:01","-");

        }


        if(type.equalsIgnoreCase("Document")){
            if(type.isEmpty()||roomNo.isEmpty()||recipient.isEmpty()||sender.isEmpty()||size.isEmpty()||imagePath.isEmpty()||importance.isEmpty()) throw new IllegalArgumentException ("Fill out the required information.");
            if(!company.isEmpty()) throw new IllegalArgumentException ("Company must be void.");
            if(!trackingNumber.isEmpty()) throw new IllegalArgumentException("Tracking number must be void.");
            newItem = new Document(type,formattedDateTime,roomNo,recipient,sender,size,imagePath,name,importance,"01/01/0001 00:00:01","-");

        }
        if(type.equalsIgnoreCase("Parcel")){
            if(!importance.isEmpty())throw new IllegalArgumentException("Importance must be void.");
            if(type.isEmpty()||roomNo.isEmpty()||recipient.isEmpty()||sender.isEmpty()||size.isEmpty()||imagePath.isEmpty()||company.isEmpty()||trackingNumber.isEmpty()) throw new IllegalArgumentException("Fill out the required information.");
            newItem = new Parcel(type,formattedDateTime,roomNo,recipient, sender,size,imagePath,name,company,trackingNumber,"01/01/0001 00:00:01","-");
        }
        itemList.add(newItem);

    }

    public void receiveItem(Item item  , ArrayList<Item> itemList, ArrayList<Item> receivedItemList){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        item.setReceivedDateAndTimeStr(formattedDateTime);
        item.setReceivedWithStaff(name);
        receivedItemList.add(item);
        itemList.remove(item);

    }

    @Override
    public LocalDateTime getTime() {
        return dateAndTime;
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
