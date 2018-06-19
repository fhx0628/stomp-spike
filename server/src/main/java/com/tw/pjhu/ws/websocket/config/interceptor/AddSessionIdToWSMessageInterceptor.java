package com.tw.pjhu.ws.websocket.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class AddSessionIdToWSMessageInterceptor implements HandshakeInterceptor {
    private static final Logger logger =LoggerFactory.getLogger(AddSessionIdToWSMessageInterceptor.class);
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest)request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            logger.info("session got in AddSessionIdToWSMessageInterceptor:" + session.getId());
            attributes.put("sessionId", session.getId());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

}
