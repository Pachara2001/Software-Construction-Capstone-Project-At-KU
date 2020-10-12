package condo.controllers;

import com.opencsv.exceptions.CsvValidationException;
import condo.Main;
import condo.models.*;
import condo.service.ReadWriteAccountCsv;
import condo.service.ReadWriteItemCsv;
import condo.service.ReadWriteRoomCsv;
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
    @FXML private Label roomNoLabel,selectedReceivedSizeLabel, selectedReceivedSenderLabel,selectedReceivedAcceptStaffLabel, selectedReceivedPickedLabel, selectedReceivedStaffPickLabel,selectedReceived1Label,receivedInfoLabel1,receivedInfoLabel2,selectedReceived2Label,selReceivedImageErrorLabel,searchReceivedErrorLabel,editRoomErrorLabel,searchRoomErrorLabel,searchItemErrorLabel,createItemErrorLabel,errorCreateResidentLabel,imageErrorLabel,errorCreateRoomLabel,errorNewPassLabel,errorReceivedLabel,myPasswordLabel,myUsernameLabel,roomsTypeLabel,selectedAcceptStaffLabel,selectedItem1Label,selectedItem2Label,selectedSenderLabel,selectedSizeLabel,welcomeLabel,welcomeLabel2,welcomeLabel3,welcomeLabel4,welcomeLabel5,welcomeLabel6,selectedItemInfo1Label,selectedItemInfo2Label;
    @FXML private PasswordField confirmNewPassTextField,newPassTextField;
    @FXML private TableColumn itemDateCol,itemRecipientCol,itemRoomNoCol,itemTypeCol,roomBuildingCol,roomFloorCol,roomNoCol,receivedItemDateCol,receivedItemRoomNoCol,receivedItemRecipientCol,receivedItemTypeCol;
    @FXML private TextField searchRoomTextField,resident2TextField,resident1TextField,createFloorTextField,createSizeTextField,createImageTextField,residentRoomNoTextField,createSenderTextField,createRecipientTextField,createCompanyTextField,createImportanceTextField,createTrackTextField,createResident1TextField,createResident2TextField,createItemRoomNoTextField,createRoomNoTextField,recipientTextField,searchItemRoomNoTextField,searchReceivedItemRoomNoTextField;
    @FXML private ChoiceBox roomsTypeChoiceBox,buildingChoiceBox,itemTypeChoiceBox;

    public  void initialize() throws IOException, CsvValidationException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                welcomeLabel .setText(accountManage.getCurrentStaff().getName());
                welcomeLabel2.setText(accountManage.getCurrentStaff().getName());
                welcomeLabel3.setText(accountManage.getCurrentStaff().getName());
                welcomeLabel4.setText(accountManage.getCurrentStaff().getName());
                welcomeLabel5.setText(accountManage.getCurrentStaff().getName());
                welcomeLabel6.setText(accountManage.getCurrentStaff().getName());
                myUsernameLabel.setText(accountManage.getCurrentStaff().getUsername());
                myPasswordLabel.setText(accountManage.getCurrentStaff().getPassword());

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
        createItemListTable(itemManage.getItemList());
        createReceivedItemListTable(itemManage.getItemList());
        createRoomListTable(roomManage.getRoomList());
        receiveBtn.setDisable(true);
    }

    public void createRoomListTable(ArrayList<Room> roomList){
        //sortListByID
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
        selectedRoom=room;
        roomNoLabel.setText(selectedRoom.getRoomNo());
        roomsTypeLabel.setText(selectedRoom.getType());
        resident1TextField.setText(selectedRoom.getResident1());
        resident2TextField.setText(selectedRoom.getResident2());
    }
    public void clearSelectedRoom(){
        roomNoLabel.setText("");
        roomsTypeLabel.setText("");
        resident1TextField.setText("");
        resident2TextField.setText("");
        editRoomErrorLabel.setText("");
        searchRoomErrorLabel.setText("");
    }
    public void handleUpdateRoomInfoBtn(ActionEvent event) throws IOException {
        editRoomErrorLabel.setTextFill(Color.web("#bf2d2d"));

        if((!selectedRoom.getResident1().isEmpty()&&resident1TextField.getText().isEmpty())||(!selectedRoom.getResident2().isEmpty()&&resident2TextField.getText().isEmpty())){
            int status=0;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/confirm_delete_resident_page.fxml"));
            stage.setScene(new Scene(loader.load(),500,300));
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
        }
        catch (IllegalArgumentException e){
            editRoomErrorLabel.setText(e.getMessage());
        }

    }
    public void handleSearchRoomBtn(ActionEvent event){
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

        if(selectedItem.getType().equalsIgnoreCase("parcel")){
            Parcel parcel = (Parcel) selectedItem;
            selectedItem1Label.setText("Company :");
            selectedItemInfo1Label.setText(parcel.getCompany());
            selectedItem2Label.setText("Tracking Number :");
            selectedItemInfo2Label.setText(parcel.getTrackingNumber());
        }
        if(selectedItem.getType().equalsIgnoreCase("document")){
            Document document = (Document) selectedItem;
            selectedItem1Label.setText("Importance :");
            selectedItemInfo1Label.setText(document.getImportance());
        }
        File jarDir = null;
        File codeDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            codeDir = jarDir.getParentFile();
            String path = codeDir.toString() + File.separator + selectedItem.getImagePath();
            File uploadFile = new File(path);
            itemImage.setImage(new Image(uploadFile.toURI().toString()));
            if(!uploadFile.exists()){
                imageErrorLabel.setText("Image not found!!");
            }
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        receiveBtn.setDisable(false);



    }
    public void clearSelectedItem(){
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
        itemImage.setImage(null);
    }
    public void handleReceiveBtn(ActionEvent event) throws IOException {
        if(recipientTextField.getText().equalsIgnoreCase(selectedItem.getRecipient())){
            accountManage.getCurrentStaff().receiveItem(selectedItem,itemManage.getItemList(),itemManage.getReceivedItemList());
            readWriteItemCsv.updateItemCsv(itemManage.getItemList());
            readWriteItemCsv.updateReceivedItemCsv(itemManage.getReceivedItemList());
            createItemListTable(itemManage.getItemList());
            createReceivedItemListTable(itemManage.getReceivedItemList());
            clearSelectedItem();
        }
        else{errorReceivedLabel.setText("Recipient name don't match with database.");}

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

        if(selectedReceivedItem.getType().equalsIgnoreCase("parcel")){
            Parcel parcel = (Parcel) selectedReceivedItem;
            selectedReceived1Label.setText("Company :");
            receivedInfoLabel1.setText(parcel.getCompany());
            selectedReceived2Label.setText("Tracking Number :");
            receivedInfoLabel2.setText(parcel.getTrackingNumber());
        }
        if(selectedReceivedItem.getType().equalsIgnoreCase("document")){
            Document document = (Document) selectedReceivedItem;
            selectedReceived1Label.setText("Importance :");
            receivedInfoLabel1.setText(document.getImportance());
        }
        File jarDir = null;
        File codeDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            codeDir = jarDir.getParentFile();
            String path = codeDir.toString() + File.separator + selectedReceivedItem.getImagePath();
            File uploadFile = new File(path);
            receivedItemImage.setImage(new Image(uploadFile.toURI().toString()));
            if(!uploadFile.exists()){
                selReceivedImageErrorLabel.setText("Image not found!!");
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

    public void handleSearchReceivedItemRoomNoBtn(){
        searchReceivedErrorLabel.setText("");
        try {
            createReceivedItemListTable(accountManage.getCurrentStaff().searchItemByRoomNo(searchReceivedItemRoomNoTextField.getText(), itemManage.getReceivedItemList()));
        }
        catch (IllegalArgumentException e){
            searchReceivedErrorLabel.setText(e.getMessage());
        }
    }



    public void handleSearchItemRoomNoBtn(ActionEvent event){
        searchItemErrorLabel.setText("");
        try {
            createItemListTable(accountManage.getCurrentStaff().searchItemByRoomNo(searchItemRoomNoTextField.getText(), itemManage.getItemList()));
        }
        catch (IllegalArgumentException e){
            searchItemErrorLabel.setText(e.getMessage());
        }
    }



    public void handleSearchSelectedItemImageBtn(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );
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
                jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                codeDir = jarDir.getParentFile();
                String path = codeDir.toString() + File.separator + selectedItem.getImagePath();
                File uploadFile = new File(path);
                itemImage.setImage(new Image(uploadFile.toURI().toString()));}
            catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }


    public void handleChangePassBtn(ActionEvent event) throws IOException {
        errorNewPassLabel.setTextFill(Color.web("#bf2d2d"));
        if(newPassTextField.getText().equals(confirmNewPassTextField.getText())){
            accountManage.getCurrentStaff().setPassword(newPassTextField.getText());
            myPasswordLabel.setText(accountManage.getCurrentStaff().getPassword());
            readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
            errorNewPassLabel.setText("Success !!");
            errorNewPassLabel.setTextFill(Color.web("#44c55a"));
        }
        else {errorNewPassLabel.setText("Passwords do not match");}
        newPassTextField.setText("");
        confirmNewPassTextField.setText("");
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
        list.add("B");
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


    @FXML public void handleCreateSearchImageBtn(ActionEvent event){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );
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

    @FXML public void handleHomeImg(MouseEvent event) throws IOException {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.show();
    }
    @FXML public void handleCreateItemBtn() throws IOException {
        createItemErrorLabel.setTextFill(Color.web("#bf2d2d"));
        try{
            accountManage.getCurrentStaff().addItem((String)itemTypeChoiceBox.getValue(),createItemRoomNoTextField.getText(),createRecipientTextField.getText(),createSenderTextField.getText(),createSizeTextField.getText(),createImageTextField.getText(),createImportanceTextField.getText(),createCompanyTextField.getText(),createTrackTextField.getText(),itemManage.getItemList(),roomManage.getRoomList());
            readWriteItemCsv.updateItemCsv(itemManage.getItemList());
            createItemListTable(itemManage.getItemList());
            createItemErrorLabel.setText("Success !!");
            createItemErrorLabel.setTextFill(Color.web("#44c55a"));
        }
        catch (IllegalArgumentException e) {
            createItemErrorLabel.setText(e.getMessage());
        }
        itemTypeChoiceBox.setValue(null);
        createSizeTextField.setText("");
        createImageTextField.setText("");
        createItemRoomNoTextField.setText("");
        createSenderTextField.setText("");
        createRecipientTextField.setText("");
        createCompanyTextField.setText("");
        createImportanceTextField.setText("");
        createTrackTextField.setText("");
    }


        public void setReadWriteAccountCsv(ReadWriteAccountCsv readWriteAccountCsv) {
        this.readWriteAccountCsv = readWriteAccountCsv;
    }
    @FXML public void handleCreateResidentBtn() throws IOException {

        errorCreateRoomLabel.setTextFill(Color.web("#bf2d2d"));
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
    @FXML public void handleCreateRoomBtn() throws IOException {
       errorCreateRoomLabel.setTextFill(Color.web("#bf2d2d"));
       try{
           accountManage.getCurrentStaff().addRoom((String)buildingChoiceBox.getValue(),createFloorTextField.getText(),createRoomNoTextField.getText(),(String) roomsTypeChoiceBox.getValue(),"","",roomManage.getRoomList());
           readWriteRoomCsv.updateRoomCsv(roomManage.getRoomList());
           createRoomListTable(roomManage.getRoomList());
           errorCreateRoomLabel.setText("Success !!");
           errorCreateRoomLabel.setTextFill(Color.web("#44c55a"));
       }
       catch (IllegalArgumentException e) {
           errorCreateRoomLabel.setText(e.getMessage());
       }
       roomsTypeChoiceBox.setValue(null);
       buildingChoiceBox.setValue(null);
       createFloorTextField.setText("");
       createRoomNoTextField.setText("");

    }
    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }
}