package be.yildizgames.sdk.entrypoint;

import be.yildizgames.sdk.configuration.Configuration;
import be.yildizgames.sdk.ui.SdkWindow;

public class EntryPoint {

    public static void main(String[] args) {
        Configuration configuration = new Configuration( System.getProperty("user.home") + "/test-projects");
        new SdkWindow().init(configuration);
    }
}
