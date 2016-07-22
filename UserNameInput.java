/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Default;

import static Default.MainMenu.c;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author shasta-ritchie
 * 
 * Allows user to input a name for the high score list.
 */

public class UserNameInput {
    RestrictiveTextField rtf;
    String username;
    int userscore;
    public Scene UserNameInput(int score){
        
        //change title of screen
        c.GameStage.setTitle("Input username...");
        
        userscore = score;
        //label 
        Label putuser = new Label();
        putuser.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        putuser.setText("Enter a username:");
        
        //text box that restricts user input
        rtf = new RestrictiveTextField();
        rtf.setMaxLength(12);
        
        //button to continue to end screen
        Button gogame = new Button();
        gogame.setText("Continue");
        gogame.setOnAction(e->gotoend(e));
        
        //vbox to hold elements
        VBox layout = new VBox();
        layout.getChildren().add(putuser);
        layout.getChildren().add(rtf);
        layout.getChildren().add(gogame);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50, 50, 50, 50));
        layout.setSpacing(10);
        
        //scene to hold vbox and to be returned
        Scene p = new Scene(layout);
        
        return p;
        
    }
    
    private void gotoend(ActionEvent e){
        username = rtf.getText();
        EndGameScene egs = new EndGameScene();
        Scene s = egs.EndGameScene(userscore, username);
        c.GameStage.setScene(s);
    }
}
