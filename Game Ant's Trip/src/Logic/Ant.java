 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import Views.FrmGame;
import Logic.ObjectsInsideMatrix;
import static Logic.ObjectsInsideMatrix.gMatrix;
import static Logic.ObjectsInsideMatrix.logicMatrix;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Icon;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


/**
 *
 * @author Fernanda and Karla
 */
public class Ant {
    int steps = 0;
    
    private String nickname;
    private int health = 100;
    private int alcoholLevel = 0;

    
    //Estados de la hormiga
    private static boolean sober; //SOBRIA
    private static boolean drunk; //EBRIA
    private static boolean dead; //MUERTA
    private static boolean poisoned; //ENVENENADA
   
    //Para mover
    private int speedX;
    private int speedY;
    

    
    
    public Ant() {
        this.speedX = 0;
        this.speedY = 0;
    }

    public Ant(String nickname, int health, int alcoholLevel, boolean sober, boolean drunk, boolean dead, boolean poisoned) {
        this.nickname = nickname;
        this.health = health;
        this.alcoholLevel = alcoholLevel;
        
        this.sober = sober;
        this.drunk = drunk;
        this.dead = dead;
        this.poisoned = poisoned;
        
        this.speedX = 0;
        this.speedY = 0;
    }

   

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAlcoholLevel() {
        return alcoholLevel;
    }

    public void setAlcoholLevel(int alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public boolean isSober() {
        return sober;
    }

    public void setSober(boolean sober) {
        this.sober = sober;
    }

    public boolean isDrunk() {
        return drunk;
    }

    public void setDrunk(boolean drunk) {
        this.drunk = drunk;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    
    
    
    //*******************************************************************************************************************************************
    //                                                           METHODS
    //*******************************************************************************************************************************************
    
    
    
//   Method of walking
    public  void walk(int keyChar) throws InterruptedException {
        switch (keyChar) {

            case KeyEvent.VK_UP:  //UP
                speedX = 0;
                speedY = 0;
                speedY -= 1;
                break;
            case KeyEvent.VK_DOWN:  //DOWN
                speedX = 0;
                speedY = 0;
                speedY += 1;
                break;
            case KeyEvent.VK_RIGHT: //RIGHT
                speedX = 0;
                speedY = 0;
                speedX += 1;
                break;
            case KeyEvent.VK_LEFT:  //LEFT
                speedX = 0;
                speedY = 0;
                speedX -= 1;
                break;
            default:
                speedY = 0;
                speedX = 0;
        }
    }
    

    //    Random Address Method
    public String hip(int steps, int speedX, int speedY, int alcoholLevel){
        String step = "";
        if (alcoholLevel > 0){
            int rd = 1 + (int)(Math.random() * 4); 
        
            switch(rd){
                case 1:   //LEFT
                    logicMatrix[speedX - 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX - 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps);    
                    break;
                case 2:  //RIGHT
                    logicMatrix[speedX + 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX + 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps); 
                    break;

                case 3: //UP
                    logicMatrix[speedX][speedY - 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY - 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps); 
                    break;
                case 4: //DOWN
                    logicMatrix[speedX][speedY + 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY + 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps); 
                    break;
            }

       }
       return step;
    }
    
    
//    Method of modifying health
    public void modifyHealth(int health){
       
    }
    
//    Method for changing the level of alcohol
    public void modifyAlcoholLevel(){
        if (alcoholLevel <= 0){
            alcoholLevel = alcoholLevel + 20;
        }
       
    }
    
//    Method to change status
    public boolean changeState(String sober, String drunk, String dead, String poisoned){
        return true;  
    }

    private Icon getPicture(int picture_drunk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
