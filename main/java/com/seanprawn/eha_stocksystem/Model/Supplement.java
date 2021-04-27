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
 * Sets up the Properties of Supplement Class
 * @author Sean Liebenberg <seansound@gmail.com>
 * @properties:
 * suppl_num
 * suppl_id
 * supl_description	
 * supl_cost_excl	
 * supl_cost_incl	
 * supl_perc_inc	
 * supl_cost_client
 * supl_min_levels
 * supl_stock_levels
 * supl_nappi_code
 * supl_supplier_id
 */
public class Supplement {

    //Declare Properties
    private IntegerProperty suplNum;
    private StringProperty suplId;
    private StringProperty suplDesc;
    private DoubleProperty suplCostExcl;
    private DoubleProperty suplCostIncl;
    private DoubleProperty suplPercIncl;
    private DoubleProperty suplCostClient;
    private IntegerProperty suplMinLevels;
    private IntegerProperty suplStockLevels;
    private StringProperty suplNappiCode;
    private IntegerProperty suplSupplierId;
    
    //Setup Supplement Number Property
    public void setSupplNum(Integer value) 
    { 
        SuplNumProperty().set(value); 
    }
    public int getSuplNum() 
    { 
        return SuplNumProperty().get(); 
    }
    public IntegerProperty SuplNumProperty() 
    {
        
        if (suplNum == null) suplNum = new SimpleIntegerProperty(this, "SuplNum");
        return suplNum;
     }
    
    //Setup Supplement ID Property
    public void setSupplId(String value) 
    { 
        SuplIdProperty().set(value); 
    }
    public String getSuplId() 
    { 
        return SuplIdProperty().get(); 
    }
    public StringProperty SuplIdProperty() 
    {
        if (suplId == null) suplId = new SimpleStringProperty(this, "SuplId");
        return suplId;
     }
    
     //Setup Supplement Description Property
    public void setSupplDesc(String value) 
    { 
        SuplDescProperty().set(value); 
    }
    public String getSuplDesc() 
    { 
        return SuplDescProperty().get(); 
    }
    public StringProperty SuplDescProperty() 
    {
        if (suplDesc == null) suplDesc = new SimpleStringProperty(this, "SuplDesc");
        return suplDesc;
     }
    
    //Setup Supplement Cost Excl Property
    public void setSuplCostExcl(Double value) 
    { 
        SuplCostExclProperty().set(value); 
    }
    public double getSuplCostExcl() 
    { 
        return SuplCostExclProperty().get(); 
    }
    public DoubleProperty SuplCostExclProperty() 
    {
        if (suplCostExcl == null) suplCostExcl = new SimpleDoubleProperty(this, "SuplCostExcl");
        return suplCostExcl;
     }
    
      //Setup Supplement Cost Incl Property
     public void setSuplCostIncl(Double value) 
     { 
         SuplCostInclProperty().set(value); 
     }
    public double getSuplCostIncl() 
    { 
        return SuplCostInclProperty().get(); 
    }
    public DoubleProperty SuplCostInclProperty() 
    {
        if (suplCostIncl == null) suplCostIncl = new SimpleDoubleProperty(this, "SuplCostIncl");
        return suplCostIncl;
     }
    
     //Setup Supplement Perc Incl Property
    public void setSuplPercIncl(Double value) 
    { 
        SuplPercInclProperty().set(value); 
    }
    public double getSuplPercIncl() 
    { 
        return SuplPercInclProperty().get(); 
    }
    public DoubleProperty SuplPercInclProperty() 
    {
        if (suplPercIncl == null) suplPercIncl = new SimpleDoubleProperty(this, "SuplPercIncl");
        return suplPercIncl;
     }
    
      //Setup Supplement Cost to Client property
    public void setSuplCostClient(Double value) 
    { 
        SuplCostClientProperty().set(value); 
    }
    public double getSuplCostClient() 
    { 
        return SuplCostClientProperty().get(); 
    }
    public DoubleProperty SuplCostClientProperty() 
    {
        if (suplCostClient == null) suplCostClient = new SimpleDoubleProperty(this, "SuplCostClient");
        return suplCostClient;
     }
    
   //Setup Supplement Minimum Levels property 
    public void setSuplMinLevels(Integer value) 
    { 
        SuplMinLevelsProperty().set(value); 
    }
    public int getSuplMinLevels() 
    { 
        return SuplMinLevelsProperty().get(); 
    }
    public IntegerProperty SuplMinLevelsProperty() 
    {
        if (suplMinLevels == null) suplMinLevels = new SimpleIntegerProperty(this, "SuplMinLevels");
        return suplMinLevels;
     }
    
     //Setup Supplement Stock Levels property 
     public void setSuplStockLevels(Integer value) 
     { 
         SuplStockLevelsProperty().set(value); 
     }
    public int getSuplStockLevels() 
    { 
        return SuplStockLevelsProperty().get(); 
    }
    public IntegerProperty SuplStockLevelsProperty() 
    {
        if (suplStockLevels == null) suplStockLevels = new SimpleIntegerProperty(this, "SuplStockLevels");
        return suplStockLevels;
     }
    
     //Setup Supplement Nappi Code Property
    public void setsuplNappiCode(String value) 
    { 
        SuplNappiCodeProperty().set(value); 
    }
    public String getsuplNappiCode() 
    { 
        return SuplNappiCodeProperty().get(); 
    }
    public StringProperty SuplNappiCodeProperty() 
    {
        if (suplNappiCode == null) suplNappiCode = new SimpleStringProperty(this, "SuplNappiCode");
        return suplNappiCode;
     }
    
    //Setup Supplement upplier ID property 
     public void setSuplSupplierId(Integer value) 
     { 
         SuplSupplierIdProperty().set(value); 
     }
    public int getSuplSupplierId() 
    { 
        return SuplSupplierIdProperty().get(); 
    }
    public IntegerProperty SuplSupplierIdProperty() 
    {
        if (suplSupplierId == null) suplSupplierId = new SimpleIntegerProperty(this, "SuplSupplierId");
        return suplSupplierId;
     }
}
