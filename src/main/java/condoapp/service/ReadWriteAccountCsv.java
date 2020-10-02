package condoapp.service;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import condoapp.models.AdminAccount;
import condoapp.models.SortByDateAndTime;
import condoapp.models.StaffAccount;

import java.io.*;
import java.util.ArrayList;

public class ReadWriteAccountCsv {
    private String adminCsvFilePath ;
    private String staffCsvFilePath ;
    private String dir ;
    private String adminFileName;
    private String staffFileName;

    public ReadWriteAccountCsv(String dir ,String adminFileName, String staffFileName){
        this.dir=dir;
        this.adminFileName=adminFileName;
        this.staffFileName = staffFileName;
        this.adminCsvFilePath=(dir+ File.separator+adminFileName);
        this.staffCsvFilePath=(dir+ File.separator+staffFileName);

        checkFilePath();
    }

    private void checkFilePath() {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(adminCsvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + adminFileName);
            }
        }

        file = new File(staffCsvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + staffFileName);
            }
        }
    }
    public void addAdminList(ArrayList<AdminAccount> adminList) throws IOException, CsvValidationException {
        adminList.clear();
        CSVReader reader = new CSVReader(new FileReader(adminCsvFilePath));
        String [] admin;
        while((admin = reader.readNext())!=null) {
            AdminAccount ad = new AdminAccount(admin[0], admin[1]);
            adminList.add(ad);

        }
        reader.close();
    }

    public void updateAdminCsv(ArrayList<AdminAccount> adminList) {

        ArrayList<String[]> adminStr = new ArrayList<>();
        adminStr.clear();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(adminCsvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CSVWriter   writer = new CSVWriter(fileWriter);


        for(AdminAccount upAdmin : adminList){
                String[] q = {upAdmin.getUsername(),upAdmin.getPassword()};
                adminStr.add(q);
            }
            writer.writeAll(adminStr);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addStaffList(ArrayList<StaffAccount> staffList) throws IOException, CsvValidationException {
        staffList.clear();
        CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();

        CSVReader reader = new CSVReaderBuilder(new FileReader(staffCsvFilePath)).withCSVParser(csvParser).build();
        String [] staff;
        while((staff = reader.readNext())!=null) {
            StaffAccount a = new StaffAccount(staff[0], staff[1],staff[2],staff[3], staff[4],staff[5],staff[6]);
            staffList.add(a);
        }

        reader.close();
    }

    public void updateStaffCsv(ArrayList<StaffAccount> staffList)  {
        ArrayList<String [] > staffStr = new ArrayList<>();
        staffList.sort(new SortByDateAndTime());
        staffStr.clear();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(staffCsvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVWriter writer = new CSVWriter(fileWriter);
        for(StaffAccount staffUp : staffList){

            String[] q = {staffUp.getUsername(),staffUp.getPassword(),staffUp.getName(),staffUp.getPermission(), staffUp.getDateAndTimeStr(),staffUp.getAttempt(),staffUp.getPicturePath()};
            staffStr.add(q);
        }

        writer.writeAll(staffStr);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
