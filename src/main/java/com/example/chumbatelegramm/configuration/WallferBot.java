package com.example.chumbatelegramm.configuration;

import com.example.chumbatelegramm.controller.QueryController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Component
public class WallferBot extends TelegramLongPollingBot {

    @Value("${chumba.bot.name}")
    private String USERNAME;
    @Value("${chumba.bot.token}")
    private String TOKEN;
    private final QueryController queryController;

    public WallferBot(QueryController queryController) {
        super(new DefaultBotOptions());
        this.queryController = queryController;
    }

    @PostConstruct
    private void registerBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi((DefaultBotSession.class));
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), queryController.queryRequest(update).getText());
            sendMessage.setParseMode(ParseMode.MARKDOWNV2);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
