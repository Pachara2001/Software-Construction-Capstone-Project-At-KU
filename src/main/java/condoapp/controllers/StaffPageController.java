package condoapp.controllers;

import condoapp.models.AccountManagement;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class StaffPageController {
    private AccountManagement account ;
    private TableView roomTable,itemTable,receivedItemTable;
    private Button changePassBtn,createItemBtn,createRoomBtn,createSearchImageBtn,itemRefreshBtn,receivedBtn,searchItemRoomNoBtn,searchReceivedItemRoomNoBtn;
    private ImageView homeImg,homeImg1,homeImg2,homeImg3,homeImg4,homeImg5,itemImage1,receivedItemImage;
    private Label createErrorBtn,errorCreateRoomLabel,errorNewPassLabel,errorReceivedLabel,myPasswordLabel,myUsernameLabel,receivedSelectedStaffPickLabel,receivedSelectedPickedLabel,roomsTypeLabel,selectedAcceptStaffLabel,selectedCompanyLabel,selectedImportanceLabel,selectedSenderLabel,selectedSizeLabel,selectedTrackLabel,welcomeLabel,welcomeLabel2,welcomeLabel3,welcomeLabel4,welcomeLabel5,welcomeLabel6,receivedSelectedSizeLabel,receivedSelectedSenderLabel,receivedSelectedCompanyLabel,receivedSelectedImportanceLabel,receivedSelectedAcceptStaffLabel,receivedSelectedTrackLabel;
    private PasswordField confirmNewPassTextField,newPassTextField;
    private TableColumn itemDateCol,itemRecipientCol,itemRoomNoCol,itemTypeCol,roomBuildingCol,roomFloorCol,roomNoCol,receivedItemDateCol,receivedItemRoomNoCol,receivedItemRecipientCol,receivedItemTypeCol;
    private TextField createSizeTextField,createImageTextField,createSenderTextField,createRecipientTextField,createCompanyTextField,createImportanceTextField,createTrackTextField,createResident1TextField,createResident2TextField,createItemRoomNoTextField,createRoomNoTextField,recipientTextField,searchItemRoomNoTextField,resident1TextField,resident2TextField,searchReceivedItemRoomNoTextField;


    public void setAccount(AccountManagement account) {
        this.account = account;
    }
}
