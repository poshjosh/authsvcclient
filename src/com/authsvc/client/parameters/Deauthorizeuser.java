package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)Deauthorizeuser.java   23-Apr-2015 19:38:47
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
public class Deauthorizeuser extends Authorizeuser {

    public static enum ParamName{appid, emailaddress}
    
    @Override
    public Map<String, String> getParameters() {
        
        Map<String, String> params = this.createHashMapNoNulls(3, 1.0f);
        
        params.put(ParamName.emailaddress.name(), this.getEmailAddress());
//        params.put(ParamName.password.name(), this.getPassword());
        params.put(ParamName.appid.name(), this.getAppid().toString());
        return params;
    }

}
