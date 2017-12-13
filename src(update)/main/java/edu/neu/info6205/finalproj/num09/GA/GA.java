package edu.neu.info6205.finalproj.num09.GA;

import edu.neu.info6205.finalproj.num09.Graph.CityNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author  Bo Han
 * @NUID    001815357
 */

/*
 * Individual refers to the path,
 * Chromosome refers to the CityNode
 */

public class GA {

    private static Logger log = Logger.getLogger(GA.class.getName());
    public static final double mutateFactorMin = 0.251;
    public static final double mutateFactorMax = 0.354;

    // crossover probability
    public static double cP = 0.85;
    // mutation probability
    public static double mP = 0.12;
    // max generation
    private int maxGene;
    // total probability of individual in population
    private static double[] iP;
    // selection  probability
    private static double[] sP;

    // each elements represents the individual's fitness
    private static double[] fitness;

    private static Random random = new Random();


    public GA( double cP, double mP, int maxGene) {
        this.cP = cP;
        this.mP = mP;
        this.maxGene = maxGene;

    }
    public  static Population evolvePopulation( Population pop, CityNode[] city ) {
        //select 70% individuals of the Population according to the larger fitness(sort fitness and then select)
        double cutSize = pop.getPopSize()*(0.7);

        Population nPopulation = new Population(pop.selGoodIndividual((int)cutSize),(int)cutSize);

        // get new generation after selection
        select(nPopulation);

        double ranNum;
        int remainNum = nPopulation.getPopSize();
        //log.info( "After selection, there are "+ remainNum + " remaining individual." );
        
        ArrayList<Individual> currentIndividuals = nPopulation.getPopulation();
        
        for(int i=0;i<nPopulation.getPopulation().size()-1; )
        {
            ranNum = random.nextDouble();
            if(ranNum < cP)
            {
                //Individual child = GA.crossover(currentIndividuals.get(i),currentIndividuals.get(i+1), myCity);
                //currentIndividuals.add(child);
            }
            i=i+2;
        }
        
        for(int i=0;i<nPopulation.getPopulation().size();i++)
        {
            mutate(currentIndividuals.get(i), city);
        }

        return nPopulation;
    }

    public static Population select(Population pop)
    {
        //log.info("select begin...");
        ArrayList<Individual> currentIndividuals = pop.getPopulation();
        Population newGene = new Population(currentIndividuals.size());
        ArrayList<Individual> newIndividuals = newGene.getPopulation();

        double totalFitness = 0.0;
        iP = new double[currentIndividuals.size()];
        fitness = new double[currentIndividuals.size()];
        sP = new double[currentIndividuals.size()];
        for(int i=0;i<currentIndividuals.size();i++) {
            fitness[i] = currentIndividuals.get(i).getFitness();
            totalFitness += fitness[i];
        }
        for(int i=0;i<currentIndividuals.size();i++)
        {
            sP[i] = fitness[i] / totalFitness;
            if(i ==0)
                iP[i] = sP[i];
            else
                iP[i] = iP[i-1] + sP[i];
        }

        double randomNum;

        for(int j= 0;j <currentIndividuals.size();j++)
        {
            randomNum = random.nextDouble()*0.02;
            if(randomNum<iP[0])
                newIndividuals.add(currentIndividuals.get(0));
            else if(randomNum <=iP[j] && randomNum>iP[j-1] )
                newIndividuals.add(currentIndividuals.get(j));
        }

        //log.info( "select end." );
        return newGene;
    }

    public static Individual crossover(Individual father, Individual mother, CityNode[] city) {
        //log.info("crossover begin...");
        //generate the start and end position randomly      
        int startPos = random.nextInt(father.getIndividual().size()-1)+1;
        int endPos = random.nextInt(father.getIndividual().size()-1)+1;
        
        ArrayList<Chromosome> offIndividual;
        ArrayList<Chromosome> temp1Individual;
        ArrayList<Chromosome> temp2Individual;        
        offIndividual = new ArrayList<Chromosome>(mother.getIndividual());
        temp1Individual = new ArrayList<Chromosome>(mother.getIndividual());
        temp2Individual = new ArrayList<Chromosome>(father.getIndividual());
        ArrayList<Chromosome> fatherIndividual = father.getIndividual();
 
        if(endPos<startPos)
        {
            int temp=endPos;
            endPos=startPos;
            startPos=temp;
        }
        
        int j =0;
        if(startPos!=endPos)
        {
          
            for(int i= endPos-1;i>=startPos-1;i--)
            {
                temp1Individual.remove(i);
                temp2Individual.remove(i);           
            }

            for(int i=startPos-1;i<endPos;i++)
            { 
                if(!temp1Individual.contains(fatherIndividual.get(i)))
                {
                    offIndividual.set(i, fatherIndividual.get(i));
                }
                else
                { 

                    while(temp1Individual.contains(temp2Individual.get(j)))
                    {
                        j++;
                    }
                    offIndividual.set(i, temp2Individual.get(j));
                    j++;
                }
            }
        }
        
        //check whether there have duplicate Chronmosomes
        HashSet<Chromosome> set1 = new HashSet<Chromosome>();
        HashSet<Integer> set2 = new HashSet<Integer>();
        for( Chromosome ch:offIndividual ){
            //System.out.println( ch.toStrig() );
            //System.out.print( ch.decode() + ", " );   
            if( !set1.add( ch ) ) 
                System.out.println( "----------Chromosome Duplicate." );

            if( !set2.add(ch.decode()) )
                System.out.println( "----------Decode result Duplicate." );
            
        }
        
        Individual offSpring = new Individual( offIndividual, city );
        return offSpring;

    }

    public static void mutate(Individual child, CityNode[] city ) {
        //log.info("mutate end.");
        int fPosition = 0;
        int sPosition;
        for(; fPosition < child.getIndividual().size(); fPosition++){

            if(Math.random() < mP){

                sPosition = (int) (child.getIndividual().size() * Math.random());

                child.mutation(fPosition, sPosition, city );

            }
        }
        //log.info("mutate end.");
    }

}