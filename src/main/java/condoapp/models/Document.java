package condoapp.models;

public class Document extends Item {
    private String importance;

    public Document(String type, String dateAndTime, String roomNo, String receiver, String sender, String size, String imagePath, String staff, String level, String receiveDateAndTime, String receivedWithStaff) {
        super(type, dateAndTime, roomNo, receiver, sender, size, imagePath, staff, receiveDateAndTime, receivedWithStaff);
        this.importance = level;
    }

    public String getLevel() {
        return importance;
    }

    @Override
    public String[] getInformation() {
        String[] q = new String[]{getType() , getDateAndTimeStr() ,getRoomNo(),getReceiver(),getSender(),getSize(),getImagePath(),getStaff(), importance,getReceiveDateAndTimeStr(),getReceivedWithStaff()};
        return q;
    }
}
