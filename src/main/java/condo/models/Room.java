package condo.models;

public class Room {
    private String roomNo,resident1,building,floor,type,resident2="-";

    public Room(String building,String floor,String roomNo, String type,String resident1,String resident2) {
        this.roomNo = roomNo;
        this.resident1 = resident1;
        this.building = building;
        this.floor = floor;
        this.type = type;
        this.resident2 = resident2;
    }
    public boolean isRoomNoMatch(String roomNo){
        if(this.roomNo.equalsIgnoreCase(roomNo)) return true;
        return false;
    }
    public boolean isResidentMatch(String name){
        if(resident1.equalsIgnoreCase(name)||resident2.equalsIgnoreCase(name)) return true;
        return false;
    }
    public boolean checkResident(){
        if(type.equalsIgnoreCase("One-Bedroom")&&!resident1.isEmpty()) return true;
        if(type.equalsIgnoreCase("Two-Bedroom")&&(!resident1.isEmpty()||!resident2.isEmpty())) return true;
        return false;
    }
    public void editResident(String resident1 , String resident2){
        if(type.equalsIgnoreCase("One-Bedroom")&&!resident2.isEmpty()) throw  new IllegalArgumentException("Resident 2 must be void.");
        this.resident1=resident1;
        this.resident2=resident2;
    }
    public void setResident1(String resident1) {
        this.resident1 = resident1;
    }
    public void setResident2(String resident2) {
        this.resident2 = resident2;
    }
    public String getRoomNo() {
        return roomNo;
    }
    public String getResident1() {
        return resident1;
    }
    public String getBuilding() {
        return building;
    }
    public String getFloor() {
        return floor;
    }
    public String getType() {
        return type;
    }
    public String getResident2() {
        return resident2;
    }
    @Override
    public String toString() {
        return "Room{" +
                "roomNo='" + roomNo + '\'' +
                ", resident1='" + resident1 + '\'' +
                ", building='" + building + '\'' +
                ", floor='" + floor + '\'' +
                ", type='" + type + '\'' +
                ", resident2='" + resident2 + '\'' +
                '}';
    }
}
