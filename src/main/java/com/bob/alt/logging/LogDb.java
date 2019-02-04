package com.bob.alt.logging;

import com.bob.alt.domain.model.LogModel;
import com.bob.alt.domain.repo.LogDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LogDb implements Logger {

    @Autowired
    private LogDao logDao;

    @Override
    public void log(Object o, Level level, String message) {
        LogModel logModel = new LogModel(new Date(), level.toString(), o.getClass().getName(), message, "");
        logDao.add(logModel);
    }

    @Override
    public void log(Object o, Level level, String message, String throwable) {
        LogModel logModel = new LogModel(new Date(), level.toString(), o.getClass().getName(), message, "");
        logDao.add(logModel);
    }

}
