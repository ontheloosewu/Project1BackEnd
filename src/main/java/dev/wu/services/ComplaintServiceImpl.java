package dev.wu.services;

import dev.wu.daos.ComplaintDAO;
import dev.wu.entities.Complaint;

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
}
