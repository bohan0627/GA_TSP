package edu.neu.info6205.finalproj.num09.GA;

import edu.neu.info6205.finalproj.num09.Graph.CityNode;
import edu.neu.info6205.finalproj.num09.Graph.Path;
import edu.neu.info6205.finalproj.num09.Graph.Position;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author  Junyuan GU
 * @NUID    001825583
 */
public class TSP_GA {
    private static Logger log = Logger.getLogger(TSP_GA.class.getName());
    public static FileReader fRead;
    public static BufferedReader bufReader = null;

    public static int nodeNum = 20;
    public static int popSize = 200;
    public static Position[] positions = new Position[nodeNum];
    public static Path[] paths = new Path[popSize];
    public static Individual[] individuals = new Individual[popSize];
    

    /**
     * default constructor
     */
    public TSP_GA() {

    }

    public void init(CityNode[] myCity) {
        //log.info( "init citynode, path/individual and population.");
        positions = new Position[nodeNum];
        assignCoor(positions);
        if( isPosDuplicate(positions) ) {
            log.info( "City Postions file error: duplicate city position." );
            System.exit(-1);
        }

        for(int i = 0; i < nodeNum; i++) {
            myCity[i] = new CityNode(i, positions[i]);
        }
        
        paths = new Path[popSize];
        individuals = new Individual[popSize];
        
        for (int i = 0; i < popSize; i++) {
            Path newPath = Path.createNewPath(myCity);
            individuals[i] = new Individual(newPath);
        }
        
        //log.info( "init done." );

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