package condoapp.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {

    private String type, receiver,roomNo,sender,size,imagePath,dateAndTimeStr,staff,receivedWithStaff,receiveDateAndTimeStr;
    private LocalDateTime dateAndTime,receiveDateAndTime;

    public Item( String type,String dateAndTime,String roomNo,String receiver , String sender, String size, String imagePath,String staff,String receiveDateAndTime,String receivedWithStaff) {
        this.receiver = receiver;
        this.roomNo = roomNo;
        this.sender = sender;
        this.size = size;
        this.imagePath = imagePath;
        dateAndTimeStr=dateAndTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateAndTime, formatter);
        this.dateAndTime = dateTime;
        this.type=type;
        this.staff=staff;
        receiveDateAndTimeStr=receiveDateAndTime;
        LocalDateTime date = LocalDateTime.parse(receiveDateAndTime, formatter);
        this.receiveDateAndTime=date;
        this.receivedWithStaff=receivedWithStaff;

    }

    public String[] getInformation() {
        String[] q = new String[]{type , dateAndTimeStr ,roomNo,receiver,sender,size,imagePath,staff,receiveDateAndTimeStr,receivedWithStaff};
        return q;
    }

    public void setReceiveDateAndTimeStr(String receiveDateAndTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(receiveDateAndTimeStr, formatter);
        receiveDateAndTime=dateTime;
        this.receiveDateAndTimeStr = receiveDateAndTimeStr;
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

    public LocalDateTime getReceiveDateAndTime() {
        return receiveDateAndTime;
    }

    public String getReceiveDateAndTimeStr() {
        return receiveDateAndTimeStr;
    }

    public String getReceiver() {
        return receiver;
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

    public String getType() { return type; }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setReceivedWithStaff(String receivedWithStaff) {
        this.receivedWithStaff = receivedWithStaff;
    }
}

