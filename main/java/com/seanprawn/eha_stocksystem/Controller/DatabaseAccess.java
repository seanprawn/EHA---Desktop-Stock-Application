/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Controller;

import com.seanprawn.eha_stocksystem.View.Alerts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class DatabaseAccess {
    
    static Connection connect = null;
    static String user;
    static String passw;
    public static String url;
    public static boolean connectionFlag; // Flag to set Online = false ; Flag to set offline = true
    
    /**
     * Sets the connection to the specific database
     * (Depending on which flag is set)
     * Flag set False = Online
     * Flag set True = Offline
     * @param connectFlag
     */
    public static void setConnectionOnlineOffline(boolean connectFlag)
    {
        DatabaseAccess.connectionFlag = connectFlag;
        if(!connectFlag) // set Online connection
        {
             //**********Connect to website URL******************
//        url = "jdbc:mysql://ns02.000webhost.com:4780/id6781922_eha_database";
//        url = "jdbc:mysql://databases.000webhost.com:3306/id6781922_eha_database";
//        user = "id6781922_sean";
//        passw = "student";

        //URL to connect to MySQL
         url = "jdbc:mysql://localhost:8889/eha_database";
//         url = "jdbc:mysql://localhost:3306/eha_database";
         user = "root";
        passw = "root";
//        connect = DriverManager.getConnection(url, user, passw);   // Connect to MySql
//    **************************************************************    
        }
        else if (connectFlag) // set offline local connection
        {
          
        // URL to connect to HSQLDB
        url = "jdbc:hsqldb://file:db-data/eha_database;";//ifexists=true";
        user = "SA";
        passw = "";
       
//   *********   Creating the connection with HSQLDB     ****************
//       This connection string will not drop connection to database on app close! 
//             con = DriverManager.getConnection("jdbc:hsqldb:file:db-data/myDatabase");

//      For unit testing purposes!
//   ******* This connection string will close the connection after every app shutdown! *********
//         connect = DriverManager.getConnection("jdbc:hsqldb:file:db-data/EHA_Database:shutdown=true");
         
        }
    }
    
    public static Connection startConnection()
    {
//    String url = null;
      
      try {

     //   *********   Registering the MySQL JDBC driver  ***************
         if(!connectionFlag) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
           //   *********   Registering the HSQLDB JDBC driver  ***************
         else if(connectionFlag) Class.forName("org.hsqldb.jdbc.JDBCDriver");
//         connect = DriverManager.getConnection(url, "root", "root");   // Connect to MySql local
//         connect = DriverManager.getConnection(url, "SA", ""); // connection to HSQLDB
        
         connect = DriverManager.getConnection(url, user, passw);   // Connect 
         if (connect == null)
         {
              System.out.println("Problem creating DB connection");

//            System.out.println("Connection To DB created successfully");
//            System.out.println("Connection To DB User: "+user+" passw: "+passw);
         }
      
      }  catch (Exception e) 
      {
          Alerts.displayAlert("Error", "Database is offline!"); // Notify user
//          System.out.println("Problem creating connection, Exception Caught:\n");
         e.printStackTrace(System.out);
      }
        return connect;
      }
    
    public static void closeConnection() throws SQLException
    {
        try {
            connect.close();
            System.out.println("Connection to DB closed");
        } catch (Exception e) {
//            System.out.println("Cannot close Connection, Exception: \n");
            e.printStackTrace(System.out);
        }
    }
}
