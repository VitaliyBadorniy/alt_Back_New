package com.bob.alt.controller;

import com.bob.alt.domain.model.Agenda;
import com.bob.alt.domain.repo.AgendaDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgendaControllerTest {

    @Autowired
    private AgendaController agendaController;
    @MockBean
    private AgendaDao agendaDao;

    @Test
    public void getEventByIdTest() {
        long id = 1;
        Agenda a = new Agenda();
        a.setId(id);
        when(agendaDao.getEventById(id)).thenReturn(a);
        Agenda agenda = agendaController.getEventById(id);
        verify(agendaDao, Mockito.times(1)).getEventById(id);
        Assert.assertEquals(id, agenda.getId().longValue());
    }

    @Test
    public void getListAgendaByDateTest() {
        List<Agenda> agendaList = agendaController.getListAgendaByDate(null, null);
        verify(agendaDao, Mockito.times(1)).getListAgendaByDate(null, null);
    }

    @Test
    public void updateEventTest() {
        Agenda agenda = new Agenda(1L,"event", new Date(), "");
        agendaController.updateEvent(agenda);
        verify(agendaDao, Mockito.times(1)).updateEvent(agenda);
    }

    @Test
    public void addEventTest() {
        Agenda agenda = new Agenda("event", new Date(), "");
        agendaController.addEvent(agenda);
        verify(agendaDao, Mockito.times(1)).addEvent(agenda);
    }

    @Test
    public void deleteEventTest() {
        long id = 1;
        agendaController.deleteEvent(id);
        verify(agendaDao, Mockito.times(1)).deleteEvent(id);
    }

}