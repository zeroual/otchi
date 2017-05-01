package com.otchi.infrastructure.config;


public final class ResourcesPath {

    private static final String URL_SEPARATOR = "/";
    private static final String VERSION = "v1";
    private static final String API_PREFIXE = "rest";
    public static final String URL_PREFIXE = URL_SEPARATOR + API_PREFIXE + URL_SEPARATOR + VERSION + URL_SEPARATOR;
    public static final String POST = URL_PREFIXE + "post";
    public static final String FEED = URL_PREFIXE + "feed";
    public static final String LOGIN = URL_PREFIXE + "login";
    public static final String LOGOUT = URL_PREFIXE + "logout";
    public static final String ME = URL_PREFIXE + "me";
    public static final String ACCOUNT = URL_PREFIXE + "account";
    public static final String REGISTER = ACCOUNT + URL_SEPARATOR + "register";
}
