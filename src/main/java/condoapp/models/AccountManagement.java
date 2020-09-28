package condoapp.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccountManagement {
    private ArrayList<AdminAccount> adminList;
    private AdminAccount currentAdmin;

    private ArrayList<StaffAccount> staffList;
    private StaffAccount currentStaff;


    public AccountManagement(){
        adminList = new ArrayList<>();
        staffList = new ArrayList<>();
    }

    public String checkAdminAccount(String username, String password){
        for(AdminAccount checkAdmin : adminList){
            if(username.equals(checkAdmin.getUsername())&&password.equals(checkAdmin.getPassword())){
                currentAdmin=checkAdmin;
                return  "";}
            if(username.equals(checkAdmin.getUsername())&&!(password.equals(checkAdmin.getPassword()))){return "Wrong password ";}
        }
        return "Wrong username";

    }

    public AdminAccount getCurrentAdmin() {
        return currentAdmin;
    }



    public String checkStaffAccount(String username, String password){
        for(StaffAccount a : staffList){
            int total = Integer.valueOf(a.getAttempt());
            if(username.equals(a.getUsername())&&password.equals(a.getPassword())&&a.getPermission().equals("Allowed")){
                currentStaff=a;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDateTime = LocalDateTime.now().format(formatter);
                currentStaff.setDateAndTimeStr(formattedDateTime);
                return  "";}
            else if(username.equals(a.getUsername())&&password.equals(a.getPassword())){
                total+=1;
                a.setAttempt(total);
                return "You don't have permission to access system.";
            }
            if(username.equals(a.getUsername())&&!(password.equals(a.getPassword()))){return "Wrong password ";}
        }
        return "Wrong username";
    }

    public StaffAccount getCurrentStaff() {
        return currentStaff;
    }

    public ArrayList<AdminAccount> getAdminList() { return adminList; }
    public ArrayList<StaffAccount> getStaffList() {
        return staffList;
    }
}
