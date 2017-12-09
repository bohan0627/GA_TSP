package edu.neu.info6205.finalproj.num09.GA;

import edu.neu.info6205.finalproj.num09.Graph.CityNode;

import java.util.ArrayList;

/**
 * @author  Junyuan GU
 * @NUID    001825583
 */
public class Individual {
    private ArrayList<Chromosome> individual = new ArrayList<Chromosome>();
    private static int Chromo_Size = 100;

    public Individual( CityNode[] vertices ) {
        if( Chromo_Size!=vertices.length )
            return;
        Chromosome nodeChromo = new Chromosome();
        for( int i=0; i<Chromo_Size; i++ ) {
            nodeChromo = null;
            nodeChromo.encode(vertices[i]);
            individual.add(nodeChromo);
        }
    }

    public ArrayList<Chromosome> getIndividual() {
        return individual;
    }


}
