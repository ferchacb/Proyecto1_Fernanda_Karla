package Logic;

/**
 *
 * @author karlita!!
 */
public class Global {
    
    private static String nickName;
    private static int columns;
    private static int rows;
    private static int sugar;
    private static int wine;
    private static int posion;

    public Global()
    {
    
    }
    //
    public static String getNickName() {
        return nickName;
    }
    //
    public static void setNickName(String aNickName) {
        nickName = aNickName;
    }
    //
    public static int getColumns() {
        return columns;
    }
    //
    public static void setColumns(int aColumns) {
        columns = aColumns;
    }
    //
    public static int getSugar() {
        return sugar;
    }
    //
    public static void setSugar(int aSugar) {
        sugar = aSugar;
    }
    //
    public static int getPosion() {
        return posion;
    }
    //
    public static void setPosion(int aPosion) {
        posion = aPosion;
    }
    //
    public static int getWine() {
        return wine;
    }
    //
    public static void setWine(int aWine) {
        wine = aWine;
    }

    /**
     * @return the rows
     */
    public static int getRows() {
        return rows;
    }

    /**
     * @param aRows the rows to set
     */
    public static void setRows(int aRows) {
        rows = aRows;
    }
    
}
