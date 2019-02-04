package com.bob.alt.domain.repo;

import com.bob.alt.domain.entity.LogEntity;
import com.bob.alt.domain.model.LogModel;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class LogDao {

    @Autowired
    private LogRepo logRepo;

    public List<LogModel> getListLogByDate(Date start, Date end) {
        if (start != null & end != null) {
            return Optional.ofNullable((logRepo.findByDateFromAndTo(start, end))
                    .stream()
                    .map(this::convertToLog)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        } else if (start != null) {
            return Optional.ofNullable((logRepo.findByDateFrom(start))
                    .stream()
                    .map(this::convertToLog)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        } else if (end != null) {
            return Optional.ofNullable((logRepo.findByDateToEnd(end))
                    .stream()
                    .map(this::convertToLog)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        } else {
            return Optional.ofNullable(((List<LogEntity>) logRepo.findAll()).stream()
                    .map(this::convertToLog)
                    .collect(collectingAndThen(toList(), ImmutableList::copyOf)))
                    .orElse(ImmutableList.of());
        }
    }

    public LogModel getLogById(long id) {
        return Optional.ofNullable(logRepo.findById(id))
                .map(e -> new LogModel(e.getId(), e.getDate(), e.getLevel(), e.getLogger(), e.getMessage(), e.getThrowable()))
                .orElse(null);
    }

    public LogModel getLogByDate(Date date) {
        return Optional.ofNullable(logRepo.findByDate(date))
                .map(e -> new LogModel(e.getId(), e.getDate(), e.getLevel(), e.getLogger(), e.getMessage(), e.getThrowable()))
                .orElse(null);
    }

    public boolean add(LogModel logModel) {
        LogEntity logEntity = new LogEntity(logModel.getDate(), logModel.getLevel(), logModel.getLogger(), logModel.getMessage(), logModel.getThrowable());
        logRepo.save(logEntity);
        return true;
    }

    private LogModel convertToLog(LogEntity e) {
        return new LogModel(e.getId(), e.getDate(), e.getLevel(), e.getLogger(), e.getMessage(), e.getThrowable());
    }

}
