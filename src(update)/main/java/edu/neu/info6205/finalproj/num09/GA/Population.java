package edu.neu.info6205.finalproj.num09.GA;

//import static edu.neu.info6205.finalproj.num09.GA.TSP_GA.log;
import edu.neu.info6205.finalproj.num09.Graph.CityNode;
import edu.neu.info6205.finalproj.num09.Graph.Path;
import edu.neu.info6205.finalproj.num09.Sort.MergeSort;
import edu.neu.info6205.finalproj.num09.Sort.QuickSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author  Bo Han  &   Junyuan Gu
 * @NUID    001815357 & 001825583
 */
public class Population {
    private static Logger log = Logger.getLogger(Population.class.getName());
    //Holds population of paths
    private ArrayList<Individual> population;
    private int popSize = 100;

    private double[] fitness;

    private double aveFitness = 0.0;
    
    public Population() {
        population = new ArrayList<>();
    }

    /**
     * constructor
     * @param in
     * @param popSize
     */
    public Population( Individual[] in, int popSize ) {
        if( popSize!=in.length ) {
            log.info( "popSize is unequal to city count of the path." );
            return;
        }
        this.popSize = popSize;
        population = new ArrayList<Individual>( popSize );
        fitness = new double[popSize];

        for( int i=0; i<popSize; i++ ) {
            population.add(in[i]);
            fitness[i] = in[i].getFitness();
        }

    }

    public Population( int popSize ) {
        population = new ArrayList<Individual>( popSize );
        fitness = new double[popSize];
    }
    
    public Population( ArrayList<Individual> pop ) {
        popSize = pop.size();
        population = new ArrayList<>();
        fitness = new double[popSize];
        
        for( int i=0; i<popSize; i++ ) {
            population.add( pop.get(i) );
            fitness[i] = pop.get(i).getFitness();
        }
        calcAveFitness();
    }

    // generate a random population
    public static void randomPopulation( CityNode[] city, boolean initialize ) {
        if (initialize) {
            int popSize = city.length;
            // Loop and create every path
            for (int i = 0; i < popSize; i++) {
                Path newPath = new Path();
                newPath.createNewPath(city);
            }
        }
    }


    public ArrayList<Individual> getPopulation() {
        return population;
    }

     public Individual getPopulationIndividual(int index) {
        return population.get(index);
    }
    /**
     * calculate the average fitness
     */
    public void calcAveFitness() {
        if( fitness==null || fitness.length!=popSize ) {
            log.info( "Population didnot complete Initialization, cannot calcAveFitness." );
            return;
        }
        double sum = 0.0;
        for( int i=0; i<popSize; i++ ) {
            sum += fitness[i];
        }
        aveFitness = sum/popSize;
    }

    // Gets the best path in this population
    public int getAboveFitnessNum() {
        int counter = 0;
        if( aveFitness == 0.0 )
            calcAveFitness();
        // loop to find the best
        for (int i = 1; i < popSize; i++) {
            if ( fitness[i]>aveFitness )
                counter++;
        }
        return counter;
    }

    /**
     * Overloading getAboveFitnessNum
     * @param factor
     * @return
     */
    public int getAboveFitnessNum(double factor) {
        int counter = 0;
        if( aveFitness == 0.0 )
            calcAveFitness();
        double std = aveFitness * factor;
        // loop to find the best
        for (int i = 1; i < popSize; i++) {
            if ( fitness[i]>=std )
                counter++;
        }
        return counter;
    }

    /**
     * select the Individual whose fitness is no less than factor*aveFitness;
     * @param factor the percentage of Above Average Fitness
     * @return
     */
    public Individual[] getGoodFitness( double factor ) {
        int size = getAboveFitnessNum(factor);
        Individual[] goodIndv = new Individual[size];
        double stdFitness = aveFitness * factor;
        int j=0;
        for( int i=0; i<popSize; i++ ) {
            if( fitness[i]>=stdFitness )
                goodIndv[j++] = population.get(i);
        }
        return goodIndv;
    }

    /**
     * calculate the cumulative probability of  TopNum good fitness.
     * @param TopNum
     * @return
     */
    public double calcCumulProb( int TopNum ) {
        //Sort fitness[];
        int size = fitness.length;
        Double[] Dfit = new Double[size];
        for( int i=0; i<size; i++ )
            Dfit[i] = fitness[i];
        QuickSort.qSort(Dfit);
        //calculate the top Num of fitness[]
        int index = size-1;
        double cumulativeProb = 0.0;
        do{
            cumulativeProb += Dfit[index];
            index--;
            TopNum--;
        }while(TopNum>0);

        return cumulativeProb;
    }

    /**
     * select TopNum size of good individual.
     * @param TopNum
     * @return
     */
    public Individual[] selGoodIndividual( int TopNum ) {
        int size = fitness.length;
        Double[] Dfit = new Double[size];
        for( int i=0; i<size; i++ )
            Dfit[i] = fitness[i];
        HashMap<Double,Individual> inMap = new HashMap<Double,Individual>( TopNum );
        for( int i=0; i<size; i++ )
            inMap.put( Dfit[i], population.get(i) );

        //Sort fitness[];
        MergeSort ms = new MergeSort(Dfit);
        ms.sort( Dfit, 0, Dfit.length-1 );
        Individual[] goodIndv= new Individual[TopNum];
        int index = size-1;
        int i=0;
        do{
            goodIndv[i++] = inMap.get( Dfit[index] );
            index--;
            TopNum--;
        }while(TopNum>0);

        return goodIndv;
    }
    /**
     * get the size of Population
     * @return
     */
    public int getPopSize() {
        return popSize;
    }
}