package dev.wu.daos;

import dev.wu.entities.Complaint;
import dev.wu.entities.Priority;

import java.util.List;

public interface ComplaintDAO {

    Complaint newComplaint(Complaint complaint);

    List<Complaint> viewComplaints();

    Complaint getComplaintById(int id);

    Complaint updateComplaint(Complaint complaint);
}
