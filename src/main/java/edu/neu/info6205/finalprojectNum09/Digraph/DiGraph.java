package edu.neu.info6205.finalprojectNum09.Digraph;

/**
 * @author  Bo Han
 * @NUID    001815357
 */

/*
 * In order to solve TSP: we need to make each Node has two properties: Indegree and OutDegree
 * Add weight to each edge
 * Assign int: [0,N-1] to each Node's key
 * Each Node has two ArrayLists to store InConnect nodes and OutConnect Nodes
 */
public class DiGraph {

    private CityNode[] vertices;


    public DiGraph(int size)
    {
        vertices = new CityNode[size];
        for(int i =0; i< size; i++)
            vertices[i] = new CityNode(i);
    }

    public void setConnection(CityNode v, CityNode w, int weigh)
    {
        v.addOutDegreeNode(w);
        w.addInDegreeNode(v);

        v.addWeigh(w,weigh);
        w.addWeigh(v,weigh);
    }

    private boolean isInConnected(CityNode v, CityNode w)
    {
        return v.isInConnected(w);
    }

    private boolean isOutConnected(CityNode v, CityNode w)
    {
        return v.isOutConnected(w);
    }

    //helper methods

    public int getVerticesSize()
    {
        return vertices.length;
    }

    public boolean isGraphEmpty()
    {
        return getVerticesSize() == 0;
    }

    private void validateVertex(int v)
    {
        if(v < 0 || v >= vertices.length)
            throw new IllegalArgumentException("This " + v + ": is not in this Digraph");
    }



}
