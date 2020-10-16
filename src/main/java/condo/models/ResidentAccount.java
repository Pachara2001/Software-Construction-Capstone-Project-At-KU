package condo.models;

public class ResidentAccount extends Account{
    private String roomNo;
    private String name;

    public ResidentAccount(String username, String password,String name ,String roomNo) {
        super(username, password);
        this.roomNo = roomNo;
        this.name=name;
    }

    public String getRoomNo() { return roomNo; }
    public String getName() { return name; }
}
