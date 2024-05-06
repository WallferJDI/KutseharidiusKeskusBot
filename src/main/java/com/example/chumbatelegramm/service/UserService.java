package com.example.chumbatelegramm.service;

import com.example.chumbatelegramm.util.CacheUtil;
import com.example.chumbatelegramm.dao.UserRepository;
import com.example.chumbatelegramm.model.ds.ExpectedMessageType;
import com.example.chumbatelegramm.model.entity.User;
import com.example.chumbatelegramm.model.ds.UserRole;
import com.example.chumbatelegramm.exceptions.BotRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.chumbatelegramm.configuration.ExceptionMessageKeyConstants.PROVIDE_GROUP_NAME_KEY;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupService groupService;

    public UserService(UserRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
    }

    public User getUserById(Long id) {
        Optional<User> cachedUser = CacheUtil.tryGetAuthorizedUserById(id);
        return cachedUser.orElseGet(() -> {
            User user = findUserOrCreate(id);
            CacheUtil.addAuthorizedUser(user);
            return user;
        });
    }

    private User findUserOrCreate(Long id) {
        User user = userRepository.findByTelegramMemberId(id);
        if (user == null) {
            user = createUser(id);
        }
        return user;
    }

    private User createUser(Long id) {
        return userRepository.insert(new User(id, EMPTY, UserRole.USER));
    }

    public void updateUserGroup(Long id, String groupName) {
        User user = getUserById(id);
        user.setGroupName(groupName);
        user.setGroupId(groupService.findGroupIdByName(groupName));
        user.setChatExpectedType(ExpectedMessageType.COMMAND);

        updateUser(user);
    }

    public void updateUser(User user) {
        CacheUtil.addAuthorizedUser(userRepository.save(user));
    }


    public void validateUser(User user) {
        if (user.getGroupName() == null || user.getGroupName().isBlank()) {
            user.setChatExpectedType(ExpectedMessageType.GROUP_NAME);
            updateUser(user);
            throw new BotRequestException(PROVIDE_GROUP_NAME_KEY);
        }
    }
}
