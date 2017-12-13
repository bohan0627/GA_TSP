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
    public  static int Chromo_Size = 80;
    private double fitness = 0.0;

    /**
     * default constructor
     */
    public Individual( ) {
        individual = new ArrayList<Chromosome>(); 
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
   
    public Individual( ArrayList<Chromosome> in, CityNode[] myCity ) {
        Chromo_Size = in.size();
        individual = new ArrayList<Chromosome>(in); 
        updateFitness(myCity);
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
    public void mutation( int index1, int index2, CityNode[] city ) {
        Chromosome ch1 = individual.get(index1);
        Chromosome ch2 = individual.get(index2);

        individual.set(index1, ch2);
        individual.set(index2, ch1);
        updateFitness(city);
    }

    /**
     * recalculate Fitness
     */
    public void updateFitness(CityNode[] myCity ) {
        int[] cityIndex = new int[Chromo_Size];
        for( int i=0; i<Chromo_Size; i++ ) {
            cityIndex[i] = decodeChromo(i);
        }

        CityNode[] city = new CityNode[Chromo_Size];
        for( int i=0; i<Chromo_Size; i++ ) {
            city[i] = myCity[cityIndex[i]] ;
        }
        Path path = new Path(city);
        fitness = path.getFitness();
    }

    public double getFitness( boolean Update, CityNode[] city ) {
        if( Update ) {
            updateFitness(city);
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
    
    public void addChromo( Chromosome ch ) {
        individual.add(ch);
    } 

    public void setChrom(Chromosome chrom, int position)
    {
        individual.set(position,chrom);
    }

    public boolean containsChrom(Chromosome chrom)
    {
        return individual.contains(chrom);
    }
    
    public boolean compareTo( Individual that ) {
        if( this.individual.size() != that.individual.size() )
            return false;
        for( int i=0; i<individual.size(); i++ ) {
            if( individual.get(i).compareTo(that.getIndividual().get(i)) != true )
                return false;
        }
        return true;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String str = "fitness: " + fitness + "\n";
        sb.append(str);
        String str1 = "distance: " + 1/fitness +"\n";
        sb.append(str1);
        sb.append("path: ");

        for( int i=0; i<individual.size(); i++ ) {
            Chromosome ch = individual.get(i);
            Integer num = ch.decode();
            sb.append( num.toString() + " " );
           
        }
        return sb.toString();
    }
}
