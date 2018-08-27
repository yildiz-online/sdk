package be.yildizgames.sdk.feature.project.save.formatter;

import be.yildizgames.sdk.feature.project.model.Project;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObjectToJson {

    public static String fromProject(Project p) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(p);
    }
}
