package com.authsvc.client;

import com.authsvc.client.parameters.Createuser;
import com.bc.util.XLogger;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * @(#)AuthSvc.java   17-Jan-2015 15:17:57
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 * @deprecated Rather use {@link com.authsvc.client.AppAuthenticationSessionImpl AppAuthenticationSessionImpl}
 */
@Deprecated
public class AuthSvcSession extends RemoteSession implements AuthDetailsStore {
    
    private final AuthDetailsStore appAuthDetails;
    
    public AuthSvcSession(AuthDetailsStore store, String target, int maxRetrials, long retrialInterval) {
        super(target, maxRetrials, retrialInterval);
        this.appAuthDetails = Objects.requireNonNull(store);
    }
    
    public void init(
            String authsvc_url, String app_name, String app_email, String app_pass) 
            throws IOException {

final long mb4 = com.bc.util.Util.availableMemory();        
final long tb4 = System.currentTimeMillis();  
        this.setTarget(authsvc_url);

        Map appDetails = this.getAppDetails();
        
        if(appDetails == null) {

XLogger.getInstance().log(Level.FINE, "Creating auth svc app for: {0}", this.getClass(), app_name);
            try{
                // Create the app if it does not exist
                boolean create = true;
                appDetails = this.getApp(app_email, app_pass, app_name, create);
// Don't write app details
//XLogger.getInstance().log(Level.INFO, "Auth svc app, details: {0}", this.getClass(), appDetails);
            }catch(ParseException e) {
                throw new IOException("Unexpected response format from server: "+this.getResponseMessage(), e);
            }

            this.validateResponse(appDetails, app_name);
            
            this.setAppDetails(appDetails);

        }

        Map tokenDetails = this.getAppToken();

        if(tokenDetails == null) {

XLogger.getInstance().log(Level.FINE, "Authorizing auth svc app token for: {0}", this.getClass(), app_name);
            
            try{
                tokenDetails = this.authorizeApp(appDetails);
// Don't write tokens            
//XLogger.getInstance().log(Level.INFO, "Done authorizing auth svc app token: {0}", this.getClass(), tokenDetails);
                
            }catch(ParseException e) {
                throw new IOException("Unexpected response format from server: "+this.getResponseMessage(), e);
            }

            this.validateResponse(tokenDetails, app_name);

            this.setAppToken(tokenDetails);
  
XLogger.getInstance().log(Level.INFO, "Done initializing {0}, Spent time: {1}, memory: {2}", this.getClass(), 
this.getClass().getName(), System.currentTimeMillis()-tb4, com.bc.util.Util.usedMemory(mb4));
        }        
    }
    
    public void validateResponse(Map response, String app_name) {
        
        int responseCode = this.getResponseCode();
        
        if(responseCode != 200 && // OK
                responseCode != 201) { // CREATED
            
            throw new UnsupportedOperationException("Failed to create authsvc "+app_name+" app. Server response: "+this.getResponseMessage());
        }
        if(response == null || this.isError(response)) {
            throw new UnsupportedOperationException("Failed to create authsvc "+app_name+" app. Server response: "+this.getResponseMessage());
        }
    }
    
    public JSONObject getUser(
            Map userdetails) 
            throws IOException, ParseException {
        
        return this.getUser(this.getAppDetails(), this.getAppToken(), userdetails);
    }
    
    public JSONObject getUser(
            String user_email, 
            String user_pass, 
            String user_name,
            boolean create) 
            throws IOException, ParseException {
        
        
        return this.getUser(this.getAppDetails(), this.getAppToken(),
                user_email, user_pass, user_name, create);
    }
    
    public JSONObject createUser(Map user) 
            throws IOException, ParseException {
        
        return this.createUser(this.getAppDetails(), this.getAppToken(), user);
    }
    
    public JSONObject editAppStatus() 
            throws IOException, ParseException {
        
        return this.editAppStatus(this.getAppDetails());
    }
    
    public JSONObject authorizeApp() 
            throws IOException, ParseException {
        
        return this.authorizeApp(this.getAppDetails());
    }
    
    public JSONObject createUser(
            String user_email, 
            String user_name, 
            String user_pass,
            boolean sendActivationMail) 
            throws IOException, ParseException {

        return this.createUser(this.getAppId(), this.getTokenString(), user_email, user_name, user_pass, sendActivationMail);
    }
    
    public JSONObject editUserStatus(Map user) 
            throws IOException, ParseException {

        return this.editUserStatus(this.getAppDetails(), user);
    }
    
    public JSONObject loginUser(Map user
            ) throws IOException, ParseException {

        return loginUser(this.getAppDetails(), user);
    }
    
    public JSONObject authorizeUser(
            Map user
            ) throws IOException, ParseException {

        return this.authorizeUser(this.getAppDetails(), user);
    }

    public JSONObject deauthorizeUser(
            Map user
            ) throws IOException, ParseException {

        return this.deauthorizeUser(this.getAppDetails(), user);
    }
    
    public JSONObject authenticateUser(
            String user_email,
            String user_token
            ) throws IOException, ParseException {
        
        return this.authenticateUser(this.getAppDetails(), user_email, user_token);
    }

    public Object getAppId() {
        return this.getAppDetails() == null ? null : this.getAppDetails().get(Createuser.ParamName.appid.name());
    }
    
    public String getTokenString() {
        return (String)this.getAppToken().get(Createuser.ParamName.token.name());
    }
    
    @Override
    public void setAppToken(Map tokenPair) {
        this.appAuthDetails.setAppToken(tokenPair);
    }
    
    @Override
    public Map getAppToken() {
        return this.appAuthDetails.getAppToken();
    }

    @Override
    public void setAppDetails(Map appDetails) {
        this.appAuthDetails.setAppDetails(appDetails);
    }
    
    @Override
    public Map getAppDetails() {
        return this.appAuthDetails.getAppDetails();
    }
}
