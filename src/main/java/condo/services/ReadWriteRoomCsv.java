package condo.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import condo.models.Room;

import java.io.*;
import java.util.ArrayList;

public class ReadWriteRoomCsv {
    private String roomCsvFilePath;
    private String roomFileName;
    private String dir;

    public ReadWriteRoomCsv(String dir, String roomFileName){
        this.dir=dir;
        this.roomFileName=roomFileName;
        roomCsvFilePath = (dir+ File.separator+roomFileName);
        checkFilePath();
    }

    private void checkFilePath() {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(roomCsvFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + roomFileName);
            }
        }


    }
    public void addRoomList(ArrayList<Room> roomList) {
        roomList.clear();
        CSVReader reader=null;
        try{
        reader = new CSVReader(new FileReader(roomCsvFilePath));
        String [] room;
        while((room = reader.readNext())!=null) {
            Room ad = new Room(room[0], room[1],room[2],room[3],room[4],room[5]);
            roomList.add(ad);
        }} catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void updateRoomCsv(ArrayList<Room> roomList) {
        ArrayList<String[]> roomStr = new ArrayList<>();
        CSVWriter writer =null;
        try{
         writer = new CSVWriter(new FileWriter(roomCsvFilePath));
        for(Room upRoom : roomList){
            String[] q = {upRoom.getBuilding(),upRoom.getFloor(),upRoom.getRoomNo(),upRoom.getType(),upRoom.getResident1(),upRoom.getResident2()};
            roomStr.add(q);
        }
        writer.writeAll(roomStr);} catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
