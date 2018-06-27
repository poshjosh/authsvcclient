package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Authenticateuser.java   16-Jan-2015 08:29:28
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
public class Authenticateuser extends DefaultParametersProvider {
    
    public static enum ParamName{appid, emailaddress, token}
    
    private Object appid;
    
    private String emailAddress;
    
    private String token;
    
    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(3, 1.0f);
        
        params.put(Authenticateuser.ParamName.emailaddress.name(), this.getEmailAddress());
        params.put(Authenticateuser.ParamName.token.name(), this.getToken());
        params.put(Authenticateuser.ParamName.appid.name(), this.getAppid().toString());
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

    public String getToken() {
        return token;
    }

    public void setToken(String t) {
        this.token = t;
    }
}

