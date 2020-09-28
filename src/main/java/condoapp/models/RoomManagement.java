package condoapp.models;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import condoapp.models.Room;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RoomManagement {
    private ArrayList<Room> roomList;
    private ArrayList<String[]> roomStr;


    public RoomManagement() {
        roomList= new ArrayList<>();
        roomStr=new ArrayList<>();
    }

    //ยังไม่เสร็จ

}
