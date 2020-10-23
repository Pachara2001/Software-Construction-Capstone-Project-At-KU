package condo.models;

public class Document extends Item {
    private String importance;

    public Document(String type, String dateAndTime, String roomNo, String recipient, String sender, String size, String imagePath, String staff, String importance,String receivedDateAndTimeStr, String receivedWithStaff) {
        super(type, dateAndTime, roomNo, recipient, sender, size, imagePath, staff,receivedDateAndTimeStr, receivedWithStaff);
        this.importance = importance;
    }

    public String getImportance() {
        return importance;
    }
    @Override
    public String[] getInformation() {
        String[] q = new String[]{getType() , getDateAndTimeStr() ,getRoomNo(), getRecipient(),getSender(),getSize(),getImagePath(),getStaff(), importance,getReceivedDateAndTimeStr(),getReceivedWithStaff()};
        return q;
    }
}
