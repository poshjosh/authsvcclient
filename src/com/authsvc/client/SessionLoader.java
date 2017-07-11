package com.authsvc.client;

import com.bc.util.Util;
import com.bc.util.XLogger;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * @(#)SessionLoader.java   11-Apr-2015 06:47:55
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
public class SessionLoader {
    
    private int maxRetrials;
    
    private int retrialInterval;
    
    public SessionLoader() { 
        maxRetrials = 2;
        retrialInterval = (int)TimeUnit.MINUTES.toMillis(10);
    }

    public void onLoad(AuthSvcSession session) { }
    
    public void loadAfter(
            long delay, TimeUnit timeUnit, final String svc_url, 
            final String app_name, final String app_email, final String app_pass) {
        
        // This depends on a web service else where. It may take
        // some time starting up. So we schedule it.
        //
        final ScheduledExecutorService svc = Executors.newSingleThreadScheduledExecutor();
        
        svc.schedule(new Runnable(){
            @Override
            public void run() {
                
                try{
                    
                    AuthSvcSession authSvcSession = SessionLoader.this.load(svc_url, app_name, app_email, app_pass);
                    
                    SessionLoader.this.onLoad(authSvcSession);
                    
                }catch(Exception e) {
                    
                    XLogger.getInstance().log(Level.WARNING, 
                     "Failed to initialize authentication service", this.getClass(), e);
                    
                }finally{
                    Util.shutdownAndAwaitTermination(svc, 1, TimeUnit.SECONDS);
                }
            }
        }, delay, timeUnit); 
    }

    public AuthSvcSession load(
            String svc_url, String app_name, String app_email, String app_pass) 
            throws IOException {
        
        AuthSvcSession authSvcSession = this.getNewSession(null, maxRetrials, retrialInterval);

        authSvcSession.init(svc_url, app_name, app_email, app_pass);

        return authSvcSession;
    }
    
    protected AuthSvcSession getNewSession(String target, int maxRetrials, long retrialInterval) {
        
        AuthSvcSession authSvcSession = new AuthSvcSession(target, maxRetrials, retrialInterval);
        
        return authSvcSession;
    }

    public int getMaxRetrials() {
        return maxRetrials;
    }

    public void setMaxRetrials(int maxRetrials) {
        this.maxRetrials = maxRetrials;
    }

    public int getRetrialInterval() {
        return retrialInterval;
    }

    public void setRetrialInterval(int retrialInterval) {
        this.retrialInterval = retrialInterval;
    }
}