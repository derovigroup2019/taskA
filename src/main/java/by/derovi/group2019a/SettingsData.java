package by.derovi.group2019a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsData {
    private List<String> serials = new ArrayList<>(Arrays.asList("Lost", "Sherlock", "The Big Bang Theory", "House", "Game of Thrones", "Supernatural", "How I Met Your Mother", "Dexter", "Breaking Bad", "Misfits"));
    private String lang = "en", login = "derovi", password = "group2019", subtitlesFolder = "subtitles", wordsFolder = "words";

    public List<String> getSerials() {
        return serials;
    }

    public void setSerials(List<String> serials) {
        this.serials = serials;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubtitlesFolder() {
        return subtitlesFolder;
    }

    public String getWordsFolder() {
        return wordsFolder;
    }
}
