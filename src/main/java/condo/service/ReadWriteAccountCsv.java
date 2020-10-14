package condo.service;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import condo.models.AdminAccount;
import condo.models.ResidentAccount;
import condo.models.StaffAccount;

import java.io.*;
import java.util.ArrayList;

public class ReadWriteAccountCsv {
    private String adminCsvFilePath ;
    private String staffCsvFilePath ;
    private String residentCsvFilePath;
    private String dir ;
    private String adminFileName;
    private String staffFileName;
    private String residentFileName;

    public ReadWriteAccountCsv(String dir ,String adminFileName, String staffFileName,String residentFileName){
        this.dir=dir;
        this.adminFileName=adminFileName;
        this.staffFileName = staffFileName;
        this.residentFileName=residentFileName;
        this.adminCsvFilePath=(dir+ File.separator+adminFileName);
        this.staffCsvFilePath=(dir+ File.separator+staffFileName);
        this.residentCsvFilePath =(dir+File.separator+residentFileName);

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
        file = new File(residentCsvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + residentFileName);
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

    public void updateAdminCsv(ArrayList<AdminAccount> adminList) throws IOException {
        ArrayList<String[]> adminStr = new ArrayList<>();
        adminStr.clear();
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(adminCsvFilePath);
        CSVWriter   writer = new CSVWriter(fileWriter);
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
        CSVReader reader = new CSVReaderBuilder(new FileReader(staffCsvFilePath)).withCSVParser(csvParser).build();
        String [] staff;
        while((staff = reader.readNext())!=null) {
            StaffAccount a = new StaffAccount(staff[0], staff[1],staff[2],staff[3], staff[4],staff[5],staff[6]);
            staffList.add(a);
        }

        reader.close();
    }

    public void updateStaffCsv(ArrayList<StaffAccount> staffList) throws IOException {
        ArrayList<String [] > staffStr = new ArrayList<>();
        FileWriter  fileWriter = new FileWriter(staffCsvFilePath);
        CSVWriter writer = new CSVWriter(fileWriter);
        for(StaffAccount staffUp : staffList){

            String[] q = {staffUp.getUsername(),staffUp.getPassword(),staffUp.getName(),staffUp.getPermission(), staffUp.getDateAndTimeStr(),staffUp.getAttempt(),staffUp.getPicturePath()};
            staffStr.add(q);
        }

        writer.writeAll(staffStr);
        staffStr.clear();
        writer.close();

    }

    public void addResidentList(ArrayList<ResidentAccount> residentList) throws IOException, CsvValidationException {
        residentList.clear();
        CSVReader reader = new CSVReader(new FileReader(residentCsvFilePath));
        String [] resident;
        while((resident = reader.readNext())!=null) {
            ResidentAccount res = new ResidentAccount(resident[0], resident[1],resident[2],resident[3]);
            residentList.add(res);

        }
        reader.close();
    }

    public void updateResidentCsv(ArrayList<ResidentAccount> residentList) throws IOException {
        ArrayList<String[]> residentStr = new ArrayList<>();
        residentStr.clear();
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(residentCsvFilePath);
        CSVWriter   writer = new CSVWriter(fileWriter);
        for(ResidentAccount upResident : residentList){
            String[] q = {upResident.getUsername(),upResident.getPassword(),upResident.getName(),upResident.getRoomNo()};
            residentStr.add(q);
        }
        writer.writeAll(residentStr);
        writer.close();
    }
}
