package condo.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import condo.models.Room;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    public void addRoomList(ArrayList<Room> roomList) throws IOException, com.opencsv.exceptions.CsvValidationException {
        roomList.clear();
        CSVReader reader = new CSVReader(new FileReader(roomCsvFilePath));
        String [] room;
        while((room = reader.readNext())!=null) {
            Room ad = new Room(room[0], room[1],room[2],room[3],room[4],room[5]);
            roomList.add(ad);
        }
        reader.close();
    }
    public void updateRoomCsv(ArrayList<Room> roomList) throws IOException{
        ArrayList<String[]> roomStr = new ArrayList<>();
        roomStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter(roomCsvFilePath));
        for(Room upRoom : roomList){
            String[] q = {upRoom.getBuilding(),upRoom.getFloor(),upRoom.getRoomNo(),upRoom.getType(),upRoom.getResident1(),upRoom.getResident2()};
            roomStr.add(q);
        }
        writer.writeAll(roomStr);
        writer.close();

    }
}
