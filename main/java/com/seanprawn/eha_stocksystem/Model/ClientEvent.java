/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

import java.math.BigInteger;
import java.sql.Time;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Used In Dashboard for Client Birthday data and also for Client appointment data
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class ClientEvent {
    
    //Properties
   private ObjectProperty<BigInteger> clId;
   private StringProperty clName;
   private StringProperty clSurname;    
   private ObjectProperty<Time> clAppointmentTime;
   
   //Setup client Id Property
    public void setEventClId(Object value) 
    { 
        EventClIdProperty().set(value); 
    }
    public Object getEventClId() 
    { 
        return EventClIdProperty().get(); 
    }
    public ObjectProperty EventClIdProperty() 
    {
        if (clId == null) clId = new SimpleObjectProperty(this, "EventClId");
        return clId;
     }
   //Setup client Name Property
    public void setEventClName(String value) 
    { 
        EventClNameProperty().set(value); 
    }
    public String getEventClName() 
    { 
        return EventClNameProperty().get(); 
    }
    public StringProperty EventClNameProperty() 
    {
        
        if (clName == null) clName = new SimpleStringProperty(this, "EventClName");
        return clName;
     }
    
    //Setup clSurname Property
    public void setEventClSurname(String value) 
    { 
        EventClSurnameProperty().set(value); 
    }
    public String getEventClSurname() 
    { 
        return EventClSurnameProperty().get(); 
    }
    public StringProperty EventClSurnameProperty() 
    {
        
        if (clSurname == null) clSurname = new SimpleStringProperty(this, "EventClSurname");
        return clSurname;
     }
    
     //Setup Client Appointment Time Property
    public void setEventClAppTime(Object value) 
    { 
        EventClAppTimeProperty().set(value); 
    }
    public Object getEventClAppTime() 
    { 
        return EventClAppTimeProperty().get(); 
    }
    public ObjectProperty EventClAppTimeProperty() 
    {
        if (clAppointmentTime == null) clAppointmentTime = new SimpleObjectProperty(this, "EventClAppTime");
        return clAppointmentTime;
     }
    
    
}
