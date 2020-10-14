package condo.controllers;



import condo.Main;
import condo.models.SortByDateAndTime;
import condo.service.ReadWriteAccountCsv;
import condo.models.AccountManagement;
import condo.models.StaffAccount;
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


import java.io.*;
import java.nio.file.Path;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class AdminPageController {
    private AccountManagement accountManage;
    private StaffAccount selectedStaff;
    private ReadWriteAccountCsv readWriteAccountCsv;

    @FXML private Label welcomeLabel,welcomeLabel1,welcomeLabel11,nameLabel,permissionLabel,attemptLabel,imageErrorLabel,errorCreateAccountLabel,errorMyAccountLabel,myPasswordLabel,myUsernameLabel;
    @FXML private PasswordField confirmNewPassTextField,confirmTextField,newPassTextField,passwordTextField;
    @FXML private ImageView staffPicImageView;
    @FXML private TableView staffListTable;
    @FXML private TableColumn usernameCol,dateTimeCol;
    @FXML private Button createBtn,changeBtn,searchImgBtn,searchImageSelectedStaffBtn,editPermissionBtn,updateSelectedStaffBtn;
    @FXML private TextField nameTextField,usernameTextField,pictureTextField;





    @FXML public  void initialize()  {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                welcomeLabel.setText(accountManage.getCurrentAdmin().getUsername());
                welcomeLabel1.setText(accountManage.getCurrentAdmin().getUsername());
                welcomeLabel11.setText(accountManage.getCurrentAdmin().getUsername());
                myPasswordLabel.setText(accountManage.getCurrentAdmin().getPassword());
                myUsernameLabel.setText(accountManage.getCurrentAdmin().getUsername());
                createStaffListTable();
            }
        });
        updateSelectedStaffBtn.setDisable(true);
        searchImageSelectedStaffBtn.setDisable(true);
        editPermissionBtn.setDisable(true);

    }
    public void createStaffListTable(){
        accountManage.getStaffList().sort(new SortByDateAndTime());
        ObservableList<StaffAccount> staffObservableList = FXCollections.observableArrayList(accountManage.getStaffList());
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<StaffAccount,String>("dateAndTimeStr"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<StaffAccount,String>("username"));
        staffListTable.setItems(staffObservableList);
        staffListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                StaffAccount a = (StaffAccount) newValue;
                showSelectedStaff(a);
            }
        });

    }
    @FXML public void handleCreateBtn(ActionEvent event)  {
        errorCreateAccountLabel.setTextFill(Color.web("#bf2d2d"));
        if(nameTextField.getText().isEmpty()||usernameTextField.getText().isEmpty()||passwordTextField.getText().isEmpty()||confirmTextField.getText().isEmpty()||pictureTextField.getText().isEmpty()){
            errorCreateAccountLabel.setText("Fill out the empty fields.");
        }
        else {
            if(passwordTextField.getText().equals(confirmTextField.getText())){
                try {
                    accountManage.getCurrentAdmin().addStaff(usernameTextField.getText(), passwordTextField.getText(), nameTextField.getText(), pictureTextField.getText(), accountManage.getStaffList());
                    readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
                    createStaffListTable();
                    errorCreateAccountLabel.setText("Success !!");
                    errorCreateAccountLabel.setTextFill(Color.web("#44c55a"));
                    pictureTextField.setText("");
                    nameTextField.setText("");
                    usernameTextField.setText("");
                    passwordTextField.setText("");
                    confirmTextField.setText("");
                }
                catch (IllegalArgumentException | IOException e){
                    usernameTextField.setText("");
                    errorCreateAccountLabel.setText(e.getMessage());
                }
            }
            else{
            errorCreateAccountLabel.setText("Passwords do not match");}
        }



    }

    @FXML public void handleChangeBtn(ActionEvent event) throws IOException {
        errorMyAccountLabel.setTextFill(Color.web("#bf2d2d"));
        if(newPassTextField.getText().isEmpty()||confirmNewPassTextField.getText().isEmpty()) errorMyAccountLabel.setText("Fill out the empty field.");
        else  if(newPassTextField.getText().equals(confirmNewPassTextField.getText())){
               accountManage.getCurrentAdmin().setPassword(newPassTextField.getText());
               myPasswordLabel.setText(accountManage.getCurrentAdmin().getPassword());
               readWriteAccountCsv.updateAdminCsv(accountManage.getAdminList());
               errorMyAccountLabel.setText("Success !!");
               errorMyAccountLabel.setTextFill(Color.web("#44c55a"));
           }
        else errorMyAccountLabel.setText("Passwords do not match");
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
            Path target = null;
            File destDir = null;
            try {

               destDir = new File("images");
                destDir.mkdirs();

                target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+selectedFile.getName());

                Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                pictureTextField.setText(destDir.getName()+File.separator+selectedFile.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

  private void showSelectedStaff(StaffAccount staff){
        staffPicImageView.setImage(null);
        imageErrorLabel.setText("");
        selectedStaff=staff;
        nameLabel.setText(staff.getName());
        permissionLabel.setText(staff.getPermission());
        if(staff.getPermission().equalsIgnoreCase("Allowed")) permissionLabel.setTextFill(Color.web("#44c55a"));
        else permissionLabel.setTextFill(Color.web("#bf2d2d"));
        attemptLabel.setText(staff.getAttempt());
      File jarDir = null;
      File codeDir = null;
      try {
          jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
          codeDir = jarDir.getParentFile();
          String path = codeDir.toString() + File.separator + selectedStaff.getPicturePath();
          File uploadFile = new File(path);
          staffPicImageView.setImage(new Image(uploadFile.toURI().toString()));
          if(!uploadFile.exists()){
              imageErrorLabel.setText("Image not found!!");
          }
      }
      catch (URISyntaxException e) {
          e.printStackTrace();
      }
      updateSelectedStaffBtn.setDisable(false);
      searchImageSelectedStaffBtn.setDisable(false);
      editPermissionBtn.setDisable(false);
    }
    @FXML public void handleSearchImageSelectedStaffBtn(ActionEvent event) {
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
                selectedStaff.setPicturePath(destDir.getName()+File.separator+selectedFile.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }

            File jarDir = null;
            File codeDir = null;
            try {
                jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                codeDir = jarDir.getParentFile();
                String path = codeDir.toString() + File.separator + selectedStaff.getPicturePath();
                File uploadFile = new File(path);
                staffPicImageView.setImage(new Image(uploadFile.toURI().toString()));}
            catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }






    }

    @FXML public void handleEditPermissionBtn(ActionEvent event){
        accountManage.getCurrentAdmin().editStaffPermission(selectedStaff);
        permissionLabel.setText(selectedStaff.getPermission());
    }

    @FXML public void handleUpdateSelectedStaffBtn(ActionEvent event) throws IOException {
        readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
        staffPicImageView.setImage(null);
        nameLabel.setText("");
        permissionLabel.setText("");
        imageErrorLabel.setText("");
        attemptLabel.setText("");
        updateSelectedStaffBtn.setDisable(true);
        searchImageSelectedStaffBtn.setDisable(true);
        editPermissionBtn.setDisable(true);
        staffListTable.refresh();
        staffListTable.getSelectionModel().clearSelection();
    }


    @FXML public void handleHomeImg(MouseEvent event) throws IOException {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.setResizable(false);
        stage.show();
    }

    public void setReadWriteAccountCsv(ReadWriteAccountCsv readWriteAccountCsv) {
        this.readWriteAccountCsv = readWriteAccountCsv;
    }
}
