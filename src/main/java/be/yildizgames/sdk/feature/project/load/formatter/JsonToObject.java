package be.yildizgames.sdk.feature.project.load.formatter;

import be.yildizgames.sdk.feature.project.model.Project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToObject {

    public static Project toProject(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, Project.class);
    }
}
