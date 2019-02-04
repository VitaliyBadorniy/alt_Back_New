package com.bob.alt.domain.repo;

import com.bob.alt.domain.entity.AgendaEntity;
import com.bob.alt.domain.model.Agenda;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class AgendaDao {

    @Autowired
    private AgendaRepo agendaRepo;

    public List<Agenda> getListAgendaByDate(Date start, Date end) {
        if (start != null & end != null) {
            return Optional.ofNullable((agendaRepo.findByDateFromAndTo(start, end))
                    .stream()
                    .map(this::convertToAgenda)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        } else if (start != null) {
            return Optional.ofNullable((agendaRepo.findByDateFrom(start))
                    .stream()
                    .map(this::convertToAgenda)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        } else if (end != null) {
            return Optional.ofNullable((agendaRepo.findByDateToEnd(end))
                    .stream()
                    .map(this::convertToAgenda)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        } else {
            return Optional.ofNullable(((List<AgendaEntity>) agendaRepo.findAll()).stream()
                    .map(this::convertToAgenda)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        }
    }

    public Agenda getEventById(long id) {
        return Optional.ofNullable(agendaRepo.findById(id))
                .map(e -> new Agenda(e.getId(), e.getName(), e.getDate(), e.getDescription()))
                .orElse(null);
    }

    private AgendaEntity getAgendaEntityById(long id) {
        return Optional.ofNullable(agendaRepo.findById(id))
                .orElse(null);
    }


    public boolean updateEvent(Agenda agenda) {
        AgendaEntity agendaEntity = getAgendaEntityById(agenda.getId());
        if (agendaEntity == null) {
            return false;
        }

        agendaEntity.setName(agenda.getName());
        agendaEntity.setDate(agenda.getDate());
        agendaEntity.setDescription(agenda.getDescription());

        agendaRepo.save(agendaEntity);
        return true;
    }

    public boolean addEvent(Agenda agenda) {
        if (agenda.getName() == null || agenda.getDate() == null || agendaRepo.findByDate(agenda.getDate()) != null) {
            return false;
        }

        AgendaEntity agendaEntity = new AgendaEntity(agenda.getName(), agenda.getDate(), agenda.getDescription());
        agendaRepo.save(agendaEntity);
        return true;
    }

    public boolean deleteEvent(long id) {
        AgendaEntity agendaEntity = getAgendaEntityById(id);
        if (agendaEntity == null) {
            return false;
        }
        agendaRepo.delete(agendaEntity);
        return true;
    }

    private Agenda convertToAgenda(AgendaEntity e) {
        return new Agenda(e.getId(), e.getName(), e.getDate(), e.getDescription());
    }

}
