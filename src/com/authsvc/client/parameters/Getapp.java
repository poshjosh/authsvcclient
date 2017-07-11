package com.authsvc.client.parameters;

import com.bc.util.XLogger;
import java.util.Map;
import java.util.logging.Level;


/**
 * @(#)Getapp.java   22-Jan-2015 13:44:55
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class Getapp extends DefaultParametersProvider {
    
    public static enum ParamName{username, emailaddress, password, create}
    
    boolean create;
    
    private String username;
    
    private String password;
    
    private String emailAddress;
    
    public Getapp() { }

    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(4, 1.0f);
        
        if(username != null) {
            params.put(ParamName.username.name(), username);
        }
        params.put(ParamName.emailaddress.name(), emailAddress);
        params.put(ParamName.password.name(), password);
        params.put(ParamName.create.name(), Boolean.toString(create));
XLogger.getInstance().log(Level.FINER, "Parameters: {0}", this.getClass(), params);
        return params;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

