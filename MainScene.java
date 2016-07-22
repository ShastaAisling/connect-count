/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Default;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static Default.MainMenu.c;

/**
 *
 * @author shasta-ritchie
 * 
 * This is the main menu scene. It contains buttons linking to begin the game, see the
 * current high-score list, user help, and to exit the program.
 */
public class MainScene {
    public Scene MainScene(){
        //set up VBox to contain buttons
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10, 50, 50, 50));
        vb.setSpacing(10);
        
        //text welcome
        Text scenetitle = new Text("Welcome to ConnectCount");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        vb.getChildren().add(scenetitle);
        
        //button that starts the game
        Button go = new Button();
        go.setText("Play the Game");
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                c = new Game();
                c.start(stage);
                MainMenu.stage.close();
            }
        });
        
        vb.getChildren().add(go);
        
        //highscores view button
        Button hs = new Button();
        hs.setText("See Highscores");
        hs.setOnAction(e->highScoreGo(e));
        
        vb.getChildren().add(hs);
        
        //help button
        Button he = new Button();
        he.setText("Help");
        he.setOnAction(e->helpGo(e));
        
        vb.getChildren().add(he);        
        
        //exit button
        Button ex = new Button();
        ex.setText("Exit");
        ex.setOnAction(e->exitGo(e));
        
        vb.getChildren().add(ex);
        
        //add Vbox to mainPane and add those to mainScene
        StackPane mainPane;
        mainPane = new StackPane();
        mainPane.getChildren().add(vb);
        Scene mainScene;
        mainScene = new Scene(mainPane,400,300);
        return mainScene;
    }
    
    private void highScoreGo(ActionEvent e){
        //instantiate new highscore scene and set active


        HighScoreScene viewhs = new HighScoreScene();
        Scene hsc = viewhs.HighScoreScene(0, "iluminati");
        MainMenu.stage.setScene(hsc);
    }
    
    private void helpGo(ActionEvent e){
        //instantiate new help scene and set active
        try {
            Desktop.getDesktop().browse(new URI("http://xprs.imcreator.com/free/leinahtan/connectcount"));
            } catch (IOException | URISyntaxException ignored){
            
        }
    }
    
    private void exitGo(ActionEvent e){
        System.exit(0);
    }
}
