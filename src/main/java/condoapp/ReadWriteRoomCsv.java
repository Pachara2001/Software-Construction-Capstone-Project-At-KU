package condoapp;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import condoapp.models.Room;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReadWriteRoomCsv {

    public void addRoomList(ArrayList<Room> roomList) throws IOException, com.opencsv.exceptions.CsvValidationException {
        roomList.clear();
        CSVReader reader = new CSVReader(new FileReader("E:\\work\\Lab SW\\Project\\CSV file\\rooms.csv"));
        String [] room;
        while((room = reader.readNext())!=null) {
            Room ad = new Room(room[0], room[1],room[2],room[3],room[4],room[5]);
        }
        reader.close();
    }
    public void updateRoomCsv(ArrayList<Room> roomList) throws IOException{
        ArrayList<String[]> roomStr = new ArrayList<>();
        roomStr.clear();
        CSVWriter writer = new CSVWriter(new FileWriter("E:\\work\\Lab SW\\Project\\CSV file\\rooms.csv"));
        for(Room upRoom : roomList){
            String[] q = {upRoom.getBuilding(),upRoom.getFloor(),upRoom.getRoomNo(),upRoom.getType(),upRoom.getResident1(),upRoom.getResident2()};
            roomStr.add(q);
        }
        writer.writeAll(roomStr);
        writer.close();

    }
}
