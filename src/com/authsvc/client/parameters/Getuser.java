package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Getuser.java   22-Jan-2015 13:38:11
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
public class Getuser extends Getapp {
    
    public static enum ParamName{appid, token, username, emailaddress, password, create}
    
    private Object appid;
    
    private String token;
    
    public Getuser() { }

    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(6, 1.0f);
        
        if(this.getUsername() != null) {
            params.put(ParamName.username.name(), this.getUsername());
        }
        params.put(ParamName.appid.name(), this.getAppid().toString());
        params.put(ParamName.token.name(), this.getToken());
        params.put(ParamName.emailaddress.name(), this.getEmailAddress());
        params.put(ParamName.password.name(), this.getPassword());
        params.put(ParamName.create.name(), Boolean.toString(this.isCreate()));
        
        return params;
    }

    public Object getAppid() {
        return appid;
    }

    public void setAppid(Object appid) {
        this.appid = appid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
