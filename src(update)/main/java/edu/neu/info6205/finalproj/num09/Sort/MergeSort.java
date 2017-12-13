/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.neu.info6205.finalproj.num09.Sort;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.apache.commons.lang3.time.StopWatch;
import static org.junit.Assert.assertTrue;

/**
 * @author Junyuan GU
 */
public class MergeSort {
    public Comparable[] aC;
    public Comparable[] aux;
    //constructor
    public MergeSort(Comparable[] array) {
        aC = new Comparable[array.length];
        aux = new Comparable[array.length];
        for( int i=0; i<array.length; i++ ) {
            aC[i] = array[i];
        }
    }
    
    //sort
    public void sort( Comparable[] a, int lo, int hi ) {
        if( hi<= lo ) return;
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid);   //left
        sort(a, mid+1,hi);  //right
        merge(a,lo,mid,hi); //merge
    }
    
    //merge
    public void merge( Comparable[] a, int lo, int mid, int hi ) {
        int i = lo, j = mid+1;
        
        for( int k=lo; k<=hi; k++ )
            aux[k] = a[k];
        
        for( int k=lo; k<=hi; k++ ) {
            if( i>mid )     a[k] = aux[j++];
            else if( j>hi ) a[k] = aux[i++];
            else if( less(aux[j],aux[i]) )
                a[k] = aux[j++];
            else    a[k] = aux[i++];        
        }
    }
    
    private boolean less(Comparable x, Comparable y) {
        return ( (x.compareTo(y) < 0) );
    }
    
    private void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    void show(Comparable[] a) {
        for(int i=0; i<a.length; i++) {
            System.out.println( "a["+i+"]: " + a[i] + " " );
        }
    }
    
    public boolean isSorted( Comparable[] a ) {
        for(int i=0; i<a.length-1; i++) {
            if( a[i].compareTo(a[i+1]) > 0 )
                return false;
        }
        return true;
    }
    
    /*
    public static void main( String[] Args ) {
    

        System.out.println("sort test1");
        Comparable[] a1 = {"S","O","R","T","E","X","A","M","P","L"};
        //int mid = (a1.length%2)==0 ? (a1.length/2-1) : a1.length/2;
        int hi = a1.length - 1;
        MergeSort instance1 = new MergeSort(a1);
        
        StopWatch sw1 = new StopWatch();
        sw1.start();
        instance1.sort(a1, 0, hi);
        sw1.stop();
        assertTrue(instance1.isSorted(a1));
        instance1.show(a1);
        System.out.println( "The elapse time of sort is " + sw1.getNanoTime() );
        
   

        System.out.println("sort test2");
        Comparable[] a2 = {"bed","bug","dad","yes","zoo","all","bad","yet"};
        //mid = (a2.length%2)==0 ? (a2.length/2-1) : a2.length/2;
        hi = a2.length - 1;
        MergeSort instance2 = new MergeSort(a2);
        StopWatch sw = new StopWatch();
        sw.start();
        instance2.sort( a2, 0, hi );
        sw.stop();
        instance2.show(a2);
        assertTrue(instance2.isSorted(a2));
        System.out.println( "The elapse time of sort is " + sw.getNanoTime() );
    }*/
    
}



