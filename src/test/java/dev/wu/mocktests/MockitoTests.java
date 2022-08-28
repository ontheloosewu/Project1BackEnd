package dev.wu.mocktests;

import dev.wu.daos.MeetingDAO;
import dev.wu.daos.ResidentDAO;
import dev.wu.entities.Meeting;
import dev.wu.entities.Resident;
import dev.wu.entities.UserType;
import dev.wu.exceptions.DuplicateUsernameException;
import dev.wu.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MockitoTests {

    @Test
    void meetings_must_take_place_in_the_future() {
        MeetingDAO meetingDAO = Mockito.mock(MeetingDAO.class);
        Meeting meeting = new Meeting(0, "Sotenbori", 1660000000, "Mockito meeting");
        Mockito.when(meetingDAO.createMeeting(meeting)).thenReturn(meeting);
        MeetingService meetingService = new MeetingServiceImpl(meetingDAO);
        Assertions.assertThrows(RuntimeException.class, () -> {
            meetingService.createValidNewMeeting(meeting);
        });
    }

    @Test
    void username_must_be_unique() {
        ResidentDAO residentDAO = Mockito.mock(ResidentDAO.class);
        Resident resident = new Resident(0, "MockitoUser", "mockitopassword", UserType.REGISTERED);
        Resident resident2 = new Resident(1, "MockitoUser", "mockitopassword2", UserType.REGISTERED);
        Mockito.when(residentDAO.registerUser(resident)).thenReturn(resident);
        Mockito.when(residentDAO.getResidentByUsername(resident2.getUsername())).thenReturn(resident2);

        ResidentService residentService = new ResidentServiceImpl(residentDAO);
        Assertions.assertThrows(DuplicateUsernameException.class, () -> {
            residentService.newValidUser(resident2);
        });
    }

    @Test
    void user_info_must_be_valid() {
        ResidentDAO residentDAO = Mockito.mock(ResidentDAO.class);
        Resident resident = new Resident(0, "MockitoUser", "mockitopassword", UserType.REGISTERED);
        Mockito.when(residentDAO.registerUser(resident)).thenReturn(resident);
        Mockito.when(residentDAO.getResidentByUsername(resident.getUsername())).thenReturn(resident);

        LoginService loginService = new LoginServiceImpl(residentDAO);
        Assertions.assertEquals(resident, loginService.validateUser(resident.getUsername(), resident.getPassword()));
    }
}
