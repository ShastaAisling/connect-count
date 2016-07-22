/*
 * Copyright (C) 2016 shasta-ritchie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Default;

import com.dropbox.core.*;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

/**
 *
 * @author shasta-ritchie
 */
public class HighScoreSort {
    private ArrayList TopTenList;
    // a linked list of HiScorers
    private final String path = "highscores.txt";
    private HighScoreProfile hs;
    private static final String APP_KEY = "kg055m21ttrswih";
    private static final String APP_SECRET = "uoy9s4y3mt8hjss";
    private static DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
    private static DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
    private static DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
    private static String code;

    private static void main() throws IOException, DbxException {

        String accessToken = "lBfi1-U2ZsAAAAAAAAAAOY9uvMThfIwNr9kIvXb57Dfd11BLUdlV5q57N54JsiS7";
        DbxClient client = new DbxClient(config, accessToken);
            FileOutputStream outputStream2 = new FileOutputStream("highscores.txt");
            try {
                DbxEntry.File downloadedFile = client.getFile("/" + "highscores.txt",
                        null, outputStream2);
            } finally {
                outputStream2.close();
            }
        }

    public void readHiscores() throws FileNotFoundException, IOException, DbxException {
        //opens file ;reads strings ; parses into strings,int for HiScore linkedlist
        main();

        TopTenList = new ArrayList<HighScoreProfile>();//can be replaced by inference "diamond operator"

        FileReader filePointer;
        String theLine;
        filePointer = new FileReader(path);

        BufferedReader inStream;
        inStream = new BufferedReader(filePointer);

        while (inStream.ready()) {
            theLine = inStream.readLine();
            //System.out.println(theLine);//debugging -worked from topten dialog 
            if (theLine.length() > 0) {
                hs = new HighScoreProfile(theLine);
                if (hs != null) {
                    TopTenList.add(hs);
                }

                // System.out.println("File IO error");
            }
        }
        //close only if open file   
        inStream.close();
        filePointer.close();
    }

    public void showHiscores() {
        Iterator ttl = TopTenList.iterator();
        while (ttl.hasNext()) {
            hs = (HighScoreProfile) ttl.next();
            //if there is nno display area
            System.out.println(hs.toString());
        }

    }
    

    public void showHiscores(TextArea tDisplay, boolean sorted) {

        Iterator ttl = TopTenList.iterator();
        Collections.sort(TopTenList);
        //tDisplay.setText("The Best of the Best:");
        ttl = TopTenList.iterator(); //reset
        while (ttl.hasNext()) {
            hs = (HighScoreProfile) ttl.next();
            tDisplay.appendText( hs.getName() + " -- ");
            tDisplay.appendText(Integer.toString(hs.getScore()) + "\n" );
        }
    }

    private void sortHiScores() {
        Collections.sort(TopTenList);
        //seperated my sort, implimented Apr 15 2016
    }
   
    public void updateHighscores(int newscore, String newname){

        //sorts TopTenList
        Collections.sort(TopTenList);
        
        //converts lowest score/name in TopTenList to string
        String theStringofLine;
        theStringofLine = TopTenList.get(9).toString();
        
        //retrieves score as int 
        HighScoreProfile lastitem = new HighScoreProfile(theStringofLine);
        System.out.println(lastitem.getScore()); // debug
        
        //creates new HiScorer item using the new score and new username
        HighScoreProfile nhs = new HighScoreProfile();
        nhs.inputName(newname);
        nhs.inputScore(newscore);
        
        //compares lowest score of TopTenList to new score and updates if needed
        if (nhs.getScore()>lastitem.getScore()){
            TopTenList.add(nhs);
            TopTenList.remove(9);
        }
        System.out.println(nhs.toString()); //debug
        
    }
    
    public void writeHighScores() throws FileNotFoundException, IOException, DbxException {

        FileWriter filePointer;
        filePointer = new FileWriter(path);
        BufferedWriter outStream;
        outStream = new BufferedWriter(filePointer);
        Iterator ttl = TopTenList.iterator();
        while (ttl.hasNext()) {
            hs = (HighScoreProfile) ttl.next();
            outStream.write(hs.toString());
            outStream.newLine();
        }
        outStream.close();
        filePointer.close();
        String accessToken = "lBfi1-U2ZsAAAAAAAAAAOY9uvMThfIwNr9kIvXb57Dfd11BLUdlV5q57N54JsiS7";
        DbxClient client = new DbxClient(config, accessToken);
        String fileName = "highscores.txt";
        File inputFile = new File("highscores.txt");
        FileInputStream fis = new FileInputStream(inputFile);
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/" + fileName, DbxWriteMode.force(), inputFile.length(), fis);
            String sharedUrl = client.createShareableUrl("/" + fileName);
        } finally {
            fis.close();
        }
    }
}
