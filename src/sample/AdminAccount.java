package sample;

import java.util.ArrayList;

public class AdminAccount {

    private  String username,password;

    public AdminAccount(String username,String password){
        this.username=username;
        this.password=password;
    }
    public void AddStaff(String username,String password){
        //open staff.csv
        //write username,password
    }

    public void ChangePassword(String password){
        //open admin csv
        //find username
        //edit password
    }

    public ArrayList<String[]> ShowStaffList(){
        //Arraylist<String[]> a=new.......
        //String[] = csv.split
        //arraylist.sort

    }
    public void EditStaffPermission(String username){
        //open staff csv
        //find username
        //edit active status
    }

    

}
