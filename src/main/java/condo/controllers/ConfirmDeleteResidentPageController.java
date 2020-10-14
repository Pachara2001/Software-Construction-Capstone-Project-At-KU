package condo.controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmDeleteResidentPageController {
   private int status =0 ;
   private String name;
   @FXML private Label nameTextField;
   @FXML private Button noBtn,yesBtn;

   @FXML public  void initialize() {
      Platform.runLater(new Runnable() {
         public void run() {
            nameTextField.setText(name+" ?");

         }
   });
}
   @FXML public void handleYesBtn(){
      status=1;
      Stage stage = (Stage) yesBtn.getScene().getWindow();
      stage.close();

   }
   @FXML public void handleNoBtn(){
      Stage stage = (Stage) noBtn.getScene().getWindow();
      stage.close();
   }
   public void setName(String name) {
      this.name = name;
   }

   public int getStatus() {
      return status;
   }
}
