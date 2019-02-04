package com.bob.alt.domain.repo;

import com.bob.alt.domain.entity.AgendaEntity;
import com.bob.alt.domain.model.Agenda;
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
public class AgendaDaoTest {

    @Autowired
    private AgendaDao agendaDao;
    @MockBean
    private AgendaRepo agendaRepo;

    @Test
    public void getListAgendaByDateFindByDateFromAndToTest() {
        Date start = new Date();
        Date end = new Date();
        List<Agenda> agendaList = agendaDao.getListAgendaByDate(start, end);
        Assert.assertNotNull("failure - expected not null", agendaList);
        verify(agendaRepo, Mockito.times(1)).findByDateFromAndTo(start, end);
    }

    @Test
    public void getListAgendaByDateFindByDateFromTest() {
        Date start = new Date();
        List<Agenda> agendaList = agendaDao.getListAgendaByDate(start, null);
        Assert.assertNotNull("failure - expected not null", agendaList);
        verify(agendaRepo, Mockito.times(1)).findByDateFrom(start);
    }

    @Test
    public void getListAgendaByDateFindByDateEndTest() {
        Date end = new Date();
        List<Agenda> agendaList = agendaDao.getListAgendaByDate(null, end);
        Assert.assertNotNull("failure - expected not null", agendaList);
        verify(agendaRepo, Mockito.times(1)).findByDateToEnd(end);
    }

    @Test
    public void getListAgendaByDateFindAllTest() {
        List<Agenda> agendaList = agendaDao.getListAgendaByDate(null, null);
        Assert.assertNotNull("failure - expected not null", agendaList);
        verify(agendaRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void getEventByIdTest() {
        long id = 1;
        when(agendaRepo.findById(id)).thenReturn(new AgendaEntity());
        Agenda agenda = agendaDao.getEventById(id);
        verify(agendaRepo, Mockito.times(1)).findById(id);
    }

    @Test
    public void updateEventTest() {
        Agenda agenda = new Agenda(1L, "event", new Date(), "");
        AgendaEntity agendaEntity = new AgendaEntity();
        when(agendaRepo.findById(agenda.getId().longValue())).thenReturn(agendaEntity);
        agendaDao.updateEvent(agenda);

        verify(agendaRepo, Mockito.times(1)).findById(agenda.getId().longValue());
        verify(agendaRepo, Mockito.times(1)).save(agendaEntity);
    }

    @Test
    public void addEventTest() {
        Agenda agenda = new Agenda("event", new Date(), "");
        Assert.assertNotNull(agenda);

        AgendaEntity agendaEntity = new AgendaEntity(agenda.getName(), agenda.getDate(), agenda.getDescription());
        Assert.assertNotNull(agendaEntity);

        boolean isAgendaCreated = agendaDao.addEvent(agenda);
        Assert.assertTrue(isAgendaCreated);

        Mockito.verify(agendaRepo, Mockito.times(1)).save(agendaEntity);
    }

    @Test
    public void deleteEventTest() {
        Agenda agenda = new Agenda(1L, "event", new Date(), "");
        AgendaEntity agendaEntity = new AgendaEntity();
        Mockito.doReturn(agendaEntity)
                .when(agendaRepo)
                .findById(agenda.getId().longValue());
        Assert.assertNotNull(agendaEntity);

        boolean isAgendaDelete = agendaDao.deleteEvent(agenda.getId());
        Assert.assertTrue(isAgendaDelete);

        verify(agendaRepo, Mockito.times(1)).findById(agenda.getId().longValue());
        verify(agendaRepo, Mockito.times(1)).delete(agendaEntity);
    }
}