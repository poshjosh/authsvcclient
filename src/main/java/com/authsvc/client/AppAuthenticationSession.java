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

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 26, 2017 11:14:38 PM
 */
public interface AppAuthenticationSession extends AuthenticationSession {
    
    void waitForInitializationToComplete(long intervalMillis, long timeoutMillis) throws InterruptedException;

    boolean isInitialized();
    
    void init(String app_name, String app_email, String app_pass, boolean createIfNone) 
            throws IOException, ParseException, AuthenticationException;

    boolean isServiceAvailable();

    boolean isError(Map response);

    Map authenticateUser(String user_email, String user_token) throws IOException, ParseException;

    Map authorizeApp() throws IOException, ParseException;

    Map authorizeUser(Map user) throws IOException, ParseException;

    Map createUser(Map user) throws IOException, ParseException;

    Map createUser(String user_email, String user_name, String user_pass, 
            boolean sendActivationMail, boolean activate) throws IOException, ParseException;

    Map deauthorizeUser(Map user) throws IOException, ParseException;

    Map editAppStatus() throws IOException, ParseException;

    Map editUserStatus(Map user) throws IOException, ParseException;

    Map getUser(Map userdetails) throws IOException, ParseException;

    Map getUser(String user_email, String user_pass, String user_name, boolean create) throws IOException, ParseException;

    Map loginUser(Map user) throws IOException, ParseException;

    /**
     * Sends a mail to the requester's email address and returns true if successful
     */
    Map requestUserPassword(Map user) throws IOException, ParseException;

    /**
     * Sends a mail to the requesters email address and returns true if successful
     */
    Map requestUserPassword(String emailAddress, String username) throws IOException, ParseException;
}
