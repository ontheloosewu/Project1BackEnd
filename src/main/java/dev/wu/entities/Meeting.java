package dev.wu.entities;

public class Meeting {

    private int meetingId;
    private String location;
    private int date;
    private String summary;

    public Meeting() {

    }

    public Meeting(int meetingId, String location, int date, String summary) {
        this.meetingId = meetingId;
        this.location = location;
        this.date = date;
        this.summary = summary;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
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
                "id=" + meetingId +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", summary='" + summary + '\'' +
                '}';
    }
}
