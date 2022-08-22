package dev.wu.services;

import dev.wu.daos.ComplaintDAO;
import dev.wu.entities.Complaint;
import dev.wu.entities.Priority;

import java.util.List;

public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintDAO complaintDAO;

    public ComplaintServiceImpl(ComplaintDAO complaintDAO) { this.complaintDAO = complaintDAO; }

    @Override
    public Complaint newValidComplaint(Complaint complaint) {
        if(complaint.getCompText().length() == 0){
            throw new RuntimeException("Complaint box cannot be empty.");
        }
        return this.complaintDAO.newComplaint(complaint);
    }

    @Override
    public List<Complaint> getAllComplaints() {
        return this.complaintDAO.viewComplaints();
    }

    @Override
    public Complaint getComplaintById(int id) {
        return this.complaintDAO.getComplaintById(id);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint) {
        return this.complaintDAO.updateComplaint(complaint);
    }
}
