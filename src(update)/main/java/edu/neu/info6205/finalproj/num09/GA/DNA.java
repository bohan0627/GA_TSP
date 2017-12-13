package edu.neu.info6205.finalproj.num09.GA;

/**
 * @author  Junyuan Gu
 * @NUID    001825583
 */
public enum DNA {
    //Allele
    A( "00", 20 ),
    C( "01", 21 ),
    G( "10", 22 ),
    T( "11", 23 ),
    //pairs
    AA( "0000", 0 ),
    AC( "0001", 1 ),
    AG( "0010", 2 ),
    AT( "0011", 3 ),

    CA( "0100", 4 ),
    CC( "0101", 5 ),
    CG( "0110", 6 ),
    CT( "0111", 7 ),

    GA( "1000", 8 ),
    GC( "1001", 9 ),
    GG( "1010", 10 ),
    GT( "1011", 11 ),

    TA( "1100", 12 ),
    TC( "1101", 13 ),
    TG( "1110", 14 ),
    TT( "1111", 15 );

    private String key;
    private int    val;

    DNA( String key, int val ) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public int  getVal() {
        return val;
    }

    public static String getKey( int val ) {
        String k = null;
        switch( val ) {
            case 0: k = "0000";
                break;
            case 1: k = "0001";
                break;
            case 2: k = "0010";
                break;
            case 3: k = "0011";
                break;
            case 4: k = "0100";
                break;
            case 5: k = "0101";
                break;
            case 6: k = "0110";
                break;
            case 7: k = "0111";
                break;
            case 8: k = "1000";
                break;
            case 9: k = "1001";
                break;
            case 10: k = "1010";
                break;
            case 11: k = "1011";
                break;
            case 12: k = "1100";
                break;
            case 13: k = "1101";
                break;
            case 14: k = "1110";
                break;
            case 15: k = "1111";
                break;
            case 20: k = "00";
                break;
            case 21: k = "01";
                break;
            case 22: k = "10";
                break;
            case 23: k = "11";
            default:
                break;
        }
        return k;
    }

}
