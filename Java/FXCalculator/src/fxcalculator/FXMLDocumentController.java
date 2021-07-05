/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxcalculator;


import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

/**
 *
 * @author Admin
 */
public class FXMLDocumentController{
    
  @FXML 
  private Label result;
  private long number1=0;
  private String operator = "";
  private boolean start= true;
  private Modal modal= new Modal();
    
    
    public void processNumbers(ActionEvent event) {
        if(start){
            result.setText("");
            start=false;
        }
        // when button is pressed get information written on button
        String value=((Button)event.getSource()).getText(); 
        result.setText(result.getText()+value);
    }
    public void processOparators(ActionEvent event){
        String value=((Button)event.getSource()).getText(); 
        if(!value.equals("=")){
            if(!operator.isEmpty())
                return;
            operator=value;
            number1 = Long.parseLong(result.getText());
            result.setText("");
            
        }else{
            if(operator.isEmpty())
                return;
            long number2 = Long.parseLong(result.getText());
            float output= modal.calculate(number1, number2, operator);
            result.setText(String.valueOf(output));
            operator="";
            start=true;
        }
    }
    

    
}
