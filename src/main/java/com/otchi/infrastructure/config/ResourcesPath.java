package com.otchi.infrastructure.config;


public final class ResourcesPath {

    private static final String URL_SEPARATOR = "/";
    private static final String VERSION = "v1";
    private static final String API_PREFIXE = "rest";
    private static final String URL_PREFIXE = URL_SEPARATOR + API_PREFIXE + URL_SEPARATOR + VERSION + URL_SEPARATOR;
    public static final String RECIPE = URL_PREFIXE + "recipe";
    public static final String POST = URL_PREFIXE + "post";
    public static final String FEED = URL_PREFIXE + "feed";
}
