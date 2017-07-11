package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)EdituserStatus.java   17-Jan-2015 14:03:51
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
public class Edituserstatus extends DefaultParametersProvider {

    public static enum ParamName{appid, emailaddress, password, userstatus}

    private Object appid;
    
    private String emailaddress;
    
    private String password;
    
    private int userstatus;
    
    public Edituserstatus() { }

    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(4, 1.0f);
        
        params.put(ParamName.appid.name(), this.appid.toString());
        params.put(ParamName.emailaddress.name(), this.emailaddress);
        params.put(ParamName.password.name(), password);
        params.put(ParamName.userstatus.name(), Integer.toString(userstatus));
        
        return params;
    }

    public Object getAppid() {
        return appid;
    }

    public void setAppid(Object appid) {
        this.appid = appid;
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
