package com.example.chumbatelegramm.controller;

import com.example.chumbatelegramm.model.ds.ExpectedMessageType;
import com.example.chumbatelegramm.model.entity.User;
import com.example.chumbatelegramm.exceptions.BotRequestException;
import com.example.chumbatelegramm.service.CommandService;
import com.example.chumbatelegramm.service.LocalizationService;
import com.example.chumbatelegramm.service.MessageService;
import com.example.chumbatelegramm.service.UserService;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.chumbatelegramm.configuration.ExceptionMessageKeyConstants.PROVIDE_GROUP_NAME_KEY;

@Controller
public class QueryController {
    private final UserService userService;
    private final CommandService commandService;
    private final MessageService messageService;
    private final LocalizationService localizationService;

    public QueryController(UserService userService, CommandService commandService, MessageService messageService, LocalizationService localizationService) {
        this.userService = userService;
        this.commandService = commandService;
        this.messageService = messageService;
        this.localizationService = localizationService;
    }

    public Message queryRequest(Update update) {
        try {
            return requestProceed(update.getMessage());
        } catch (BotRequestException e) {
            Message responseMessage = new Message();
            responseMessage.setText(localizationService.getMessageByKey(e.getMessage(), update.getMessage().getFrom().getLanguageCode()));
            return responseMessage;
        }
    }

    private Message requestProceed(Message requestMessage) {
        User user = userService.getUserById(requestMessage.getFrom().getId());


        if (!requestMessage.hasText()) {
            return new Message();
        }

        if (requestMessage.isCommand()) {
            if (ExpectedMessageType.COMMAND.equals(user.getChatExpectedType())) {
                return commandService.commandProceed(requestMessage, user);
            }

            throw new BotRequestException(PROVIDE_GROUP_NAME_KEY);
        }

        return messageService.messageProceed(requestMessage, user);
    }
}
