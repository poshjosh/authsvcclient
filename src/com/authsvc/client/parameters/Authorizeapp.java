package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Authenticateapp.java   16-Dec-2014 21:02:45
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * /authsvc?req=authenticateapp&emailAddress=[email]&password=[pass]
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class Authorizeapp extends DefaultParametersProvider {
    
    public static enum ParamName{emailaddress, password}
    
    public Authorizeapp() {}
 
    private String password;
    
    private String emailAddress;
    
    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(2, 1.0f);
        
        params.put(ParamName.emailaddress.name(), this.getEmailAddress());
        params.put(ParamName.password.name(), this.getPassword());
        
        return params;
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
