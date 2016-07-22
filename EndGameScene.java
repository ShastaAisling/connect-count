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
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author shasta-ritchie
 * 
 * This scene is displayed after the user inputs a username. It shows the updated 
 * high scores and gives the user the option to quit or return to the main menu.
 * 
 */
public class EndGameScene {
    public Scene EndGameScene(int score, String name){
        
        //change title of screen
        c.GameStage.setTitle("Did you win?");
        
        //hold title and hiscores box
        VBox gp = new VBox();
        gp.setAlignment(Pos.CENTER);
        gp.setSpacing(10);
        gp.setPadding(new Insets(10, 10, 10, 20));
        
        //title of screen
        Text scenetitle = new Text("Highscores:");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gp.getChildren().add(scenetitle);
        
        //add hiscore box with new hiscore and name to vbox
        HighScoreScene hsc = new HighScoreScene();
        TextArea hiscores = hsc.ReturnHighscoresBox(score, name);
        hiscores.setMaxSize(200, 200);
        gp.getChildren().add(hiscores);
        
        //add vbox to split pane
        HBox np = new HBox();
        np.getChildren().add(0,gp);
        
        //add menu vbox with buttons
        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10, 20, 10, 10));
        menu.setSpacing(10);
        
        Button returnmain = new Button();
        returnmain.setText("Return to Main Menu");
        returnmain.setOnAction(e->gotoMain(e));
        menu.getChildren().add(returnmain);
        
        Button exitgo = new Button();
        exitgo.setText("Quit");
        exitgo.setOnAction(e->exitGo(e));
        menu.getChildren().add(exitgo);       
        np.getChildren().add(1,menu);
        
        //set layout properties of np
        np.autosize();
        np.setPadding(new Insets(10, 10, 10, 10));
        
        
        Scene end = new Scene(np);
        return end;
    }

    private void gotoMain(ActionEvent e) {
        //goes to main screen
        MainScene ms = new MainScene();
        Scene m = ms.MainScene();
        MainMenu.stage.setScene(m);
        MainMenu.stage.show();
        c.GameStage.close();
    }

    private void exitGo(ActionEvent e) {
        System.exit(0);
    }
}
