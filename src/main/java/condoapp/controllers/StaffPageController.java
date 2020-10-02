package condoapp.controllers;

import com.opencsv.exceptions.CsvValidationException;
import condoapp.service.ReadWriteAccountCsv;
import condoapp.service.ReadWriteRoomCsv;
import condoapp.models.AccountManagement;
import condoapp.models.RoomManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class StaffPageController {
    private AccountManagement accountManage;
    private RoomManagement roomManage;
    private ReadWriteRoomCsv readWriteRoomCsv;
    private ReadWriteAccountCsv readWriteAccountCsv;
    @FXML private TableView roomTable,itemTable,receivedItemTable;
    @FXML private Button changePassBtn,createResidentBtn,createItemBtn,createRoomBtn,createSearchImageBtn,itemRefreshBtn,receivedBtn,searchItemRoomNoBtn,searchReceivedItemRoomNoBtn;
    @FXML private ImageView homeImg,homeImg1,homeImg2,homeImg3,homeImg4,homeImg5,itemImage1,receivedItemImage;
    @FXML private Label createErrorBtn,errorCreateResidentLabel,errorCreateRoomLabel,errorNewPassLabel,errorReceivedLabel,myPasswordLabel,myUsernameLabel,receivedSelectedStaffPickLabel,receivedSelectedPickedLabel,roomsTypeLabel,selectedAcceptStaffLabel,selectedCompanyLabel,selectedImportanceLabel,selectedSenderLabel,selectedSizeLabel,selectedTrackLabel,welcomeLabel,welcomeLabel2,welcomeLabel3,welcomeLabel4,welcomeLabel5,welcomeLabel6,receivedSelectedSizeLabel,receivedSelectedSenderLabel,receivedSelectedCompanyLabel,receivedSelectedImportanceLabel,receivedSelectedAcceptStaffLabel,receivedSelectedTrackLabel;
    @FXML private PasswordField confirmNewPassTextField,newPassTextField;
    @FXML private TableColumn itemDateCol,itemRecipientCol,itemRoomNoCol,itemTypeCol,roomBuildingCol,roomFloorCol,roomNoCol,receivedItemDateCol,receivedItemRoomNoCol,receivedItemRecipientCol,receivedItemTypeCol;
    @FXML private TextField createSizeTextField,createImageTextField,residentRoomNoTextField,createSenderTextField,createRecipientTextField,createCompanyTextField,createImportanceTextField,createTrackTextField,createResident1TextField,createResident2TextField,createItemRoomNoTextField,createRoomNoTextField,recipientTextField,searchItemRoomNoTextField,resident1TextField,resident2TextField,searchReceivedItemRoomNoTextField;
    @FXML private ChoiceBox roomsTypeChoiceBox,buildingChoiceBox,itemTypeChoiceBox,floorChoiceBox;

    public  void initialize() throws IOException, CsvValidationException {
        fillRoomsTypeChoiceBox();
        fillBuildingChoiceBox();
        fillItemTypeChoiceBox();
        fillFloorChoiceBox();
        roomManage = new RoomManagement();

        readWriteRoomCsv = new ReadWriteRoomCsv("csv","room.csv");
        readWriteRoomCsv.addRoomList(roomManage.getRoomList());
    }



    public void fillRoomsTypeChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("One-Bedroom");
        list.add("Two-Bedroom");
        ObservableList obList = FXCollections.observableArrayList(list);
        roomsTypeChoiceBox.setItems(obList);
    }
    public void fillBuildingChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        ObservableList obList = FXCollections.observableArrayList(list);
        buildingChoiceBox.setItems(obList);
    }
    public void fillItemTypeChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Letter");
        list.add("Document");
        list.add("Parcel");
        ObservableList obList = FXCollections.observableArrayList(list);
        itemTypeChoiceBox.setItems(obList);
    }
    public void fillFloorChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        ObservableList obList = FXCollections.observableArrayList(list);
        floorChoiceBox.setItems(obList);
    }

    public void setReadWriteAccountCsv(ReadWriteAccountCsv readWriteAccountCsv) {
        this.readWriteAccountCsv = readWriteAccountCsv;
    }
    @FXML public void handleCreateResidentBtn() throws IOException {
        errorCreateRoomLabel.setTextFill(Color.web("#bf2d2d"));
        errorCreateResidentLabel.setText(accountManage.getCurrentStaff().addResident(residentRoomNoTextField.getText(), createResident1TextField.getText(), createResident2TextField.getText(), roomManage.getRoomList()));
        if (errorCreateResidentLabel.getText().isEmpty()) {
            readWriteRoomCsv.updateRoomCsv(roomManage.getRoomList());
            errorCreateRoomLabel.setText("Success !!");
            errorCreateRoomLabel.setTextFill(Color.web("#44c55a"));
        }
    }
    @FXML public void handleCreateRoomBtn() throws IOException {
       errorCreateRoomLabel.setTextFill(Color.web("#bf2d2d"));
       errorCreateRoomLabel.setText( accountManage.getCurrentStaff().addRoom((String)buildingChoiceBox.getValue(),(String) floorChoiceBox.getValue(),createRoomNoTextField.getText(),(String) roomsTypeChoiceBox.getValue(),createResident1TextField.getText(),createResident2TextField.getText(),roomManage.getRoomList()));
       if(errorCreateRoomLabel.getText().isEmpty()){
           readWriteRoomCsv.updateRoomCsv(roomManage.getRoomList());
           errorCreateRoomLabel.setText("Success !!");
           errorCreateRoomLabel.setTextFill(Color.web("#44c55a"));

       }
       roomsTypeChoiceBox.setValue(null);
       buildingChoiceBox.setValue(null);
       floorChoiceBox.setValue(null);
       createRoomNoTextField.setText("");
       createResident1TextField.setText("");
       createResident2TextField.setText("");
    }
    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }
}
