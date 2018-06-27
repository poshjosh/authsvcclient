package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Authorizeuser.java   16-Jan-2015 08:27:50
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
public class Authorizeuser extends DefaultParametersProvider {
    
    public static enum ParamName{appid, emailaddress, password}
    
    private Object appid;
    
    private String emailAddress;
    
    private String password;
    
    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(3, 1.0f);
        
        params.put(ParamName.emailaddress.name(), this.getEmailAddress());
        params.put(ParamName.password.name(), this.getPassword());
        params.put(ParamName.appid.name(), this.getAppid().toString());
        return params;
    }

    public Object getAppid() {
        return appid;
    }

    public void setAppid(Object appid) {
        this.appid = appid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
