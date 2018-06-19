package com.tw.pjhu.ws.config.websocket.principal;

import java.security.Principal;

public class WebSocketPrincipal implements Principal {
    private  String name;

    public WebSocketPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
