package com.example.chumbatelegramm.util;

import com.example.chumbatelegramm.model.entity.Lesson;
import com.example.chumbatelegramm.model.entity.User;
import org.glassfish.grizzly.utils.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CacheUtil {


    // When kutseharidus will fix Json Lazy loading -> return lesson caching
    private static final Pair<LocalDate, List<Lesson>> todayLessonQueryResult = new Pair<>();
    private static final HashMap<Long, User> authorizedUsers = new HashMap<>();

    public static Optional<List<Lesson>> tryGetLastLessonQueryResult() {
        if (LocalDate.now().isEqual(todayLessonQueryResult.getFirst())) {
            return Optional.ofNullable(todayLessonQueryResult.getSecond());
        } else if (todayLessonQueryResult.getFirst() != null) {
            todayLessonQueryResult.release();
        }
        return Optional.empty();
    }

    public static void setTodayLessonQueryResult(List<Lesson> lessonEntities) {
        todayLessonQueryResult.setFirst(LocalDate.now());
        todayLessonQueryResult.setSecond(lessonEntities);
    }

    public static void addAuthorizedUser(User user) {
        tryRemoveAuthorizedUserById(user.getTelegramMemberId());
        authorizedUsers.put(user.getTelegramMemberId(), user);
    }

    public static Optional<User> tryGetAuthorizedUserById(Long id) {
        return Optional.ofNullable(authorizedUsers.get(id));
    }

    public static void tryRemoveAuthorizedUserById(Long id) {
        if (authorizedUsers.get(id) != null) {
            authorizedUsers.remove(id);
        }
    }
}
