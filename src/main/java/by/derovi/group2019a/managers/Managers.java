package by.derovi.group2019a.managers;

import by.derovi.group2019a.analyzer.Analyzer;

public class Managers {
    private static OpenSubtitlesManager openSubtitlesManager;
    private static SettingsManager settingsManager;
    private static DialogManager dialogManager;
    private static Analyzer analyzer;

    public static OpenSubtitlesManager getOpenSubtitlesManager() {
        return openSubtitlesManager;
    }

    public static void initOpenSubtitlesManager(String login, String password) {
        openSubtitlesManager = new OpenSubtitlesManager(login, password);
    }

    public static DialogManager getDialogManager() {
        return dialogManager;
    }

    public static void initDialogManager() {
        dialogManager = new DialogManager();
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static void initSettingsManager() {
        settingsManager = new SettingsManager();
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static void initAnalyzer() {
        analyzer = new Analyzer();
    }
}

