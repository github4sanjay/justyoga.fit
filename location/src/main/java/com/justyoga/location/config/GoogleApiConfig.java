package com.justyoga.location.config;

import com.google.maps.GeoApiContext;

public class GoogleApiConfig {

    private static String GOOGLE_API_KEY = "AIzaSyCg9KqjmKWZOFws3MUGPdx4uPJgjlndwds";
    private static GeoApiContext context;

    private GoogleApiConfig() {}

    public static GeoApiContext context() {
        if (context == null) {
            synchronized (GoogleApiConfig.class) {
                if (context == null) {
                    context = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();
                }
            }
        }
        return context;
    }
}
