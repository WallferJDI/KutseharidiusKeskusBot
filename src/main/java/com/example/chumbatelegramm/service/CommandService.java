package com.example.chumbatelegramm.service;

import com.example.chumbatelegramm.model.entity.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CommandService {
    private final TimeTableService timeTableService;
    private final UserService userService;

    public CommandService(TimeTableService timeTableService, UserService userService) {
        this.timeTableService = timeTableService;
        this.userService = userService;
    }

    public Message commandProceed(Message requestMessage, User user) {
        userService.validateUser(user);

        Message message = new Message();
        String text = requestMessage.getText();
        if (text.equals("/timetable")) {
            message.setText(timeTableService.requestTimeTable(user.getGroupId()));
        }

        return message;
    }

}