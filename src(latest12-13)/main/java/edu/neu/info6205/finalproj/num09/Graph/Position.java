package edu.neu.info6205.finalproj.num09.Graph;

/**
 * @author  Bo Han
 * @NUID    001815357
 */
public class Position {
    protected int x;
    protected int y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return ("Pos[" + x + "," + y + "]");
    }
    
    public boolean compareTo( Position pos ) {
        return ( x==pos.getX() && y==pos.getY() );
    }

}
