/*
 * Copyright 2019 NUROX Ltd.
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

package com.authsvc.client.net;

import com.bc.net.util.QueryParametersConverter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Chinomso Bassey Ikwuagwu on Jan 9, 2019 11:19:05 AM
 */
public class HttpClientImpl implements HttpClient {

    private transient static final Logger LOG = Logger.getLogger(HttpClientImpl.class.getName());
    
    private final Map<String, String> headers;

    private final QueryParametersConverter queryBuilder;

    private boolean readyToPopulate;
    
    public HttpClientImpl() {
        this.headers = new HashMap<>();
        this.queryBuilder = new QueryParametersConverter("&");
        this.reset();
    }

    @Override
    public HttpClient reset() {
        this.readyToPopulate = true;
        this.headers.clear();
        this.header("Accept-Charset", StandardCharsets.ISO_8859_1.name());
        return this;
    }

    public void makeReadyToPopulate() {
        if(!this.readyToPopulate) {
            this.readyToPopulate = true;
        }
    }

    @Override
    public Response get(String urlString) throws MalformedURLException, IOException {
        final URLConnection conn = this.openConnectionWithHeaders(urlString);
        return new ResponseImpl(conn);
    }

    @Override
    public HttpClient header(String name, String value) {
        this.makeReadyToPopulate();
        this.headers.put(name, value);
        return this;
    }

    @Override
    public String getHeader(String name, String resultIfNone) {
        return this.headers.getOrDefault(name, resultIfNone);
    }

    @Override
    public Response postForm(String urlString, Map params, boolean encode) throws IOException {
        
        final URLConnection conn = this.openConnectionWithHeaders(urlString);
        
        conn.setDoOutput(true);
        
        this.addParams(conn, params, encode);
        
        return new ResponseImpl(conn);
    }
    
    public URLConnection openConnectionWithHeaders(String urlString) throws IOException {
        
        if(this.getHeader("User-Agent", null) == null) {
            header("User-Agent", this.getClass().getName()+"/Version-1.0");
        }
        
        final URL url = new URL(urlString);
        
        final URLConnection conn = url.openConnection();

        this.addHeaders(conn);
        
        return conn;
    }
    
    public void addHeaders(URLConnection conn) {
        
        if(headers != null && !headers.isEmpty()) {
            
            for(String name : headers.keySet()) {
                
                conn.setRequestProperty(name, headers.get(name));
            }
        }
    }
    
    public void addParams(URLConnection conn, Map params, boolean encode) throws IOException {
        
        if(params != null && !params.isEmpty()) {
            
            final StringBuilder paramBuffer = new StringBuilder();
    
            final String charset = this.getHeader("Accept-Charset", StandardCharsets.ISO_8859_1.name());
            
            for(Object name : params.keySet()) {
                
                final Object value = params.get(name);
                
                this.appendParam(paramBuffer, name.toString(), value.toString(), charset, encode);
            }

            try(OutputStream output = conn.getOutputStream()) {
                
                this.write(paramBuffer, output, charset);
            }        
        }
    }

    public boolean appendParam(StringBuilder paramBuffer, String name, String value, String charset, boolean encode) {

        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        Objects.requireNonNull(charset);

        if(paramBuffer.length() > 0) {
            paramBuffer.append(this.queryBuilder.getSeparator());
        }
        
        final boolean appended = this.queryBuilder.appendQueryPair(
                name, value, paramBuffer, encode, charset);
        
        if(LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "Appended: {0}, {1} = {2}", new Object[]{appended, name, value});
        }
        
        return appended;
    }

    public void write(StringBuilder paramBuffer, OutputStream out, String charset) 
            throws UnsupportedEncodingException, IOException {
        
        this.readyToPopulate = false;
        
        try (final PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, charset), true)) {

            this.writeBuffer(writer, paramBuffer);
        }
    }

    public boolean writeBuffer(PrintWriter writer, StringBuilder buff) {
        if(buff.length() > 0) {
            writer.write(buff.toString());
            writer.flush();
            return true;
        }else{
            return false;
        }
    }
}
