package GameEngine;import Util.Utils;import java.awt.*;import java.util.ArrayList;import java.util.Random;public class GoldMine {    private boolean belong = false;    private int team = 0;    private Cell cell;    private int maxHeight;    private int maxWidth;    private Image image;    private Image closedBox;    private Image openedBox;    private static int numberOfGoldMines = 0;    private int rank;    public GoldMine(Cell cell, int height, int width) {        this.cell = cell;        maxHeight = height;        maxWidth = width;        rank = numberOfGoldMines;        numberOfGoldMines++;        openedBox = Utils.getImage("goldMine.png");        closedBox = Utils.getImage("CloseBox.png");        image = closedBox;    }    public boolean ifFree() {        return !belong;    }    public int getRank() {        return rank;    }    public boolean ifBelong(int team) {        return this.team == team;    }    public boolean setBelong(int team) {        if (!belong) {            belong = true;            this.team = team;            image = openedBox;            return true;        } else {            return false;        }    }//false means mine belongs to the other team and should be destroyed instead    public Cell getCell() {        return cell;    }    public void setCell(int row, int column) {        cell.setRow(row);        cell.setColumn(column);    }    public GoldMine destroy(GoldMine goldMine, ArrayList<Path> paths, ArrayList<ArrayList<Barracks>> barracks, ArrayList<Ancient> ancients) {        if (!belong)            return null;        else {            belong = false;            team = 0;            image = closedBox;            ArrayList<Cell> invalidCells = new ArrayList<>();            boolean validChoice = false;            for (Path path : paths) {                for (Cell cell : path.getPathCells())                    invalidCells.add(cell);            }            for (ArrayList<Barracks> barrackses:barracks) {                for (Barracks barrack:barrackses) {                    for (Cell[] cells:barrack.getCells()) {                        for (Cell cell1:cells)                        invalidCells.add(cell1);                    }                }            }            for (Ancient ancient:ancients) {                for (Cell[] cells:ancient.getCell()) {                    for (Cell cell1:cells)                        invalidCells.add(cell1);                }            }            Random rand = new Random();            int newRow = rand.nextInt(maxHeight - 1);            int newCul = rand.nextInt(maxWidth - 1);           outer: while (!validChoice) {                for (Cell cell : invalidCells) {                    if (cell.getRow() == newRow && cell.getColumn() == newCul) {                        newRow = rand.nextInt(maxHeight - 1);                        newCul = rand.nextInt(maxWidth - 1);                        continue outer;                    }                }                validChoice = true;            }            cell.setColumn(newCul);            cell.setRow(newRow);            return goldMine;        }//if it belongs to a team find a new place for it    }    public Image getImage() {        return image;    }}