package pu.fmi.rainytime.models;

public class Search {
    private String id;
    private String location;
    private String timestamp;


    public Search(String id, String location, String timestamp) {
        this.id = id;
        this.location = location;
        this.timestamp = timestamp;
    }

    public Search(String location, String timestamp) {
        this.location = location;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
