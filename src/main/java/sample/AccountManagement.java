package sample;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccountManagement {
    private ArrayList<AdminAccount> adminList;
    private AdminAccount currentAdmin;
    private ArrayList<String[]> adminStr,staffStr;
    private ArrayList<StaffAccount> staffList;
    private StaffAccount currentStaff;


    public AccountManagement(){
        adminList = new ArrayList<>();
        staffList = new ArrayList<>();
        adminStr = new ArrayList<>();
        staffStr= new ArrayList<>();
    }
    public void AddAdmin() throws IOException, com.opencsv.exceptions.CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\CSV file\\admin.csv"));
        String [] admin= new  String [2];
        while((admin = reader.readNext())!=null) {
            AdminAccount ad = new AdminAccount(admin[0], admin[1]);
            adminList.add(ad);

        }
        reader.close();
    }
    public String CheckAdminAccount(String username,String password){
        for(AdminAccount checkAdmin : adminList){
            if(username.equals(checkAdmin.getUsername())&&password.equals(checkAdmin.getPassword())){
                currentAdmin=checkAdmin;
                return  "pass";}
            if(username.equals(checkAdmin.getUsername())&&!(password.equals(checkAdmin.getPassword()))){return "wrong password ";}
        }
        return "wrong username";

    }

    public AdminAccount getCurrentAdmin() {
        return currentAdmin;
    }

    public void UpdateAdmin() throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter("src\\main\\resources\\CSV file\\admin.csv"));
        for(AdminAccount upAdmin : adminList){
            String[] q = {upAdmin.getUsername(),upAdmin.getPassword()};
            adminStr.add(q);
        }
        writer.writeAll(adminStr);
        writer.close();

    }
    public void AddStaff() throws IOException, com.opencsv.exceptions.CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\CSV file\\staff.csv"));
        String [] staff= new  String [5];
        while((staff = reader.readNext())!=null) {
            StaffAccount a = new StaffAccount(staff[0], staff[1],staff[2],staff[3], staff[4]);
            staffList.add(a);
        }
        System.out.println(staffList);
        reader.close();
    }
    public String CheckStaffAccount(String username,String password){
        for(StaffAccount a : staffList){
            int total = Integer.valueOf(a.getTotal());
            if(username.equals(a.getUsername())&&password.equals(a.getPassword())&&a.getPermission().equals("1")){
                currentStaff=a;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                currentStaff.setDateAndTime(formatter.format(date));
                return  "pass";}
            else if(username.equals(a.getUsername())&&password.equals(a.getPassword())){
                total+=1;
                a.setTotal(total);

                return "you don't have permission";
            }
            if(username.equals(a.getUsername())&&!(password.equals(a.getPassword()))){return "wrong password ";}
        }
        return "wrong username";
    }

    public StaffAccount getCurrentStaff() {
        return currentStaff;
    }

    public void UpdateStaff() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("src\\main\\resources\\CSV file\\staff.csv"));
        for(StaffAccount staffUp : staffList){
            String[] q = {staffUp.getUsername(),staffUp.getPassword(),staffUp.getPermission(),staffUp.getDateAndTime(),staffUp.getTotal()};
            staffStr.add(q);
        }
        writer.writeAll(staffStr);
        writer.close();
    }


}
