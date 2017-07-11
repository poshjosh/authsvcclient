

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * @(#)RemoteUrlStream.java   16-Dec-2014 20:33:22
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
public class RemoteUrlStream {

    public RemoteUrlStream() { }

    public InputStream getInputStream(String urlString) throws IOException {
    
        return this.getInputStream(urlString, -1, -1);
    }
    
    public InputStream getInputStream(String urlString, int readTimeout, int connectTimeout) throws IOException {
        InputStream in = null;

debug("Creating url for: "+urlString);
        URL url = new URL(urlString);//We take an object of class URL

debug("Opening connection");        
        URLConnection conn = url.openConnection(); //Create a connection object and open the connection

        if(!(conn instanceof HttpURLConnection)) {
            throw new IOException("Not an Http connection");
        }
        try
        {
            HttpURLConnection httpConn = (HttpURLConnection) conn; //httpConn object is assigned the value of conn. Typecasting is done to avoid conflict.

            httpConn.setRequestProperty("Accept-Charset", "UTF-8");            
            if(readTimeout > -1) {
                httpConn.setReadTimeout(readTimeout);
            }
            if(connectTimeout > -1) {
                httpConn.setConnectTimeout(connectTimeout);
            }
            httpConn.setDoInput(true);
            
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
debug("Connecting");        

            httpConn.connect();
            
            int responseCode = httpConn.getResponseCode();
            
debug("Response code: "+responseCode);        

            if(responseCode >= 400) {
                in = httpConn.getErrorStream();
            }else{
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            
            throw new IOException("Error Connecting", ex);
        }
        return in;
    }

    protected void debug(CharSequence msg) {
//System.out.println(this.getClass().getName()+". DEBUG. "+msg);        
    }
}
