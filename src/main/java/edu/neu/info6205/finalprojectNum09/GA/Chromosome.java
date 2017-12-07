package edu.neu.info6205.finalprojectNum09.GA;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by callouswander on 2017/12/6.
 */
public class Chromosome {
    public static int CHROMO_LENGTH = 4;
    private Gene[] genetype = new Gene[CHROMO_LENGTH];
    private String[] gtSplitX = new String[CHROMO_LENGTH];
    private String[] gtSplitY = new String[CHROMO_LENGTH];
    //private double

    /**
     * default constructor: generate random chromosome
     */
    public Chromosome() {
        Random rand = new Random(19);
        for( int i=0; i<CHROMO_LENGTH; i++ ) {
            int val = rand.nextInt(16);
            genetype[i] = new Gene( DNA.getKey(val) );
        }
    }

    public Chromosome( Gene[] genetype ) {
        // check if argument's length is correct
        if( CHROMO_LENGTH != genetype.length ) {
            genetype = null;
            return;
        }
        this.genetype = genetype;
        for( int i=0; i<CHROMO_LENGTH; i++ ){
            gtSplitX[i] = genetype[i].getGene_x();
            gtSplitY[i] = genetype[i].getGene_y();
        }
    }

    /**
     * generation Gene randomly
     * @return
     */
    public Gene nextRandomGene() {
        Random rand = new Random(31);
        int val = rand.nextInt(16); //integers belongs to [0,16)
        String key = DNA.getKey(val);

        return new Gene(key);
    }

    /**
     * Probably generate 4 kinds of chromosome for children
     * @param father
     * @param mother
     * @return
     */
    public ArrayList<Chromosome> crossover(Chromosome father, Chromosome mother ) {

    }




}
