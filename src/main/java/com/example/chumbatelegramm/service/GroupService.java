package com.example.chumbatelegramm.service;

import com.example.chumbatelegramm.util.JsonReader;
import com.example.chumbatelegramm.configuration.UrlConstants;
import com.example.chumbatelegramm.exceptions.BotRequestException;
import com.example.chumbatelegramm.model.ds.GroupDs;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.chumbatelegramm.configuration.ExceptionMessageKeyConstants.GROUP_NOT_EXISTS;

@Service
public class GroupService {
    private final JsonReader jsonReader;
    public GroupService(JsonReader jsonReader ) {
        this.jsonReader = jsonReader;
    }

    public Integer findGroupIdByName(String groupName) {
        JSONArray array = fetchJsonObject();

        return extractGroupId(array, groupName);
    }

    private JSONArray fetchJsonObject() {
        try {
            return jsonReader.readJsonArrayFromUrl(UrlConstants.GROUPS_URL);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer extractGroupId(JSONArray array, String groupName ) {

        // validate if -1
        for (int i = 0; i < array.length(); i++) {
            GroupDs groupDs = createGroupDs(array.getJSONObject(i));
            if (groupName.equalsIgnoreCase(groupDs.name())){
                return groupDs.id();
            }
        }

        throw new BotRequestException(GROUP_NOT_EXISTS);
    }

    private GroupDs createGroupDs(JSONObject groupObject) {
        return new GroupDs(
                groupObject.getString("groupCode"),
                groupObject.getInt("groupId")
        );
    }
}

