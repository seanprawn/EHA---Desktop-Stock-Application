/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanprawn.eha_stocksystem.Model;

/**
 *
 * @author Sean Liebenberg <seansound@gmail.com>
 */
public class User {
    int id;
    int userGroupId;
    String firstName;
    String lastName;
    String email;
    String password;

//    Constructor
    public User()
    {
    }

//    override constructor
    public User(String firstName, String lastName, String email ,String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
//    override constructor
    public User(String firstName, String lastName, String email, String password, int id, int userGroupId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.id = id;
        this.userGroupId = userGroupId;
    }
    
//    Getters and setters
    public void setFirstName(String name)
    {
        this.firstName = name;
    }
    
    public String getFirstName()
    {
        String name = this.firstName;
        return name;
    }
    
     public void setLastName(String name)
    {
        this.lastName = name;
    }
    
    public String getLastName()
    {
        String name = this.lastName;
        return name;
    }
    
     public void setEmail(String useremail)
    {
        this.email = useremail;
    }
    
    public String getEmail()
    {
        String userEmail = this.email;
        return userEmail;
    }
    
    public void setPassword(String userPassword)
    {
        this.password = userPassword;
    }
    
    public String getPassword()
    {
        String userPass = this.password;
        return userPass;
    }
    
    public void setUserGroupId(int groupId)
    {
        this.userGroupId = groupId;
    }
    
    public int getUserGroupId()
    {
        int uGroup = this.userGroupId;
        return uGroup;
    }
    
    public int getUserId()
    {
        int userId = this.id;
        return userId;
    }
    
    public void setUserId(int userId)
    {
        this.id = userId;
    }
    
//    Methods
   
    
}
