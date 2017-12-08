package edu.neu.info6205.finalprojectNum09.Digraph;

/**
 * @author  Bo Han
 * @NUID    001815357
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityNode implements Comparable<CityNode>{

    private int cityKey;

    private ArrayList<CityNode> inDegreeNode;
    private ArrayList<CityNode> outDegreeNode;


    private HashMap<CityNode,Integer> weigh;


    public CityNode(int key)
    {
        this.cityKey = key;
        inDegreeNode = new ArrayList<CityNode>();
        outDegreeNode = new ArrayList<CityNode>();
        weigh = new HashMap<CityNode,Integer>();
    }


    public void setCityKey(int cityKey)
    {
        this.cityKey = cityKey;
    }

    public int getCityKey(String cityKey)
    {
        return this.cityKey;
    }



    public void addWeigh(CityNode adjNode, int weighValue)
    {
        weigh.put(adjNode,weighValue);
    }

    public int getWeigh(CityNode node)
    {
        for(Map.Entry<CityNode,Integer> entry : weigh.entrySet())
        {
            CityNode n = entry.getKey();
            if(n.compareTo(node) > 0)
            {
                return entry.getValue();
            }
        }
        return 0;
    }

    public ArrayList<CityNode> getInDegreeNode()
    {
        return inDegreeNode;
    }

    public ArrayList<CityNode> getOutDegreeNode()
    {
        return outDegreeNode;
    }


    public void addInDegreeNode(CityNode node)
    {
        inDegreeNode.add(node);
    }

    public void addOutDegreeNode(CityNode node)
    {
        outDegreeNode.add(node);
    }

    
    public boolean isInConnected(CityNode node)
    {
        for(CityNode n: inDegreeNode )
        {
            if(n.cityKey == node.cityKey)
            {
                return true;
            }
        }
        return false;

    }

    public boolean isOutConnected(CityNode node)
    {

        for(CityNode n: outDegreeNode )
        {
            if(n.cityKey == node.cityKey)
            {
                return true;
            }
        }
        return false;
    }


    public int compareTo(CityNode node)
    {
        if(this.cityKey == node.cityKey)
            return 1;
        else return -1;
    }




}
