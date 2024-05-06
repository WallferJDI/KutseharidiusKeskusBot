package com.example.chumbatelegramm.model.entity;

import com.example.chumbatelegramm.model.ds.ExpectedMessageType;
import com.example.chumbatelegramm.model.ds.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    private String id;
    private Long telegramMemberId;
    private UserRole userRole;
    private String groupName;
    private Integer groupId;
    private ExpectedMessageType chatExpectedType = ExpectedMessageType.GROUP_NAME;

    public User(Long telegramMemberId, String groupName, UserRole userRole) {
        this.telegramMemberId = telegramMemberId;
        this.groupName = groupName;
        this.userRole = userRole;
    }

    public Long getTelegramMemberId() {
        return telegramMemberId;
    }

    public void setTelegramMemberId(Long telegramMemberId) {
        this.telegramMemberId = telegramMemberId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public ExpectedMessageType getChatExpectedType() {
        return chatExpectedType;
    }

    public void setChatExpectedType(ExpectedMessageType chatExpectedType) {
        this.chatExpectedType = chatExpectedType;
    }
}
