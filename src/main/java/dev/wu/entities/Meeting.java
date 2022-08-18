package dev.wu.entities;

public class Meeting {

    private int id;
    private String location;
    private int date;
    private String summary;

    public Meeting() {

    }

    public Meeting(int id, String location, int date, String summary) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", summary='" + summary + '\'' +
                '}';
    }
}
