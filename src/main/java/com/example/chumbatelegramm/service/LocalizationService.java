package com.example.chumbatelegramm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;

@Service
public class LocalizationService {

    private final List<String> localeKeys = asList("en","ru");

    private final MessageSource messageSource;

    private final LocaleResolver localeResolver;

    public LocalizationService(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }


    public String getMessageByKey(String key, String languageCode){
       return messageSource.getMessage(key, null,
               localeKeys.contains(languageCode) ? Locale.forLanguageTag(languageCode) : Locale.forLanguageTag("en"));
    }
}
