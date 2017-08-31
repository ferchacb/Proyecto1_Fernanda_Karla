/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Fernanda and Karla
 */
public class ObjectsInsideMatrix {
   
//FOR IMAGES IN THE MATRIX
    public static final int picture_box = 0;
    public static final int picture_anthill = 3;
    public static final int picture_drunk = 9;
    public static final int picture_grass = 2;
    
      
   
    public static JLabel [][] gMatrix;
    public static int [][] logicMatrix;
  
    public static int positionX;
    public static int positionY;
    public static int totalSugarCubes = 0;
    
    private ImageIcon getPicture(int type) {
        switch (type) {
            case ObjectsInsideMatrix.picture_box:
                return new ImageIcon(getClass().getResource("/Pictures/box.png"));
            case ObjectsInsideMatrix.picture_grass:
                return new ImageIcon(getClass().getResource("/Pictures/grass.jpg"));
            case ObjectsInsideMatrix.picture_anthill:
                return new ImageIcon(getClass().getResource("/Pictures/HouseAnt.png"));
            case ObjectsInsideMatrix.picture_drunk:
                return new ImageIcon(getClass().getResource("/Pictures/drunk.png"));
            default:
                return new ImageIcon(getClass().getResource("/Pictures/box.png"));

        }
    
    }    
}
