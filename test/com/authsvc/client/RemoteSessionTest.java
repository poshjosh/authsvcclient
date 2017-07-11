package com.authsvc.client;

import com.authsvc.client.parameters.Authenticateuser;
import com.authsvc.client.parameters.Createuser;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Josh
 */
public class RemoteSessionTest {
    
    public RemoteSessionTest() {
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
            
            RemoteSession handler = new RemoteSession();
            handler.setTarget("http://localhost:8081/authsvc");
            
            final String app_email = "idiscoverynuroxltd@gmail.com";
            final String app_name = "iDiscovery";
            final String app_pass = "x86-1B234Y";        

            JSONObject createdApp = handler.createApp(app_email, app_pass, app_name);
print("Created app: ", createdApp);

            if(createdApp == null) {
                return;
            }
            
            final Object app_id = createdApp.get(Createuser.ParamName.appid.name());

            JSONObject editstatusResponse = handler.editAppStatus(createdApp);
print("Edit app status output: ", editstatusResponse);

            if(editstatusResponse == null) {
                return;
            }
            
            JSONObject appToken = handler.authorizeApp(createdApp);
print("App token: ", appToken);
            
            if(appToken == null) {
                return;
            }
            
            String user_email = "chinomsoikwuagwu@yahoo.com";
            String user_name = null;
            String user_pass = "1kjvdul-";
            boolean sendActivationMail = false;
            
            JSONObject createdUser = handler.createUser(
                    createdApp, appToken,
                    user_email, user_name, user_pass, sendActivationMail);
print("Created user: ", createdUser); 

            if(createdUser == null) {
                return;
            }
            
            editstatusResponse = handler.editUserStatus(createdApp, createdUser);
print("Edit user status output: ", editstatusResponse);

            if(editstatusResponse == null) {
                return;
            }
            
            JSONObject loginmsg = handler.loginUser(createdApp, createdUser);
            
print("Login user, output: ", loginmsg);

            if(loginmsg == null) {
                return;
            }

            JSONObject userToken = handler.authorizeUser(createdApp, createdUser);

print("User Authorization: ", userToken);

            if(userToken == null) {
                return;
            }

            String user_token = (String)userToken.get(Authenticateuser.ParamName.token.name());
            
            JSONObject authmsg = handler.authenticateUser(app_id, user_email, user_token);

print("User Authentication: ", authmsg);

            JSONObject returnedApp = handler.getApp(app_email, app_pass, app_name, false);
            
print("Returned app: ", returnedApp);

        }catch(IOException e) {
            e.printStackTrace();
        }catch(ParseException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void print(String key, JSONObject val) {
System.out.println(this.getClass().getName()+". "+key+"\n"+(val==null?null:val.toJSONString()));        
    }
}
