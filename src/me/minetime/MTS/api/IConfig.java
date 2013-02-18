package me.minetime.MTS.api;

public interface IConfig {

    String getString(String path);

    void setString(String path, String value);

    boolean getBoolean(String path);

    void setBoolean(String path, boolean value);

}