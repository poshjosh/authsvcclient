package com.authsvc.client.parameters;

import java.util.logging.Logger;
import java.util.Map;
import java.util.logging.Level;


/**
 * @(#)Requestapppassword.java   27-Mar-2015 18:06:39
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
public class Requestapppassword extends DefaultParametersProvider {
    private transient static final Logger LOG = Logger.getLogger(Requestapppassword.class.getName());

    public static enum ParamName{username, emailaddress, sender_emailaddress, sender_password}
    
    private String username;
    
    private String emailAddress;
    
    public Requestapppassword() {}
    
    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(2, 1.0f);
        
        if(this.getUsername() != null) {
            params.put(Getuser.ParamName.username.name(), this.getUsername());
        }
        params.put(Getuser.ParamName.emailaddress.name(), this.getEmailAddress());
if(LOG.isLoggable(Level.FINER)){
LOG.log(Level.FINER, "Parameters: {0}", params);
}
        return params;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
