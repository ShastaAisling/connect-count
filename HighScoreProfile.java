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

/**
 *
 * @author shasta-ritchie
 */
public class HighScoreProfile implements Comparable{
    
    String theName;
    int theScore;
    
    HighScoreProfile(){
        theName = "";
        theScore = 0;
    }
 
    public void inputName(String inName) {
        this.theName = inName;
    }

    public void inputScore(int score) {
        this.theScore = score;
    }

    public String getName() {
        return theName;
    }

    public int getScore() {
        return theScore;
    }
    
    
    @Override
    public String toString() {
        String theString = this.theName + "$" + Integer.toString(this.theScore);
        return theString;
    }
    
  
    public HighScoreProfile(String theString) {
        int thePlace = 0;
        HighScoreProfile hs = null;// if we don't get a valid string to parse this indicates failed construct
        if (0 < (thePlace = theString.lastIndexOf('$'))) {
            System.out.println(thePlace);
            this.theScore = Integer.parseInt(theString.substring(thePlace+1).trim());
            this.theName = theString.substring(0,thePlace).trim();

        }
    }
    
    @Override
    public int compareTo(Object o) {
        int compareScore;
        HighScoreProfile hsp = (HighScoreProfile) o;
        compareScore = (int) hsp.getScore();
        return compareScore-this.theScore; // descending order
    }
}
