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
 */
public class ClientRef {
//client_ref - int
//client_ref_desc - string
    private IntegerProperty clRef;
     private StringProperty clRefDesc;
     
     //Setup clAddrCode Property
    public void setRefClRef(Integer value) 
    { 
        RefClRefProperty().set(value); 
    }
    public int getRefClRef() 
    { 
        return RefClRefProperty().get(); 
    }
    public IntegerProperty RefClRefProperty() 
    {
        
        if (clRef == null) clRef = new SimpleIntegerProperty(this, "RefClRef");
        return clRef;
     }
    
    //Setup clName Property
    public void setRefClRefDesc(String value) 
    { 
        RefClRefDescProperty().set(value); 
    }
    public String getRefClRefDesc() 
    { 
        return RefClRefDescProperty().get(); 
    }
    public StringProperty RefClRefDescProperty() 
    {
        
        if (clRefDesc == null) clRefDesc = new SimpleStringProperty(this, "RefClRefDesc");
        return clRefDesc;
     }
}
