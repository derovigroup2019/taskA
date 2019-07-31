package by.derovi.group2019a.analyzer;

import by.derovi.group2019a.SubtitleUtils;
import com.github.dnbn.submerge.api.subtitle.srt.SRTLine;
import com.github.dnbn.submerge.api.subtitle.srt.SRTTime;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Phrase {
    private List<Sentence> sentences = new ArrayList<>();
    private long duration = 0;
    private SRTTime time;

    public Phrase() {

    }

    public Phrase(SRTLine srtLine) {
        time = srtLine.getTime();
        duration = SubtitleUtils.calculateDuration(time);
        StringBuilder stringBuilder = new StringBuilder();
        for(String line : srtLine.getTextLines()) {
            stringBuilder.append(line).append(' ');
        }
        String text = stringBuilder.toString();
        text = removeTags(text);
        String[] strings = text.split("\\s*[\\.\\?|!]+\\s*");
        for(String str : strings) {
            if(str.length() < 2) continue;
            sentences.add(new Sentence(str));
        }
    }

    private String removeTags(String text) {
        int tagCount = 0;
        StringBuilder ans = new StringBuilder();
        for(int idx = 0; idx < text.length(); ++ idx) {
            if(text.charAt(idx) == '<') {
                ++ tagCount;
                continue;
            }
            if(text.charAt(idx) == '>') {
                -- tagCount;
                continue;
            }
            if(tagCount <= 0)
                ans.append(text.charAt(idx));
        }
        return ans.toString();
    }

    public Phrase(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public SRTTime getTime() {
        return time;
    }

    public void setTime(SRTTime time) {
        this.time = time;
    }
}
