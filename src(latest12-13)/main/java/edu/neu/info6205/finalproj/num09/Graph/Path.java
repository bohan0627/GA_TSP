package edu.neu.info6205.finalproj.num09.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * @author  Bo Han
 * @NUID    001815357
 */


public class Path {
    // this pathCityCnt used for initialization cities
    private int pathCityCnt = 80;
    // path will be encoded as Individual
    private HashMap<String, Position> path = new HashMap<String, Position>(pathCityCnt);
    private int[]   cityIndex;

    // distance refers to the path length
    private double fitness = 0.0;
    private double distance = 0;


    /**
     * default constructor
     */
    public Path() {

    }

    /**
     *
     * @param city
     */
    public Path( CityNode[] city ) {
        pathCityCnt = city.length;
        cityIndex = new int[pathCityCnt];
        for( int i=0; i<pathCityCnt; i++ ) {
            path.put( city[i].getCityKey(), city[i].getPosition() );
            cityIndex[i] = city[i].getCityNum();
        }
        getFitness();
    }

    public Path( int pathCityCnt ) {
        this.pathCityCnt = pathCityCnt;
        path = new HashMap<String, Position>(pathCityCnt);
        cityIndex = new int[pathCityCnt];
        fitness = 0.0;
        distance = 0;
    }

    public static Path createNewPath( CityNode[] city ) {
        int size = city.length;
        Path path = new Path( size );
        ArrayList<CityNode> cityList = new ArrayList<CityNode>();
        for( int i=0; i<size; i++ )
            cityList.add( city[i] );
        //shuffle the list
        Collections.shuffle(cityList);
        
        for(int i=0;i<size;i++) {
            path.setCityNode( cityList.get(i), i ); //add HashMap, cityIndex[]
        }
        path.getFitness();
        
        return path;
    }


    public double calcDistanceSum() {
        distance =0;
        int size = pathSize();
        // Loop through our cities
        for (int i=0; i < size; i++) {
            // get the start city Position
            Position startCityPos = getCityPosition( Integer.toString(cityIndex[i]) );

            Position endCityPos = new Position();

            if( i+1 < size ) {
                endCityPos = getCityPosition( Integer.toString(cityIndex[i+1]) );
            }
            else {
                endCityPos = getCityPosition( Integer.toString(cityIndex[0]) ); //cycle(n+1 Node), the last node connects to the first node
            }
            // Get the distance between the two cities
            distance += CityNode.calcDistance( startCityPos, endCityPos );
        }

        return distance;
    }

    public int pathSize()
    {
        return path.size();
    }


    public void setCityNode( CityNode city, int index ) {
        path.put(city.getCityKey(), city.getPosition());
        cityIndex[index] = city.getCityNum();
        //update the fitness and distance after each change
        //distance = calcDistanceSum();
        //fitness = 1/distance;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/calcDistanceSum();
        }
        return fitness;
    }


    public Position getCityPosition( String cityKey ) {
        return path.get(cityKey);
    }

    public int getPathCityCnt() {
        return pathCityCnt;
    }

    public int[] getCityIndex() {
        return cityIndex;
    }

    public int getSpecificIndex( int index ) {
        return cityIndex[index];
    }


}
