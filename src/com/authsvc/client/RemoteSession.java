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
import com.bc.net.ConnectionManager;
import com.bc.util.XLogger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * @(#)RemoteSession.java   22-Jan-2015 12:15:31
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
public class RemoteSession extends ConnectionManager {
    
    private String target;

    public RemoteSession() {
        this.setAddCookies(true);
        this.setGetCookies(true);
    }
    
    public RemoteSession(String target) {
        this.target = target;
        this.setAddCookies(true);
        this.setGetCookies(true);
    }

    public RemoteSession(String target, int maxRetrials, long retrialIntervals) {
        super(maxRetrials, retrialIntervals);
        this.setAddCookies(true);
        this.setGetCookies(true);
    }
    
    public JSONObject getApp(
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
        
XLogger.getInstance().log(Level.FINER, "Created {0}", this.getClass(), params.getClass().getName());
        
        return this.getJsonResponse(params);
    }

    public JSONObject getUser(
            Map app,
            Map appToken,
            Map user) 
            throws IOException, ParseException {
        
        String user_email = (String)user.get(Getuser.ParamName.emailaddress.name());
        String user_name = (String)user.get(Getuser.ParamName.username.name());
        String user_pass = (String)user.get(Getuser.ParamName.password.name());
        Object oval = user.get(Getuser.ParamName.create.name());
        boolean create = oval == null ? false : Boolean.parseBoolean(oval.toString());
        
        return this.getUser(app, appToken, user_email, user_pass, user_name, create);
    }
    
    public JSONObject getUser(
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
    
    public JSONObject getUser(
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
    
    public JSONObject createApp(
            String app_email, 
            String app_pass, 
            String app_name) 
            throws IOException, ParseException {
        
        Createapp createapp = new Createapp();
        
        createapp.setEmailAddress(app_email);
        createapp.setUsername(app_name);
        createapp.setPassword(app_pass);
        createapp.setSendRegistrationMail(false);
        
        return this.getJsonResponse(createapp);
    }
    
    public JSONObject editAppStatus(Map app) 
            throws IOException, ParseException {
        
        String app_email = (String)app.get(Editappstatus.ParamName.emailaddress.name());
        String app_pass = (String)app.get(Editappstatus.ParamName.password.name());

XLogger.getInstance().log(Level.FINER, "Editing status of app. Email: {0}, Pass: {1}", 
        this.getClass(), app_email, app_pass);

        return this.editAppStatus(app_email, app_pass);
    }
    
    public JSONObject editAppStatus(
            String app_email, String app_pass) 
            throws IOException, ParseException {
        
        Editappstatus editstatus = new Editappstatus();

        editstatus.setEmailaddress(app_email);
        editstatus.setPassword(app_pass);
        editstatus.setUserstatus(2);

        return this.getJsonResponse(editstatus);
    }
    
    public JSONObject authorizeApp(Map app) 
            throws IOException, ParseException {
        
        String app_email = (String)app.get(Editappstatus.ParamName.emailaddress.name());
        String app_pass = (String)app.get(Editappstatus.ParamName.password.name());
        
        return this.authorizeApp(app_email, app_pass);
    }
    
    public JSONObject authorizeApp(
            String app_email, String app_pass) 
            throws IOException, ParseException {
        
        Authorizeapp authapp = new Authorizeapp();
        authapp.setEmailAddress(app_email);
        authapp.setPassword(app_pass);

        return this.getJsonResponse(authapp);
    }

    public JSONObject createUser(
            Map app,
            Map appToken,
            Map user) 
            throws IOException, ParseException {
        
        String user_email = (String)user.get(Getuser.ParamName.emailaddress.name());
        String user_name = (String)user.get(Getuser.ParamName.username.name());
        String user_pass = (String)user.get(Getuser.ParamName.password.name());
        Object oval = user.get(Createuser.ParamName.sendregistrationmail.name());
        boolean sendMail = oval == null ? false : Boolean.parseBoolean(oval.toString());
        
        return this.createUser(app, appToken, user_email, user_name, user_pass, sendMail);
    }
    
    public JSONObject createUser(
            Map app,
            Map appToken,
            String user_email, 
            String user_name, 
            String user_pass,
            boolean sendActivationMail) 
            throws IOException, ParseException {

        Object app_id = app.get(Createuser.ParamName.appid.name());
        String app_token = (String)appToken.get(Createuser.ParamName.token.name());
        
        return this.createUser(app_id, app_token, user_email, user_name, user_pass, sendActivationMail);
    }
    
    public JSONObject createUser(
            Object app_id,
            String app_token,
            String user_email, 
            String user_name, 
            String user_pass,
            boolean sendActivationMail) 
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

        return this.getJsonResponse(createuser);
    }

    public JSONObject editUserStatus(
            Map app,
            Map user) 
            throws IOException, ParseException {

        Object app_id = app.get(Edituserstatus.ParamName.appid.name());
        String user_email = (String)user.get(Edituserstatus.ParamName.emailaddress.name());
        String user_pass = (String)user.get(Edituserstatus.ParamName.password.name());
        
        return this.editUserStatus(app_id, user_email, user_pass);
    }
    
    public JSONObject editUserStatus(
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
    
    public JSONObject loginUser(
            Map app,
            Map user
            ) throws IOException, ParseException {

        Object app_id = app.get(Createuser.ParamName.appid.name());
        String user_email = (String)user.get(Createuser.ParamName.emailaddress.name());
        String user_pass = (String)user.get(Createuser.ParamName.password.name());
        
        return loginUser(app_id, user_email, user_pass);
    }
    
    public JSONObject loginUser(
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

    public JSONObject authorizeUser(
            Map app,
            Map user) throws IOException, ParseException {
        
        return this.authorizeUser(app, user, true);
    }

    public JSONObject deauthorizeUser(
            Map app,
            Map user) throws IOException, ParseException {
        
        return this.authorizeUser(app, user, false);
    }
    
    public JSONObject authorizeUser(
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

    public JSONObject authorizeUser(
            Object app_id,
            String user_email,
            String user_pass) throws IOException, ParseException {
        
        return this.authorizeUser(app_id, user_email, user_pass, true);
    }
    
    public JSONObject deauthorizeUser(
            Object app_id,
            String user_email,
            String user_pass) throws IOException, ParseException {
        
        return this.authorizeUser(app_id, user_email, user_pass, false);
    }
    
    public JSONObject authorizeUser(
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
    
    public JSONObject authenticateUser(
            Map app,
            String user_email,
            String user_token
            ) throws IOException, ParseException {
        
        Object app_id = app.get(Createuser.ParamName.appid.name());
        
        return this.authenticateUser(app_id, user_email, user_token);
    }
    
    public JSONObject authenticateUser(
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
    public JSONObject requestUserPassword(
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
    public JSONObject requestUserPassword(
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
    
    public boolean isError(Map json) {
        //@related error message format. Don't change this without changing all related
        //
        boolean error = false;
        for(Object key:json.keySet()) {
            if(key.toString().toLowerCase().contains("error")) {
                error = true;
                break;
            }
        }
        return error;
    }

    public String getErrorMessage(Map json) {
        //@related error message format. Don't change this without changing all related
        //
        Object error = null;
        for(Object key:json.keySet()) {
            if(key.toString().toLowerCase().contains("error")) {
                error = json.get(key);
                break;
            }
        }
        return error == null ? null : error.toString();
    }
    
    private JSONObject getJsonResponse(ParametersProvider provider) 
            throws IOException {
        
        return this.getJsonResponse(
                provider.getServletPath(), 
                provider.getParameters());
    }
    
    public JSONObject getJsonResponse(
            String servletpath, Map<String, String> params) 
            throws IOException {

        URL url = new URL(this.getUrl(servletpath));
//System.out.println("URL: "+url+". "+this.getClass());
//System.out.println("Params: "+params+". "+this.getClass());
        
XLogger.getInstance().log(Level.FINER, "Getting input stream from {0}, Parameters: {1}", this.getClass(), url, params);
        
        final InputStream in = this.getInputStreamForUrlEncodedForm(url, params, true);
        
        try{
            
            return this.getJsonResponse(in);
            
        }finally{

            close(in);
        }
    }
    
    public JSONObject getJsonResponse(InputStream in) throws IOException {
//        Reader r = new InputStreamReader(in, "utf-8");
        String str = new CharFileIO().readChars(in).toString();
//System.out.println("======================================================="+this.getClass());        
//System.out.println(str);
//System.out.println("======================================================="+this.getClass());
        JSONParser parser = new JSONParser();
        try{
            return (JSONObject)parser.parse(str);
        }catch(ParseException e) {
XLogger.getInstance().log(Level.FINE, "{0}", this.getClass(), str);
            throw new IOException("Unexpected output format", e);
        }finally{
//            close(r);
        }
    }
    
    public String getUrl(String servletpath) {
        if(!servletpath.startsWith("/")) {
            servletpath = "/" + servletpath;
        }
        return this.getUrl() + servletpath;
    }
    
    public String getUrl() {
        return target;
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
    
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
