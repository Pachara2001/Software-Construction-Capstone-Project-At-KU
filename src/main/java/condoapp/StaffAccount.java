package condoapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StaffAccount extends Account {
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

    public String getDateAndTimeStr() {
        return dateAndTimeStr;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
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

    public String getName() {
        return name;
    }

    public String getAttempt() {
        return attempt;
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
