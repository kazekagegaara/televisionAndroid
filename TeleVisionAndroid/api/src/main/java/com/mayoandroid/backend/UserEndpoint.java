package com.mayoandroid.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "backend.mayoandroid.com",
                ownerName = "backend.mayoandroid.com",
                packagePath = ""
        )
)
public class UserEndpoint {
    User val = null;
    String url ="";

    public Void initdb(){
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Connecting from App Engine.
                // Load the class that provides the "jdbc:google:mysql://"
                // prefix.
                try {
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                url ="jdbc:google:mysql://big-icon-861:mayoasu/mayoasu?user=root";
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiMethod(name= "GetIfUserExist")
    public User getIfUserExist(@Named("Email") String eml, @Named("Password") String pass) throws SQLException {
        initdb();
        Connection conn = null;
        User act = new User();
        int val=2;

        conn = DriverManager.getConnection(url);
        String query = "SELECT `active` FROM `users` WHERE username='"+eml+"' AND password='"+pass+"'";
        ResultSet res = conn.createStatement().executeQuery(query);
        if(!res.next()){
            val = 0;
        }else {
            //res.next();
            val = res.getInt(1);
        }
        act.setActive(val);

        return act;
    }

}