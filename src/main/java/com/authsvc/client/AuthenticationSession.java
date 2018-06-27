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

import com.bc.net.Response;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import com.bc.net.RequestBuilder;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 10:36:55 PM
 */
public interface AuthenticationSession {

    Map authenticateUser(Map app, String user_email, String user_token) throws IOException, ParseException;

    Map authenticateUser(Object app_id, String user_email, String user_token) throws IOException, ParseException;

    Map authorizeApp(Map app) throws IOException, ParseException;

    Map authorizeApp(String app_email, String app_pass) throws IOException, ParseException;

    Map authorizeUser(Map app, Map user) throws IOException, ParseException;

    Map authorizeUser(Map app, Map user, boolean authorize) throws IOException, ParseException;

    Map authorizeUser(Object app_id, String user_email, String user_pass) throws IOException, ParseException;

    Map authorizeUser(Object app_id, String user_email, String user_pass, boolean authorize) throws IOException, ParseException;

    Map createApp(String app_email, String app_pass, String app_name,
            boolean sendActivationMail, boolean activate) 
            throws IOException, ParseException;

    Map createUser(Map app, Map appToken, Map user) throws IOException, ParseException;

    Map createUser(Map app, Map appToken, String user_email, String user_name, String user_pass, 
            boolean sendActivationMail, boolean activate) throws IOException, ParseException;

    Map createUser(Object app_id, String app_token, String user_email, 
            String user_name, String user_pass, 
            boolean sendActivationMail, boolean activate) throws IOException, ParseException;

    Map deauthorizeUser(Map app, Map user) throws IOException, ParseException;

    Map deauthorizeUser(Object app_id, String user_email, String user_pass) throws IOException, ParseException;

    Map editAppStatus(Map app) throws IOException, ParseException;

    Map editAppStatus(String app_email, String app_pass) throws IOException, ParseException;

    Map editUserStatus(Map app, Map user) throws IOException, ParseException;

    Map editUserStatus(Object app_id, String user_email, String user_pass) throws IOException, ParseException;

    Map getApp(String app_email, String app_pass, String app_name, boolean create) throws IOException, ParseException;

    RequestBuilder getConnectionManager();
    
    Response getResponse();

    void validateResponse(Map response, String app_name) throws AuthenticationException;

    boolean isError(Map response);
           
    String getServiceEndPoint();

    Map getUser(Map app, Map appToken, Map user) throws IOException, ParseException;

    Map getUser(Map app, Map appToken, String user_email, String user_pass, String user_name, boolean create) throws IOException, ParseException;

    Map getUser(Object app_id, String app_token, String user_email, String user_pass, String user_name, boolean create) throws IOException, ParseException;

    Map loginUser(Map app, Map user) throws IOException, ParseException;

    Map loginUser(Object app_id, String user_email, String user_pass) throws IOException, ParseException;

    /**
     * Sends a mail to the requester's email address and returns true if successful
     */
    Map requestUserPassword(Map app, Map user) throws IOException, ParseException;

    /**
     * Sends a mail to the requesters email address and returns true if successful
     */
    Map requestUserPassword(Object app_id, String emailAddress, String username) throws IOException, ParseException;
}
