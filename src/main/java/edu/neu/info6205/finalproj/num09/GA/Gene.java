package edu.neu.info6205.finalproj.num09.GA;

/**
 * ACGT pairs, any two of ACGT combine as a pair, like AC or AG.
 * Notice: AC and CA are different.
 * @author  Junyuan Gu
 * @NUID    001825583
 */
public class Gene {
    private String genePair = null; //"xy"
    private int  val = -1;  //-1 means no value;
    private String gene_x = null;   //"x"
    private String gene_y = null;   //"y"

    /**
     * constructor
     * @param genePair
     */
    public Gene(String genePair) {
        //check if argument is between DNA.AA.getKey() and DNA.TT.getKey();
        if( genePair !=null ) {
            if( genePair.length()!= 4 || genePair.compareTo(DNA.TT.getKey())>0
                || genePair.compareTo(DNA.AA.getKey())<0 ) {
                this.genePair = null;
                val = Integer.parseInt( genePair, 2 );
                gene_x = null;
                gene_y = null;
                return;
            }
            this.genePair = genePair;
            gene_x = genePair.substring(0,1);
            gene_y = genePair.substring(2,3);
        }
    }

    //getter
    public String getGenePair() {
        return genePair;
    }

    public int getVal() {
        return val;
    }

    public String getGene_x() {
        return gene_x;
    }

    public String getGene_y() {
        return gene_y;
    }

    //setter
    public void setGenePair(String genePair ) {
        //check if argument is between DNA.AA.getKey() and DNA.TT.getKey();
        if( genePair !=null ) {
            if( genePair.length()!= 4 || genePair.compareTo(DNA.TT.getKey())>0
                    || genePair.compareTo(DNA.AA.getKey())<0 ) {
                return;
            }
            this.genePair = genePair;
            val = Integer.parseInt( genePair, 2 );
            gene_x = genePair.substring(0,1);
            gene_y = genePair.substring(2,3);
        }
    }

    public void setGene_x( String geneX ) {
        gene_x = geneX;
        StringBuffer gxy = new StringBuffer() ;
        gxy.append( gene_x );
        gxy.append( gene_y );
        genePair = gxy.toString();
        val = Integer.parseInt( genePair, 2 );
    }

    public void setGene_y( String geneY ) {
        StringBuffer xy = new StringBuffer() ;
        gene_y = geneY;
        xy.append( gene_x );
        xy.append( gene_y );
        genePair = xy.toString();
        val = Integer.parseInt( genePair, 2 );
    }

    public int compareTo( String a ) {
        return genePair.compareTo(a);
    }

}
