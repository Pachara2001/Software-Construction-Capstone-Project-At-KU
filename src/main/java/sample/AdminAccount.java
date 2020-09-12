package sample;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class AdminAccount extends Account{



    public AdminAccount(String username,String password){
        super(username,password);
    }
    public void AddStaff(String username,String password) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("src\\main\\resources\\staff.csv",true));
        String[] a= {username,password,"1","date","0"};
        writer.writeNext(a);
        writer.close();
    }




    public void EditStaffPermission(StaffAccount staff){
        if(staff.getPermission().equals("0")){
            staff.setPermission("1");
            staff.setTotal(0);}
        else staff.setPermission("0");
    }



    @Override
    public String toString() {
        return "AdminAccount{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
