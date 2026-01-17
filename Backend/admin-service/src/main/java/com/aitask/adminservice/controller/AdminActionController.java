package com.aitask.adminservice.controller;

import com.aitask.adminservice.model.AdminAction;
import com.aitask.adminservice.service.AdminActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/actions")
@RequiredArgsConstructor
public class AdminActionController {

    private final AdminActionService adminActionService;

    // ---------------- ALL ACTIONS ----------------
    @GetMapping("/getallaction")
    public ResponseEntity<List<AdminAction>> getAllActions() {
        return ResponseEntity.ok(adminActionService.getAllActions());
    }

    // ---------------- BY ADMIN ----------------
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AdminAction>> getActionsByAdmin(
            @PathVariable Long adminId
    ) {
        return ResponseEntity.ok(
                adminActionService.getActionsByAdmin(adminId)
        );
    }

    // ---------------- BY ACTION TYPE ----------------
    @GetMapping("/type/{actionType}")
    public ResponseEntity<List<AdminAction>> getActionsByType(
            @PathVariable String actionType
    ) {
        return ResponseEntity.ok(
                adminActionService.getActionsByActionType(actionType)
        );
    }

    // ---------------- BY TARGET ----------------
    @GetMapping("/target")
    public ResponseEntity<List<AdminAction>> getActionsByTarget(
            @RequestParam String targetType,
            @RequestParam Long targetId
    ) {
        return ResponseEntity.ok(
                adminActionService.getActionsByTarget(targetType, targetId)
        );
    }

    // ---------------- DATE RANGE ----------------
    @GetMapping("/range")
    public ResponseEntity<List<AdminAction>> getActionsBetweenDates(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        return ResponseEntity.ok(
                adminActionService.getActionsBetweenDates(start, end)
        );
    }
}
