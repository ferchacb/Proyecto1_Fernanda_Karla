/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Logic.Ant;
import Logic.Global;
import Logic.ObjectsInsideMatrix;
import static Logic.ObjectsInsideMatrix.gMatrix;
import static Logic.ObjectsInsideMatrix.logicMatrix;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author Fernanda and Karla
 */
public class FrmGame extends javax.swing.JFrame {

    private int columneSize = 20;
    private int fileSize = 20;
    private int speedX = 0; //Position of the ant in the column
    private int speedY = 0; //Ant position in the row
    private int sugar = 0;
    private int poisson = 0;
    private int wine = 0;
    private int life = 0;
    private int drunk = 0;
    private boolean win = false;

    public boolean newGame;

    public static final int boxSize = 60;
// LOGICAL MATRIX
    private int[][] logicMatrix = new int[columneSize][fileSize];
// GRAPHIC MATRIX
    private JLabel[][] gMatrix = new JLabel[columneSize][fileSize];
    //private JLabel[][] gMatrixTemp = new JLabel[columneSize][fileSize];
    private String[][] gMatrixTemp = new String[columneSize][fileSize];

    int steps = 0;

    public FrmGame() {
        initComponents();
        //createLogicalMap(20, 20);
        //deployArray(20, 20);
        this.newGame = false;
        this.ReadFilePlay();
        //Here is the method of music
        this.Music();
    }

    //
    public void ReadFilePlay() {
        Global oGlobal = new Global();
        int columns = 0;
        int sugar = 0;
        int wine = 0;
        int posion = 0;
        int rows = 0;
        String nickName = oGlobal.getNickName();
        if (nickName == null) {
            LoadPreferences();
        }
        columns = oGlobal.getColumns();
        sugar = oGlobal.getSugar();
        wine = oGlobal.getWine();
        posion = oGlobal.getPosion();
        rows = oGlobal.getRows();

        createLogicalMap(rows, columns, sugar, wine, posion);
        deployArray(rows, columns);

    }

    //
    private void LoadPreferences() {
        try {
            ObjectInputStream find = new ObjectInputStream(new FileInputStream("default"));
            Object nick = find.readObject();
            Object columne = find.readObject();
            Object rows = find.readObject();
            Object sugar = find.readObject();
            Object wine = find.readObject();
            Object poison = find.readObject();
            //
            Global oGlobal = new Global();
            oGlobal.setNickName(nick.toString());
            oGlobal.setColumns(Integer.valueOf(columne.toString()));
            oGlobal.setRows(Integer.valueOf(rows.toString()));
            oGlobal.setPosion(Integer.valueOf(poison.toString()));
            oGlobal.setSugar(Integer.valueOf(sugar.toString()));
            oGlobal.setWine(Integer.valueOf(wine.toString()));
            //
            this.life = 100 + (Integer.valueOf(sugar.toString()) * 10);
            psbHealthLevel.setMinimum(0);
            psbHealthLevel.setMaximum(life);
            life = 100;
            psbHealthLevel.setValue(100);
            psbAlcohol.setMinimum(0);
            psbAlcohol.setMaximum(50);
            psbAlcohol.setValue(0);
            //
        } catch (Exception e) {
        }
    }

    //Method to load music
    private void Music() {
        //Instance of the object of the audio player library
        AudioPlayer MGB = AudioPlayer.player;
        //Instance of the audiostream object
        AudioStream BMG = null;
        ContinuousAudioDataStream loop = null;
        try {
            //Loaded audio dl file
            BMG = new AudioStream(new FileInputStream("song.wav"));
            //Audio start
            //AudioPlayer.player.start(BMG);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        //Audio start
        //MGB.start(loop);
        if (this.newGame == true) {
            MGB.stop(BMG);
        } else {
            AudioPlayer.player.start(BMG);
            MGB.start(loop);
        }
    }

    //In this method we develop the logical matrix
    private void createLogicalMap(int x, int y, int sugar, int wine, int poisson) {

        int temp[][] = new int[x][y];
        int[] lit = {4, 5, 6};

        int contA = sugar;
        int contB = wine;
        int contC = poisson;

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                int random = (lit[new Random().nextInt(lit.length)]);

                int rd2 = (int) (Math.random() * i + j);
                if (i == 0 && j == 0) {
                    temp[i][j] = 9;
                } else if (i == temp.length - 1 && j == temp[0].length - 1) {
                    temp[i][j] = 3;
                } else if (random == 4 && contA != 0) {
                    temp[i][j] = random;
                    contA--;
                } else if (random == 5 && contB != 0) {
                    temp[i][j] = random;
                    contB--;
                } else if (random == 6 && contC != 0) {
                    temp[i][j] = random;
                    contC--;
                } else {
                    temp[i][j] = 0;
                }

            }

        }

        logicMatrix = temp;

    }

    //METHOD FOR SHOWING THE MATRIX
    private void deployArray(int x, int y) {
        try {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    gMatrix[i][j] = new JLabel();
                    gMatrix[i][j].setOpaque(true);
                    gMatrix[i][j].setBounds((i * boxSize) + 30, (j * boxSize) + 30, boxSize, boxSize);
                    gMatrix[i][j].setVisible(true);
                    gMatrix[i][j].setBackground(Color.black);
                    gMatrix[i][j].setIcon(getPicture(logicMatrix[i][j]));
                    pnlGame.add(gMatrix[i][j]);
                }

            }
        } catch (Exception ex) {
            JOptionPane.showConfirmDialog(this, ex.getMessage());
        }
    }

    //
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

    private void proof(int value) {
        try {
            Ant oAnt = new Ant();
            if (oAnt.isDead()) {
                return;
            }
            if (win) {
                return;
            }
            switch (value) {

                case 1: //Left
                    if (logicMatrix[speedX - 1][speedY] == 2) {
                        return;
                    }

                    switch (logicMatrix[speedX - 1][speedY]) {
                        case 4:
                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);

                            if (drunk > 0) {
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death by poisson");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX - 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX - 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedX--;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;

                case 2: //Right
                    if (logicMatrix[speedX + 1][speedY] == 2) {
                        return;
                    }
                    switch (logicMatrix[speedX + 1][speedY]) {
                        case 4:
                            sugar = sugar + 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            if (drunk > 0) {
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death by poison");

                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX + 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX + 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedX++;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;

                case 3: //Up
                    if (logicMatrix[speedX][speedY - 1] == 2) {
                        return;
                    }
                    switch (logicMatrix[speedX][speedY - 1]) {
                        case 4:
                            sugar = sugar + 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            if (drunk > 0) {
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death by poison");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX][speedY - 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY - 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedY--;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;

                case 4: //Dpwn
                    if (logicMatrix[speedX][speedY + 1] == 2) {
                        return;
                    }
                    switch (logicMatrix[speedX][speedY + 1]) {
                        case 4:
                            sugar = sugar + 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            if (drunk > 0) {
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX][speedY + 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY + 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedY++;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "You cannot leave the map");

        }

    }

    //Random Address Method
    public String hip(int steps, int speedX, int speedY, int alcoholLevel) {
        String step = "";
        if (alcoholLevel > 0) {
            int rd = 1 + (int) (Math.random() * 4);

            switch (rd) {
                case 1:   //Left
                    if (logicMatrix[speedX - 1][speedY] == 2) {
                        break;
                    }
                    logicMatrix[speedX - 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX - 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps);
                    break;
                case 2:  //Right
                    if (logicMatrix[speedX + 1][speedY] == 2) {
                        break;
                    }
                    logicMatrix[speedX + 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX + 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps);
                    break;

                case 3: //Up
                    if (logicMatrix[speedX][speedY - 1] == 2) {
                        break;
                    }
                    logicMatrix[speedX][speedY - 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY - 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    steps++;
                    step = String.valueOf(steps);
                    break;
                case 4: //Down
                    if (logicMatrix[speedX][speedY + 1] == 2) {
                        break;
                    }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGame = new javax.swing.JPanel();
        psbAlcoholLevel = new javax.swing.JPanel();
        psbHealthLevel = new javax.swing.JProgressBar();
        lblHealthLevel = new javax.swing.JLabel();
        psbAlcohol = new javax.swing.JProgressBar();
        lblAlcoholLevel = new javax.swing.JLabel();
        lblSugar = new javax.swing.JLabel();
        lblWine = new javax.swing.JLabel();
        lblPoisson = new javax.swing.JLabel();
        imgNormalSugar = new javax.swing.JLabel();
        imgWine = new javax.swing.JLabel();
        imgPoisson = new javax.swing.JLabel();
        txtSteps = new javax.swing.JTextField();
        lblSteps = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniNewGame = new javax.swing.JMenuItem();
        mniMenu = new javax.swing.JMenuItem();
        mniSettings = new javax.swing.JMenuItem();
        mniExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        pnlGame.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlGameLayout = new javax.swing.GroupLayout(pnlGame);
        pnlGame.setLayout(pnlGameLayout);
        pnlGameLayout.setHorizontalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlGameLayout.setVerticalGroup(
            pnlGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        psbAlcoholLevel.setBackground(new java.awt.Color(255, 255, 255));

        psbHealthLevel.setBackground(new java.awt.Color(51, 255, 0));

        lblHealthLevel.setFont(new java.awt.Font("Chiller", 1, 24)); // NOI18N
        lblHealthLevel.setForeground(new java.awt.Color(0, 51, 204));
        lblHealthLevel.setText("Health Level");

        psbAlcohol.setBackground(new java.awt.Color(255, 0, 0));

        lblAlcoholLevel.setFont(new java.awt.Font("Chiller", 1, 24)); // NOI18N
        lblAlcoholLevel.setForeground(new java.awt.Color(0, 51, 204));
        lblAlcoholLevel.setText("Alcohol Level");

        lblSugar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSugar.setText("0");

        lblWine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWine.setText("0");

        lblPoisson.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPoisson.setText("0");

        imgNormalSugar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/normalSugar.png"))); // NOI18N

        imgWine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/wine.png"))); // NOI18N

        imgPoisson.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pictures/poisson.png"))); // NOI18N

        txtSteps.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSteps.setText("0");
        txtSteps.setToolTipText("");
        txtSteps.setFocusable(false);
        txtSteps.setRequestFocusEnabled(false);

        lblSteps.setFont(new java.awt.Font("Chiller", 1, 24)); // NOI18N
        lblSteps.setForeground(new java.awt.Color(0, 51, 204));
        lblSteps.setText("STEPS");

        javax.swing.GroupLayout psbAlcoholLevelLayout = new javax.swing.GroupLayout(psbAlcoholLevel);
        psbAlcoholLevel.setLayout(psbAlcoholLevelLayout);
        psbAlcoholLevelLayout.setHorizontalGroup(
            psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                .addContainerGap(224, Short.MAX_VALUE)
                .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSugar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                        .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSteps)
                            .addComponent(txtSteps, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addComponent(imgNormalSugar)))
                .addGap(29, 29, 29)
                .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgWine, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblWine, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgPoisson, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPoisson, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                        .addComponent(lblHealthLevel)
                        .addGap(18, 18, 18)
                        .addComponent(psbHealthLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                        .addComponent(lblAlcoholLevel)
                        .addGap(18, 18, 18)
                        .addComponent(psbAlcohol, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
        );
        psbAlcoholLevelLayout.setVerticalGroup(
            psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblAlcoholLevel)
                        .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                            .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblHealthLevel)
                                .addComponent(psbHealthLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(7, 7, 7)
                            .addComponent(psbAlcohol, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                        .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(imgWine)
                                .addComponent(imgPoisson, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(psbAlcoholLevelLayout.createSequentialGroup()
                                    .addComponent(lblSteps)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtSteps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(imgNormalSugar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(psbAlcoholLevelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPoisson)
                                .addComponent(lblWine))
                            .addComponent(lblSugar))))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        mniNewGame.setText("New Game");
        mniNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNewGameActionPerformed(evt);
            }
        });
        jMenu1.add(mniNewGame);

        mniMenu.setText("Menu");
        mniMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniMenuActionPerformed(evt);
            }
        });
        jMenu1.add(mniMenu);

        mniSettings.setText("Settings");
        mniSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSettingsActionPerformed(evt);
            }
        });
        jMenu1.add(mniSettings);

        mniExit.setText("Exit");
        mniExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniExitActionPerformed(evt);
            }
        });
        jMenu1.add(mniExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(psbAlcoholLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(psbAlcoholLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
//         System.out.println(evt.getKeyCode());
        try {
            Ant oAnt = new Ant();
            if (oAnt.isDead()) {
                return;
            }
            if (win) {
                return;
            }
            switch (evt.getKeyCode()) {

                case KeyEvent.VK_LEFT: //Left
                    if (logicMatrix[speedX - 1][speedY] == 2) {
                        return;
                    }

                    switch (logicMatrix[speedX - 1][speedY]) {
                        case 4:
                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);

                            if (drunk > 0) {
                                int rd = 1 + (int) (Math.random() * 4);
                                proof(rd);
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk ");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death by poison");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX - 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX - 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedX--;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;

                case KeyEvent.VK_RIGHT: //Right
                    if (logicMatrix[speedX + 1][speedY] == 2) {
                        return;
                    }
                    switch (logicMatrix[speedX + 1][speedY]) {
                        case 4:
                            sugar = sugar + 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            if (drunk > 0) {
                                int rd = 1 + (int) (Math.random() * 4);
                                proof(rd);
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death by poison");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX + 1][speedY] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX + 1][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedX++;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;

                case KeyEvent.VK_UP: //Up
                    if (logicMatrix[speedX][speedY - 1] == 2) {
                        return;
                    }
                    switch (logicMatrix[speedX][speedY - 1]) {
                        case 4:
                            sugar = sugar + 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            if (drunk > 0) {
                                int rd = 1 + (int) (Math.random() * 4);
                                proof(rd);
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death by poison");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX][speedY - 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY - 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedY--;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;

                case KeyEvent.VK_DOWN: //Down
                    if (logicMatrix[speedX][speedY + 1] == 2) {
                        return;
                    }
                    switch (logicMatrix[speedX][speedY + 1]) {
                        case 4:
                            sugar = sugar + 1;
                            lblSugar.setText("" + sugar);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            break;
                        case 5:
                            wine += 1;
                            lblWine.setText("" + wine);
                            drunk = drunk + 20;
                            psbAlcohol.setValue(drunk);
                            life = life - 10;
                            psbHealthLevel.setValue(life);

                            sugar += 1;
                            lblSugar.setText("" + sugar);
                            drunk = drunk - 10;
                            psbAlcohol.setValue(drunk);
                            life = life + 10;
                            psbHealthLevel.setValue(life);
                            if (drunk > 0) {
                                int rd = 1 + (int) (Math.random() * 4);
                                proof(rd);
                                String steps = hip(Integer.valueOf(txtSteps.getText().toString()), speedX, speedY, drunk);
                                txtSteps.setText(String.valueOf(steps));
                                if (drunk >= 50) {
                                    JOptionPane.showMessageDialog(this, "Game Over, drunk");
                                    oAnt.setDead(true);
                                }
                                return;
                            }
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Game Over...");
                                oAnt.setDead(true);
                            }
                            break;
                        case 6:
                            poisson += 1;
                            life = life - 50;
                            psbHealthLevel.setValue(life);
                            lblPoisson.setText("" + poisson);
                            if (life <= 0) {
                                JOptionPane.showMessageDialog(this, "Upsss your death");
                                oAnt.setDead(true);
                            }
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(this, "You're win!!!!");
                            win = true;
                        default:
                            break;
                    }

                    logicMatrix[speedX][speedY + 1] = ObjectsInsideMatrix.picture_drunk;
                    logicMatrix[speedX][speedY] = ObjectsInsideMatrix.picture_grass;

                    gMatrix[speedX][speedY + 1].setIcon(getPicture(ObjectsInsideMatrix.picture_drunk));
                    gMatrix[speedX][speedY].setIcon(getPicture(ObjectsInsideMatrix.picture_grass));
                    speedY++;
                    steps++;
                    txtSteps.setText(String.valueOf(steps));

                    break;
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "You cannot leave the map");

        }

    }//GEN-LAST:event_formKeyReleased

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void mniExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mniExitActionPerformed

    private void mniSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSettingsActionPerformed
        FrmSettingsNew settings = new FrmSettingsNew();
        settings.setLocationRelativeTo(null);
        settings.setVisible(true);
        dispose();
    }//GEN-LAST:event_mniSettingsActionPerformed

    private void mniMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniMenuActionPerformed
        FrmStart menu = new FrmStart();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_mniMenuActionPerformed

    private void mniNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNewGameActionPerformed
        this.newGame = true;
        
        FrmGame newGame = new FrmGame();
        newGame.setLocationRelativeTo(null);
        newGame.setVisible(true);
        this.newGame = true;
        dispose();
    }//GEN-LAST:event_mniNewGameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imgNormalSugar;
    private javax.swing.JLabel imgPoisson;
    private javax.swing.JLabel imgWine;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblAlcoholLevel;
    private javax.swing.JLabel lblHealthLevel;
    private javax.swing.JLabel lblPoisson;
    private javax.swing.JLabel lblSteps;
    private javax.swing.JLabel lblSugar;
    private javax.swing.JLabel lblWine;
    private javax.swing.JMenuItem mniExit;
    private javax.swing.JMenuItem mniMenu;
    private javax.swing.JMenuItem mniNewGame;
    private javax.swing.JMenuItem mniSettings;
    private javax.swing.JPanel pnlGame;
    private javax.swing.JProgressBar psbAlcohol;
    private javax.swing.JPanel psbAlcoholLevel;
    private javax.swing.JProgressBar psbHealthLevel;
    private javax.swing.JTextField txtSteps;
    // End of variables declaration//GEN-END:variables
}
