package by.derovi.group2019a;

import by.derovi.group2019a.managers.Managers;

public class Bootloader {
    public static void start() {
        Managers.initSettingsManager();
        Managers.initOpenSubtitlesManager(Managers.getSettingsManager().getLogin(), Managers.getSettingsManager().getPassword());
        Managers.initDialogManager();
        Managers.initAnalyzer();
    }
}


