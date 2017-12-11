package edu.neu.info6205.finalproj.num09.GA;

import edu.neu.info6205.finalproj.num09.Graph.CityNode;
import edu.neu.info6205.finalproj.num09.Graph.Path;
import edu.neu.info6205.finalproj.num09.Graph.Position;
import edu.neu.info6205.finalproj.num09.log.LogGlobe;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class TSP_GA {
    private static Logger log = Logger.getLogger(TSP_GA.class.getName());
    public static FileReader fRead;
    public static BufferedReader bufReader = null;

    public static int testNum = 100;
    public static Position[] positions;
    public static CityNode[] myCity;
    public static Path[] paths;
    public static Individual[] individuals;
    public static int popSize = 100;

    /**
     * default constructor
     */
    public TSP_GA() {

    }

    /*
    public static void main(String[] args) {
        TSP_GA tsp = new TSP_GA();

        tsp.init();

        Population pop = new Population(individuals, popSize);

        // iterate number
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }
    }*/

    public void init() {
        log.info( "init citynode, path/individual and population.");
        positions = new Position[testNum];
        assignCoor(positions);
        if( isPosDuplicate(positions) ) {
            log.info( "City Postions file error: duplicate city position." );
            System.exit(-1);
        }

        CityNode[] myCity = new CityNode[testNum];
        for (int i = 0; i < testNum; i++) {
            myCity[i] = new CityNode(i, positions[i]);
        }
        
        paths = new Path[testNum];
        individuals = new Individual[testNum];
        
        for (int i = 0; i < testNum; i++) {
            Path newPath = Path.createNewPath(myCity);
            individuals[i] = new Individual(newPath);
        }
        
        log.info( "init done." );

    }


    public void assignCoor(Position[] positions) {
        //get the directory of Position file
        String root = System.getProperty("user.dir");
        String filename = root + File.separator + "coor.txt";
        
        //open the file
        try{
            bufReader = new BufferedReader( new FileReader(filename) );
            
            String inLine = null;
            inLine = bufReader.readLine();
            for (int i = 0; i < positions.length; i++) {

                String[] items = inLine.split(",");
                int x = Integer.parseInt(items[0]);
                int y = Integer.parseInt(items[1]);
                positions[i] = new Position( x, y );
                inLine = bufReader.readLine();
            }
            
        }catch( FileNotFoundException e ) {
            System.out.println( filename + " is not found! Please input a correct path." );
            log.severe(filename+ " is not found!");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufReader != null)
                    bufReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
    public boolean isPosDuplicate( Position[] positions ) {
        boolean dup = false;
        int size = positions.length;
        for( int i=0; i<size; i++ ) {
            for( int j=i+1; j<size; j++ ) {
                dup |= positions[i].compareTo(positions[j]);
            }
        }
        return dup;
    }
}