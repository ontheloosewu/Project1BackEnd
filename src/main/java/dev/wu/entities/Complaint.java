package dev.wu.entities;

public class Complaint {

    private int complaintId;
    private String compText;
    private Priority priority;
    private int meetingId;

    public Complaint() {

    }

    public Complaint(int complaintId, String compText, Priority priority, int meetingId) {
        this.complaintId = complaintId;
        this.compText = compText;
        this.priority = priority;
        this.meetingId = meetingId;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getCompText() {
        return compText;
    }

    public void setCompText(String compText) {
        this.compText = compText;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + complaintId +
                ", compText='" + compText + '\'' +
                ", priority=" + priority +
                ", meetingId=" + meetingId +
                '}';
    }
}
