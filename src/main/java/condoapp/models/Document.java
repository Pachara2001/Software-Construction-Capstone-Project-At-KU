package condoapp.models;

import java.time.LocalDateTime;

public class Document extends Item {
    private String level;

    public Document(String type, String dateAndTime, String roomNo, String receiver, String sender, String size, String imagePath, String staff, String level, String receiveDateAndTime, String receivedWithStaff) {
        super(type, dateAndTime, roomNo, receiver, sender, size, imagePath, staff, receiveDateAndTime, receivedWithStaff);
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String[] getInformation() {
        String[] q = new String[]{getType() , getDateAndTimeStr() ,getRoomNo(),getReceiver(),getSender(),getSize(),getImagePath(),getStaff(),level,getReceiveDateAndTimeStr(),getReceivedWithStaff()};
        return q;
    }
}
