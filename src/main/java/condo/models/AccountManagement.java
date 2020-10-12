package condo.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccountManagement {
    private ArrayList<AdminAccount> adminList;
    private AdminAccount currentAdmin=null;

    private ArrayList<StaffAccount> staffList;
    private StaffAccount currentStaff=null;


    public AccountManagement(){
        adminList = new ArrayList<>();
        staffList = new ArrayList<>();
    }

    public void checkAdminAccount(String username, String password){
        for(AdminAccount checkAdmin : adminList){
            if(username.equalsIgnoreCase(checkAdmin.getUsername())&&password.equals(checkAdmin.getPassword())){
                currentAdmin=checkAdmin;
            }
            if(username.equalsIgnoreCase(checkAdmin.getUsername())&&!(password.equals(checkAdmin.getPassword()))){ throw new IllegalArgumentException("Wrong pass word.");}
        }
        if(currentAdmin==null){throw new IllegalArgumentException("Wrong username.");}

    }

    public AdminAccount getCurrentAdmin() {
        return currentAdmin;
    }



    public void checkStaffAccount(String username, String password){
        for(StaffAccount a : staffList){
            int total = Integer.valueOf(a.getAttempt());
            if(username.equalsIgnoreCase(a.getUsername())&&password.equals(a.getPassword())&&a.getPermission().equals("Allowed")){
                currentStaff=a;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDateTime = LocalDateTime.now().format(formatter);
                currentStaff.setDateAndTimeStr(formattedDateTime);
              }
            else if(username.equalsIgnoreCase(a.getUsername())&&password.equals(a.getPassword())){
                total+=1;
                a.setAttempt(total);
                throw new SecurityException("You don't have permission to access system.");
            }
            if(username.equalsIgnoreCase(a.getUsername())&&!(password.equals(a.getPassword()))){ throw new IllegalArgumentException( "Wrong password ");}
        }
        if(currentStaff==null) throw new IllegalArgumentException( "Wrong username");
    }

    public StaffAccount getCurrentStaff() {
        return currentStaff;
    }

    public ArrayList<AdminAccount> getAdminList() { return adminList; }
    public ArrayList<StaffAccount> getStaffList() {
        return staffList;
    }
}
