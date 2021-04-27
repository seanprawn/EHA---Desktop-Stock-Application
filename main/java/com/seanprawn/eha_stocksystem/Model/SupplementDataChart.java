/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * Dashboard element - Supplements sold 
 * supplement
 * qty_sold
 */
public class SupplementDataChart {
    private StringProperty supplement;
    private IntegerProperty qtySold;
    
    //Setup Properties
    public void setSuplID(String value)
    {
     SuplIDProperty().set(value);
    }
    
    public String getSuplID()
    {
        return SuplIDProperty().get();
    }
    
    public StringProperty SuplIDProperty()
    {
        if (supplement == null) supplement = new SimpleStringProperty(this, "SuplID");
        return supplement;
    }
    
    public void setSupplQtySold(Integer value) 
    { 
        SuplQtySoldProperty().set(value); 
    }
    public int getSuplQtySold() 
    { 
        return SuplQtySoldProperty().get(); 
    }
    public IntegerProperty SuplQtySoldProperty() 
    {
        if (qtySold == null) qtySold = new SimpleIntegerProperty(this, "SuplQtySold");
        return qtySold;
     }
}
