package com.authsvc.client.parameters;

import java.util.logging.Logger;
import java.util.Map;
import java.util.logging.Level;


/**
 * @(#)Editappstatus.java   26-Dec-2014 12:34:06
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
public class Editappstatus extends DefaultParametersProvider {
    private transient static final Logger LOG = Logger.getLogger(Editappstatus.class.getName());
    
    public static enum ParamName{emailaddress, password, userstatus}
    
    private String emailaddress;
    
    private String password;
    
    private int userstatus;
    
    public Editappstatus() { }

    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(3, 1.0f);
        
if(LOG.isLoggable(Level.FINER)){
LOG.log(Level.FINER, "Email: {0}, Pass: {1}, Status: {2}",
new Object[]{ this.emailaddress,  this.password,  this.userstatus});
}

        params.put(ParamName.emailaddress.name(), this.emailaddress);
        params.put(ParamName.password.name(), password);
        params.put(ParamName.userstatus.name(), Integer.toString(userstatus));
        
        return params;
    }

    public int getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(int userstatus) {
        this.userstatus = userstatus;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
