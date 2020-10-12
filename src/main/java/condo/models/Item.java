package condo.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item implements Sort{

    private String type, recipient,roomNo,sender,size,imagePath,dateAndTimeStr,staff,receivedWithStaff, receivedDateAndTimeStr;
    private LocalDateTime dateAndTime ,receivedDateAndTime;

    public Item(String type, String dateAndTime, String roomNo, String recipient, String sender, String size, String imagePath, String staff,String receivedDateAndTimeStr, String receivedWithStaff) {
        this.type=type;
        dateAndTimeStr=dateAndTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateAndTime, formatter);
        this.dateAndTime = dateTime;
        this.roomNo = roomNo;
        this.recipient = recipient;
        this.sender = sender;
        this.size = size;
        this.imagePath = imagePath;
        this.staff=staff;
        this. receivedDateAndTimeStr= receivedDateAndTimeStr;
        dateTime = LocalDateTime.parse(receivedDateAndTimeStr, formatter);
        receivedDateAndTime = dateTime;
        this.receivedWithStaff=receivedWithStaff;

    }

    public String[] getInformation() {
        String[] q = new String[]{type , dateAndTimeStr ,roomNo, recipient,sender,size,imagePath,staff,receivedDateAndTimeStr,receivedWithStaff};
        return q;
    }



    public String getStaff() {
        return staff;
    }

    public String getDateAndTimeStr() {
        return dateAndTimeStr;
    }


    public String getReceivedWithStaff() {
        return receivedWithStaff;
    }

    public void setDateAndTimeStr(String dateAndTimeStr) {
        this.dateAndTimeStr = dateAndTimeStr;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateAndTimeStr, formatter);
        this.dateAndTime = dateTime;
    }

    public void setReceivedDateAndTimeStr(String receivedDateAndTimeStr) {
        this.receivedDateAndTimeStr = receivedDateAndTimeStr;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(receivedDateAndTimeStr, formatter);
        receivedDateAndTime = dateTime;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getSender() {
        return sender;
    }

    public String getSize() {
        return size;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() { return type; }

    @Override
    public LocalDateTime getTime() {
        if(receivedWithStaff.equalsIgnoreCase("-")) return dateAndTime;
        return receivedDateAndTime;
    }

    public String getReceivedDateAndTimeStr() {
        return receivedDateAndTimeStr;
    }

    public void setReceivedWithStaff(String receivedWithStaff) {
        this.receivedWithStaff = receivedWithStaff;
    }
}

