/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

import java.math.BigInteger;
import java.util.Date;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 *  ** Properties *********************************
 * inv_id - int
 * inv_num - text
 * inv_date - date
 * inv_client_id - bigint
 * inv_consultation_fee - decimal
 * 
 */
public class Invoice {
    
    private IntegerProperty invId;
    private StringProperty invNum;
    private ObjectProperty<Date> invDate;
    private ObjectProperty<BigInteger> invClientId;
    private DoubleProperty invConsultFee;
    
    //Setup invId Property
    public void setInvId(Integer value) 
    { 
        InvIdProperty().set(value); 
    }
    public int getInvId() 
    { 
        return InvIdProperty().get(); 
    }
    public IntegerProperty InvIdProperty() 
    {
        
        if (invId == null) invId = new SimpleIntegerProperty(this, "InvId");
        return invId;
     }
    
    //Setup InvNum Property
    public void setInvNum(String value) 
    { 
        InvNumProperty().set(value); 
    }
    public String getInvNum() 
    { 
        return InvNumProperty().get(); 
    }
    public StringProperty InvNumProperty() 
    {
        if (invNum == null) invNum = new SimpleStringProperty(this, "InvNum");
        return invNum;
     }
    
    //Setup InvDate Property
    public void setInvDate(Object value) 
    { 
        InvDateProperty().set(value); 
    }
    public Object getInvDate() 
    { 
        return InvDateProperty().get(); 
    }
    public ObjectProperty InvDateProperty() 
    {
        if (invDate == null) invDate = new SimpleObjectProperty(this, "InvDate");
        return invDate;
     }
    
    //Setup InvClientId Property
    public void setInvClientId(Object value) 
    { 
        InvClientIdProperty().set(value); 
    }
    public Object getInvClientId() 
    { 
        return InvClientIdProperty().get(); 
    }
    public ObjectProperty InvClientIdProperty() 
    {
        if (invClientId == null) invClientId = new SimpleObjectProperty(this, "InvClientId");
        return invClientId;
     }
    
    //Setup invConsultFee Property
    public void setInvConsultFee(Double value)
    {
        InvConsultFeeProperty().set(value);
    }
    public Double getInvConsultFee()
    {
        return InvConsultFeeProperty().get();
    }
    public DoubleProperty InvConsultFeeProperty() {
         if (invConsultFee == null) invConsultFee = new SimpleDoubleProperty(this, "InvConsultFee");
        return invConsultFee;
    }
}
