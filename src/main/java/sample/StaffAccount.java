package sample;

public class StaffAccount extends Account{
    private  String permission,dateAndTime,total;



    public StaffAccount(String username,String password,String permission,String dateAndTime,String total){
        super(username,password);
        this.permission=permission;
        this.dateAndTime=dateAndTime;
        this.total=total;

    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setTotal(int total) {
        String a=String.valueOf(total);
        this.total = a;
    }

    public String getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "StaffAccount{" + "Username :"+ getUsername()+ "Password :"+ getPassword()+
                "permission='" + permission + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
