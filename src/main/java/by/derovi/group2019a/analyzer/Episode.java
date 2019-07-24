package by.derovi.group2019a.analyzer;

import by.derovi.group2019a.SubtitleUtils;
import com.github.dnbn.submerge.api.subtitle.srt.SRTLine;
import com.github.dnbn.submerge.api.subtitle.srt.SRTSub;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Episode {
    private int number;
    private List<Phrase> phrases = new ArrayList<>();

    public Episode(int number) {
        this.number = number;
    }

    public Episode(int number, List<Phrase> phrases) {
        this.number = number;
        this.phrases = phrases;
    }

    public Episode(File episodeFile) {
        number = AnalyzerUtils.extractNumber(episodeFile.getName());
        try {
            SRTSub srtSub = SubtitleUtils.getSubs(new FileInputStream(episodeFile));
            for(SRTLine srtLine : srtSub.getLines()) {
                phrases.add(new Phrase(srtLine));
            }
        } catch (Exception ex) {
            System.out.println("Invalid file: " + episodeFile.getPath());
        }
    }

    public int getNumber() {
        return number;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }
}
