package com.bob.alt.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agenda")
public class AgendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;//name of action
    private Date date;//date create
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;//description

    public AgendaEntity(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
