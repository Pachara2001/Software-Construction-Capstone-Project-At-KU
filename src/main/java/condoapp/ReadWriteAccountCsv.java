package condoapp;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import condoapp.models.AdminAccount;
import condoapp.models.StaffAccount;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadWriteAccountCsv {

    public void addAdminList(ArrayList<AdminAccount> adminList) throws IOException, com.opencsv.exceptions.CsvValidationException {
        adminList.clear();
        CSVReader reader = new CSVReader(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\admin.csv"));
        String [] admin;
        while((admin = reader.readNext())!=null) {
            AdminAccount ad = new AdminAccount(admin[0], admin[1]);
            adminList.add(ad);

        }
        reader.close();
    }

    public void updateAdminCsv(ArrayList<AdminAccount> adminList) throws IOException{
        ArrayList<String[]> adminStr = new ArrayList<>();
        adminStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\admin.csv"));
        for(AdminAccount upAdmin : adminList){
            String[] q = {upAdmin.getUsername(),upAdmin.getPassword()};
            adminStr.add(q);
        }
        writer.writeAll(adminStr);
        writer.close();

    }

    public void addStaffList(ArrayList<StaffAccount> staffList) throws IOException, CsvValidationException {
        staffList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\staff.csv")).withCSVParser(csvParser).build();
        String [] staff;
        while((staff = reader.readNext())!=null) {
            StaffAccount a = new StaffAccount(staff[0], staff[1],staff[2],staff[3], staff[4],staff[5],staff[6]);
            staffList.add(a);
        }

        reader.close();
    }

    public void updateStaffCsv(ArrayList<StaffAccount> staffList) throws IOException {
        ArrayList<String [] > staffStr = new ArrayList<>();
        staffStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\staff.csv"));
        for(StaffAccount staffUp : staffList){

            String[] q = {staffUp.getUsername(),staffUp.getPassword(),staffUp.getName(),staffUp.getPermission(), staffUp.getDateAndTimeStr(),staffUp.getAttempt(),staffUp.getPicturePath()};
            staffStr.add(q);
        }
        writer.writeAll(staffStr);
        writer.close();
    }
}
