package com.aitask.adminservice.repository;

import com.aitask.adminservice.model.AdminAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdminActionRepository extends JpaRepository<AdminAction, Long> {

    // All actions by a specific admin
    List<AdminAction> findByAdminId(Long adminId);

    // Filter actions by type
    List<AdminAction> findByActionType(String actionType);

    // Filter actions on a target entity
    List<AdminAction> findByTargetTypeAndTargetId(String targetType, Long targetId);

    // Admin activity between dates
    List<AdminAction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // Recent actions (dashboard)
    List<AdminAction> findTop20ByOrderByCreatedAtDesc();
}
