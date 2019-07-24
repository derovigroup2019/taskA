package by.derovi.group2019a.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Season {
    private int number;
    private List<Episode> episodes = new ArrayList<>();

    public Season(int number) {
        this.number = number;
    }

    public Season(int number, List<Episode> episodes) {
        this.number = number;
        this.episodes = episodes;
    }

    public Season(File folder) {
        number = AnalyzerUtils.extractNumber(folder.getName());
        File[] folders = folder.listFiles();
        if(folders != null) for(File episodeFile : folders) {
            if(!episodeFile.exists()) continue;
            if(!episodeFile.isFile()) continue;
            episodes.add(new Episode(episodeFile));
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
