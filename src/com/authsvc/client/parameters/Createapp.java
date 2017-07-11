package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Createapp.java   16-Dec-2014 20:18:27
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
public class Createapp extends DefaultParametersProvider {
    
    public static enum ParamName{username, emailaddress, password, sendregistrationmail}
    
    private String username;
    
    private String password;
    
    private String emailAddress;
    
    private boolean sendRegistrationMail;
    
    public Createapp() {
        Createapp.this.setSendRegistrationMail(true);
    }

    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(4, 1.0f);
        
        if(username != null) {
            params.put(ParamName.username.name(), username);
        }
        params.put(ParamName.emailaddress.name(), emailAddress);
        if(password != null) {
            params.put(ParamName.password.name(), password);
        }
        params.put(ParamName.sendregistrationmail.name(), Boolean.toString(sendRegistrationMail));
        
        return params;
    }

    public boolean isSendRegistrationMail() {
        return sendRegistrationMail;
    }

    public void setSendRegistrationMail(boolean sendRegistrationMail) {
        this.sendRegistrationMail = sendRegistrationMail;
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
