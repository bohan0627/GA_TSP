package edu.neu.info6205.finalproj.num09.GA;

//import static edu.neu.info6205.finalproj.num09.GA.TSP_GA.log;
import edu.neu.info6205.finalproj.num09.Graph.CityNode;
import edu.neu.info6205.finalproj.num09.Graph.Path;

import java.util.ArrayList;
import java.util.logging.Logger;



/**
 * @author  Junyuan GU
 * @NUID    001825583
 */
public class Individual {
    private static Logger log = Logger.getLogger(Individual.class.getName());
    private ArrayList<Chromosome> individual = new ArrayList<Chromosome>();
    public  static int Chromo_Size = 100;
    private double fitness = 0.0;

    /**
     * default constructor
     */
    public Individual( ) {
        
    }
    
    /**
     * Overloading constructor with Parameter:Path
     * @param path
     */
    public Individual( Path path ) {
        Chromo_Size =path.getPathCityCnt();
        individual = new ArrayList<>(Chromo_Size);
        
        for( int i=0; i<Chromo_Size; i++ ) {
            Chromosome nodeChromo = new Chromosome();
            nodeChromo.encode( path.getSpecificIndex(i) );
            individual.add(nodeChromo);
        }
        fitness = path.getFitness();
    }
   

    public ArrayList<Chromosome> getIndividual() {
        return individual;
    }

    public int decodeChromo( int ChromoIndex ) {
        int cityIndex = 0;
        Chromosome nodeChromo = individual.get( ChromoIndex );
        cityIndex = nodeChromo.decode();
        return cityIndex;
    }

    public int[] getCityIndex() {
        int[] cityIndex = new int[Chromo_Size];
        for( int i=0; i<Chromo_Size; i++ ) {
            cityIndex[i] = decodeChromo(i);
        }
        return cityIndex;
    }

    /**
     * exchange the index1 and index2 of individual and updateFitness();
     */
    public void mutation( int index1, int index2 ) {
        Chromosome ch1 = individual.get(index1);
        Chromosome ch2 = individual.get(index2);

        individual.set(index1, ch2);
        individual.set(index2, ch1);
        updateFitness();
    }

    /**
     * recalculate Fitness
     */
    public void updateFitness() {
        int[] cityIndex = new int[Chromo_Size];
        for( int i=0; i<Chromo_Size; i++ ) {
            cityIndex[i] = decodeChromo(i);
        }

        CityNode[] city = new CityNode[Chromo_Size];
        for( int i=0; i<Chromo_Size; i++ ) {
            city[i] = TSP_GA.myCity[cityIndex[0]];
        }
        Path path = new Path(city);
        fitness = path.getFitness();
    }

    public double getFitness( boolean Update ) {
        if( Update ) {
            updateFitness();
        }
        return fitness;
    }


    public double getFitness() {
        return fitness;
    }

    public Chromosome getChrom(int position)
    {
        return individual.get(position);
    }

    public void setChrom(Chromosome chrom, int position)
    {
        individual.set(position,chrom);
    }

    public boolean containsChrom(Chromosome chrom)
    {
        return individual.contains(chrom);
    }
}
