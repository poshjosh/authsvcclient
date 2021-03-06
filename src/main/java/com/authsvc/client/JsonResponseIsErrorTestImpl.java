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
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 11:04:01 PM
 */
public class JsonResponseIsErrorTestImpl implements JsonResponseIsErrorTest{

    private final String errorMarker;

    public JsonResponseIsErrorTestImpl() {
        this("error");
    }
    
    public JsonResponseIsErrorTestImpl(String errorMarker) {
        this.errorMarker = errorMarker;
    }
    
    @Override
    public boolean test(Map json) {
        //@related error message format. Don't change this without changing all related
        //
        boolean error = false;
        for(Object key:json.keySet()) {
            if(key.toString().toLowerCase().contains(this.errorMarker)) {
                error = true;
                break;
            }
        }
        return error;
    }
}
