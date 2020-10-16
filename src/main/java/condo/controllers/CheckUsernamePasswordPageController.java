package condo.controllers;



import condo.services.ReadWriteAccountCsv;
import condo.models.AccountManagement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckUsernamePasswordPageController {
    private String stat;
    private AccountManagement accountManage;
    private ReadWriteAccountCsv readWriteAccountCsv;
    @FXML private Button okBtn,registerBtn;
    @FXML private ImageView homeImg;
    @FXML private PasswordField passwordField;
    @FXML private TextField usernameText;
    @FXML private Label errorLabel;



    @FXML public  void initialize()  {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(stat.equals("resident")) registerBtn.setDisable(false);
            }
        });
        readWriteAccountCsv = new ReadWriteAccountCsv("csv","admin.csv","staff.csv","resident.csv");
        accountManage = new AccountManagement();
        readWriteAccountCsv.addStaffList(accountManage.getStaffList());
        readWriteAccountCsv.addAdminList(accountManage.getAdminList());
        readWriteAccountCsv.addResidentList(accountManage.getResidentList());
        registerBtn.setDisable(true);
    }
    @FXML public void handleOkBtn(ActionEvent event) {
        if(stat.equals("admin")){
            try {
                accountManage.checkAdminAccount(usernameText.getText(), passwordField.getText());
                Button b = (Button)event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_page.fxml"));
                stage.setScene(new Scene(loader.load(),800,600));
                AdminPageController admin = loader.getController();
                admin.setAccountManage(accountManage);
                admin.setReadWriteAccountCsv(readWriteAccountCsv);
                stage.setResizable(false);
                stage.show();
            }
           catch ( NullPointerException |IllegalArgumentException e){
                errorLabel.setText(e.getMessage());
            }
            catch (IOException e) {e.printStackTrace();}
        }
        
        else if(stat.equals("staff")) {

            try {
                accountManage.checkStaffAccount(usernameText.getText(), passwordField.getText());
                Button b = (Button)event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staff_page.fxml"));
                stage.setScene(new Scene(loader.load(),800,600));
                StaffPageController staff = loader.getController();
                staff.setAccountManage(accountManage);
                staff.setReadWriteAccountCsv(readWriteAccountCsv);
                readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
                stage.setResizable(false);
                stage.show();
            }
            catch (SecurityException | IllegalArgumentException | NullPointerException e) {
                errorLabel.setText(e.getMessage());
            }
            catch (IOException e) {e.printStackTrace();}
            finally {
                readWriteAccountCsv.updateStaffCsv(accountManage.getStaffList());
            }
        }
        else{
            try {
                accountManage.checkResidentAccount(usernameText.getText(), passwordField.getText());
                Button b = (Button)event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/resident_page.fxml"));
                stage.setScene(new Scene(loader.load(),800,600));
                ResidentPageController resident = loader.getController();
                resident.setAccountManage(accountManage);
                resident.setReadWriteAccountCsv(readWriteAccountCsv);
                stage.setResizable(false);
                stage.show();
            }
            catch (NullPointerException | IllegalArgumentException e){
                errorLabel.setText(e.getMessage());
            }
            catch (IOException e) {e.printStackTrace();}
        }

    }
    @FXML public void handleRegisterBtn(ActionEvent event) {
        try{
        Button b = (Button)event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register_page.fxml"));
        stage.setScene(new Scene(loader.load(),600,400));
        RegisterPageController register = loader.getController();
        register.setAccountManage(accountManage);
        register.setReadWriteAccountCsv(readWriteAccountCsv);
        stage.setResizable(false);
        stage.show();
        }
        catch (IOException e) {e.printStackTrace();}
    }

    @FXML public void handleHomeImg(MouseEvent event)  {
        try{
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.setResizable(false);
        stage.show();}
        catch (IOException e) {e.printStackTrace();}
    }


    public void setStat(String stat) {
        this.stat = stat;
    }
}
