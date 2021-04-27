/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class InvoiceSupplementSelect {
//      private IntegerProperty invSuplNumId; //pk
//    private IntegerProperty invSuplNum;
    private StringProperty invSuplId;
    private DoubleProperty invSuplPrice;
    private IntegerProperty invSuplQty;
    private DoubleProperty invSuplPriceTotal;
    private DoubleProperty invTotPriceSuplConsult;
    
    //Setup invSuplNumId Property
//    public void setInvSuplNumId(Integer value)
//    {   
//        InvSuplNumIdProperty().set(value);
//    }
//    public Integer getInvSuplNumId()
//    {
//        return InvSuplNumIdProperty().get();
//    }
//    public IntegerProperty InvSuplNumIdProperty()
//    {
//        if(invSuplNumId == null) invSuplNumId = new SimpleIntegerProperty(this, "InvSuplNumId");
//        return invSuplNumId;
//    }
//    
//    //Setup invSuplNum Property
//    public void setInvSuplNum(Integer value)
//    {   
//        InvSuplNumProperty().set(value);
//    }
//    public Integer getInvSuplNum()
//    {
//        return InvSuplNumProperty().get();
//    }
//    public IntegerProperty InvSuplNumProperty()
//    {
//        if(invSuplNum == null) invSuplNum = new SimpleIntegerProperty(this, "InvSuplNum");
//        return invSuplNum;
//    }
    
    //setup invSuplId Property
    public void setInvSuplId(String value)
    {   
        InvSuplIdProperty().set(value);
    }
    public String getInvSuplId()
    {
        return InvSuplIdProperty().get();
    }
    public StringProperty InvSuplIdProperty()
    {
        if(invSuplId == null) invSuplId = new SimpleStringProperty(this, "InvSuplId");
        return invSuplId;
    }
    
    //Setup invSuplPrice Property
    public void setInvSuplPrice(Double value)
    {   
        InvSuplPriceProperty().set(value);
    }
    public Double getInvSuplPrice()
    {
        return InvSuplPriceProperty().get();
    }
    public DoubleProperty InvSuplPriceProperty()
    {
        if(invSuplPrice == null) invSuplPrice = new SimpleDoubleProperty(this, "InvSuplPrice");
        return invSuplPrice;
    }
    
    //Setup invSuplQty Property
    public void setInvSuplQty(Integer value)
    {   
        InvSuplQtyProperty().set(value);
    }
    public Integer getInvSuplQty()
    {
        return InvSuplQtyProperty().get();
    }
    public IntegerProperty InvSuplQtyProperty()
    {
        if(invSuplQty == null) invSuplQty = new SimpleIntegerProperty(this, "InvSuplQty");
        return invSuplQty;
    }
    
    //Setup invSuplPriceTotal Property
    public void setInvSuplPriceTotal(Double value)
    {   
        InvSuplPriceTotalProperty().set(value);
    }
    public Double getInvSuplPriceTotal()
    {
        return InvSuplPriceTotalProperty().get();
    }
    public DoubleProperty InvSuplPriceTotalProperty()
    {
        if(invSuplPriceTotal == null) invSuplPriceTotal = new SimpleDoubleProperty(this, "InvSuplPriceTotal");
        return invSuplPriceTotal;
    }
    
    //Setup invTotPriceSuplConsult Property
    public void setInvTotPriceSuplConsult(Double value)
    {   
        InvTotPriceSuplConsultProperty().set(value);
    }
    public Double getInvTotPriceSuplConsult()
    {
        return InvTotPriceSuplConsultProperty().get();
    }
    public DoubleProperty InvTotPriceSuplConsultProperty()
    {
        if(invTotPriceSuplConsult == null) invTotPriceSuplConsult = new SimpleDoubleProperty(this, "InvTotPriceSuplConsult");
        return invTotPriceSuplConsult;
    }
}
