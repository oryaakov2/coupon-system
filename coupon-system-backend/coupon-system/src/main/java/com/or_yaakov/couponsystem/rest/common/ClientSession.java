package com.or_yaakov.couponsystem.rest.common;

public class ClientSession {
    public static final String CUSTOMER = "customer";
    public static final String COMPANY = "company";

    private final long id;
    private long lastAccessMillis;
    private final String clientType;

    private ClientSession(long id, long lastAccessMillis, String clientType) {
        this.id = id;
        this.lastAccessMillis = lastAccessMillis;
        this.clientType = clientType;
    }

    public static ClientSession of(long id, String clientType) {
        return new ClientSession(id, System.currentTimeMillis(), clientType);
    }

    public long getId() {
        return id;
    }

    public String getClientType() {
        return clientType;
    }

    public long getLastAccessMillis() {
        return lastAccessMillis;
    }

    public ClientSession access() {
        lastAccessMillis = System.currentTimeMillis();
        return this;
    }
}