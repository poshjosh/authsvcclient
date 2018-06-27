package com.authsvc.client.parameters;

import java.util.logging.Logger;
import java.util.Map;
import java.util.logging.Level;

/**
 * @(#)Requestuserpassword.java   27-Mar-2015 18:09:36
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
public class Requestuserpassword extends Requestapppassword {
    private transient static final Logger LOG = Logger.getLogger(Requestuserpassword.class.getName());

    public static enum ParamName{appid, username, emailaddress, sender_emailaddress, sender_password}
    
    private Object appid;
    
    public Requestuserpassword() {}
    
    @Override
    public Map<String, String> getParameters() {
        Map<String, String> params = this.createHashMapNoNulls(4, 1.0f);
        
        if(this.getUsername() != null) {
            params.put(Getuser.ParamName.username.name(), this.getUsername());
        }
        params.put(Getuser.ParamName.appid.name(), this.getAppid().toString());
        params.put(Getuser.ParamName.emailaddress.name(), this.getEmailAddress());
if(LOG.isLoggable(Level.FINER)){
LOG.log(Level.FINER, "Parameters: {0}", params);
}
        return params;
    }

    public Object getAppid() {
        return appid;
    }

    public void setAppid(Object appid) {
        this.appid = appid;
    }
}
