package condo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML private Button staffBtn,adminBtn,residentBtn;



   @FXML public void handleAdminBtn(ActionEvent event) throws IOException {
       Button b = (Button)event.getSource();
       Stage stage = (Stage) b.getScene().getWindow();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/check_username_password_page.fxml"));
       stage.setScene(new Scene(loader.load(),600,400));
       CheckUsernamePasswordPageController check = loader.getController();
       check.setStat("admin");
       stage.setResizable(false);
       stage.show();
   }
    @FXML public void handleStaffBtn(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/check_username_password_page.fxml"));
        stage.setScene(new Scene(loader.load(),600,400));
        CheckUsernamePasswordPageController check = loader.getController();
        check.setStat("staff");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void handleResidentBtn(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/check_username_password_page.fxml"));
        stage.setScene(new Scene(loader.load(),600,400));
        CheckUsernamePasswordPageController check = loader.getController();
        check.setStat("resident");
        stage.setResizable(false);
        stage.show();
    }


}




