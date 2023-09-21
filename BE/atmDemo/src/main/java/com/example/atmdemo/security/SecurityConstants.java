package com.example.atmdemo.security;

public class SecurityConstants {
    public static final long JWT_EXPIRATION_TIME = 900000; // 15 minutes

    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 1209600000; // 14 days

    public static final byte[] JWT_SECRET = "SecretKeyToGenJWTsdsadsadeffnuvbtribsbfdvfdisvriuenviruesnvirnsvruvnfdsivnfudisnvdfis".getBytes();
}
