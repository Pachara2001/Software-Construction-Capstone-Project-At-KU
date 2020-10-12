package condo.models;


import java.util.ArrayList;


public class AdminAccount extends Account {



    public AdminAccount(String username,String password){
        super(username,password);
    }


    public void addStaff(String username, String password, String name, String picturePath, ArrayList<StaffAccount> staff)  {
        for(StaffAccount s : staff){
            if(username.equalsIgnoreCase(s.getUsername())) throw  new IllegalArgumentException("This username already exists ");
        }
        StaffAccount newStaff = new StaffAccount(username,password,name,"Allowed","01/01/0001 00:00:01","0",picturePath);
        staff.add(newStaff);

    }




    public void editStaffPermission(StaffAccount staff){
        if(staff.getPermission().equalsIgnoreCase("Not allowed")){
            staff.setPermission("Allowed");
            staff.setAttempt(0);}
        else staff.setPermission("Not allowed");
    }



    @Override
    public String toString() {
        return "AdminAccount{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
