package com.tw.pjhu.ws.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GsonInstance {

    @Bean
    public Gson createGson() {
        return new GsonBuilder().create();
    }
}
