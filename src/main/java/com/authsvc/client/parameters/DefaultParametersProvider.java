package com.authsvc.client.parameters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @(#)Baseclient.java   16-Dec-2014 20:19:47
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
public abstract class DefaultParametersProvider implements Serializable, ParametersProvider {
    
    @Override
    public String getServletPath() {
        return this.getClass().getSimpleName().toLowerCase();
    }
    
    public DefaultParametersProvider() { }
    
    protected Map<String, String> createHashMapNoNulls(int initial, float loadFactor) {
        return new HashMap<String, String>(initial, loadFactor) {
            @Override
            public String put(String key, String value) {
                if(key == null) {
                    throw new NullPointerException();
                }
                if(value == null) {
                    throw new NullPointerException("Value for key: "+key+" is null");
                }
                return super.put(key, value); 
            }
        };
    }
}
