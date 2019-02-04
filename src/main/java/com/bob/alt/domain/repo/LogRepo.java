package com.bob.alt.domain.repo;

import com.bob.alt.domain.entity.LogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface LogRepo extends CrudRepository<LogEntity, Long> {

    LogEntity findById(long id);

    LogEntity findByDate(Date date);

    @Query("select a from LogEntity a where a.date>=:from")
    List<LogEntity> findByDateFrom(@Param("from") Date from);

    @Query("select a from LogEntity a where a.date<=:end")
    List<LogEntity> findByDateToEnd(@Param("end") Date end);

    @Query("select a from LogEntity a where a.date>=:from and a.date<=:end")
    List<LogEntity> findByDateFromAndTo(@Param("from") Date from, @Param("end") Date end);


}
