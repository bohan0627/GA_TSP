package edu.neu.info6205.finalproj.num09.GA;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
        /*
         * Individual refers to the path,
         * Chromosome refers to the CityNode
         */

public class GA {

    private static Logger log = Logger.getLogger(GA.class.getName());
    public static final double mutateFactorMin = 0.251;
    public static final double mutateFactorMax = 0.354;

    // private static final double mutationRate = 0.015;

    // crossover probability
    private static double cP = 0.75;
    // mutation probability
    private static double mP = 0.12;
    // max generation
    private int maxGene;
    // population scale
    private int scale;
    // size of Chromosome, also the number of cities
    private int nChrom;

    // total probability of individual in population
    private static double[] iP;
    // selection  probability
    private static double[] sP;

    private int[] bestPath;
    private int bestLength;

    private int bestGeneration;
    private int currentGeneration;
    // each elements represents the individual's fitness
    private static double[] fitness;

    private static Random random = new Random();


    public GA( double cP, double mP, int maxGene) {
        this.cP = cP;
        this.mP = mP;
        this.maxGene = maxGene;

    }

    public  static Population evolvePopulation(Population pop) {


        // only allocate memory, not to initialize
        //int size = pop.getPopulation().size();

        //select 70% individuals of the Population according to the larger fitness(sort fitness and then select)
        double cutSize = pop.getPopSize()*(0.7);

        Population nPopulation = new Population(pop.selGoodIndividual((int)cutSize),(int)cutSize);

        // get new generation after selection
        select(nPopulation);

        double ranNum;
        int remainNum = nPopulation.getPopSize();
        log.info( "After selection, there are "+ remainNum + " remaining individual." );
        
        ArrayList<Individual> currentIndividuals = nPopulation.getPopulation();
        for(int i=0;i<nPopulation.getPopulation().size()-1; )
        {
            ranNum = random.nextDouble();
            if(ranNum < cP)
            {
                Individual child = crossover(currentIndividuals.get(i),currentIndividuals.get(i+1));
                currentIndividuals.add(child);
            }
            i=i+2;
        }

        for(int i=0;i<nPopulation.getPopulation().size();i++)
        {
            mutate(currentIndividuals.get(i));
        }

        return nPopulation;
    }

    public static Population select(Population pop)
    {
        log.info("select begin...");
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

        log.info( "select end." );
        return newGene;
    }

    public static Individual crossover(Individual father, Individual mother) {
        log.info("crossover begin...");

        Individual offSpring = new Individual();


        int startPos = (int) (Math.random() * father.getIndividual().size());
        int endPos = (int) (Math.random() * father.getIndividual().size());


        for (int i = 0; i < father.getIndividual().size(); i++) {

            if (startPos < endPos && i > startPos && i < endPos) {
                offSpring.setChrom(father.getChrom(i),i);
            }
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    offSpring.setChrom(father.getChrom(i),i);
                }
            }
        }


        for (int i = 0; i < mother.getIndividual().size(); i++) {

            if (!offSpring.containsChrom(mother.getChrom(i))) {

                for (int ii = 0; ii < offSpring.getIndividual().size(); ii++) {

                    if (offSpring.getChrom(ii) == null) {
                        offSpring.setChrom(mother.getChrom(i),ii);
                        break;
                    }
                }
            }
        }
        offSpring.getFitness(true);
        log.info("crossover end.");
        return offSpring;
    }

    private static void mutate(Individual child) {
        log.info("mutate end.");
        int fPosition = 0;
        int sPosition;
        for(; fPosition < child.getIndividual().size(); fPosition++){

            if(Math.random() < mP){

                sPosition = (int) (child.getIndividual().size() * Math.random());

                child.mutation(fPosition,sPosition);

                /*
                Chromosome c1 = child.getChrom(fPosition);
                Chromosome c2 = child.getChrom(sPosition);


                child.setChrom(sPosition, c1);
                child.setChrom(fPosition, c2);
                */
            }
        }
        log.info("mutate end.");
    }

}