package com.radoslawzerek.recruitment_task.repository;

import com.radoslawzerek.recruitment_task.entity.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
}
