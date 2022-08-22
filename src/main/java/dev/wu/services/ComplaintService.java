package dev.wu.services;

import dev.wu.entities.Complaint;
import dev.wu.entities.Priority;

import java.util.List;

public interface ComplaintService {

    Complaint newValidComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();

    Complaint getComplaintById(int id);

    Complaint updateComplaint(Complaint complaint);
}
