package com.aitask.adminservice.service;

import com.aitask.adminservice.dto.JwtUserPrincipal;
import com.aitask.adminservice.model.AdminAction;
import com.aitask.adminservice.repository.AdminActionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminActionService {

    private final AdminActionRepository repository;

    // ================= EXISTING METHOD (UNCHANGED) =================

    public void logAction(
            JwtUserPrincipal admin,
            String actionType,
            String targetType,
            Long targetId,
            String description,
            HttpServletRequest request
    ) {
        AdminAction action = AdminAction.builder()
                .adminId(admin.getUserId())
                .adminUsername(admin.getUsername())
                .actionType(actionType)
                .targetType(targetType)
                .targetId(targetId)
                .description(description)
                .ipAddress(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .build();

        repository.save(action);
    }

    // ================= NEW READ / REPORT METHODS =================

    public List<AdminAction> getAllActions() {
        return repository.findAll();
    }

    public List<AdminAction> getActionsByAdmin(Long adminId) {
        return repository.findByAdminId(adminId);
    }

    public List<AdminAction> getActionsByActionType(String actionType) {
        return repository.findByActionType(actionType);
    }

    public List<AdminAction> getActionsByTarget(String targetType, Long targetId) {
        return repository.findByTargetTypeAndTargetId(targetType, targetId);
    }

    public List<AdminAction> getActionsBetweenDates(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return repository.findByCreatedAtBetween(start, end);
    }

    // ================= OPTIONAL OVERLOAD (NO REQUEST OBJECT) =================

    public void logAction(
            JwtUserPrincipal admin,
            String actionType,
            String targetType,
            Long targetId,
            String description
    ) {
        AdminAction action = AdminAction.builder()
                .adminId(admin.getUserId())
                .adminUsername(admin.getUsername())
                .actionType(actionType)
                .targetType(targetType)
                .targetId(targetId)
                .description(description)
                .build();

        repository.save(action);
    }
}
