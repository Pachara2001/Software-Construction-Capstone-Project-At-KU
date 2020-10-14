package condo.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccountManagement {
    private ArrayList<AdminAccount> adminList;
    private AdminAccount currentAdmin=null;

    private ArrayList<StaffAccount> staffList;
    private StaffAccount currentStaff=null;

    private ArrayList<ResidentAccount> residentList;
    private ResidentAccount currentResident=null;


    public AccountManagement(){
        adminList = new ArrayList<>();
        staffList = new ArrayList<>();
        residentList = new ArrayList<>();
    }

    public void checkAdminAccount(String username, String password){
        for(AdminAccount checkAdmin : adminList){
            if(username.equalsIgnoreCase(checkAdmin.getUsername())){
                if(checkAdmin.getPassword().equals(password)) currentAdmin=checkAdmin;
                else throw new IllegalArgumentException("Wrong password.");
            }
        }
        if(currentAdmin==null){throw new NullPointerException("Wrong username.");}

    }

    public AdminAccount getCurrentAdmin() {
        return currentAdmin;
    }



    public void checkStaffAccount(String username, String password){
        for(StaffAccount a : staffList){
            if(username.equalsIgnoreCase(a.getUsername())){

                if(password.equals(a.getPassword())){
                    if(a.getPermission().equals("Allowed")){
                        currentStaff=a;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        String formattedDateTime = LocalDateTime.now().format(formatter);
                        currentStaff.setDateAndTimeStr(formattedDateTime);
                    }
                    else{
                        int total = Integer.valueOf(a.getAttempt());
                        total+=1;
                        a.setAttempt(total);
                        throw new SecurityException("You don't have permission to access system.");
                    }
                }
                else throw new IllegalArgumentException( "Wrong password.");
            }
        }
        if(currentStaff==null) throw new NullPointerException( "Wrong username.");
    }

    public void createResident(String roomNo,String name,String password,ArrayList<Room> roomList){
        int findStatus =0;
        if(roomNo.isEmpty()||name.isEmpty()||password.isEmpty()) throw  new IllegalArgumentException("Fill out the empty fields.");
        for(Room room : roomList ){
            if(room.isRoomNoMatch(roomNo)) {
                findStatus=1;
                if(!room.isResidentMatch(name)) throw new IllegalArgumentException("Name not match with any resident in this room number.");
            }
        }
        roomNo = roomNo.toUpperCase();
        String username = name+roomNo;
        if(findStatus==0) throw new IllegalArgumentException("Not found this room number in system.");
        for(ResidentAccount res : residentList){
            if(res.getUsername().equalsIgnoreCase(username)) throw new IllegalArgumentException("This name already have an account.");
        }

        residentList.add(new ResidentAccount(username,password,name,roomNo));
    }

    public void checkResidentAccount(String username,String password){
        for(ResidentAccount resident : residentList){
            if(resident.getUsername().equalsIgnoreCase(username)){
                if(resident.getPassword().equals(password)) currentResident=resident;
                else throw new IllegalArgumentException("Wrong password.");
            }
        }
        if(currentResident==null) throw new NullPointerException("Wrong username.");

    }
    public StaffAccount getCurrentStaff() {
        return currentStaff;
    }

    public ArrayList<AdminAccount> getAdminList() { return adminList; }
    public ArrayList<StaffAccount> getStaffList() {
        return staffList;
    }

    public ArrayList<ResidentAccount> getResidentList() {
        return residentList;
    }

    public ResidentAccount getCurrentResident() {
        return currentResident;
    }
}
