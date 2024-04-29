package com.dev.timetracker.repository;

import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.entity.EntityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RepositoryTask extends JpaRepository<EntityTask, Long> {
    Page<EntityTask> findAllByActiveTrue(Pageable pageable);
    EntityTask findByIdAndActiveTrue(Long id);
    Page<EntityTask> findAllByIdUserAndActiveTrue(EntityUser idUser, Pageable pageable);
    List<EntityTask> findAllByProjectIdAndActiveTrue(Long projectId);
    List<EntityTask> findByProjectIdAndActiveTrue(Long projectId);

    @Query("SELECT t FROM Task t WHERE t.idUser.id = :userId AND t.active = true AND t.startTime >= :startTimestamp AND t.endTime <= :endTimestamp")
    List<EntityTask> findByUserIdAndActiveTrueAndStartTimeBetweenAndEndTimeBetween(
            @Param("userId") Long userId,
            @Param("startTimestamp") Timestamp startTimestamp,
            @Param("endTimestamp") Timestamp endTimestamp);

}