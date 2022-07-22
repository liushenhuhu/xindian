package com.ruoyi.framework.websocket;

import javax.websocket.Session;

/**
 * @Author: han
 * @Date: 2022-07-14
 */
public class WebSocketClient {

    private Session session;

    private String uri;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
