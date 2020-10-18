package condo.controllers;


import condo.Start;
import condo.models.*;
import condo.services.ReadWriteAccountCsv;
import condo.services.ReadWriteItemCsv;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ResidentPageController {
    private ReadWriteAccountCsv readWriteAccountCsv;
    private AccountManagement accountManage;
    private ItemManagement itemManage;
    private ReadWriteItemCsv readWriteItemCsv;
    private Item selectedItem,selectedReceivedItem;
    @FXML private Label errorMyAccountLabel,imageErrorLabel,myPasswordLabel,myUsernameLabel,receivedInfoLabel1,receivedInfoLabel2,selectedAcceptStaffLabel,selectedItem1Label,selectedItem2Label,selectedItemInfo1Label,selectedItemInfo2Label,selectedReceived1Label,selectedReceived2Label,selectedReceivedAcceptStaffLabel,selectedReceivedPickedLabel,selectedReceivedSenderLabel,selectedReceivedSizeLabel,selectedReceivedStaffPickLabel,selectedSenderLabel,selectedSizeLabel,selReceivedImageErrorLabel,welcomeLabel1,welcomeLabel2,welcomeLabel3;
    @FXML private PasswordField confirmNewPassTextField,newPassTextField;
    @FXML private TableColumn itemDateCol,itemRecipientCol,itemRoomNoCol,itemTypeCol,receivedItemDateCol,receivedItemRecipientCol,receivedItemRoomNoCol,receivedItemTypeCol;
    @FXML private TableView itemTable,receivedItemTable;
    @FXML private ImageView itemImage,receivedItemImage;

    @FXML public  void initialize()  {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                welcomeLabel1.setText("Welcome : "+accountManage.getCurrentResident().getName());
                welcomeLabel2.setText("Welcome : "+accountManage.getCurrentResident().getName());
                welcomeLabel3.setText("Welcome : "+accountManage.getCurrentResident().getName());
                myUsernameLabel.setText(accountManage.getCurrentResident().getUsername());
                myPasswordLabel.setText(accountManage.getCurrentResident().getPassword());
                itemManage = new ItemManagement();
                readWriteItemCsv = new ReadWriteItemCsv("csv","items.csv","receivedItem.csv");
                readWriteItemCsv.addItemList(itemManage.getItemList());
                readWriteItemCsv.addReceivedItemList(itemManage.getReceivedItemList());
                createItemListTable(itemManage.searchItemByRoomNo(accountManage.getCurrentResident().getRoomNo(),itemManage.getItemList()));
                createReceivedItemListTable(itemManage.searchItemByRoomNo(accountManage.getCurrentResident().getRoomNo(),itemManage.getReceivedItemList()));
            }
        });
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
            selectedItem2Label.setText("Tracking No. :");
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
    }
    public void clearSelectedItem(){
        selectedItem1Label.setText("");
        selectedItemInfo1Label.setText("");
        selectedItem2Label.setText("");
        selectedItemInfo2Label.setText("");
        selectedSizeLabel.setText("");
        selectedSenderLabel.setText("");
        selectedAcceptStaffLabel.setText("");
        imageErrorLabel.setText("");
        itemImage.setImage(null);
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
        selReceivedImageErrorLabel.setText("");
    }
    @FXML public void handleHomeImg(MouseEvent event)   {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        try {
            stage.setScene(new Scene(loader.load(),800,600));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void handleChangeBtn(ActionEvent event)  {
        errorMyAccountLabel.setTextFill(Color.web("#bf2d2d"));
        if(newPassTextField.getText().isEmpty()||confirmNewPassTextField.getText().isEmpty()) errorMyAccountLabel.setText("Fill out the empty field.");
        else  if(newPassTextField.getText().equals(confirmNewPassTextField.getText())){
            accountManage.getCurrentResident().setPassword(newPassTextField.getText());
            myPasswordLabel.setText(accountManage.getCurrentResident().getPassword());
            readWriteAccountCsv.updateResidentCsv(accountManage.getResidentList());
            errorMyAccountLabel.setText("Success !!");
            errorMyAccountLabel.setTextFill(Color.web("#44c55a"));
        }
        else errorMyAccountLabel.setText("Passwords do not match.");
        newPassTextField.setText("");
        confirmNewPassTextField.setText("");
    }

    public void setReadWriteAccountCsv(ReadWriteAccountCsv readWriteAccountCsv) { this.readWriteAccountCsv = readWriteAccountCsv; }

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }
}
