package com.example.chumbatelegramm.service;


import com.example.chumbatelegramm.model.ds.ExpectedMessageType;
import com.example.chumbatelegramm.model.entity.User;
import com.example.chumbatelegramm.exceptions.BotRequestException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.chumbatelegramm.configuration.ExceptionMessageKeyConstants.UNRECOGNIZED_MESSAGE;

@Service
public class MessageService {

    private final UserService userService;

    public MessageService(UserService userService) {
        this.userService = userService;
    }

    public Message messageProceed(Message requestMessage, User user) {
        if (ExpectedMessageType.GROUP_NAME.equals(user.getChatExpectedType())) {
            userService.updateUserGroup(user.getTelegramMemberId(), requestMessage.getText());
            Message message = new Message();
            message.setText("Group set -" + requestMessage.getText());
            return message;
        }

        throw new BotRequestException(UNRECOGNIZED_MESSAGE);
    }


}
