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

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class SeanTestRegex {
    
    public SeanTestRegex() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void Testing()
    {
//        boolean checkMatch = checkRegexMatch(" 1_12234");
//        boolean checkID = isClientIdInputValid("11223427812");
        boolean checkPhone = isPhoneNumberValid("098-");
//       boolean check1 = checkRegexChar("Supplement-248/*&%$#@!");
//       boolean check2 = checkRegex("Supplement");
//       boolean check3 = checkRegexNumber("2");
//       boolean check4 = checkRegexNumber("7000.01");
//        System.out.println("\n Any Char result is: "+check1+"\n");
//        System.out.println("\n String result is: "+check2+"\n");
//        System.out.println("\n Number result is: "+check3+"\n");
//        System.out.println("\n Number Double result is: "+check4+"\n");
//        System.out.println("\n Match result is: "+checkMatch+"\n");
//        System.out.println("\n ID result is: "+checkID+"\n");
        System.out.println("\n Phone NUmber result is: "+checkPhone+"\n");
    }
    
    public boolean checkRegexChar(String check)
    {
//    String check = "Supplement-248/*&%$#@!";
    boolean flag = false;
//    if(check.matches("/[- ^a-zA-Z0-9]/g")) //test for numbers, letters and special chars
    if(check.matches(".{1,}")) //test for any chars, any number of times
            {
            flag = true;
            }
    return flag;
    }
    
     public boolean checkRegex(String check)
    {
//    String check = "Supplement-248/*&%$#@!";
    boolean flag = false;
//    if(check.matches("/[- ^a-zA-Z0-9]/g")) //test for numbers, letters and special chars
    if(check.matches("[A-Za-z\\s]+")) //test for any chars, any number of times
            {
            flag = true;
            }
    return flag;
    }
    
    public boolean checkRegexNumber(String check)
    {
//    String check = "248";
    boolean flag = false;
//    if(check.matches("/[- ^a-zA-Z0-9]/g")) //test for numbers, letters and special chars
    if(check.matches("[0-9]*.[0-9]*")) //test for any number, any number of times
            {
            flag = true;
            }
    return flag;
    }
    
    public boolean checkRegexMatch(String check)
    {
//    String check = "248";
    boolean flag = false;
//    if(check.matches("/[- ^a-zA-Z0-9]/g")) //test for numbers, letters and special chars
    if(check.matches("^ [0-9_]*.")) //test for any number and underscore
            {
            flag = true;
            }
    return flag;
    }
    
    public static boolean validateClientUniqueIdTextField(String text) 
    {
         if(isClientIdInputValid(text))
        {
            return true;
        }
        else
        {
            System.out.println("Number Input is NOT Valid!");
        return false;
        }
    }
   
    private static boolean isClientIdInputValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
//            if(check.matches("[0-9]*.[0-9]*")) // Regex to check if Number (Int or Double is alowed)
            if(check.matches("^(\\d{11}|\\d{12})$")) // Regex to check only ID number of 11 or 12 digits
            {
                flag = true;
            }
        }
            return flag;
    }
    
        public static Boolean validatePhoneNumberTextField(String string)
    {
        if(isPhoneNumberValid(string))
        {
            return true;
        }
        else
        {
            System.out.println("Number Input is NOT Valid!");
        return false;
        }
    }
   
    private static boolean isPhoneNumberValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
            if(check.matches("^[0-9]+[0-9-]*")) // Regex to check if Number (Int or Double is allowed)
            {
                flag = true;
            }
        }
            return flag;
    }
}
//0-9]+.[0-9]+