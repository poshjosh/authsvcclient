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

import com.authsvc.client.AuthDetailsLocalDiscStore.PathContext;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 31, 2017 9:12:18 PM
 */
public class PathContextImpl implements PathContext {

    @Override
    public String getPath(String filename) {
        try{
            final URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
            final String path;
            if(url == null) {
                path = new File(filename).getAbsolutePath();
            }else{
                path = Paths.get(url.toURI()).toAbsolutePath().toString(); 
            }
            return path;
        }catch(Exception e) {
            throw new RuntimeException(e);
        }    
    }
}
