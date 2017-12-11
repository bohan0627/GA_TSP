package edu.neu.info6205.finalproj.num09.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by callouswander on 2017/12/10.
 */
public class QuickSort {

    public QuickSort() {
    }

    private static int partition( Comparable[] a, int low, int high ) {
        int i = low, j = high+1;
        while( true ) {
            while( less(a[++i], a[low]) )
                if( i==high ) break;
            while( less(a[low], a[--j]) )
                if( j==low ) break;
            if( i>=j ) break;
            exch( a, i, j );
        }

        exch(a, low, j);
        return j;
    }

    private static boolean less(Comparable x, Comparable y) {
        return ( (x.compareTo(y) < 0) );
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void qSort( Comparable[] a ) {
        //Shuffle the array
        List<Comparable> aList = new ArrayList<Comparable>(a.length);
        for( int i=0; i<a.length; i++ )
            aList.add(a[i]);
        Collections.shuffle(aList);
        for( int i=0; i<a.length; i++ )
            a[i] = aList.get(0);
        //recursively quick sort
        sort( a, 0, a.length-1 );
    }

    private static void sort( Comparable[] a, int low, int high ) {
        if( high<=low ) return;
        int j = partition( a, low, high );
        sort( a, low, j-1 );
        sort( a, j+1, high );

    }
}
