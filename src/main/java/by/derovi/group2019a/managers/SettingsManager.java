package by.derovi.group2019a.managers;

import by.derovi.group2019a.SettingsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class SettingsManager {
    private List<String> serials;
    private String lang, login, password;
    private String subtitlesFolder, wordsFolder;
    final private String settingsFileName = "settings.json";

    public SettingsManager() {
        try {
            loadSettings();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Can't load settings!");
        }
    }

    private void loadSettings() throws Exception {
        File settingsFile = new File(settingsFileName);
        SettingsData settingsData = null;

        if(settingsFile.exists()) {
            try {
                settingsData = new Gson().fromJson(new FileReader(settingsFile), SettingsData.class);
            } catch (Exception ex) {
                System.out.println("Invalid json file");
            }
        }
        if(settingsData == null)
            settingsData = new SettingsData();
        FileUtils.writeStringToFile(settingsFile, new GsonBuilder().setPrettyPrinting().create().toJson(settingsData));
        loadData(settingsData);
        loadWordsFolder();
    }

    private void loadWordsFolder() {
        File file = new File(wordsFolder);
        if(!file.exists())
            file.mkdir();
        if(!file.isDirectory()) {
            System.out.println("Invalid words folder!");
            return;
        }
        File adjectivesFile = new File(wordsFolder + "/adjectives.txt");
        File nounsFile = new File(wordsFolder + "/nouns.txt");
        File verbsFile = new File(wordsFolder + "/verbs.txt");
        File adverbsFile = new File(wordsFolder + "/adverbs.txt");
        File pronounsFile = new File(wordsFolder + "/pronouns.txt");
        if(!adjectivesFile.exists())
            extractWordFile("adjectives", adjectivesFile);
        if(!nounsFile.exists())
            extractWordFile("nouns", nounsFile);
        if(!verbsFile.exists())
            extractWordFile("verbs", verbsFile);
        if(!adverbsFile.exists())
            extractWordFile("adverbs", adverbsFile);
        if(!pronounsFile.exists())
            extractWordFile("pronouns", pronounsFile);
    }

    public void extractWordFile(String type, File dest) {
        try {
            URL inputUrl = getClass().getResource("/words/" + type + ".txt");
            FileUtils.copyURLToFile(inputUrl, dest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData(SettingsData settingsData) {
        serials = settingsData.getSerials();
        lang = settingsData.getLang();
        login = settingsData.getLogin();
        password = settingsData.getPassword();
        subtitlesFolder = settingsData.getSubtitlesFolder();
        wordsFolder = settingsData.getWordsFolder();
    }

    public List<String> getSerials() {
        return serials;
    }

    public String getLang() {
        return lang;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSubtitlesFolder() {
        return subtitlesFolder;
    }

    public String getWordsFolder() {
        return wordsFolder;
    }
}
