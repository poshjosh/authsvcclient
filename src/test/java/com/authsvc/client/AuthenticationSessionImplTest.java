/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authsvc.client;

import com.authsvc.client.parameters.Authenticateuser;
import com.authsvc.client.parameters.Createuser;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import org.json.simple.JSONValue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Josh
 */
public class AuthenticationSessionImplTest {
    
    public AuthenticationSessionImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAll() {
        try{
            
            if(true) {
                System.out.println("========== Skipping test ==========");
                return;
            }
            
            final String serviceEndpoint = "http://localhost:8081/authsvc";
            final AuthenticationSession handler = new AuthenticationSessionImpl(serviceEndpoint);
            
            final String app_email = "idiscoverynuroxltd@gmail.com";
            final String app_name = "iDiscovery";
            final String app_pass = "x86-1B234Y";        

            
            final Map createdApp = handler.createApp(app_email, app_pass, app_name, false, true);
print("Created app: ", createdApp);

            if(createdApp == null) {
                return;
            }
            
            final Object app_id = createdApp.get(Createuser.ParamName.appid.name());

            final Map editAppStatusResponse = handler.editAppStatus(createdApp);
print("Edit app status output: ", editAppStatusResponse);

            if(editAppStatusResponse == null) {
                return;
            }
            
            final Map appToken = handler.authorizeApp(createdApp);
print("App token: ", appToken);
            
            if(appToken == null) {
                return;
            }
            
            final String user_email = "chinomsoikwuagwu@yahoo.com";
            final String user_name = null;
            final String user_pass = "1kjvdul-";
            final boolean sendActivationMail = false;
            final boolean activate = false;
            
            final Map createdUser = handler.createUser(
                    createdApp, appToken,
                    user_email, user_name, user_pass, 
                    sendActivationMail, activate);
print("Created user: ", createdUser); 

            if(createdUser == null) {
                return;
            }
            
            final Map editUserStatusResponse = handler.editUserStatus(createdApp, createdUser);
print("Edit user status output: ", editUserStatusResponse);

            if(editUserStatusResponse == null) {
                return;
            }
            
            final Map loginmsg = handler.loginUser(createdApp, createdUser);
            
print("Login user, output: ", loginmsg);

            if(loginmsg == null) {
                return;
            }

            final Map userToken = handler.authorizeUser(createdApp, createdUser);

print("User Authorization: ", userToken);

            if(userToken == null) {
                return;
            }

            String user_token = (String)userToken.get(Authenticateuser.ParamName.token.name());
            
            final Map authmsg = handler.authenticateUser(app_id, user_email, user_token);

print("User Authentication: ", authmsg);

            final Map returnedApp = handler.getApp(app_email, app_pass, app_name, false);
            
print("Returned app: ", returnedApp);

        }catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    private void print(String key, Map map) {
System.out.println(this.getClass().getName()+". "+key+"\n"+(map==null?null:JSONValue.toJSONString(map)));        
    }
}
