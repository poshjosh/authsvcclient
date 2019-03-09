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

import com.authsvc.client.net.HttpClient;
import com.authsvc.client.net.HttpClientImpl;
import com.authsvc.client.parameters.Createuser;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.text.ParseException;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 10:45:29 PM
 */
public class AppAuthenticationSessionImpl extends AuthenticationSessionImpl implements AppAuthenticationSession {
    
    private transient static final Logger logger = Logger.getLogger(AppAuthenticationSessionImpl.class.getName());
    
    private boolean initialized;
    
    private final AuthDetailsStore authDetailsStore;
    
    public AppAuthenticationSessionImpl(String svcEndPoint, 
            String appTokenFilename, String appDetailsFilename) {
        this(new HttpClientImpl(), 
                svcEndPoint, 
                new AuthDetailsLocalDiscStore(appTokenFilename, appDetailsFilename), 
                new JsonResponseIsErrorTestImpl());
    }
    
    public AppAuthenticationSessionImpl(
            String svcEndPoint, String dir,
            String appTokenFilename, String appDetailsFilename) {
        this(new HttpClientImpl(), 
                svcEndPoint, 
                new AuthDetailsLocalDiscStore(dir, appTokenFilename, appDetailsFilename), 
                new JsonResponseIsErrorTestImpl());
    }
    
    public AppAuthenticationSessionImpl(String svcEndPoint, 
            AuthDetailsLocalDiscStore.GetPathForName pathContext,
            String appTokenFilename, String appDetailsFilename) {
        this(new HttpClientImpl(), 
                svcEndPoint, 
                new AuthDetailsLocalDiscStore(pathContext, appTokenFilename, appDetailsFilename), 
                new JsonResponseIsErrorTestImpl());
    }

    public AppAuthenticationSessionImpl(
            HttpClient httpClient, String svcEndPoint,
            String directory, String appTokenFilename, String appDetailsFilename,
            Predicate<Map> responseIsErrorTest) {
        this(httpClient, 
                svcEndPoint, 
                new AuthDetailsLocalDiscStore(directory, appTokenFilename, appDetailsFilename),
                responseIsErrorTest);
    }
    
    public AppAuthenticationSessionImpl(HttpClient httpClient, String svcEndPoint,
            AuthDetailsStore authDetailsStore, Predicate<Map> responseIsErrorTest) {
        super(httpClient, svcEndPoint, responseIsErrorTest);
        this.authDetailsStore = Objects.requireNonNull(authDetailsStore);
    }
    
    @Override
    public synchronized void waitForInitializationToComplete(long intervalMillis, long timeoutMillis) throws InterruptedException {
        long spentMillis = 0;
        try{
            while(!this.isInitialized()) {
                this.wait(intervalMillis);
                spentMillis += intervalMillis;
                if(spentMillis >= timeoutMillis) {
                    break;
                }
            }
        }finally{
            this.notifyAll();
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
    
    @Override
    public void init(String app_name, String app_email, String app_pass, boolean createIfNone) 
            throws IOException, ParseException, AuthenticationException {

        Map appDetails = this.getAppDetails();
        
        logger.log(Level.FINE, "Loaded auth svc app, details: {0}", appDetails==null?null:appDetails.keySet()); 
        
        if(appDetails == null) {

            logger.log(Level.FINE, "Creating auth svc app for: {0}", app_name);
            
            try{
                appDetails = this.getApp(app_email, app_pass, app_name, createIfNone);
            }catch(ParseException e) {
                throw new ParseException("Error parsing application details from server response", e.getErrorOffset());
            }
            
            logger.log(Level.FINE, "Auth svc app, details: {0}", appDetails==null?null:appDetails.keySet());

            this.validateResponse(appDetails, app_name);
            
            this.setAppDetails(appDetails);

        }

        Map tokenDetails = this.getAppToken();
        logger.log(Level.FINE, "Loaded auth svc app token: {0}", tokenDetails==null?null:tokenDetails.keySet());

        if(tokenDetails == null) {

            logger.log(Level.FINER, "Authorizing auth svc app token for: {0}", app_name);
            
            try{
                
                tokenDetails = this.authorizeApp(appDetails);
          
                logger.log(Level.FINE, "Done authorizing auth svc app token: {0}", tokenDetails==null?null:tokenDetails.keySet());
                
            }catch(ParseException e) {
                throw new ParseException("Error parsing response from server for authorizing application named: " + app_name, e.getErrorOffset());
            }

            this.validateResponse(tokenDetails, app_name);

            this.setAppToken(tokenDetails);
        }    
        
        this.initialized = true;
    }
    
    @Override
    public boolean isServiceAvailable() {
//        final Map appDetails = this.getAppDetails();
//        return appDetails != null && !appDetails.isEmpty();
        return this.isInitialized();
    }
    
    @Override
    public Map getUser(
            Map userdetails) 
            throws IOException, ParseException {
        
        return this.getUser(this.getAppDetails(), this.getAppToken(), userdetails);
    }
    
    @Override
    public Map getUser(
            String user_email, 
            String user_pass, 
            String user_name,
            boolean create) 
            throws IOException, ParseException {
        
        
        return this.getUser(this.getAppDetails(), this.getAppToken(),
                user_email, user_pass, user_name, create);
    }
    
    @Override
    public Map createUser(Map user) 
            throws IOException, ParseException {
        
        return this.createUser(this.getAppDetails(), this.getAppToken(), user);
    }
    
    @Override
    public Map editAppStatus() 
            throws IOException, ParseException {
        
        return this.editAppStatus(this.getAppDetails());
    }
    
    @Override
    public Map authorizeApp() 
            throws IOException, ParseException {
        
        return this.authorizeApp(this.getAppDetails());
    }
    
    @Override
    public Map createUser(
            String user_email, 
            String user_name, 
            String user_pass,
            boolean sendActivationMail,
            boolean activate) 
            throws IOException, ParseException {

        return this.createUser(this.getAppId(), this.getTokenString(), 
                user_email, user_name, user_pass, sendActivationMail, activate);
    }
    
    @Override
    public Map editUserStatus(Map user) 
            throws IOException, ParseException {

        return this.editUserStatus(this.getAppDetails(), user);
    }
    
    @Override
    public Map loginUser(Map user
            ) throws IOException, ParseException {

        return loginUser(this.getAppDetails(), user);
    }
    
    @Override
    public Map authorizeUser(
            Map user
            ) throws IOException, ParseException {

        return this.authorizeUser(this.getAppDetails(), user);
    }

    @Override
    public Map deauthorizeUser(
            Map user
            ) throws IOException, ParseException {

        return this.deauthorizeUser(this.getAppDetails(), user);
    }
    
    @Override
    public Map authenticateUser(
            String user_email,
            String user_token
            ) throws IOException, ParseException {
        
        return this.authenticateUser(this.getAppDetails(), user_email, user_token);
    }

    @Override
    public Map requestUserPassword(Map user) throws IOException, ParseException {
        return this.requestUserPassword(this.getAppDetails(), user);
    }

    @Override
    public Map requestUserPassword(String emailAddress, String username) throws IOException, ParseException {
        return this.requestUserPassword(this.getAppId(), emailAddress, username);
    }

    public Object getAppId() {
        return this.getAppDetails() == null ? null : this.getAppDetails().get(Createuser.ParamName.appid.name());
    }
    
    public String getTokenString() {
        return (String)this.getAppToken().get(Createuser.ParamName.token.name());
    }
    
    public void setAppToken(Map tokenPair) {
        this.authDetailsStore.setAppToken(tokenPair);
    }
    
    public Map getAppToken() {
        return this.authDetailsStore.getAppToken();
    }

    public void setAppDetails(Map appDetails) {
        this.authDetailsStore.setAppDetails(appDetails);
    }
    
    public Map getAppDetails() {
        return this.authDetailsStore.getAppDetails();
    }
}
