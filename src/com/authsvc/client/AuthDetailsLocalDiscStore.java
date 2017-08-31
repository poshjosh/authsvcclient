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

import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 9:36:02 PM
 */
public class AuthDetailsLocalDiscStore implements AuthDetailsStore {
    
    public static interface PathContext {
        String getPath(String filename);
    }
    
    public static class DirPathContext implements PathContext{
        private final String dir;
        public DirPathContext(String dir) {
            this.dir = Objects.requireNonNull(dir);
        }
        @Override
        public String getPath(String filename) {
            return Paths.get(dir, filename).toString().replace('\\', '/');
        }
    }
    
    private class AuthDetailsIO extends com.bc.io.IOWrapper<Map> {
        public AuthDetailsIO(String filename) {
            super(null, filename);
        }
        @Override
        public String getPath(String fileName) {
            return AuthDetailsLocalDiscStore.this.pathContext.getPath(fileName);
        }
    }

    private final AuthDetailsIO appTokenIO;

    private final AuthDetailsIO appDetailsIO;

    private final PathContext pathContext;

    public AuthDetailsLocalDiscStore(String tokenFname, String detailsFname) {
        this(new PathContextImpl(), tokenFname, detailsFname);
    }
    
    public AuthDetailsLocalDiscStore(
            String dir, String tokenFname, String detailsFname) {
        this(new DirPathContext(dir), tokenFname, detailsFname);    
    }
    
    public AuthDetailsLocalDiscStore(
            PathContext pathContext, String tokenFname, String detailsFname) {
        this.pathContext = Objects.requireNonNull(pathContext);
        this.appTokenIO = new AuthDetailsIO(tokenFname);
        this.appDetailsIO = new AuthDetailsIO(detailsFname);
    }
    
    @Override
    public void setAppToken(Map tokenPair) {
        this.appTokenIO.setTarget(tokenPair);
    }

    @Override
    public Map getAppToken() {
        return (Map)this.appTokenIO.getTarget();
    }

    @Override
    public void setAppDetails(Map appDetails) {
        this.appDetailsIO.setTarget(appDetails);
    }

    @Override
    public Map getAppDetails() {
        return (Map)this.appDetailsIO.getTarget();
    }
    
    public String getPath(String filename) {
        return this.pathContext.getPath(filename);
    }
}
