package condoapp.controllers;

import com.opencsv.exceptions.CsvValidationException;

import condoapp.models.AccountManagement;
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
    private AccountManagement accountManagement;
    @FXML private Button okBtn;
    @FXML private ImageView homeImg;
    @FXML private PasswordField passwordField;
    @FXML private TextField usernameText;
    @FXML private Label errorLabel;



    @FXML
    public  void initialize() throws IOException, CsvValidationException {
        accountManagement = new AccountManagement();
        accountManagement.addStaffList();
        accountManagement.addAdminList();
    }
    @FXML public void handleOkBtn(ActionEvent event) throws IOException {
        if(stat.equals("admin")){
            String str =accountManagement.checkAdminAccount(usernameText.getText(),passwordField.getText());
            errorLabel.setText(str);
            if(str.equals("")){
                Button b = (Button)event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_page.fxml"));
                stage.setScene(new Scene(loader.load(),800,600));
                AdminPageController admin = loader.getController();
                admin.setAccountManage(accountManagement);
                stage.show();
            }
        }
        else {
            String str =accountManagement.checkStaffAccount(usernameText.getText(),passwordField.getText());
            errorLabel.setText(str);
            if(str.equals("")){
                Button b = (Button)event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/staff_page.fxml"));
                stage.setScene(new Scene(loader.load(),800,600));
                StaffPageController staff = loader.getController();
                staff.setAccount(accountManagement);
                accountManagement.updateStaffCsv();
                stage.show();
            }
            else accountManagement.updateStaffCsv();
    }
    }

    @FXML public void handleHomeImg(MouseEvent event) throws IOException {
        ImageView b=(ImageView) event.getSource();
        Stage stage =(Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));
        stage.show();
    }


    public void setStat(String stat) {
        this.stat = stat;
    }
}
