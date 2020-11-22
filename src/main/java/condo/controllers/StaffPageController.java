package condo.controllers;

import condo.Start;
import condo.models.*;
import condo.services.ReadWriteAccountCsv;
import condo.services.ReadWriteItemCsv;
import condo.services.ReadWriteRoomCsv;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class StaffPageController {
    private AccountManagement accountManage;
    private RoomManagement roomManage;
    private ReadWriteRoomCsv readWriteRoomCsv;
    private ReadWriteAccountCsv readWriteAccountCsv;
    private ItemManagement itemManage;
    private ReadWriteItemCsv readWriteItemCsv;
    private Room selectedRoom;
    private Item selectedItem,selectedReceivedItem;
    @FXML private TableView roomTable,itemTable,receivedItemTable;
    @FXML private Button updateRoomInfoBtn,searchRoomBtn,searchSelectedItemImageBtn,changePassBtn,createResidentBtn,createItemBtn,createRoomBtn,createSearchImageBtn,itemRefreshBtn, receiveBtn,searchItemRoomNoBtn,searchReceivedItemRoomNoBtn;
    @FXML private ImageView homeImg,homeImg1,homeImg2,homeImg3,homeImg4,homeImg5,itemImage,receivedItemImage;
    @FXML private Label roomNoLabel,selectedReceivedSizeLabel, selectedReceivedSenderLabel,selectedReceivedAcceptStaffLabel, selectedReceivedPickedLabel, selectedReceivedStaffPickLabel,selectedReceived1Label,receivedInfoLabel1,receivedInfoLabel2,selectedReceived2Label,selReceivedImageErrorLabel,searchReceivedErrorLabel,editRoomErrorLabel,searchRoomErrorLabel,searchItemErrorLabel,createItemErrorLabel,errorCreateResidentLabel,imageErrorLabel,errorCreateRoomLabel,errorNewPassLabel,errorReceivedLabel,myUsernameLabel,roomsTypeLabel,selectedAcceptStaffLabel,selectedItem1Label,selectedItem2Label,selectedSenderLabel,selectedSizeLabel,welcomeLabel,welcomeLabel2,welcomeLabel3,welcomeLabel4,welcomeLabel5,welcomeLabel6,selectedItemInfo1Label,selectedItemInfo2Label;
    @FXML private PasswordField confirmNewPassTextField,newPassTextField;
    @FXML private TableColumn itemDateCol,itemRecipientCol,itemRoomNoCol,itemTypeCol,roomBuildingCol,roomFloorCol,roomNoCol,receivedItemDateCol,receivedItemRoomNoCol,receivedItemRecipientCol,receivedItemTypeCol;
    @FXML private TextField searchRoomTextField,resident2TextField,resident1TextField,createFloorTextField,createSizeTextField,createImageTextField,residentRoomNoTextField,createSenderTextField,createRecipientTextField,createCompanyTextField,createTrackTextField,createResident1TextField,createResident2TextField,createItemRoomNoTextField,createRoomNoTextField,recipientTextField,searchItemRoomNoTextField,searchReceivedItemRoomNoTextField;
    @FXML private ChoiceBox createImportanceChoiceBox,roomsTypeChoiceBox,buildingChoiceBox,itemTypeChoiceBox;

 @FXML   public  void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                welcomeLabel .setText("Welcome : "+accountManage.getCurrentStaff().getName());
                welcomeLabel2.setText("Welcome : "+accountManage.getCurrentStaff().getName());
                welcomeLabel3.setText("Welcome : "+accountManage.getCurrentStaff().getName());
                welcomeLabel4.setText("Welcome : "+accountManage.getCurrentStaff().getName());
                welcomeLabel5.setText("Welcome : "+accountManage.getCurrentStaff().getName());
                welcomeLabel6.setText("Welcome : "+accountManage.getCurrentStaff().getName());
                myUsernameLabel.setText(accountManage.getCurrentStaff().getUsername());

            }
        });
        roomManage = new RoomManagement();
        itemManage = new ItemManagement();
        readWriteRoomCsv = new ReadWriteRoomCsv("csv","room.csv");
        readWriteRoomCsv.addRoomList(roomManage.getRoomList());
        readWriteItemCsv = new ReadWriteItemCsv("csv","items.csv","receivedItem.csv");
        readWriteItemCsv.addItemList(itemManage.getItemList());
        readWriteItemCsv.addReceivedItemList(itemManage.getReceivedItemList());
        fillRoomsTypeChoiceBox();
        fillBuildingChoiceBox();
        fillItemTypeChoiceBox();
        fillCreateImportanceChoiceBox();
        createItemListTable(itemManage.getItemList());
        createReceivedItemListTable(itemManage.getReceivedItemList());
        createRoomListTable(roomManage.getRoomList());
        errorCreateRoomLabel.setText("");
        errorCreateResidentLabel.setText("");
        createItemErrorLabel.setText("");
        errorNewPassLabel.setText("");

    }

    public void createRoomListTable(ArrayList<Room> roomList){
        clearSelectedRoom();
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList(roomList);
        roomBuildingCol.setCellValueFactory(new PropertyValueFactory<Room,String>("building"));
        roomFloorCol.setCellValueFactory(new PropertyValueFactory<Room,String>("floor"));
        roomNoCol.setCellValueFactory(new PropertyValueFactory<Room,String>("roomNo"));
        roomTable.setItems(roomObservableList);
        roomTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Room a = (Room) newValue;
                showSelectedRoom(a);
            }
        });
    }
    private void showSelectedRoom(Room room){
        updateRoomInfoBtn.setDisable(false);
        selectedRoom=room;
        roomNoLabel.setText(selectedRoom.getRoomNo());
        roomsTypeLabel.setText(selectedRoom.getType());
        resident1TextField.setText(selectedRoom.getResident1());
        resident2TextField.setText(selectedRoom.getResident2());
        if(selectedRoom.getResident1().isEmpty()&&selectedRoom.getResident2().isEmpty()) updateRoomInfoBtn.setDisable(true);
    }
    public void clearSelectedRoom(){
        updateRoomInfoBtn.setDisable(true);
        roomNoLabel.setText("");
        roomsTypeLabel.setText("");
        resident1TextField.setText("");
        resident2TextField.setText("");
        editRoomErrorLabel.setText("");
        searchRoomErrorLabel.setText("");
    }
    @FXML public void handleUpdateRoomInfoBtn(ActionEvent event)  {
        editRoomErrorLabel.setTextFill(Color.web("#bf2d2d"));

        if((!selectedRoom.getResident1().isEmpty()&&resident1TextField.getText().isEmpty())||(!selectedRoom.getResident2().isEmpty()&&resident2TextField.getText().isEmpty())){
            int status=0;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirm_delete_resident_page.fxml"));
            try {
                stage.setScene(new Scene(loader.load(),500,300));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConfirmDeleteResidentPageController confirm = loader.getController();
            if(!selectedRoom.getResident1().isEmpty()&&resident1TextField.getText().isEmpty()) confirm.setName(selectedRoom.getResident1());
            else if(!selectedRoom.getResident2().isEmpty()&&resident2TextField.getText().isEmpty()) confirm.setName(selectedRoom.getResident2());
            if((!selectedRoom.getResident1().isEmpty()&&resident1TextField.getText().isEmpty())&&(!selectedRoom.getResident2().isEmpty()&&resident2TextField.getText().isEmpty())) confirm.setName(selectedRoom.getResident1()+" and "+selectedRoom.getResident2());
            stage.setTitle("confirm");
            stage.setResizable(false);
            stage.showAndWait();
            status = confirm.getStatus();
            if(status ==0) return;
        }
        try {
            selectedRoom.editResident(resident1TextField.getText(), resident2TextField.getText());
            readWriteRoomCsv.updateRoomCsv(roomManage.getRoomList());
            editRoomErrorLabel.setText("success !!");
            editRoomErrorLabel.setTextFill(Color.web("#44c55a"));
            clearSelectedRoom();
            roomTable.refresh();
            roomTable.getSelectionModel().clearSelection();
        }
        catch (IllegalArgumentException e){
            editRoomErrorLabel.setText(e.getMessage());
        }

    }
    @FXML public void handleSearchRoomBtn(ActionEvent event){
        searchRoomErrorLabel.setText("");
        try {
            createRoomListTable(roomManage.searchRoomByResident(searchRoomTextField.getText()));
        }
        catch (IllegalArgumentException e){
            searchRoomErrorLabel.setText(e.getMessage());
        }
    }

    public void createItemListTable(ArrayList<Item> itemList){
        clearSelectedItem();
        itemList.sort(new SortByDateAndTime());
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(itemList);
        itemDateCol.setCellValueFactory(new PropertyValueFactory<Item,String>("dateAndTimeStr"));
        itemRoomNoCol.setCellValueFactory(new PropertyValueFactory<Item,String>("roomNo"));
        itemRecipientCol.setCellValueFactory(new PropertyValueFactory<Item,String>("recipient"));
        itemTypeCol.setCellValueFactory(new PropertyValueFactory<Item,String>("type"));
        itemTable.setItems(itemObservableList);
        itemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Item a = (Item) newValue;
                showSelectedItem(a);
            }
        });

    }
    public void showSelectedItem(Item item){
        selectedItem=item;
        clearSelectedItem();
        selectedSizeLabel.setText(selectedItem.getSize());
        selectedSenderLabel.setText(selectedItem.getSender());
        selectedAcceptStaffLabel.setText(selectedItem.getStaff());

        if(selectedItem.checkType("parcel")){
            Parcel parcel = (Parcel) selectedItem;
            selectedItem1Label.setText("Company :");
            selectedItemInfo1Label.setText(parcel.getCompany());
            selectedItem2Label.setText("Tracking No. :");
            selectedItemInfo2Label.setText(parcel.getTrackingNumber());
        }
        if(selectedItem.checkType("document")){
            Document document = (Document) selectedItem;
            selectedItem1Label.setText("Importance :");
            selectedItemInfo1Label.setText(document.getImportance());
        }
        File jarDir = null;
        File codeDir = null;
        try {
            jarDir = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            codeDir = jarDir.getParentFile();
            String path = codeDir.toString() + File.separator + selectedItem.getImagePath();
            File uploadFile = new File(path);
            itemImage.setImage(new Image(uploadFile.toURI().toString()));
            if(!uploadFile.exists()){
                imageErrorLabel.setText("Image not found !!");
            }
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        receiveBtn.setDisable(false);
        searchSelectedItemImageBtn.setDisable(false);

    }
    public void clearSelectedItem(){
        receiveBtn.setDisable(true);
        searchSelectedItemImageBtn.setDisable(true);
        selectedItem1Label.setText("");
        selectedItemInfo1Label.setText("");
        selectedItem2Label.setText("");
        selectedItemInfo2Label.setText("");
        errorReceivedLabel.setText("");
        selectedSizeLabel.setText("");
        selectedSenderLabel.setText("");
        selectedAcceptStaffLabel.setText("");
        searchItemErrorLabel.setText("");
        imageErrorLabel.setText("");
        recipientTextField.setText("");
        itemImage.setImage(null);
    }
    @FXML public void handleReceiveBtn(ActionEvent event)  {
        if(recipientTextField.getText().equalsIgnoreCase(selectedItem.getRecipient())){
            accountManage.getCurrentStaff().receiveItem(selectedItem,itemManage.getItemList(),itemManage.getReceivedItemList());
            readWriteItemCsv.updateItemCsv(itemManage.getItemList());
            readWriteItemCsv.updateReceivedItemCsv(itemManage.getReceivedItemList());
            createItemListTable(itemManage.getItemList());
            createReceivedItemListTable(itemManage.getReceivedItemList());
            clearSelectedItem();
            itemTable.refresh();
            itemTable.getSelectionModel().clearSelection();
        }
        else{errorReceivedLabel.setText("Recipient name not match.");}
    }

    public void createReceivedItemListTable(ArrayList<Item> itemList){
        clearSelectedReceivedItem();
        itemList.sort(new SortByDateAndTime());
        ObservableList<Item> receivedItemObservableList = FXCollections.observableArrayList(itemList);
        receivedItemDateCol.setCellValueFactory(new PropertyValueFactory<Item,String>("receivedDateAndTimeStr"));
        receivedItemRoomNoCol.setCellValueFactory(new PropertyValueFactory<Item,String>("roomNo"));
        receivedItemRecipientCol.setCellValueFactory(new PropertyValueFactory<Item,String>("recipient"));
        receivedItemTypeCol.setCellValueFactory(new PropertyValueFactory<Item,String>("type"));
        receivedItemTable.setItems(receivedItemObservableList);
        receivedItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Item a = (Item) newValue;
                showSelectedReceivedItem(a);
            }
        });

    }
    public void showSelectedReceivedItem(Item item){
        selectedReceivedItem=item;
        clearSelectedReceivedItem();
        selectedReceivedSizeLabel.setText(selectedReceivedItem.getSize());
        selectedReceivedSenderLabel.setText(selectedReceivedItem.getSender());
        selectedReceivedAcceptStaffLabel.setText(selectedReceivedItem.getStaff());
        selectedReceivedPickedLabel.setText(selectedReceivedItem.getRecipient());
        selectedReceivedStaffPickLabel.setText(selectedReceivedItem.getReceivedWithStaff());
        if(selectedReceivedItem.checkType("parcel")){
            Parcel parcel = (Parcel) selectedReceivedItem;
            selectedReceived1Label.setText("Company :");
            receivedInfoLabel1.setText(parcel.getCompany());
            selectedReceived2Label.setText("Tracking Number :");
            receivedInfoLabel2.setText(parcel.getTrackingNumber());
        }
        if(selectedReceivedItem.checkType("document")){
            Document document = (Document) selectedReceivedItem;
            selectedReceived1Label.setText("Importance :");
            receivedInfoLabel1.setText(document.getImportance());
        }
        File jarDir = null;
        File codeDir = null;
        try {
            jarDir = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            codeDir = jarDir.getParentFile();
            String path = codeDir.toString() + File.separator + selectedReceivedItem.getImagePath();
            File uploadFile = new File(path);
            receivedItemImage.setImage(new Image(uploadFile.toURI().toString()));
            if(!uploadFile.exists()){
                selReceivedImageErrorLabel.setText("Image not found !!");
            }
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void clearSelectedReceivedItem(){
        receivedItemImage.setImage(null);
        selectedReceivedSizeLabel.setText("");
        selectedReceivedSenderLabel.setText("");
        selectedReceivedAcceptStaffLabel.setText("");
        selectedReceived1Label.setText("");
        receivedInfoLabel1.setText("");
        selectedReceived2Label.setText("");
        receivedInfoLabel2.setText("");
        selectedReceivedPickedLabel.setText("");
        selectedReceivedStaffPickLabel.setText("");
        searchReceivedErrorLabel.setText("");
        selReceivedImageErrorLabel.setText("");
    }

    @FXML public void handleSearchReceivedItemRoomNoBtn(){
        searchReceivedErrorLabel.setText("");
        try {
            createReceivedItemListTable(itemManage.searchItemByRoomNo(searchReceivedItemRoomNoTextField.getText(), itemManage.getReceivedItemList()));
        }
        catch (IllegalArgumentException e){
            searchReceivedErrorLabel.setText(e.getMessage());
        }
    }

    @FXML public void handleSearchItemRoomNoBtn(ActionEvent event){
        searchItemErrorLabel.setText("");
        try {
            createItemListTable(itemManage.searchItemByRoomNo(searchItemRoomNoTextField.getText(), itemManage.getItemList()));
        }
        catch (IllegalArgumentException e){
            searchItemErrorLabel.setText(e.getMessage());
        }
    }

    @FXML public void handleSearchSelectedItemImageBtn(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null){
            imageErrorLabel.setText("");
            File destDir = null;
            try {
                destDir = new File("images");
                destDir.mkdirs();
                Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+selectedFile.getName());
                Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                selectedItem.setImagePath(destDir.getName()+File.separator+selectedFile.getName());
                readWriteItemCsv.updateItemCsv(itemManage.getItemList());

            } catch (IOException e) {
                e.printStackTrace();
            }
            File jarDir = null;
            File codeDir = null;
            try {
                jarDir = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                codeDir = jarDir.getParentFile();
                String path = codeDir.toString() + File.separator + selectedItem.getImagePath();
                File uploadFile = new File(path);
                itemImage.setImage(new Image(uploadFile.toURI().toString()));}
            catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }


    @FXML  public void handleChangePassBtn(ActionEvent event)  {
        errorNewPassLabel.setTextFill(Color.web("#bf2d2d"));
        if(newPassTextField.getText().isEmpty()||confirmNewPassTextField.getText().isEmpty()) errorNewPassLabel.setText("Fill out the empty field.");
        if(newPassTextField.getText().equals(confirmNewPassTextField.getText())){
            accountManage.getCurrentStaff().setPassword(newPassTextField.getText());
            readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
            errorNewPassLabel.setText("Success !!");
            errorNewPassLabel.setTextFill(Color.web("#44c55a"));
        }
        else {errorNewPassLabel.setText("Passwords do not match.");}
        newPassTextField.setText("");
        confirmNewPassTextField.setText("");
    }
    public void fillRoomsTypeChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("One-Bedroom");
        list.add("Two-Bedroom");
        ObservableList obList = FXCollections.observableArrayList(list);
        roomsTypeChoiceBox.setItems(obList);
        roomsTypeChoiceBox.setValue("-");
    }
    public void fillBuildingChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        ObservableList obList = FXCollections.observableArrayList(list);
        buildingChoiceBox.setItems(obList);
        buildingChoiceBox.setValue("-");
    }
    public void fillItemTypeChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Letter");
        list.add("Document");
        list.add("Parcel");
        ObservableList obList = FXCollections.observableArrayList(list);
        itemTypeChoiceBox.setItems(obList);
        itemTypeChoiceBox.setValue("-");
    }
    public void fillCreateImportanceChoiceBox(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Top Secret");
        list.add("Secret");
        list.add("Confidential");
        list.add("Restricted");
        list.add("Unclassified");
        list.add("Emergency");
        list.add("Urgent");
        list.add("Standard");
        list.add("Normal");
        ObservableList obList = FXCollections.observableArrayList(list);
        createImportanceChoiceBox.setItems(obList);
        createImportanceChoiceBox.setValue("-");
    }


    @FXML public void handleCreateSearchImageBtn(ActionEvent event){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null){
            Path target = null;
            File destDir = null;
            try {
                destDir = new File("images");
                destDir.mkdirs();
                target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+selectedFile.getName());
                Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                createImageTextField.setText(destDir.getName()+File.separator+selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void handleHomeImg(MouseEvent event) {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        try {
            stage.setScene(new Scene(loader.load(),800,600));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    @FXML public  void handleItemTypeChoiceBox(){
     try{
        if(((String)itemTypeChoiceBox.getValue()).equalsIgnoreCase("Letter")){
            createCompanyTextField.setDisable(true);
            createImportanceChoiceBox.setDisable(true);
            createTrackTextField.setDisable(true);
        }
         if (((String)itemTypeChoiceBox.getValue()).equalsIgnoreCase("Document")){
            createTrackTextField.setDisable(true);
            createCompanyTextField.setDisable(true);
             createImportanceChoiceBox.setDisable(false);
        }
        if (((String)itemTypeChoiceBox.getValue()).equalsIgnoreCase("Parcel")) {
            createImportanceChoiceBox.setDisable(true);
            createTrackTextField.setDisable(false);
            createCompanyTextField.setDisable(false);
        }}
     catch (NullPointerException e){}

    }
    @FXML public void handleCreateItemBtn()  {
        createItemErrorLabel.setTextFill(Color.web("#bf2d2d"));
        try{
            accountManage.getCurrentStaff().addItem((String)itemTypeChoiceBox.getValue(),createItemRoomNoTextField.getText(),createRecipientTextField.getText(),createSenderTextField.getText(),createSizeTextField.getText(),createImageTextField.getText(),(String)createImportanceChoiceBox.getValue(),createCompanyTextField.getText(),createTrackTextField.getText(),itemManage.getItemList(),roomManage.getRoomList());
            readWriteItemCsv.updateItemCsv(itemManage.getItemList());
            createItemListTable(itemManage.getItemList());
            createItemErrorLabel.setText("Success !!");
            createItemErrorLabel.setTextFill(Color.web("#44c55a"));
            createSizeTextField.setText("");
            createImageTextField.setText("");
            createItemRoomNoTextField.setText("");
            createSenderTextField.setText("");
            createRecipientTextField.setText("");
            createCompanyTextField.setText("");
            createTrackTextField.setText("");
            createCompanyTextField.setDisable(false);
            createImportanceChoiceBox.setDisable(false);
            createTrackTextField.setDisable(false);
            itemTypeChoiceBox.getSelectionModel().clearSelection();
            itemTypeChoiceBox.setValue("-");
            createImportanceChoiceBox.setValue(null);
            createImportanceChoiceBox.setValue("-");
        }
        catch (IllegalArgumentException e) {
            createItemErrorLabel.setText(e.getMessage());
        }
    }



    @FXML public void handleCreateResidentBtn()  {
        errorCreateResidentLabel.setTextFill(Color.web("#bf2d2d"));
        try{
            accountManage.getCurrentStaff().addResident(residentRoomNoTextField.getText(), createResident1TextField.getText(), createResident2TextField.getText(), roomManage.getRoomList());
            readWriteRoomCsv.updateRoomCsv(roomManage.getRoomList());
            createRoomListTable(roomManage.getRoomList());
            errorCreateResidentLabel.setText("Success !!");
            errorCreateResidentLabel.setTextFill(Color.web("#44c55a"));
        }
        catch (IllegalArgumentException e) {
            errorCreateResidentLabel.setText(e.getMessage());
        }
        residentRoomNoTextField.setText("");
        createResident1TextField.setText("");
        createResident2TextField.setText("");
    }
    @FXML public void handleCreateRoomBtn() {
       errorCreateRoomLabel.setTextFill(Color.web("#bf2d2d"));

       try{
           accountManage.getCurrentStaff().addRoom((String)buildingChoiceBox.getValue(),createFloorTextField.getText(),createRoomNoTextField.getText(),(String) roomsTypeChoiceBox.getValue(),"","",roomManage.getRoomList());
           readWriteRoomCsv.updateRoomCsv(roomManage.getRoomList());
           createRoomListTable(roomManage.getRoomList());
           errorCreateRoomLabel.setText("Success !! Your room number is "+buildingChoiceBox.getValue()+parseInt(createFloorTextField.getText())+createRoomNoTextField.getText()+".");
           errorCreateRoomLabel.setTextFill(Color.web("#44c55a"));
       }
       catch (IllegalArgumentException e) {
           errorCreateRoomLabel.setText(e.getMessage());
       }
       roomsTypeChoiceBox.setValue(null);
       roomsTypeChoiceBox.setValue("-");
       buildingChoiceBox.setValue(null);
       buildingChoiceBox.setValue("-");
       createFloorTextField.setText("");
       createRoomNoTextField.setText("");

    }
    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }
    public void setReadWriteAccountCsv(ReadWriteAccountCsv readWriteAccountCsv) { this.readWriteAccountCsv = readWriteAccountCsv; }
}
