package edu.neu.info6205.finalproj.num09.GA;


import edu.neu.info6205.finalproj.num09.Graph.CityNode;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author  Junyuan GU
 * @NUID    001825583
 */
public class Chromosome {
    public static int CHROMO_LENGTH = 4;
    private Gene[] genotype = new Gene[CHROMO_LENGTH];
    private String[] gtSplitX = new String[CHROMO_LENGTH];
    private String[] gtSplitY = new String[CHROMO_LENGTH];
    private int fitness = 0;

    private Random rand = new Random(31);
    //private double

    /**
     * default constructor: generate random chromosome
     */
    public Chromosome() {
        CHROMO_LENGTH = 4;
    }

    public Chromosome(Gene[] genotype) {
        // check if argument's length is correct
        if( CHROMO_LENGTH != genotype.length ) {
            genotype = null;
            return;
        }
        this.genotype = genotype;
        for( int i=0; i<CHROMO_LENGTH; i++ ){
            gtSplitX[i] = genotype[i].getGene_x();
            gtSplitY[i] = genotype[i].getGene_y();
        }
        //fitness = 0;
        calcFitness();
    }


    public static Chromosome clone(Chromosome in ) {
        if (in == null || in.getGenotype() == null) {
            return null;
        }
        Chromosome copy = new Chromosome();
        int size  = in.getGenotype().length;
        copy.initGenotype(size);
        for (int i = 0; i < size; i++) {
            copy.setGenotype( i, in.getGenotype(i) );
        }
        return copy;
    }

    /**
     * generation Gene randomly
     * @return
     */
    public Gene nextRandomGene() {
        Random rd = new Random(rand.nextInt());
        int val = rd.nextInt(16); //integers belongs to [0,16)
        String key = DNA.getKey(val);

        return new Gene(key);
    }

    /**
     * get the sequential Xs of genotype
     * @return
     */
    public String[] getGtSplitX() {
        if( genotype !=null ) {
            for (int i = 0; i < CHROMO_LENGTH; i++)
                gtSplitX[i] = genotype[i].getGene_x();
        }
        return gtSplitX;
    }

    /**
     * get the sequential Ys of genotype
     * @return
     */
    public String[] getGtSplitY() {
        if( genotype !=null ) {
            for (int i = 0; i < CHROMO_LENGTH; i++)
                gtSplitY[i] = genotype[i].getGene_y();
        }
        return gtSplitY;
    }

    public Gene[] getGenotype() {
        return genotype;
    }

    public Gene getGenotype(int index) {
        return genotype[index];
    }

    public void initGenotype(int size) {
        this.genotype = new Gene[size];
    }

    /**
     * genotype[index] = g
     * @param index
     * @param g
     */
    public void setGenotype( int index, Gene g ) {
        if( index<0 || index>CHROMO_LENGTH  )
            return;
        genotype[index] = g;
    }

    /**
     * genotype mutates according to mutateFactor
     */
    public void mutation() {
        Random rd = new Random(rand.nextInt());
        int mutatePosCnt = 0;
        //int pos = rd.nextInt(CHROMO_LENGTH);
        boolean isX = rd.nextBoolean();
        double isMutate = 0.0;

        for( int i=0; i<CHROMO_LENGTH; i++ ) {
            isMutate = Math.random();
            if( isMutate<=GA.mutateFactorMax && isMutate>=GA.mutateFactorMin ) {
                mutatePosCnt++;
                if( isX ) {
                    String nextAlleleX = mutateAllele(genotype[i].getGene_x());
                    genotype[i].setGene_x(nextAlleleX);
                }
                else {
                    String nextAlleleY = mutateAllele(genotype[i].getGene_y());
                    genotype[i].setGene_y(nextAlleleY);
                }
                isX = rd.nextBoolean();
            }
        }
    }

    public String mutateAllele( String currentAllele ) {
        Random rd = new Random(rand.nextInt());
        int alter = rd.nextInt(4);
        String alterAllele = DNA.getKey( 20+alter );

        while( currentAllele.compareTo(alterAllele)==0 )
        {
            alter = ( rd.nextInt(10) + Integer.parseInt(currentAllele, 2) )%4;
            alterAllele = DNA.getKey( 20+alter );
        }
        return alterAllele;
    }

    /**
     * calculate fitness
     */
    public void calcFitness() {
        fitness = 0;
        for (int i = 0; i < CHROMO_LENGTH; i++) {
            if ( genotype[i].compareTo(DNA.AA.getKey())==0
                    || genotype[i].compareTo(DNA.CC.getKey())==0
                    || genotype[i].compareTo(DNA.GG.getKey())==0
                    || genotype[i].compareTo(DNA.TT.getKey())==0 ) {
                ++fitness;
            }
        }
    }

    
    /**
     * encode CityNode to GA's Chromosome
     * @param city
     */
    public void encode( CityNode city ) {
        int padSize = 0;
        StringBuffer sb = new StringBuffer();
        //change cityIndex to binary String
        int cityIndex = city.getCityNum();
        String cityKey = Integer.toBinaryString(cityIndex);
        //padding with bit '0'
        int size = 4*CHROMO_LENGTH;
        if( cityKey.length()<size )
            padSize = size - cityKey.length();
        for( int i=0; i<padSize; i++ )
            sb.append("0");
        sb.append(cityKey);
        //assign binary String to genotype[j]( j=0, 1, ..., CHROMO_LENGTH )
        String wholeStr = sb.toString();
        for( int j=0; j<CHROMO_LENGTH; j++ )
            genotype[j] = new Gene( wholeStr.substring(j*CHROMO_LENGTH, j*CHROMO_LENGTH+3) );

        for (int i = 0; i < CHROMO_LENGTH; i++) {
            gtSplitX[i] = genotype[i].getGene_x();
            gtSplitY[i] = genotype[i].getGene_y();
        }
    }

    //Overloading encode method
    public void encode( int cityIndex) {
        int padSize = 0;
        StringBuffer sb = new StringBuffer();

        String cityKey = Integer.toBinaryString(cityIndex);
        int size = 4*CHROMO_LENGTH;
        if( cityKey.length()<size )
            padSize = size - cityKey.length();
        for( int i=0; i<padSize; i++ )
            sb.append("0");
        sb.append(cityKey);
        String wholeStr = sb.toString();
        for( int j=0; j<CHROMO_LENGTH; j++ )
            genotype[j] = new Gene( wholeStr.substring(j*CHROMO_LENGTH, j*CHROMO_LENGTH+4) );

        for (int i = 0; i < CHROMO_LENGTH; i++) {
            gtSplitX[i] = genotype[i].getGene_x();
            gtSplitY[i] = genotype[i].getGene_y();
        }
    }

    /**
     * Decode GA individual's genotype to city No.(int)
     * @return
     */
    public int decode() {
        StringBuffer sb = new StringBuffer();
        int cityCode = 0;

        for( int i=0; i<CHROMO_LENGTH; i++ )
            sb.append( genotype[i].getGenePair() );
        cityCode = Integer.parseInt( sb.toString(), 2 );

        return cityCode;
    }

    public boolean compareTo(Chromosome ch) {
        if( genotype.length != ch.getGenotype().length )
            return false;
        for( int i=0; i<genotype.length; i++ ) {
            String s1 = genotype[i].getGenePair();
            String s2 = ch.getGenotype(i).getGenePair();
            if(s1.compareTo(s2)!=0)
                return false;
        }
        return true;
    }
    
    public boolean equals(Object obj) {  
        if (!(obj instanceof Chromosome))  
            return false;  
        if (obj == this)  
            return true;  
        Chromosome ch = (Chromosome) obj;  
        boolean result = true;
        
        for( int i=0; i<genotype.length; i++ )
            if(genotype[i].getGenePair().compareTo(ch.getGenotype(i).getGenePair())!=0) {
                return  false;
            }
                
        return result;  
    }  
    
   
        
    public String toStrig() {
        StringBuffer sb = new StringBuffer();
        for( Gene g:genotype ) {
            sb.append( g.getGenePair() );
        }
        sb.append(" gtSplitX:" );
        for( String s: gtSplitX )
            sb.append(s);
        sb.append(" gtSplitY:");
        for( String s: gtSplitY )
            sb.append(s);
        sb.append( " fitness:" + fitness );
        
        return sb.toString();
        
    }


}
