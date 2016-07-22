/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Default;

import com.dropbox.core.DbxException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shasta-ritchie
 * 
 * This class returns the scene to view current high-scores and also can return
 *  an updated list in a text area that takes a new high-score and name. 
 */
public class HighScoreScene {
    TextArea ta;
    public Scene HighScoreScene(int score, String name){
         //create a grid to hold the nodes
        VBox gp = new VBox();
        gp.setAlignment(Pos.CENTER);
        gp.setSpacing(10);
        gp.setPadding(new Insets(10, 50, 10, 50));
        
        
        //title of screen
        Text scenetitle = new Text("Highscores:");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gp.getChildren().add(scenetitle);
        
        
        //get textarea with highscores
        TextArea newta = this.ReturnHighscoresBox(score, name);
        gp.getChildren().add(newta);
        
        //Cancel btn
        Button btn = new Button();
        btn.setText("Go Back");
        btn.setOnAction(e->mainGo(e));
        gp.getChildren().add(btn);
        
        //add Vbox to mainPane and add those to mainScene
        StackPane hiscoresPane;
        hiscoresPane = new StackPane();
        hiscoresPane.getChildren().add(gp);
        Scene hiscoresScene;
        hiscoresScene = new Scene(hiscoresPane,400,300);
        return hiscoresScene;
    }

    private void mainGo(ActionEvent e) {
        //instantiate new mainscene and set the stage
        MainScene ms = new MainScene();
        Scene mainScene = ms.MainScene();
        MainMenu.stage.setScene(mainScene);
    }
    
    public TextArea ReturnHighscoresBox(int newscore, String newname){
        //textarea that holds highscore info
        ta = new TextArea();
        
        //update and display highscores
        HighScoreSort topTen=new HighScoreSort();
        try {
            topTen.readHiscores();
            if ("".equals(newname)){
                topTen.updateHighscores(newscore,"User");
            } else{
                topTen.updateHighscores(newscore,newname);
            }
            topTen.writeHighScores();
            topTen.showHiscores(ta,true);
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DbxException e) {
            e.printStackTrace();
        }

        //disable edits and add textarea to Vbox
        ta.setEditable(false);
        return ta;
    }
}
