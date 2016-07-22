package Default;

/*  ConnectCount
 *  2nd Semester, 2016
 *  ICS4U, BCI
 *  Prof. Garcia
 *  Shasta Ritchie and Nathaniel Reid-Smith
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author shasta-ritchie
 * 
 * This class initiates the stage for the main menu, current high-score list, and user help.
 * During boot-up, the stage set is MainScene (the main menu).
 *  
 */
public class MainMenu extends Application {
    
    StackPane mainPane, hiscoresPane, helpPane;
    public static Stage stage;
    Scene mainScene, hiscoresScene, helpScene;
    static Text time;
    static Game c;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        mainSceneSetup();
        
        
        //setup default scene       
        stage.setTitle("ConnectCount");
        stage.show();
    }
    
  
    
    private void mainSceneSetup(){
        
        MainScene ms = new MainScene();
        Scene main = ms.MainScene();
        stage.setScene(main);
    }  
}
