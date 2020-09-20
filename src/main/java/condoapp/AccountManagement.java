package condoapp;

import com.opencsv.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
        adminList.clear();
        CSVReader reader = new CSVReader(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\admin.csv"));
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
                return  "";}
            if(username.equals(checkAdmin.getUsername())&&!(password.equals(checkAdmin.getPassword()))){return "Wrong password ";}
        }
        return "Wrong username";

    }

    public AdminAccount getCurrentAdmin() {
        return currentAdmin;
    }

    public void UpdateAdmin() throws IOException{
        adminStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\admin.csv"));
        for(AdminAccount upAdmin : adminList){
            String[] q = {upAdmin.getUsername(),upAdmin.getPassword()};
            adminStr.add(q);
        }
        writer.writeAll(adminStr);
        writer.close();

    }
    public void AddStaff() throws IOException, com.opencsv.exceptions.CsvValidationException {
        staffList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\staff.csv")).withCSVParser(csvParser).build();
        String [] staff= new  String[7];
        while((staff = reader.readNext())!=null) {
            StaffAccount a = new StaffAccount(staff[0], staff[1],staff[2],staff[3], staff[4],staff[5],staff[6]);
            staffList.add(a);
        }

        reader.close();
    }
    public String CheckStaffAccount(String username,String password){
        for(StaffAccount a : staffList){
            int total = Integer.valueOf(a.getAttempt());
            if(username.equals(a.getUsername())&&password.equals(a.getPassword())&&a.getPermission().equals("Allowed")){
                currentStaff=a;
                currentStaff.setDateAndTime(LocalDateTime.now());
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


    public void UpdateStaff() throws IOException {
        staffStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\staff.csv"));
        for(StaffAccount staffUp : staffList){
            LocalDateTime time = staffUp.getDateAndTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = time.format(formatter);
            String[] q = {staffUp.getUsername(),staffUp.getPassword(),staffUp.getName(),staffUp.getPermission(), formattedDateTime,staffUp.getAttempt(),staffUp.getPicturePath()};
            staffStr.add(q);
        }
        writer.writeAll(staffStr);
        writer.close();
    }

    public void setStaffList(ArrayList<StaffAccount> staffList) {
        this.staffList = staffList;
    }

    public ArrayList<StaffAccount> getStaffList() {
        return staffList;
    }
}
