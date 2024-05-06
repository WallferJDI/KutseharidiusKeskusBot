package com.example.chumbatelegramm.service;

import com.example.chumbatelegramm.util.JsonReader;
import com.example.chumbatelegramm.configuration.UrlConstants;
import com.example.chumbatelegramm.model.entity.Lesson;
import org.glassfish.grizzly.utils.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.chumbatelegramm.configuration.JsonParseConstants.*;

@Service
public class TimeTableService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final int DAYS_IN_RANGE = 5;
    private final JsonReader jsonReader;

    public TimeTableService(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public String requestTimeTable(Integer groupId) {
        JSONObject jsonObject = fetchJsonObject(groupId);
        JSONArray array = jsonObject.getJSONArray(TABLE);

        Pair<Integer, LocalDate> startParseDate = findStartParseDayIndex(array);
        LocalDateTime dayRange = startParseDate.getSecond().atStartOfDay().plusDays(DAYS_IN_RANGE);

        List<Lesson> lessonEntities = extractLessonEntities(array, startParseDate.getFirst(), dayRange);
        Collections.sort(lessonEntities);

        return formatLessonEntities(lessonEntities);
    }

    private JSONObject fetchJsonObject(Integer groupId) {
        try {
            return jsonReader.readJsonFromUrl(UrlConstants.generateURL(groupId));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Pair<Integer, LocalDate> findStartParseDayIndex(JSONArray array) {
        LocalDate startDate = LocalDate.now();

        for (int i = 0; i < array.length(); i++) {
            LocalDate lessonDate = LocalDate.parse(array.getJSONObject(i).getString(DATE), DateTimeFormatter.ISO_DATE_TIME);
            if (startDate.isEqual(lessonDate) || startDate.isBefore(lessonDate)) {
                return new Pair<>(i, lessonDate);
            }
        }
        return new Pair<>();
    }

    private List<Lesson> extractLessonEntities(JSONArray array, int startIndex, LocalDateTime dayRange) {
        List<Lesson> lessonEntities = new ArrayList<>();

        // validate if -1
        for (int i = startIndex; i < array.length(); i++) {
            LocalDateTime dateTime = LocalDateTime.parse(array.getJSONObject(i).getString(DATE), DATE_FORMATTER);

            if (dateTime.isEqual(dayRange) || dateTime.isAfter(dayRange)) {
                break;
            }

            Lesson lesson = createLessonEntity(array.getJSONObject(i));
            lessonEntities.add(lesson);
        }

        return lessonEntities;
    }

    private Lesson createLessonEntity(JSONObject lessonObject) {
        try {
            return new Lesson(
                    lessonObject.getString(DATE),
                    lessonObject.getString(LESSON_NAME),
                    lessonObject.getString(LESSON_TIME_START),
                    lessonObject.getString(LESSON_TIME_END),
                    lessonObject.getJSONArray(ROOMS_ARRAY).getJSONObject(0).getString(ROOM_CODE),
                    LocalDateTime.parse(lessonObject.getString(DATE), DATE_FORMATTER).getDayOfWeek()
            );
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatLessonEntities(List<Lesson> lessonEntities) {
        StringBuilder stringBuilder = new StringBuilder();
        lessonEntities.forEach(item -> stringBuilder.append("```")
                .append(item.getDate())
                .append("-")
                .append(item.getDayOfWeek().toString().toLowerCase())
                .append("\n")
                .append(item).append("```"));
        return stringBuilder.toString();
    }
}
