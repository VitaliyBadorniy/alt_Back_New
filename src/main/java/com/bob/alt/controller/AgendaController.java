package com.bob.alt.controller;

import com.bob.alt.logging.Level;
import com.bob.alt.logging.Logger;
import com.bob.alt.domain.model.Agenda;
import com.bob.alt.domain.repo.AgendaDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/agenda/*")
public class AgendaController {

    @Autowired
    private AgendaDao agendaDao;
    @Autowired
    private Logger logger;

    @GetMapping("/eventById")
    public Agenda getEventById(@RequestParam("id") long id) {

        logger.log(this, Level.INFO,"Call of method: getEventById");

        log.info("Call of method: getEventById");

        return agendaDao.getEventById(id);
    }

    @GetMapping("/listAgendaByDate")
    public List<Agenda> getListAgendaByDate(@RequestParam("start") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date start,
                                            @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date end) {

        logger.log(this, Level.INFO,"Call of method: getListAgendaByDate");

        log.info("Call of method: getListAgendaByDate");

        return agendaDao.getListAgendaByDate(start, end);
    }

    @PutMapping("/updateEvent")
    public boolean updateEvent(@RequestBody Agenda agenda) {

        logger.log(this, Level.INFO,"Call of method: getListAgendaByDate");

        log.info("Call of method: updateEvent");

        return agendaDao.updateEvent(agenda);
    }

    @PostMapping("/addEvent")
    public boolean addEvent(@RequestBody Agenda agenda) {

        logger.log(this, Level.INFO,"Call of method: addEvent");

        log.info("Call of method: addEvent");

        return agendaDao.addEvent(agenda);
    }

    @PostMapping("/deleteEvent")
    public boolean deleteEvent(@RequestParam("id") long id) {

        logger.log(this, Level.INFO,"Call of method: deleteEvent");

        log.info("Call of method: deleteEvent");

        return agendaDao.deleteEvent(id);
    }

}
