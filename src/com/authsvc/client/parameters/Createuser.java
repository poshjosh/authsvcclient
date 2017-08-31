package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Createuser.java   16-Dec-2014 21:14:12
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * /authsvc?req=createuser&emailAddress=[email]&password=[pass]&appid=[appid]&token=[apptoken]
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class Createuser extends Createapp {
    
    public static enum ParamName{appid, token, username, emailaddress, password, sendregistrationmail, activateuser}
    
    private Object appid;
    
    private String token;
    
    @Override
    public Map<String, String> getParameters() {
        
        final Map<String, String> params = this.createHashMapNoNulls(8, 1.0f);
        
        params.put(Createapp.ParamName.emailaddress.name(), this.getEmailAddress());
        if(this.getPassword() != null) {
            params.put(Createapp.ParamName.password.name(), this.getPassword());
        }
        params.put(Createuser.ParamName.appid.name(), this.getAppid().toString());
        params.put(Createuser.ParamName.token.name(), this.getToken());
        params.put(Createapp.ParamName.sendregistrationmail.name(), Boolean.toString(this.isSendRegistrationMail()));
        if(this.getUsername() != null) {
            params.put(Createapp.ParamName.username.name(), this.getUsername());
        }  
        params.put(Createuser.ParamName.activateuser.name(), Boolean.toString(this.isActivateuser()));
        
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
