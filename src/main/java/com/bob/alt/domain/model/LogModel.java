package com.bob.alt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogModel {

    private Long id;
    private Date date;
    private String level;
    private String logger;
    private String message;
    private String throwable;

    public LogModel(Date date, String level, String logger, String message, String throwable) {
        this.date = date;
        this.level = level;
        this.logger = logger;
        this.message = message;
        this.throwable = throwable;
    }
}
