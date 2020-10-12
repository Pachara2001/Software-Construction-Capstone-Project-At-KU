package condo.models;

public class Document extends Item {
    private String importance;

    public Document(String type, String dateAndTime, String roomNo, String receiver, String sender, String size, String imagePath, String staff, String importance,String receivedDateAndTimeStr, String receivedWithStaff) {
        super(type, dateAndTime, roomNo, receiver, sender, size, imagePath, staff,receivedDateAndTimeStr, receivedWithStaff);
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
