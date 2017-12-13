/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.neu.info6205.finalproj.num09.GA;

//import static edu.neu.info6205.finalproj.num09.GA.TSP_GA.log;
import static edu.neu.info6205.finalproj.num09.GA.GA.select;
import edu.neu.info6205.finalproj.num09.Graph.CityNode;
import edu.neu.info6205.finalproj.num09.Sort.MergeSort;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author  JunyuanGu, partner: Bo Han
 * @NUID    001825583,          001815357
 */
public class TSP_GATest {
    public static Logger log = Logger.getLogger(TSP_GATest.class.getName());
    public static CityNode[] myCity = new CityNode[TSP_GA.nodeNum];
    public static Population nextPop = new Population();
    
    @Test
    public void test1_CityNodeInit() {
        
        //Test the init of CityNode
        log.info("start test1_CityNodeInit");
        TSP_GA tsp = new TSP_GA();

        tsp.init(myCity);
        boolean test = true;
        
        //check if the total number of city is legal
        if( myCity.length!=TSP_GA.nodeNum )
            test = false;
        for( int i=0; i<TSP_GA.nodeNum; i++ ) {
            if(myCity[i]==null) {
                test = false;
                break;
            }
        }
        //check if city number is duplicate
        HashMap<String,Integer> cityHMap = new HashMap<String,Integer>();
        for( int i=0; i<TSP_GA.nodeNum; i++ ){
            cityHMap.put( myCity[i].getCityKey(), myCity[i].getCityNum());
        }
        if( cityHMap.size()!=TSP_GA.nodeNum )
            test = false;
        //check if city number is legal
        for( int i=0; i<TSP_GA.nodeNum; i++ ) {
            int cityNum = myCity[i].getCityNum();
            if( cityNum>TSP_GA.nodeNum || cityNum<0 ) {
                test = false;
                break;
            }
        }
        assertEquals(test, true);
        log.info("test1 done.");
    }
    
    @Test
    public void test2_PopulationInit() {
        log.info("start test2_PopulationInit");
        boolean test = true;
        
        TSP_GA tsp = new TSP_GA();
        tsp.init(myCity);
        
        Population pop = new Population(TSP_GA.individuals, TSP_GA.popSize);
        //check if null
        for( int i=0; i<TSP_GA.popSize; i++ ) {
            if( pop.getPopulation().get(i)==null ) {
                test = false;
                break;
            }
        }
        
        //check if population is duplicate
        for( int i=0; i<TSP_GA.popSize; i++ ) {
            for( int j=i+1; j<TSP_GA.popSize; j++ ) {
                Individual in = pop.getPopulationIndividual(i);
                if(in.compareTo(pop.getPopulationIndividual(j)) == true ) {
                    test = false;
                    break;
                }
            }
        }
        
        assertEquals(test, true);
        System.out.println("test2 done.");
    }
    
    @Test
    public void test3_Selection() {
        boolean test = true;
        TSP_GA tsp = new TSP_GA();
        log.info( "Population size is " + TSP_GA.popSize );
        log.info( "Number of CityNode is " + TSP_GA.nodeNum );
        tsp.init(myCity);
        Population pop = new Population(TSP_GA.individuals, TSP_GA.popSize);
        
         //select 70% individuals of the Population according to the larger fitness(sort fitness and then select)
        double cutSize = pop.getPopSize()*(0.7);

        Population nPopulation = new Population(pop.selGoodIndividual((int)cutSize),(int)cutSize);

        // get new generation after selection
        select(nPopulation);
        
        int size = nPopulation.getPopulation().size();
        //check if null
        for( int i=0; i<size; i++ ) {
            if( nPopulation.getPopulation().get(i)==null ) {
                test = false;
                break;
            }
        }
        
        //check whether has duplicate Individuals
        HashSet<Individual> set1 = new HashSet<Individual>();
        for( Individual ch:nPopulation.getPopulation() )
        {
            if( !set1.add( ch ) ) 
            {   
                test = false;
                System.out.println( "----------Individual Duplicate." );
            }
        }
        
        assertEquals(test, true);
        System.out.println("test3 done.");
    }
    
    @Test
    public void TestTSP_GA(){
        Random random = new Random();
        log.info("start TSP_GATest");
        TSP_GA tsp = new TSP_GA();
        log.info( "Population size is " + TSP_GA.popSize );
        tsp.init(myCity);
        Population pop = new Population(TSP_GA.individuals, TSP_GA.popSize);
        
        //the first generation
        double cutSize = pop.getPopSize()*(0.7);

        Population nPopulation = new Population(pop.selGoodIndividual( (int)cutSize ), (int)cutSize );
        // get new generation after selection
        select(nPopulation);
 
        double ranNum;
        int remainNum = nPopulation.getPopSize();
        log.info( "After selection, there are "+ remainNum + " remaining individual." );

        ArrayList<Individual> currentIndividuals = new ArrayList<Individual>((int)cutSize);
        ArrayList<Individual> nextIndividuals = new ArrayList<Individual>((int)cutSize);

        for( int x=0; x<nPopulation.getPopulation().size(); x++ ) {
            currentIndividuals.add(nPopulation.getPopulationIndividual(x));
            nextIndividuals.add(nPopulation.getPopulationIndividual(x));
        }
        /*
         * If ranNum fits the cP, then use the adjacent two parents to crossover
        */
        for(int i=0; i<nPopulation.getPopulation().size()-1;  )
        {
            ranNum = random.nextDouble();
            if(ranNum < GA.cP)
            {
                Individual child = GA.crossover(currentIndividuals.get(i),currentIndividuals.get(i+1), myCity);
                nextIndividuals.add(child);
            }
            i=i+2;
        }
 
        for(int j=0;j<nPopulation.getPopulation().size();j++)
        {
            GA.mutate(nextIndividuals.get(j), myCity);
        }

        nextPop = new Population(nextIndividuals);
        //the first round end
        
        // 1-999round, total iteration number = 1000
        for (int k = 1; k < 1000; k++) {
            cutSize = nextPop.getPopSize()*(0.7);
            
            nPopulation = new Population(nextPop.selGoodIndividual((int)cutSize),(int)cutSize);
            // get new generation after selection
             select(nPopulation);
            
            remainNum = nPopulation.getPopSize();
            //log.info( "After selection, there are "+ remainNum + " remaining individual." );

            currentIndividuals = new ArrayList<Individual>();
            nextIndividuals = new ArrayList<Individual>();
            
            for( int x=0; x<nPopulation.getPopulation().size(); x++ ) {
                currentIndividuals.add(nPopulation.getPopulationIndividual(x));
                nextIndividuals.add(nPopulation.getPopulationIndividual(x));
            }
            for(int i=0; i<nPopulation.getPopulation().size()-1;  )
            {
                ranNum = random.nextDouble();
                if(ranNum < GA.cP)
                {
                    Individual child = GA.crossover(currentIndividuals.get(i),currentIndividuals.get(i+1), myCity);
                    nextIndividuals.add(child);
                }
                i=i+2;
            }
 
            for(int j=0;j<nPopulation.getPopulation().size();j++)
            {
                GA.mutate(nextIndividuals.get(j), myCity);
            }
            nextPop = new Population(nextIndividuals);
            if( nextPop.getPopulation().size()<5 ){
                log.info( "Terminate, there are "+ remainNum + " remaining individual." );
                log.info("Total generations: " + k);
                log.info("Current cP : " + GA.cP+ ", Current mP : "+GA.mP);
                break;
            }
            
            //System.out.println("This is " + k);
        }
        
        //check whether has duplicate Chromosomes
        HashSet<Chromosome> set1 = new HashSet<Chromosome>();
        HashSet<Integer> set2 = new HashSet<Integer>();
        for( Chromosome ch:nextPop.getPopulation().get(0).getIndividual() ){
            //System.out.println( ch.toStrig() );
            //System.out.print( ch.decode() + ", " );   
            if( !set1.add( ch ) ) 
                System.out.println( "----------Chromosome Duplicate." );

            if( !set2.add(ch.decode()) )
                System.out.println( "----------Decode result Duplicate." );
        } 
        log.info( "There are not any kinds of duplicates" );
        
        int size = nextPop.getPopulation().size();
        double[] fitness = new double[size];
        Double[] Dfit = new Double[size];
        for( int i=0; i<nextPop.getPopulation().size(); i++ ) {
            fitness[i]= nextPop.getPopulation().get(i).getFitness();
            Dfit[i] = fitness[i];
        }
        
        HashMap<Double,Individual> inMap = new HashMap<Double,Individual>(  );
        for( int i=0; i<size; i++ )
            inMap.put( Dfit[i], nextPop.getPopulation().get(i) );
        
        for( int i=0; i<nextPop.getPopulation().size(); i++ )
            fitness[i]= nextPop.getPopulation().get(i).getFitness();
        
        
        //Sort fitness[];
        MergeSort ms = new MergeSort(Dfit);
        ms.sort( Dfit, 0, Dfit.length-1 );
        Individual goodIndv= inMap.get( Dfit[size-1] );
        
        System.out.println("The best case: ");
        System.out.println( goodIndv );
        //Write log to the file
        
        log.info("End of TSP_GATest");
    }   
}
