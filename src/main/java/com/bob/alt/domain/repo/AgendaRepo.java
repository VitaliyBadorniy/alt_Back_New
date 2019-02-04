package com.bob.alt.domain.repo;

import com.bob.alt.domain.entity.AgendaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface AgendaRepo extends CrudRepository<AgendaEntity, Long> {

    AgendaEntity findById(long id);

    AgendaEntity findByDate(Date date);

    @Query("select a from AgendaEntity a where a.date>=:from")
    List<AgendaEntity> findByDateFrom(@Param("from") Date from);

    @Query("select a from AgendaEntity a where a.date<=:end")
    List<AgendaEntity> findByDateToEnd(@Param("end") Date end);

    @Query("select a from AgendaEntity a where a.date>=:from and a.date<=:end")
    List<AgendaEntity> findByDateFromAndTo(@Param("from") Date from, @Param("end") Date end);

}
