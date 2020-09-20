package condoapp;

import com.opencsv.exceptions.CsvValidationException;
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
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AdminPageController {
    private AccountManagement accountManage;
    private StaffAccount selectedStaff;
    private ObservableList<StaffAccount> staffObservableList;
    @FXML private Label welcomeLabel,welcomeLabel1,welcomeLabel11,nameLabel,permissionLabel,attemptLabel,imageErrorLabel,errorCreateAccountLabel,errorMyAccountLabel,myPasswordLabel,myUsernameLabel;
    @FXML private PasswordField confirmNewPassTextField,confirmTextField,newPassTextField,passwordTextField;
    @FXML private ImageView staffPicImageView;
    @FXML private TableView staffListTable;
    @FXML private TableColumn usernameCol,dateTimeCol;
    @FXML private Button createBtn,changeBtn,searchImgBtn,refreshBtn,searchImageSelectedStaffBtn,editPermissionBtn,updateSelectedStaffBtn;
    @FXML private TextField nameTextField,usernameTextField,pictureTextField;





    public  void initialize() throws IOException, CsvValidationException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                welcomeLabel.setText(accountManage.getCurrentAdmin().getUsername());
                welcomeLabel1.setText(accountManage.getCurrentAdmin().getUsername());
                welcomeLabel11.setText(accountManage.getCurrentAdmin().getUsername());
                myPasswordLabel.setText(accountManage.getCurrentAdmin().getPassword());
                myUsernameLabel.setText(accountManage.getCurrentAdmin().getUsername());
                updateSelectedStaffBtn.setDisable(true);
                searchImageSelectedStaffBtn.setDisable(true);
                editPermissionBtn.setDisable(true);
                accountManage.getStaffList().sort(new SortByDataAndTime());
                staffObservableList = FXCollections.observableArrayList(accountManage.getStaffList());
                dateTimeCol.setCellValueFactory(new PropertyValueFactory<StaffAccount,String>("dateAndTimeStr"));
                usernameCol.setCellValueFactory(new PropertyValueFactory<StaffAccount,String>("username"));
                staffListTable.setItems(staffObservableList);
                staffListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        StaffAccount a = (StaffAccount) newValue;
                        ShowSelectedStaff(a);
                    }
                });

            }
        });

    }

    @FXML public void handleCreateBtn(ActionEvent event) throws IOException {
        if(nameTextField.getText().isEmpty()||usernameTextField.getText().isEmpty()||passwordTextField.getText().isEmpty()||confirmTextField.getText().isEmpty()||pictureTextField.getText().isEmpty()){
            errorCreateAccountLabel.setText("Fill out the empty fields.");
        }
        else {
            if(passwordTextField.getText().equals(confirmTextField.getText())){
                errorCreateAccountLabel.setText(accountManage.getCurrentAdmin().AddStaff(usernameTextField.getText(),passwordTextField.getText(),nameTextField.getText(),pictureTextField.getText(),accountManage));
                accountManage.UpdateStaff();
            }
            else{
            errorCreateAccountLabel.setText("Passwords do not match");}
        }
        pictureTextField.setText("");
        nameTextField.setText("");
        usernameTextField.setText("");
        passwordTextField.setText("");
        confirmTextField.setText("");

    }

    @FXML public void handleChangeBtn(ActionEvent event) throws IOException {
           if(newPassTextField.getText().equals(confirmNewPassTextField.getText())){
               accountManage.getCurrentAdmin().setPassword(newPassTextField.getText());
               myPasswordLabel.setText(accountManage.getCurrentAdmin().getPassword());
               accountManage.UpdateAdmin();
           }
           else {errorMyAccountLabel.setText("Passwords do not match");}
        newPassTextField.setText("");
        confirmNewPassTextField.setText("");


    }
    @FXML public void handleSearchBtn(ActionEvent event){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null){
            pictureTextField.setText(selectedFile.getAbsolutePath());
        }
    }
    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

  private void ShowSelectedStaff(StaffAccount staff){
        staffPicImageView.setImage(null);
        selectedStaff=staff;
        nameLabel.setText(staff.getName());
        permissionLabel.setText(staff.getPermission());
        attemptLabel.setText(staff.getAttempt());
        try{
            String imagePath=selectedStaff.getPicturePath();
            InputStream stream = new FileInputStream(imagePath);
            Image image = new Image(stream);
            staffPicImageView.setImage(image);
            imageErrorLabel.setText("");
        }
        catch(Exception FileNotFoundException){
            imageErrorLabel.setText("Image not found!!");
        }
      updateSelectedStaffBtn.setDisable(false);
      searchImageSelectedStaffBtn.setDisable(false);
      editPermissionBtn.setDisable(false);
    }
    @FXML public void handleSearchImageSelectedStaffBtn(ActionEvent event) throws FileNotFoundException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null){
            String imagePath=selectedFile.getAbsolutePath();
            selectedStaff.setPicturePath(imagePath);
            InputStream stream = new FileInputStream(imagePath);
            Image image = new Image(stream);
            staffPicImageView.setImage(image);
            imageErrorLabel.setText("");
        }
    }

    @FXML public void handleEditPermissionBtn(ActionEvent event){
        accountManage.getCurrentAdmin().EditStaffPermission(selectedStaff);
        permissionLabel.setText(selectedStaff.getPermission());
    }

    @FXML public void handleUpdateSelectedStaffBtn(ActionEvent event) throws IOException {
        ArrayList<StaffAccount> st = new ArrayList<StaffAccount>(staffListTable.getItems());
        accountManage.setStaffList(st);
        accountManage.UpdateStaff();
        staffPicImageView.setImage(null);
        nameLabel.setText("");
        permissionLabel.setText("");
        imageErrorLabel.setText("");
        updateSelectedStaffBtn.setDisable(true);
        searchImageSelectedStaffBtn.setDisable(true);
        editPermissionBtn.setDisable(true);
        staffListTable.refresh();
        staffListTable.getSelectionModel().clearSelection();
    }

    @FXML public void handleRefreshBtn(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_page.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        AdminPageController admin = loader.getController();
        admin.setAccountManage(accountManage);
        stage.show();
    }
    @FXML public void handleHomeImg(MouseEvent event) throws IOException {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.show();
    }
}
