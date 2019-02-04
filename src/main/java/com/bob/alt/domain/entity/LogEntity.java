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
@Table(name = "logging")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String level;
    @Column(length = 500)
    private String logger;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String message;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String throwable;

    public LogEntity(Date date, String level, String logger, String message, String throwable) {
        this.date = date;
        this.level = level;
        this.logger = logger;
        this.message = message;
        this.throwable = throwable;
    }
}
