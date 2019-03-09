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

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Chinomso Bassey Ikwuagwu on Jan 9, 2019 10:56:42 AM
 */
public interface HttpClient {
    
    HttpClient reset();

    Response get(String url) throws IOException;
    
    default Response postForm(String url, Map params, String charset, boolean encode) throws IOException {
        this.formContentType(charset);
        return this.postForm(url, params, encode);
    }
    
    default HttpClient formContentType(String charset) {
        Objects.requireNonNull(charset);
        this.header("Accept-Charset", charset);
        this.header("Content-Type", "application/x-www-form-urlencoded;charset="+charset);
        return this;
    }
    
    Response postForm(String url, Map params, boolean encode) throws IOException;

    HttpClient header(String name, String value);
    
    String getHeader(String name, String resultIfNone);
}
