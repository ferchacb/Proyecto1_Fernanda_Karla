/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


/**
 *
 * @author Fernanda and Karla
 */
public class Path extends JButton{
    
    private int columneSize;
    private int fileSize;
    
    private int antPositionFile = 1;
    private int antPositionColumne = 1;
    private int anthillPositionFile = 8;
    private int anthillPositionColumne = 8;
    
    public static final int boxSize = 60;
    
    // GRAPHIC MATRIX
    private JLabel [][] mGrafic = new JLabel [columneSize][fileSize];
    
    private int steps;  
    
    
    //SUGAR CUBES
    //OBJECTS THAT GO ON THE MATRIX
    private String sugarCubes;
    private String sugarPoisson;
    private String sugarWine;

    
    

    public Path() {}

    public Path(int columneSize, int fileSize, int steps, String sugarCubes, String sugarPoisson, String sugarWine) {
        this.columneSize = columneSize;
        this.fileSize = fileSize;
        this.steps = steps;
        this.sugarCubes = sugarCubes;
        this.sugarPoisson = sugarPoisson;
        this.sugarWine = sugarWine;
    }



    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getSugarCubes() {
        return sugarCubes;
    }

    public void setSugarCubes(String sugarCubes) {
        this.sugarCubes = sugarCubes;
    }

    public String getSugarPoisson() {
        return sugarPoisson;
    }

    public void setSugarPoisson(String sugarPoisson) {
        this.sugarPoisson = sugarPoisson;
    }

    public String getSugarWine() {
        return sugarWine;
    }

    public void setSugarWine(String sugarWine) {
        this.sugarWine = sugarWine;
    }

    public int getColumneSize() {
        return columneSize;
    }

    public void setColumneSize(int columneSize) {
        this.columneSize = columneSize;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getAntPositionFile() {
        return antPositionFile;
    }

    public void setAntPositionFile(int antPositionFile) {
        this.antPositionFile = antPositionFile;
    }

    public int getAnthillPositionFile() {
        return anthillPositionFile;
    }

    public void setAnthillPositionFile(int anthillPositionFile) {
        this.anthillPositionFile = anthillPositionFile;
    }

    public int getAnthillPositionColumne() {
        return anthillPositionColumne;
    }

    public void setAnthillPositionColumne(int anthillPositionColumne) {
        this.anthillPositionColumne = anthillPositionColumne;
    }

    public JLabel[][] getmGrafic() {
        return mGrafic;
    }

    public void setmGrafic(JLabel[][] mGrafic) {
        this.mGrafic = mGrafic;
    }
//    Start game
    public  void start(){     
    }
    
//    Method to generate cells
    public  void generateBoxes(){    
    }
       

}
