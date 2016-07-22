//to do: fade messes top row
//       selected blue outline
//       help
package Default;

import static Default.MainMenu.c;
import javafx.animation.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.BitSet;
import javafx.event.ActionEvent;

import static javafx.geometry.Orientation.HORIZONTAL;
import javafx.scene.layout.HBox;

public class Game extends Application {

    //VARIABLE DECLARATIONS
    private final int limit = 81;
    private final Button[] TheButtonArray = new Button[limit];
    private int score = 0; // users total score
    private int TotalSum = 0; // sum of selected tiles
    //   public int[] alreadySelected = new int [limit]; // place to store already selected square #s
    private final BitSet alreadySelected2 = new BitSet();// place to store already selected square #s, starts false when clicked goes to true
    private int winNum;//Number to attain to win level
    private final int[] temp = new int[limit];
    //private static int min = 1;//min # generated
    private static final int max = 10; //the max #generated
    private final int root = 9; //square root of limit ; dimensions of grid
    private Text userScore;
    private Text sumNumLabel;
    private Text winNumLabel;
    private Text time;
    private Text timeLeftLabel;
    Stage GameStage;
    private Timeline timeline;
    private int timeCount = 60;
    private int turnTime = 15;

    private void endGame() {

        //animations???
        UserNameInput uni = new UserNameInput();
        Scene unInputGo = uni.UserNameInput(score);
        GameStage.setScene(unInputGo);
    }

    @Override
    public void start(Stage primaryStage) {
        GameStage = primaryStage;
        initButtonsArray();
        // add something else to add above grid
        for (int i = 0; limit <= i; i++) {
            alreadySelected2.set(i, false);
        }
        //group and grid to hold button array
        Group rt = new Group();
        rt.getChildren().add(getGrid());

        //Vbox to hold grid and other thing
        VBox gb = new VBox();
        gb.setAlignment(Pos.CENTER);
        gb.setSpacing(10);

        //hbox to hold button options
        HBox buttonmenu = new HBox();
        buttonmenu.setAlignment(Pos.CENTER);
        buttonmenu.setSpacing(10);

        //return to main menu button
        Button getoutahere = new Button();
        getoutahere.setText("Return to Main Menu");
        getoutahere.setOnAction(this::gotoMain);
        buttonmenu.getChildren().add(getoutahere);

        //quit button
        Button exitgo = new Button();
        exitgo.setText("Quit");
        exitgo.setOnAction(this::exitGo);
        buttonmenu.getChildren().add(exitgo);

        //add something else to gb
        gb.getChildren().add(rt);
        gb.getChildren().add(buttonmenu);

        //Vbox to hold text for score, target num, and current sum
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(10);

        //splitpane to contain the grid and vbox
        SplitPane mainlayout = new SplitPane();
        mainlayout.setOrientation(HORIZONTAL);
        mainlayout.getItems().add(gb);

        // initialize text
        Text userScoreTitle = new Text();
        userScoreTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        userScoreTitle.setText("Current Score:");
        vb.getChildren().add(userScoreTitle);

        userScore = new Text();
        userScore.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        userScore.setText("0");
        vb.getChildren().add(userScore);

        Text timeTitle = new Text();
        timeTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        timeTitle.setText("Time Left:");
        vb.getChildren().add(timeTitle);

        time = new Text();
        time.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        time.setText("0");
        vb.getChildren().add(time);

        Text winNumTitle = new Text();
        winNumTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        winNumTitle.setText("Target Sum:");
        vb.getChildren().add(winNumTitle);

        winNumLabel = new Text();
        winNumLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        winNumLabel.setText("0");
        vb.getChildren().add(winNumLabel);

        Text sumNumTitle = new Text();
        sumNumTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        sumNumTitle.setText("Current Sum:");
        vb.getChildren().add(sumNumTitle);

        sumNumLabel = new Text();
        sumNumLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        sumNumLabel.setText("0");
        vb.getChildren().add(sumNumLabel);

        Text timeLeftTitle = new Text();
        timeLeftTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        timeLeftTitle.setText("Turn Timer");
        vb.getChildren().add(timeLeftTitle);

        timeLeftLabel = new Text();
        timeLeftLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        timeLeftLabel.setText("60");
        vb.getChildren().add(timeLeftLabel);

        //set initial winNum
        winNum = (int) (Math.random() * 20 + max + 1); //number to add to calculation
        winNumLabel.setText(Integer.toString(winNum)); //set goal label
        //add vbox to mainlayout split pane
        mainlayout.getItems().add(vb);
        //add mainlayout to scene
        Scene scene = new Scene(mainlayout, 700, 600);
        GameStage.setTitle("Play!");
        GameStage.setScene(scene);
        GameStage.show();

        //create timer that counts down to zero for full game
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), ae -> addtoTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // create turn timer
        Timeline turn = new Timeline();
        turn.getKeyFrames().add(new KeyFrame(Duration.millis(1000), ae -> resetTurnTime()));
        turn.setCycleCount(Animation.INDEFINITE);
        turn.play();

    }

    // makes board reset when turntimer is 0
    private void resetTurnTime() {
        timeLeftLabel.setText(Integer.toString(turnTime));
        //System.out.println(timeCount);  -> debug
        if (turnTime == 0) {
            Reset();
        }
        turnTime--;
    }

    private void exitGo(ActionEvent e) {
        System.exit(0);
    }

    //returns user to main menu
    private void gotoMain(ActionEvent e) {
        //goes to main screen
        MainScene ms = new MainScene();
        Scene m = ms.MainScene();
        MainMenu.stage.setScene(m);
        MainMenu.stage.show();
        c.GameStage.close();
    }

    // adds remaining turnTime to timer
    private void addtoTurnTime() {
        timeCount += turnTime;
    }

    // changes screen to make the game end when time is up
    private void addtoTime() {
        time.setText(Integer.toString(timeCount));
        //System.out.println(timeCount);  -> debug
        if (timeCount == 0) {
            timeline.stop();
            timeCount = 60;
            endGame();
        }
        timeCount--;
    }

    private void shiftShift(int k) {
        int tempY = (int) TheButtonArray[k].getLayoutY(); //stores original y coordinate of a button
            if (k < 72){
                    Path path = new Path();
                    path.getElements().add(new MoveTo(25, 25)); // move to 25 because it is centre of button
                    path.getElements().add(new javafx.scene.shape.VLineTo(75)); //down 75, 25-75 = -50 so goes 50 down
                    PathTransition pathTransition = new PathTransition();
                int speed = 500;
                pathTransition.setDuration(Duration.millis(speed)); //speed of down shift
                    pathTransition.setPath(path);   //selects the path that this animation follows
                    pathTransition.setNode(TheButtonArray[k]); //picks button to move
                    pathTransition.play();
                    pathTransition.setOnFinished(event -> {
                        //code
                        TheButtonArray[k].setTranslateY(TheButtonArray[k].getLayoutY() - tempY); //moves buttons back to where it was
                        if (k > 9) {
                            TheButtonArray[k].setText(Integer.toString(temp[k - root]));      //handles shifting text down
                        } else {
                            SingleReset(k);//
                            
                           //makeItFade(k);      //fades buttons after on the top row of moved columns
                        }
                    });
            }
            else{
                TheButtonArray[k].setVisible(false);
            } 
                }

//    private void makeItFade(int k) { //this fades a button with index k
//        SingleReset(k);
////        TheButtonArray[k].setText(Integer.toString((int) (Math.random() * max + 1)));
////        TheButtonArray[k].setDisable(true);
////        FadeTransition ft = new FadeTransition(Duration.millis(speed * 2), TheButtonArray[k]);
////        ft.setFromValue(0.3);
////        ft.setToValue(1.0);
////        ft.play();
////        ft.setOnFinished(event -> SingleReset(k));
//    }

    private Pane getGrid() {

        int i = 0;
        GridPane gridPane = new GridPane();
        for (Button b : TheButtonArray) {
            int k = i;
            //int root;
            b.setPrefSize(50, 50);
            b.setStyle("-fx-focus-color: rgba(255, 255, 255, 0);");
            b.setOnAction(event -> {
                b.setDisable(true);
                //set other buttons, besides those adjacent, disabled
                alreadySelected2.set(k, true); //sets bitset to true for that square
                for (int i1 = 0; i1 < limit; i1++) {
                    if (i1 != k - 1 && i1 != k + 1 && i1 != k - 9 && i1 != k + 9) {
                        TheButtonArray[i1].setDisable(true);
                    } else if (!alreadySelected2.get(i1)) {

                        TheButtonArray[i1].setDisable(false);
                        //                                MakeShastaMad(k-1);
                        //                                MakeShastaMad(k+1);
                        //                                MakeShastaMad(k+root);
                        //                                MakeShastaMad(k-root);
                    }
                }
                //disable adjacent squares at the 1st and last columns
                if ((k - 1) % 9 == 8) {
                    TheButtonArray[k - 1].setDisable(true);
                } else if ((k + 1) % 9 == 0 && k != 80) {
                    TheButtonArray[k + 1].setDisable(true);
                }
                //disable squares already selected
                if (alreadySelected2.get(k)) {
                    b.setDisable(true);
                }
                buttonSelect(b);
                winCheck();
                // last=k;+++++++++++++++++++++++
            });
            b.setText(Integer.toString((int) (Math.random() * 9 + 1)));
            // do something with your button
            // maybe add an EventListener or something

            gridPane.add(b, ((i + 9) % 9), i / 9);
            final InnerShadow is = new InnerShadow();
            is.setOffsetX(0.0);
            is.setOffsetY(2.0);
            is.setColor(Color.LIGHTGRAY);
            new Timeline(
                    new KeyFrame(Duration.seconds(0.1), new KeyValue(b.effectProperty(), is))).play();
            i++;
        }
        return gridPane;
    }
//    private void MakeShastaMad(int mad){
//        final Tooltip tooltip = new Tooltip();
//        tooltip.setText(Integer.toString(score + Integer.parseInt(TheButtonArray[mad].getText())));
////        System.out.println(score + " is score " + TheButtonArray[mad].getText() + " is value");
//        TheButtonArray[mad].setTooltip(tooltip);
//    }

    private void initButtonsArray() {
        for (int i = 0; i < TheButtonArray.length; i++) {
            TheButtonArray[i] = new Button("Button-" + i);
        }
    }

    // THIS MAKES THE BUTTON DISAPPEAR!!!
//    private void buttonDisappear(Button b) {
//        final Timeline timeline = new Timeline();
//        timeline.setCycleCount(1);
//        final KeyValue kv = new KeyValue(b.opacityProperty(), 0);
//        final KeyFrame kf = new KeyFrame(Duration.millis(600), kv);
//        timeline.getKeyFrames().add(kf);
//        timeline.play();
//    }
    //Animates button selection
    private void buttonSelect(Button b) {
        //adds functionality
        TotalSum += Integer.parseInt(b.getText()); //adds value of that button to the total
        b.setDisable(true);
        //visually selects button
        final InnerShadow is = new InnerShadow();
        is.setOffsetX(2.0);
        is.setOffsetY(2.0);
        is.setColor(Color.BLACK);
        new Timeline(
                new KeyFrame(Duration.seconds(.1), new KeyValue(b.effectProperty(), is))).play();
        //checks for a win
        //winCheck(k);
    }

    private void addToScore() {
        score += winNum; //update score
//         System.out.println("Added score");
        userScore.setText(Integer.toString(score)); //displays new score
    }

    private void RegenTile(int SquareLoc) {
        shiftShift(SquareLoc);
    }

    private void playWrongSound() {
        // System.out.println("wrong");
        final Task task2 = new Task() {
            @Override
            protected Object call() throws Exception {
                AudioClip audio = new AudioClip(getClass().getResource("Computer_Error_Alert-SoundBible.wav").toExternalForm());//http://www.flashkit.com/soundfx/Electronic/Beeps/Beep_sig-NEO_Soun-8411/index.php
                audio.setVolume(0.5f);
                audio.play();
                audio.setCycleCount(1);
                return null;
            }
        };
        Thread thread = new Thread(task2);
        thread.start();
    }

    private void playWinSound() {
        final Task task = new Task() {

            @Override
            protected Object call() throws Exception {
                AudioClip audio = new AudioClip(getClass().getResource("Beep_sig-NEO_Soun-8411_hifi.wav").toExternalForm());//http://www.flashkit.com/soundfx/Electronic/Beeps/Beep_sig-NEO_Soun-8411/index.php
                audio.setVolume(0.5f);
                audio.play();
                audio.setCycleCount(1);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private void winCheck() { //check if player has reached winning # or exceeded
//         System.out.println("win check done");
        if (TotalSum == winNum) {

            addToScore();
            playWinSound();
            addtoTurnTime();
//            System.out.println("You win!!");
            for (int i = 0; i < limit; i++) {
                Button t = TheButtonArray[i];
                temp[i] = Integer.parseInt(t.getText());

            }
//            System.out.println("test2");

            for (int i = 0; alreadySelected2.length() > i; i++) {
//                  System.out.println("test1");

                if (alreadySelected2.get(i)) {
                    if (i < 9) {
                        shiftShift(i);
//                        System.out.println("test");
                        RegenTile((i));             //resets top row tiles
                    }
                    for (int w = i; w > 9; w = w - 9) {
                        if (w >= 9) {
                            Button r = TheButtonArray[w];

                            r.setText(Integer.toString(temp[w - 9]));
                            // System.out.println("test " + w % 9);
                            RegenTile((w) % 9);
                            shiftShift(w);
                        }
                    }
                }
            }
            Reset();
        } else if (TotalSum > winNum) {
            //System.out.println("test "); //debug
            Reset();
            playWrongSound();
            winNumLabel.setText(Integer.toString(winNum)); //reset goal display
        } else {
            sumNumLabel.setText(Integer.toString(TotalSum));
        }
    }

    private void Reset() {
        turnTime = 15;
        TotalSum = 0;
        sumNumLabel.setText(""); //reset sumLabel
        winNum = (int) (Math.random() * 20 + max + 1); //number to add to calculation
        winNumLabel.setText(Integer.toString(winNum));
        //System.out.println("Get: " + winNum); //debug

        for (int i = 0; i < limit; i++) {
            Button b = TheButtonArray[i];
            b.setDisable(false);
            b.setVisible(true);
            alreadySelected2.set(i, false);

            final InnerShadow is = new InnerShadow();
            is.setOffsetX(0.0);
            is.setOffsetY(2.0);
            is.setColor(Color.LIGHTGRAY);
            new Timeline(
                    new KeyFrame(Duration.seconds(.1), new KeyValue(b.effectProperty(), is))).play();
            //insert "no" sound here
            //  b.setBackground(java.awt.Color.WHITE);
//            if (alreadySelected2.get(i) == true) {
//                //System.out.println("test" + i);
//                randomNum = (int) (Math.random() * max + 1);
//                b.setText(Integer.toString(randomNum));
//            }
        }

    }

    private void SingleReset(int k) {
        Button b = TheButtonArray[k];
        TheButtonArray[k].setText(Integer.toString((int) (Math.random() * max + 1)));
        b.setDisable(false);
        alreadySelected2.set(k, false);
        final InnerShadow is = new InnerShadow();
        is.setOffsetX(0.0);
        is.setOffsetY(2.0);
        is.setColor(Color.LIGHTGRAY);

        new Timeline(
                new KeyFrame(Duration.seconds(.1), new KeyValue(TheButtonArray[k].effectProperty(), is))).play();

//        b.setVisible(false);
    

    }
}
