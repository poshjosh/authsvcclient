package com.authsvc.client.parameters;

import java.util.Map;


/**
 * @(#)NewInterface.java   22-Jan-2015 12:43:30
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
public interface ParametersProvider {

    Map<String, String> getParameters();

    String getServletPath();

}
