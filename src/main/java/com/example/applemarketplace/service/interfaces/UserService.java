package com.example.applemarketplace.service.interfaces;
import com.example.applemarketplace.model.dtos.UserEmailDTO;
import com.example.applemarketplace.model.dtos.UserProfileDTO;
import com.example.applemarketplace.model.dtos.UserRegisterDTO;
import com.example.applemarketplace.model.entity.User;
import com.example.applemarketplace.model.view.UserControlCenterView;
import com.example.applemarketplace.model.view.UserProfileView;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserRegisterDTO userRegisterDTO);

    void dbInitAdmin();

    Optional<User> findByUsername(String username);

    void saveCurrentUser(User user);

    UserProfileView mapUserToView(String username);

    UserProfileDTO mapUserToDTO(String username);

    void updateUser(UserProfileDTO userProfileDTO, String username);

    List<UserControlCenterView> findAllUsersExcludingMyUser();

    void addAdminRole(Long userId);

    void removeAdminRole(Long userId);

    void blockUser(Long userId);

    void unblockUser(Long userId);

    void deleteUser(Long userId);

    List<UserEmailDTO> getAllUserEmails();

}



