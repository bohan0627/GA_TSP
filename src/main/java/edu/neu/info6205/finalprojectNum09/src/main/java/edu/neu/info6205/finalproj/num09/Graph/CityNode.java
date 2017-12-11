package edu.neu.info6205.finalproj.num09.Graph;

/**
 * @author  Bo Han
 * @NUID    001815357
 */

public class CityNode implements Comparable<CityNode>{

    private int cityNum;
    private String cityKey;

    private Position pos;


    public CityNode(int cityNum, Position pos){
        this.cityNum = cityNum;
        this.pos = pos;
        cityKey = Integer.toString(cityNum);
    }


    public void setCityNum(int cityNum) {
        this.cityNum = cityNum;
        cityKey = Integer.toString(cityNum);
    }

    public int getCityNum() {
        return this.cityNum;
    }

    public String getCityKey(){
        return cityKey;
    }

    public Position getPosition() {
        return pos;
    }

    public int getX(){
        return pos.getX();
    }

    // Gets city's y coordinate
    public int getY(){
        return pos.getY();
    }


    public double calWeigh(CityNode city) {
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

        return distance;
    }

    public static double calcDistance( Position startPos, Position endPos ) {
        int x = Math.abs( startPos.getX() - endPos.getX() );
        int y = Math.abs( startPos.getY() - endPos.getY() );
        return Math.sqrt( x*x + y*y );
    }

    public int compareTo(CityNode node) {
        if( cityNum == node.getCityNum() && cityKey==node.getCityKey() )
            return 0;
        else return -1;
    }

    @Override
    public int hashCode(){
        int hashCode = 19;
        hashCode = hashCode*cityKey.hashCode() + cityNum*31;
        hashCode = hashCode*pos.getX() + pos.getY()*31;
        return hashCode;

    }

    @Override
    public boolean equals(Object obj){
        if( this==obj )
            return true;
        else if( obj==null || obj.getClass()!=this.getClass() )
            return false;

        CityNode oCN = (CityNode)obj;
        return ( oCN.getCityKey().equals(cityKey) && oCN.getCityNum()==cityNum
                && oCN.getX()==getX() && oCN.getY()==getY() );
    }




}
