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

import com.authsvc.client.parameters.Authenticateuser;
import com.authsvc.client.parameters.Authorizeapp;
import com.authsvc.client.parameters.Authorizeuser;
import com.authsvc.client.parameters.Createapp;
import com.authsvc.client.parameters.Createuser;
import com.authsvc.client.parameters.Deauthorizeuser;
import com.authsvc.client.parameters.Editappstatus;
import com.authsvc.client.parameters.Edituserstatus;
import com.authsvc.client.parameters.Getapp;
import com.authsvc.client.parameters.Getuser;
import com.authsvc.client.parameters.Loginuser;
import com.authsvc.client.parameters.ParametersProvider;
import com.authsvc.client.parameters.Requestuserpassword;
import com.bc.io.CharFileIO;
import com.bc.net.Response;
import com.bc.net.impl.RequestBuilderImpl;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.text.ParseException;
import java.util.function.Predicate;
import org.json.simple.parser.JSONParser;
import com.bc.net.RequestBuilder;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 10:21:47 PM
 */
public class AuthenticationSessionImpl implements AuthenticationSession {
    
    private transient static final Logger LOG = Logger.getLogger(AuthenticationSessionImpl.class.getName());
    
    private final String serviceEndPoint;
    
    private final RequestBuilder connectionManager;
    
    private final Predicate<Map> responseIsErrorTest;

    private Response response;

    public AuthenticationSessionImpl(String svcEndPoint) {
        this(new RequestBuilderImpl(), svcEndPoint, new JsonResponseIsErrorTestImpl());
    }

    public AuthenticationSessionImpl(RequestBuilder connMgr, 
            String svcEndPoint, Predicate<Map> responseIsErrorTest) {
        this.connectionManager = Objects.requireNonNull(connMgr);
        this.serviceEndPoint = Objects.requireNonNull(svcEndPoint);
        this.responseIsErrorTest = Objects.requireNonNull(responseIsErrorTest);
    }
    
    @Override
    public Map getApp(
            String app_email, 
            String app_pass, 
            String app_name,
            boolean create) 
            throws IOException, ParseException {
    
        Getapp params = new Getapp();
        
        params.setEmailAddress(app_email);
        params.setUsername(app_name);
        params.setPassword(app_pass);
        params.setCreate(create);
        
if(LOG.isLoggable(Level.FINER)){
LOG.log(Level.FINER, "Created {0}", params.getClass().getName());
}
        
        return this.getJsonResponse(params);
    }

    @Override
    public Map getUser(
            Map app,
            Map appToken,
            Map user) 
            throws IOException, ParseException {
        
        String user_email = (String)user.get(Getuser.ParamName.emailaddress.name());
        String user_name = (String)user.get(Getuser.ParamName.username.name());
        String user_pass = (String)user.get(Getuser.ParamName.password.name());
        Object oval = user.get(Getuser.ParamName.create.name());
        boolean create = oval == null ? false : Boolean.parseBoolean(oval.toString());
        
//        LOG.fine(() -> "App: " + app + "\nApp token: " + appToken + "\nUser: " + user);
        
        return this.getUser(app, appToken, user_email, user_pass, user_name, create);
    }
    
    @Override
    public Map getUser(
            Map app,
            Map appToken,
            String user_email, 
            String user_pass, 
            String user_name,
            boolean create) 
            throws IOException, ParseException {
        
        return this.getUser(app.get(Createuser.ParamName.appid.name()), 
                appToken.get(Createuser.ParamName.token.name()).toString(), 
                user_email, user_pass, user_name, create);
    }
    
    @Override
    public Map getUser(
            Object app_id,
            String app_token,
            String user_email, 
            String user_pass, 
            String user_name,
            boolean create) 
            throws IOException, ParseException {
        
        Getuser params = new Getuser();
        
        params.setAppid(app_id);
        params.setToken(app_token);
        params.setEmailAddress(user_email);
        params.setUsername(user_name);
        params.setPassword(user_pass);
        
        params.setCreate(create);
        
        return this.getJsonResponse(params);
    }
    
    @Override
    public Map createApp(
            String app_email, 
            String app_pass, 
            String app_name,
            boolean sendActivationMail, 
            boolean activate) 
            throws IOException, ParseException {
        
        Createapp createapp = new Createapp();
        
        createapp.setEmailAddress(app_email);
        createapp.setUsername(app_name);
        createapp.setPassword(app_pass);
        createapp.setSendRegistrationMail(sendActivationMail);
        createapp.setActivateuser(activate);
        
        return this.getJsonResponse(createapp);
    }
    
    @Override
    public Map editAppStatus(Map app) 
            throws IOException, ParseException {
        
        String app_email = (String)app.get(Editappstatus.ParamName.emailaddress.name());
        String app_pass = (String)app.get(Editappstatus.ParamName.password.name());

if(LOG.isLoggable(Level.FINER)){
LOG.log(Level.FINER, "Editing status of app. Email: {0}, Pass: {1}", 
new Object[]{ app_email,  app_pass});
}

        return this.editAppStatus(app_email, app_pass);
    }
    
    @Override
    public Map editAppStatus(
            String app_email, String app_pass) 
            throws IOException, ParseException {
        
        Editappstatus editstatus = new Editappstatus();

        editstatus.setEmailaddress(app_email);
        editstatus.setPassword(app_pass);
        editstatus.setUserstatus(2);

        return this.getJsonResponse(editstatus);
    }
    
    @Override
    public Map authorizeApp(Map app) 
            throws IOException, ParseException {
        
        String app_email = (String)app.get(Editappstatus.ParamName.emailaddress.name());
        String app_pass = (String)app.get(Editappstatus.ParamName.password.name());
        
        return this.authorizeApp(app_email, app_pass);
    }
    
    @Override
    public Map authorizeApp(
            String app_email, String app_pass) 
            throws IOException, ParseException {
        
        Authorizeapp authapp = new Authorizeapp();
        authapp.setEmailAddress(app_email);
        authapp.setPassword(app_pass);

        return this.getJsonResponse(authapp);
    }

    @Override
    public Map createUser(
            Map app,
            Map appToken,
            Map user) 
            throws IOException, ParseException {
        
        String user_email = (String)user.get(Createuser.ParamName.emailaddress.name());
        String user_name = (String)user.get(Createuser.ParamName.username.name());
        String user_pass = (String)user.get(Createuser.ParamName.password.name());
        Object oval = user.get(Createuser.ParamName.sendregistrationmail.name());
        final boolean sendMail = oval == null ? false : Boolean.parseBoolean(oval.toString());
        oval = user.get(Createuser.ParamName.activateuser.name());
        final boolean activate = oval == null ? false : Boolean.parseBoolean(oval.toString());
        
        return this.createUser(app, appToken, user_email, user_name, user_pass, sendMail, activate);
    }
    
    @Override
    public Map createUser(
            Map app,
            Map appToken,
            String user_email, 
            String user_name, 
            String user_pass,
            boolean sendActivationMail,
            boolean activate) 
            throws IOException, ParseException {

        Object app_id = app.get(Createuser.ParamName.appid.name());
        String app_token = (String)appToken.get(Createuser.ParamName.token.name());
        
        return this.createUser(app_id, app_token, user_email, 
                user_name, user_pass, sendActivationMail, activate);
    }
    
    @Override
    public Map createUser(
            Object app_id,
            String app_token,
            String user_email, 
            String user_name, 
            String user_pass,
            boolean sendActivationMail,
            boolean activate) 
            throws IOException, ParseException {
        
        Createuser createuser = new Createuser();
        createuser.setAppid(app_id);
        createuser.setEmailAddress(user_email);
        createuser.setPassword(user_pass);
        createuser.setSendRegistrationMail(sendActivationMail);
        createuser.setToken(app_token);
        if(user_name != null) {
            createuser.setUsername(user_name);
        }
        createuser.setActivateuser(activate);
        return this.getJsonResponse(createuser);
    }

    @Override
    public Map editUserStatus(
            Map app,
            Map user) 
            throws IOException, ParseException {

        Object app_id = app.get(Edituserstatus.ParamName.appid.name());
        String user_email = (String)user.get(Edituserstatus.ParamName.emailaddress.name());
        String user_pass = (String)user.get(Edituserstatus.ParamName.password.name());
        
        return this.editUserStatus(app_id, user_email, user_pass);
    }
    
    @Override
    public Map editUserStatus(
            Object app_id,
            String user_email, String user_pass) 
            throws IOException, ParseException {
        
        Edituserstatus editstatus = new Edituserstatus();

        editstatus.setAppid(app_id);
        editstatus.setEmailaddress(user_email);
        editstatus.setPassword(user_pass);
        editstatus.setUserstatus(2);

        return this.getJsonResponse(editstatus);
    }
    
    @Override
    public Map loginUser(
            Map app,
            Map user
            ) throws IOException, ParseException {

        Object app_id = app.get(Createuser.ParamName.appid.name());
        String user_email = (String)user.get(Createuser.ParamName.emailaddress.name());
        String user_pass = (String)user.get(Createuser.ParamName.password.name());
        
        return loginUser(app_id, user_email, user_pass);
    }
    
    @Override
    public Map loginUser(
            Object app_id,
            String user_email,
            String user_pass
            ) throws IOException, ParseException {
        
        Loginuser loginuser = new Loginuser();
        
        loginuser.setAppid(app_id);
        loginuser.setEmailAddress(user_email);
        loginuser.setPassword(user_pass);
        
        return this.getJsonResponse(loginuser);
    }

    @Override
    public Map authorizeUser(
            Map app,
            Map user) throws IOException, ParseException {
        
        return this.authorizeUser(app, user, true);
    }

    @Override
    public Map deauthorizeUser(
            Map app,
            Map user) throws IOException, ParseException {
        
        return this.authorizeUser(app, user, false);
    }
    
    @Override
    public Map authorizeUser(
            Map app,
            Map user,
            boolean authorize) throws IOException, ParseException {

        Object app_id = app.get(Createuser.ParamName.appid.name());
        String user_email = (String)user.get(Createuser.ParamName.emailaddress.name());
        String user_pass = (String)user.get(Createuser.ParamName.password.name());
        
        if(authorize) {
            return authorizeUser(app_id, user_email, user_pass);
        }else{
            return deauthorizeUser(app_id, user_email, user_pass);
        }
    }

    @Override
    public Map authorizeUser(
            Object app_id,
            String user_email,
            String user_pass) throws IOException, ParseException {
        
        return this.authorizeUser(app_id, user_email, user_pass, true);
    }
    
    @Override
    public Map deauthorizeUser(
            Object app_id,
            String user_email,
            String user_pass) throws IOException, ParseException {
        
        return this.authorizeUser(app_id, user_email, user_pass, false);
    }
    
    @Override
    public Map authorizeUser(
            Object app_id,
            String user_email,
            String user_pass,
            boolean authorize) throws IOException, ParseException {
        
        Authorizeuser authuser;
        if(authorize) {
            authuser = new Authorizeuser();
        }else{
            authuser = new Deauthorizeuser();
        }
        
        authuser.setAppid(app_id);
        authuser.setEmailAddress(user_email);
        authuser.setPassword(user_pass);
        
        return this.getJsonResponse(authuser);
    }
    
    @Override
    public Map authenticateUser(
            Map app,
            String user_email,
            String user_token
            ) throws IOException, ParseException {
        
        Object app_id = app.get(Createuser.ParamName.appid.name());
        
        return this.authenticateUser(app_id, user_email, user_token);
    }
    
    @Override
    public Map authenticateUser(
            Object app_id,
            String user_email,
            String user_token
            ) throws IOException, ParseException {
        
        Authenticateuser authuser = new Authenticateuser();
        
        authuser.setAppid(app_id);
        authuser.setEmailAddress(user_email);
        authuser.setToken(user_token);
        
        return this.getJsonResponse(authuser);
    }
    
    /**
     * Sends a mail to the requester's email address and returns true if successful
     */
    @Override
    public Map requestUserPassword(
            Map app,
            Map user) 
            throws IOException, ParseException {
        
        Object app_id = app.get(Getuser.ParamName.appid.name());
        String user_email = (String)user.get(Getuser.ParamName.emailaddress.name());
        String user_name = (String)user.get(Getuser.ParamName.username.name());
        
        return this.requestUserPassword(app_id, user_email, user_name);
    }
    
    /**
     * Sends a mail to the requesters email address and returns true if successful
     */
    @Override
    public Map requestUserPassword(
            Object app_id, 
            String emailAddress,
            String username) 
            throws IOException, ParseException {
        
        Requestuserpassword params = new Requestuserpassword();
        
        params.setAppid(app_id);
        params.setUsername(username);
        params.setEmailAddress(emailAddress);
        
        return this.getJsonResponse(params);
    }
    
    public Map getJsonResponse(ParametersProvider provider) 
            throws IOException, ParseException {
        
        return this.getJsonResponse(
                provider.getServletPath(), 
                provider.getParameters());
    }
    
    public Map getJsonResponse(
            String servletpath, Map<String, String> params) 
            throws IOException, ParseException {

        URL url = new URL(this.getUrl(servletpath));
//System.out.println("URL: "+url+". "+this.getClass());
//System.out.println("Params: "+params+". "+this.getClass());
        
        if(LOG.isLoggable(Level.FINE)){
            LOG.log(Level.FINE, "Getting input stream from {0}\nParams: {1}", new Object[]{url,  params});
        }

        final String charset = StandardCharsets.UTF_8.name();
        
       this.response = this.connectionManager
                .formContentType(charset)
                .url(url)
                .body()
                .params(params, true)
                .back()
                .response();
        
        final InputStream in = response.getInputStream();
        
        try{
            
            final Map jsonResponse = this.getJsonResponse(in);
            
            LOG.fine(() -> "Response: " + jsonResponse);
            
            return jsonResponse;
            
        }finally{
            if(in != null) {
                try{ in.close(); } 
                catch(IOException e) {
                    LOG.log(Level.WARNING, "Error closing input stream from: " + url, e);
                }
            }
        }
    }
    
    public Map getJsonResponse(InputStream in) throws IOException, ParseException {
//        Reader r = new InputStreamReader(in, "utf-8");
        String str = new CharFileIO().readChars(in).toString();
//System.out.println("======================================================="+this.getClass());        
//System.out.println(str);
//System.out.println("======================================================="+this.getClass());
        JSONParser parser = new JSONParser();
        try{
            return (Map)parser.parse(str);
        }catch(org.json.simple.parser.ParseException e) {
            if(LOG.isLoggable(Level.WARNING)){
                  LOG.log(Level.WARNING, "Error parsing respnose", e);
            }
            throw new ParseException("Error parsing response", parser.getPosition());
        }finally{
//            close(r);
        }
    }
    
    public String getUrl(String servletpath) {
        if(!servletpath.startsWith("/")) {
            servletpath = "/" + servletpath;
        }
        return this.getServiceEndPoint() + servletpath;
    }
    
    public Map<String, String> getParameters(String appId, String email, String pass, String user) {
        Map<String, String> params = new HashMap<>(4, 1.0f);
        if(appId != null) {
            params.put(com.authsvc.client.parameters.Createuser.ParamName.appid.name(), appId);
        }
        params.put(com.authsvc.client.parameters.Createuser.ParamName.emailaddress.name(), email);
        if(pass != null) {
            params.put(com.authsvc.client.parameters.Createuser.ParamName.password.name(), pass);
        }
        if(user != null) {
            params.put(com.authsvc.client.parameters.Createuser.ParamName.username.name(), user);
        }
        return params;
    }

    @Override
    public void validateResponse(Map response, String app_name) throws AuthenticationException {
        final String msg;
        if(response != null && !response.isEmpty()) {
            msg = response.values().iterator().next().toString();
        }else{
            msg = this.getResponse().getMessage();
        }
        final int responseCode = this.getResponse().getCode();

        if(responseCode != 200 && // OK
                responseCode != 201) { // CREATED
            
            throw new AuthenticationException("Failed to create authentication session for "+app_name+" app. Server response: " + msg);
        }
        
        if(this.isError(response)) {
            throw new AuthenticationException("Failed to create authentication session for "+app_name+" app. Server response: " + msg);
        }
    }

    @Override
    public boolean isError(Map response) {
        return response == null || this.responseIsErrorTest.test(response);
    }

    @Override
    public Response getResponse() {
        return response;
    }
    
    @Override
    public final RequestBuilder getConnectionManager() {
        return connectionManager;
    }
    
    @Override
    public final String getServiceEndPoint() {
        return serviceEndPoint;
    }
}
