package com.example.demo.model;

public class Information {
    private final String origName;
    private final String uuid;
    private final String extension;
    private final String savedName;
    private final String savedPath;
    private final String loadPath;
    private final double latitude;
    private final double longitude;

    public Information(String origName, String uuid, String extension, String savedName, String savedPath, String loadPath, double latitude, double longitude) {
        this.origName = origName;
        this.uuid = uuid;
        this.extension = extension;
        this.savedName = savedName;
        this.savedPath = savedPath;
        this.loadPath = loadPath;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getOrigName() {
        return origName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getExtension() {
        return extension;
    }

    public String getSavedName() {
        return savedName;
    }

    public String getSavedPath() {
        return savedPath;
    }

    public String getLoadPath() { return loadPath; }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
