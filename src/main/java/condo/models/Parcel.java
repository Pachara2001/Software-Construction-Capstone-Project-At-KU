package condo.models;

public class Parcel extends Item {
    private String company,trackingNumber;

    public Parcel(String type, String dateAndTime, String roomNo, String receiver, String sender, String size, String imagePath, String staff, String company, String trackingNumber, String receivedDateAndTimeStr,String receivedWithStaff) {
        super(type, dateAndTime, roomNo, receiver, sender, size, imagePath, staff,receivedDateAndTimeStr, receivedWithStaff);
        this.company = company;
        this.trackingNumber = trackingNumber;
    }


    public String getCompany() {
        return company;
    }
    public String getTrackingNumber() {
        return trackingNumber;
    }
    @Override
    public String[] getInformation() {
        String[] q = new String[]{getType() , getDateAndTimeStr() ,getRoomNo(), getRecipient(),getSender(),getSize(),getImagePath(),getStaff(),company,trackingNumber,getReceivedDateAndTimeStr(),getReceivedWithStaff()};
        return q;
    }
}
