/*
 * Copyright 2017 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.authsvc.client;

import java.util.Map;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 9:29:40 PM
 */
public interface AuthDetailsStore {

    void setAppToken(Map tokenPair);
    
    Map getAppToken();

    void setAppDetails(Map appDetails);
    
    Map getAppDetails();
}
