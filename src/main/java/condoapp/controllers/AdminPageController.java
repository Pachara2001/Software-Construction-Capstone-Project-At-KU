package condoapp.controllers;

import com.opencsv.exceptions.CsvValidationException;
import condoapp.service.ReadWriteAccountCsv;
import condoapp.models.AccountManagement;
import condoapp.models.StaffAccount;
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
    private ObservableList<StaffAccount> staffObservableList;
    private ReadWriteAccountCsv readWriteAccountCsv;
    @FXML private Label welcomeLabel,welcomeLabel1,welcomeLabel11,nameLabel,permissionLabel,attemptLabel,imageErrorLabel,errorCreateAccountLabel,errorMyAccountLabel,myPasswordLabel,myUsernameLabel;
    @FXML private PasswordField confirmNewPassTextField,confirmTextField,newPassTextField,passwordTextField;
    @FXML private ImageView staffPicImageView;
    @FXML private TableView staffListTable;
    @FXML private TableColumn usernameCol,dateTimeCol;
    @FXML private Button createBtn,changeBtn,searchImgBtn,searchImageSelectedStaffBtn,editPermissionBtn,updateSelectedStaffBtn;
    @FXML private TextField nameTextField,usernameTextField,pictureTextField;





    public  void initialize()  {

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
                createStaffListTable();

            }
        });

    }
    public void createStaffListTable(){
        staffObservableList = FXCollections.observableArrayList(accountManage.getStaffList());
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<StaffAccount,String>("dateAndTimeStr"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<StaffAccount,String>("username"));
        staffListTable.setItems(staffObservableList);
        staffListTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                StaffAccount a = (StaffAccount) newValue;
                showSelectedStaff(a);
            }
        });

    }    @FXML public void handleCreateBtn(ActionEvent event) throws IOException {
        if(nameTextField.getText().isEmpty()||usernameTextField.getText().isEmpty()||passwordTextField.getText().isEmpty()||confirmTextField.getText().isEmpty()||pictureTextField.getText().isEmpty()){
            errorCreateAccountLabel.setText("Fill out the empty fields.");
        }
        else {
            if(passwordTextField.getText().equals(confirmTextField.getText())){
                errorCreateAccountLabel.setTextFill(Color.web("#bf2d2d"));
                errorCreateAccountLabel.setText(accountManage.getCurrentAdmin().addStaff(usernameTextField.getText(),passwordTextField.getText(),nameTextField.getText(),pictureTextField.getText(),accountManage.getStaffList()));
                readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
                createStaffListTable();
                pictureTextField.setText("");
                nameTextField.setText("");
                usernameTextField.setText("");
                passwordTextField.setText("");
                confirmTextField.setText("");

                if(errorCreateAccountLabel.getText().equals("")) {
                    errorCreateAccountLabel.setText("Success !!");
                    errorCreateAccountLabel.setTextFill(Color.web("#44c55a"));

                }
            }
            else{
            errorCreateAccountLabel.setText("Passwords do not match");}
        }



    }

    @FXML public void handleChangeBtn(ActionEvent event)  {
        errorMyAccountLabel.setTextFill(Color.web("#bf2d2d"));
           if(newPassTextField.getText().equals(confirmNewPassTextField.getText())){
               accountManage.getCurrentAdmin().setPassword(newPassTextField.getText());
               myPasswordLabel.setText(accountManage.getCurrentAdmin().getPassword());
               readWriteAccountCsv.updateAdminCsv(accountManage.getAdminList());
               errorMyAccountLabel.setText("Success !!");
               errorMyAccountLabel.setTextFill(Color.web("#44c55a"));
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
            Path target = null;
            File destDir = null;
            try {

               destDir = new File("images");
                destDir.mkdirs();

                target = FileSystems.getDefault().getPath(destDir.getAbsolutePath()+System.getProperty("file.separator")+selectedFile.getName());

                Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING );

            } catch (IOException e) {
                e.printStackTrace();
            }
            pictureTextField.setText(destDir.getName()+File.separator+selectedFile.getName());


        }
    }
    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }

  private void showSelectedStaff(StaffAccount staff){
        staffPicImageView.setImage(null);
        selectedStaff=staff;
        nameLabel.setText(staff.getName());
        permissionLabel.setText(staff.getPermission());
        attemptLabel.setText(staff.getAttempt());
      File jarDir = null;
      File codeDir = null;
      try {
          jarDir = new File(Main.class.getProtectionDomain()
                  .getCodeSource().getLocation()
                  .toURI().getPath());
          codeDir = jarDir.getParentFile();
          String path = codeDir.toString() + File.separator + selectedStaff.getPicturePath();
          File uploadFile = new File(path);
          staffPicImageView.setImage(new Image(uploadFile.toURI().toString()));}
        catch (URISyntaxException e) {
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
        accountManage.getCurrentAdmin().editStaffPermission(selectedStaff);
        permissionLabel.setText(selectedStaff.getPermission());
    }

    @FXML public void handleUpdateSelectedStaffBtn(ActionEvent event)  {

        readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
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


    @FXML public void handleHomeImg(MouseEvent event) throws IOException {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.show();
    }

    public void setReadWriteAccountCsv(ReadWriteAccountCsv readWriteAccountCsv) {
        this.readWriteAccountCsv = readWriteAccountCsv;
    }
}
