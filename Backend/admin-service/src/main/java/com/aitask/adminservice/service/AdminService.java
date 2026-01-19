package com.aitask.adminservice.service;

import com.aitask.adminservice.dto.CreateAdminProfileDTO;
import com.aitask.adminservice.model.AdminAction;
import com.aitask.adminservice.model.AdminProfile;
import com.aitask.adminservice.repository.AdminActionRepository;
import com.aitask.adminservice.repository.AdminProfileRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminProfileRepository adminProfileRepository;
    private final AdminActionRepository adminActionRepository;
    private final com.aitask.adminservice.repository.AuthServiceClient authClient;

    // ---------- Admin Profile ----------

    public AdminProfile createAdminProfile(CreateAdminProfileDTO dto, Long adminId) {

        // 1️⃣ Check if profile already exists
        if (adminProfileRepository.existsByAdminId(adminId)) {
            throw new IllegalStateException("Admin profile already exists");
        }

        // 2️⃣ Fetch admin details from auth-service
        CreateAdminProfileDTO authUser;
        try {
            authUser = authClient.getUserById(adminId);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Admin not found in Auth Service");
        }

        // 3️⃣ Build AdminProfile entity
        AdminProfile adminProfile = AdminProfile.builder()
                .adminId(adminId) // ✅ IMPORTANT: use adminId, not dto.getAdminId()
                .name(dto.getName() != null ? dto.getName() : authUser.getName())
                .phoneNumber(dto.getPhoneNumber())
                .profileImage(dto.getProfileImage())
                .bio(dto.getBio())
                .timezone(dto.getTimezone())
                .language(dto.getLanguage())
                .theme(dto.getTheme())
                .accessLevel(dto.getAccessLevel())
                .build();

        return adminProfileRepository.save(adminProfile);
    }


    public AdminProfile getAdminProfile(Long adminId) {
        return adminProfileRepository.findByAdminId(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin profile not found"));
    }

    
    public AdminProfile updateAdminProfile(Long adminId, AdminProfile updatedProfile) {
        AdminProfile existingProfile = getAdminProfile(adminId);
        
        if (updatedProfile == null) {
            throw new IllegalArgumentException("Profile data cannot be null");
        }

        existingProfile.setName(updatedProfile.getName());
        existingProfile.setPhoneNumber(updatedProfile.getPhoneNumber());
        existingProfile.setProfileImage(updatedProfile.getProfileImage());
        existingProfile.setBio(updatedProfile.getBio());
        existingProfile.setTimezone(updatedProfile.getTimezone());
        existingProfile.setLanguage(updatedProfile.getLanguage());
        existingProfile.setTheme(updatedProfile.getTheme());
        existingProfile.setAccessLevel(updatedProfile.getAccessLevel());

        return adminProfileRepository.save(existingProfile);
    }

    
    public boolean adminProfileExists(Long adminId) {
        return adminProfileRepository.existsByAdminId(adminId);
    }

    // ---------- Admin Actions ----------

    
    public AdminAction logAdminAction(AdminAction adminAction) {
        return adminActionRepository.save(adminAction);
    }

    
    public List<AdminAction> getActionsByAdmin(Long adminId) {
        return adminActionRepository.findByAdminId(adminId);
    }

    
    public List<AdminAction> getActionsByType(String actionType) {
        return adminActionRepository.findByActionType(actionType);
    }

    
    public List<AdminAction> getActionsForTarget(String targetType, Long targetId) {
        return adminActionRepository.findByTargetTypeAndTargetId(targetType, targetId);
    }

    
    public List<AdminAction> getActionsBetween(LocalDateTime start, LocalDateTime end) {
        return adminActionRepository.findByCreatedAtBetween(start, end);
    }

    
    public List<AdminAction> getRecentActions() {
        return adminActionRepository.findTop20ByOrderByCreatedAtDesc();
    }
}
