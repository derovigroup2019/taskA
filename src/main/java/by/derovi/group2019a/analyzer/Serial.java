package by.derovi.group2019a.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Serial {
    private String name;
    private List<Season> seasons = new ArrayList<>();

    public Serial(String name) {
        this.name = name;
    }

    public Serial(String name, List<Season> seasons) {
        this.name = name;
        this.seasons = seasons;
    }

    public Serial(File folder) {
        name = folder.getName();
        File[] folders = folder.listFiles();
        if(folders != null) for(File seasonFolder : folders) {
            if(!seasonFolder.exists()) continue;
            if(!seasonFolder.isDirectory()) continue;
            seasons.add(new Season(seasonFolder));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
}

