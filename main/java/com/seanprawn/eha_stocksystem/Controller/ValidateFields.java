/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Controller;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class ValidateFields {
    
      /**
     * Validates an input String field 
     * will match on any string field, no special chars or numbers
     * @param string
     * @return 
     */
    public static Boolean validateStringTextField(String string)
    {
        if(isTextInputValid(string))
        {
//            System.out.println("Text Input is Valid");
            return true;
        }
        else
        {
            System.out.println("Text Input is NOT Valid!");
        return false;
        }
    }
    
    private static boolean isTextInputValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
            if(check.matches("[A-Za-z\\s]+")) // Regex to check if string only
            {
                flag = true;
            }
        }
            return flag;
    }
    
     /**
     * Validates an input String field 
     * will match on any field, any character text or number or special character
     * @param string
     * @return 
     */
     public static Boolean validateStringCharTextField(String string)
    {
        if(isTextCharInputValid(string))
        {
//            System.out.println("Text Input is Valid");
            return true;
        }
        else
        {
            System.out.println("Text Input is NOT Valid!");
        return false;
        }
    }
    
    private static boolean isTextCharInputValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
            if(check.matches(".{1,}"))
            {
                flag = true;
            }
        }
            return flag;
    }
    
     /**
     * Validates an input number field 
     * will match on any numeric field, (with or without any number of decimal points)
     * @param string
     * @return 
     */
    public static Boolean validateNumberTextField(String string)
    {
        if(isNumberInputValid(string))
        {
            return true;
        }
        else
        {
            System.out.println("Number Input is NOT Valid!");
        return false;
        }
    }
   
    private static boolean isNumberInputValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
            if(check.matches("[0-9]*.[0-9]*")) // Regex to check if Number (Int or Double is allowed)
            {
                flag = true;
            }
        }
            return flag;
    }

      /**
     * Validates an input email field 
     * will match on any text field with "@"
     * @param text
     * @return 
     */
    public static boolean validateEmailTextField(String text) {
         if(isTextEmailInputValid(text))
        {
            return true;
        }
        else
        {
            System.out.println("Email Input is NOT Valid!");
        return false;
        }
    }
    
    private static boolean isTextEmailInputValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
//            if(check.matches("^(.+)@(.+)$")) // Regex to match "@"
            if(check.matches("^(.*)@(.*)(\\.)(.*)$")) // Regex to match "@"
            {
                flag = true;
            }
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
            if(check.matches("^(\\d{11}|\\d{12}|\\d{13})$")) // Regex to check only ID number of 11, 12 or 13 digits
            {
                flag = true;
            }
        }
            return flag;
    }
//}

 public static Boolean validateIntegerNumberTextField(String string)
    {
        if(isNumberInputIntegerValid(string))
        {
            return true;
        }
        else
        {
            System.out.println("Number Input is NOT Valid!");
        return false;
        }
    }
   
    private static boolean isNumberInputIntegerValid(String string) {
        Boolean flag = false;
        if(!(string == null || string.length() == 0)) 
        {
            String check = string;
            if(check.matches("\\d*")) // Regex to check if Number (Int or Double is allowed)
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