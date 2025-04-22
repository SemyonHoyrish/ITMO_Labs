package itmo.semyonh.lab6.helpers;

import com.google.gson.Gson;
import itmo.semyonh.lab6.commands.Command;
import itmo.semyonh.lab6.net.Request;
import itmo.semyonh.lab6.net.Response;
import itmo.semyonh.lab6.types.StudyGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converter {

    public static String commandToJson(Command cmd) {
        Gson gson = new Gson();
        return gson.toJson(cmd);
    }

    public static Command commandFromJson(String json, Type t) {
        Gson gson = new Gson();
        return gson.fromJson(json, t);
    }

    public static String requestToJson(Request request) {
        return new Gson().toJson(request);
    }

    public static Request requestFromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }

    public static String responseToJson(Response response) {
        return new Gson().toJson(response);
    }

    public static Response responseFromJson(String json) {
        return new Gson().fromJson(json, Response.class);
    }

    public static String studyGroupToJson(StudyGroup studyGroup) {
        return new Gson().toJson(studyGroup);
    }

    public static StudyGroup studyGroupFromJson(String json) {
        return new Gson().fromJson(json, StudyGroup.class);
    }

    public static String studyGroupListToJson(ArrayList<StudyGroup> studyGroups) {
        return new Gson().toJson(studyGroups);
    }

    public static ArrayList<StudyGroup> studyGroupListFromJson(String json) {
        return new Gson().fromJson(json, ArrayList.class);
    }
}
