/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

import java.math.BigInteger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 * client_id - bigint
 * client_name - text
 * client_surname - text
 * client_address - text
 * client_address_code - int
 * client_tel_h	- text
 * client_tel_w - text
 * client_tel_cell - text
 * client_email - text
 * client_ref - int
 */
public class Client {
   private ObjectProperty<BigInteger> clId;
   private StringProperty clName;
   private StringProperty clSurname;
   private StringProperty clAddress;
   private IntegerProperty clAddrCode;
   private StringProperty clTelHome;
   private StringProperty clTelWork;
   private StringProperty clTelCell;
   private StringProperty clEmail;
   private IntegerProperty clRef;
   
    //Setup clId Property
    public void setClId(Object value) 
    { 
        ClIdProperty().set(value); 
    }
    public Object getClId() 
    { 
        return ClIdProperty().get(); 
    }
    public ObjectProperty ClIdProperty() 
    {
        if (clId == null) clId = new SimpleObjectProperty(this, "ClId");
        return clId;
     }
   //Setup clName Property
    public void setClName(String value) 
    { 
        ClNameProperty().set(value); 
    }
    public String getClName() 
    { 
        return ClNameProperty().get(); 
    }
    public StringProperty ClNameProperty() 
    {
        
        if (clName == null) clName = new SimpleStringProperty(this, "ClName");
        return clName;
     }
    
    //Setup clSurname Property
    public void setClSurname(String value) 
    { 
        ClSurnameProperty().set(value); 
    }
    public String getClSurname() 
    { 
        return ClSurnameProperty().get(); 
    }
    public StringProperty ClSurnameProperty() 
    {
        
        if (clSurname == null) clSurname = new SimpleStringProperty(this, "ClSurname");
        return clSurname;
     }
    
     //Setup clAddress Property
    public void setClAddress(String value) 
    { 
        ClAddressProperty().set(value); 
    }
    public String getClAddress() 
    { 
        return ClAddressProperty().get(); 
    }
    public StringProperty ClAddressProperty() 
    {
        
        if (clAddress == null) clAddress = new SimpleStringProperty(this, "ClAddress");
        return clAddress;
     }
    
    //Setup clAddrCode Property
    public void setClAddrCode(Integer value) 
    { 
        ClAddrCodeProperty().set(value); 
    }
    public int getClAddrCode() 
    { 
        return ClAddrCodeProperty().get(); 
    }
    public IntegerProperty ClAddrCodeProperty() 
    {
        
        if (clAddrCode == null) clAddrCode = new SimpleIntegerProperty(this, "ClAddrCode");
        return clAddrCode;
     }
    
     //Setup clTelHome Property
    public void setClTelHome(String value) 
    { 
        ClTelHomeProperty().set(value); 
    }
    public String getClTelHome() 
    { 
        return ClTelHomeProperty().get(); 
    }
    public StringProperty ClTelHomeProperty() 
    {
        
        if (clTelHome == null) clTelHome = new SimpleStringProperty(this, "ClTelHome");
        return clTelHome;
     }
    
    //Setup clTelWork Property
    public void setClTelWork(String value) 
    { 
        ClTelWorkProperty().set(value); 
    }
    public String getClTelWork() 
    { 
        return ClTelWorkProperty().get(); 
    }
    public StringProperty ClTelWorkProperty() 
    {
        
        if (clTelWork == null) clTelWork = new SimpleStringProperty(this, "ClTelWork");
        return clTelWork;
     }
    
    //Setup clTelCell Property
    public void setClTelCell(String value) 
    { 
        ClTelCellProperty().set(value); 
    }
    public String getClTelCell() 
    { 
        return ClTelCellProperty().get(); 
    }
    public StringProperty ClTelCellProperty() 
    {
        
        if (clTelCell == null) clTelCell = new SimpleStringProperty(this, "ClTelCell");
        return clTelCell;
     }
    
    //Setup clEmail Property
    public void setClEmail(String value) 
    { 
        ClEmailProperty().set(value); 
    }
    public String getClEmail() 
    { 
        return ClEmailProperty().get(); 
    }
    public StringProperty ClEmailProperty() 
    {
        
        if (clEmail == null) clEmail = new SimpleStringProperty(this, "ClEmail");
        return clEmail;
     }
    
    //Setup clRef Property
    public void setClRef(Integer value) 
    { 
        ClRefProperty().set(value); 
    }
    public int getClRef() 
    { 
        return ClRefProperty().get(); 
    }
    public IntegerProperty ClRefProperty() 
    {
        
        if (clRef == null) clRef = new SimpleIntegerProperty(this, "ClRef");
        return clRef;
     }
}
