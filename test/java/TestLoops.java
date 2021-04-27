/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class TestLoops {
    
    public TestLoops() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
@Test
public void Loops()
{
        int rows = 0;
    for(int i =0;i<4;i++)
    {
        rows+=15;
    }
    System.out.println("Rows: "+rows);
    System.out.println("\n 440 - ROWS == "+(440-rows));
    
//);
    int amt = Math.round((250/50));
    System.out.println("\n AMOUNT EXACT: "+amt);
}
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
