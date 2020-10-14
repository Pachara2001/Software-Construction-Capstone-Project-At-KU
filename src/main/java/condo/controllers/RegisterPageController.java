package condo.controllers;

import com.opencsv.exceptions.CsvValidationException;
import condo.models.AccountManagement;
import condo.models.ItemManagement;
import condo.models.RoomManagement;
import condo.service.ReadWriteAccountCsv;
import condo.service.ReadWriteItemCsv;
import condo.service.ReadWriteRoomCsv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPageController {
    private AccountManagement accountManage;
    private RoomManagement roomManage;
    private ReadWriteAccountCsv readWriteAccountCsv;
    private ReadWriteRoomCsv readWriteRoomCsv;
@FXML private TextField roomNoTextField,nameTextField,passwordTextField,confirmTextField;
@FXML private Label errorLabel;


@FXML public void initialize() throws IOException, CsvValidationException {
    roomManage = new RoomManagement();
    readWriteRoomCsv = new ReadWriteRoomCsv("csv","room.csv");
    readWriteRoomCsv.addRoomList(roomManage.getRoomList());
}
@FXML public void handleCreateBtn(ActionEvent event){
    errorLabel.setTextFill(Color.web("#bf2d2d"));
    if(passwordTextField.getText().equals(confirmTextField.getText())){
        try{
            accountManage.createResident(roomNoTextField.getText(),nameTextField.getText(),passwordTextField.getText(),roomManage.getRoomList());
            readWriteAccountCsv.updateResidentCsv(accountManage.getResidentList());
            errorLabel.setText("Your username is "+nameTextField.getText()+roomNoTextField.getText().toUpperCase());
            errorLabel.setTextFill(Color.web("#44c55a"));
        }
        catch (IllegalArgumentException | IOException e){
            errorLabel.setText(e.getMessage());
        }
        roomNoTextField.setText("");
        nameTextField.setText("");
        passwordTextField.setText("");
        confirmTextField.setText("");
    }
    else errorLabel.setText("Passwords do not match.");
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

    public void setAccountManage(AccountManagement accountManage) {
        this.accountManage = accountManage;
    }
}