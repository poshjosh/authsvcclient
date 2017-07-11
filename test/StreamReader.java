

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


/**
 * @(#)StreamReadee.java   16-Dec-2014 20:51:35
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
public class StreamReader {

    public String readContents(InputStream is) throws IOException {
        
        if(is == null) {
            throw new NullPointerException();
        }
        
        InputStreamReader isr = null;
        
        BufferedReader br = null;
        
        try{
    
debug("Reading contents of response");
            isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            
            br = new BufferedReader(isr);
            
            String jsonText = readAll(br);
            
debug("Size read: "+(jsonText==null?null:jsonText.length())); 

            return jsonText;
            
        }finally{
            
            close(br);
            
            close(isr);
        }
    }
    
    private String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        String cp;
        
//        int i = 0;
        
        while ((cp = rd.readLine()) != null) {
            
//            this.publishProgress(++i);
            
            sb.append(cp);
        }
        return sb.toString();
    }
    
    public void close(Closeable cl) {
        if(cl != null) {
            try{
                cl.close();
            }catch(IOException e) { 
                e.printStackTrace();
            }
        }
    }

    protected void debug(CharSequence msg) {
        
    }
}
