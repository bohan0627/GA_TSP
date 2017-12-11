/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.neu.info6205.finalproj.num09.GA;

//import static edu.neu.info6205.finalproj.num09.GA.TSP_GA.log;
import edu.neu.info6205.finalproj.num09.log.LogGlobe;
import edu.neu.info6205.finalproj.num09.log.LogUtil;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author  JunyuanGu
 * @NUID    001825583
 */
public class TSP_GATest {
    static Logger log = Logger.getLogger(TSP_GATest.class.getName());
    public TSP_GATest() {
    }
    
    @Test
    public void TestTSP_GA(){
        log.info("start TSP_GATest");
        TSP_GA tsp = new TSP_GA();
   
        tsp.init();

        Population pop = new Population(TSP_GA.individuals, TSP_GA.popSize);

        // iterate number = 100
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }
        
        //Write log to the file
        LogUtil.addFileHandler(log, Level.INFO, LogGlobe.LOG_FOLDER + File.separator + "TSP.log");
        log.info("End of TSP_GATest");
    }
    
    
}
