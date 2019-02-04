package com.bob.alt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

    private Long id;
    private String name;//name of action
    private Date date;//date create
    private String description;//description

    public Agenda(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
