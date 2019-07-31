package by.derovi.group2019a.dialog;

import by.derovi.group2019a.managers.Managers;
import com.github.dnbn.submerge.api.subtitle.srt.SRTSub;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandDownload extends Command{
    public CommandDownload() {
        super("download", "download - download all subtitles for serials from settings to " + Managers.getSettingsManager().getSubtitlesFolder() + " folder", 0);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        System.out.println("\nDownloading all " + Integer.toString(Managers.getSettingsManager().getSerials().size()) + " serials subtitles!");
        File subtitlesFolder = new File(Managers.getSettingsManager().getSubtitlesFolder());
        if(!subtitlesFolder.exists()) {
            System.out.println("Subtitles folder not found, creating");
            subtitlesFolder.mkdir();
        }
        List<String> notFound = new ArrayList<>();
        for(String serialName : Managers.getSettingsManager().getSerials()) {
            File serialFolder = new File(Managers.getSettingsManager().getSubtitlesFolder() + '/' + serialName);
            if(!serialFolder.exists())
                serialFolder.mkdir();
            int seasonCount = 0, seriesCount = 0;
            for(int season = 1; ; season ++) {
                boolean seasonExist = false;
                List<SubtitleInfo> subtitles = new ArrayList<>();
                int seriesSkipped = 0;
                for(int series = 1; ; series ++ ) {
                    try {
                        File seriesFile = new File(Managers.getSettingsManager().getSubtitlesFolder() + '/' + serialName + "/season" + Integer.toString(season) + "/series" + Integer.toString(series) + ".srt");
                        if(seriesFile.exists()) {
                            seasonExist = true;
                            for(int idx = 0; idx <= seriesSkipped; ++ idx)
                                subtitles.add(null);
                            seriesSkipped = 0;
                            continue;
                        }
                        SubtitleInfo subtitleInfo = Managers.getOpenSubtitlesManager().findMostSuitable(serialName, season, series);
                        System.out.println(subtitleInfo.getFileName());
                        seasonExist = true;
                        for(int idx = 0; idx < seriesSkipped; ++ idx)
                            subtitles.add(null);
                        subtitles.add(subtitleInfo);
                        seriesSkipped = 0;
                    } catch (Exception ex) {
                        if(seriesSkipped ++ > 1) break;
                        continue;
                    }
                }
                if(!seasonExist) {
                    seasonCount = season - 1;
                    break;
                }
                File seasonFolder = new File(Managers.getSettingsManager().getSubtitlesFolder() + '/' + serialName + "/season" + Integer.toString(season));
                if(!seasonFolder.exists())
                    seasonFolder.mkdir();
                seriesCount += subtitles.size();
                for(int series = 0; series < subtitles.size(); ++ series) {
                    SubtitleInfo subtitleInfo = subtitles.get(series);
                    if(subtitleInfo == null)
                        continue;
                    Set<String> ignoreSet = new HashSet<>();
                    while(true) {
                        try {
                            File seriesFile = new File(Managers.getSettingsManager().getSubtitlesFolder() + '/' + serialName + "/season" + Integer.toString(season) + "/series" + Integer.toString(series + 1) + ".srt");
                            SRTSub srtSub = Managers.getOpenSubtitlesManager().extractSubtitles(subtitleInfo);
                            FileUtils.writeStringToFile(seriesFile, srtSub.toString());
                            break;
                        } catch (Exception ex) {
                            if(subtitleInfo == null) {
                                System.out.println("Can't find file for " + serialName + '/' + Integer.toString(season) + '/' + Integer.toString(series + 1));
                                notFound.add(serialName + '/' + Integer.toString(season) + '/' + Integer.toString(series + 1));
                                break;
                            }
                            System.out.println(subtitleInfo.getDownloadLink());
                            System.out.println("Can't extract subtitles for " + serialName + '/' + Integer.toString(season) + '/' + Integer.toString(series + 1) + " trying another srt file.");
                            try {
                                ignoreSet.add(subtitleInfo.getZipDownloadLink());
                                subtitleInfo = Managers.getOpenSubtitlesManager().findMostSuitable(serialName, season, series + 1, ignoreSet);
                            } catch (Exception exc) {
                                System.out.println("Can't find file for " + serialName + '/' + Integer.toString(season) + '/' + Integer.toString(series + 1));
                                notFound.add(serialName + '/' + Integer.toString(season) + '/' + Integer.toString(series + 1));
                                break;
                            }
                        }
                    }
                }
            }
            System.out.println("Subtitles of " + serialName + " downloaded, " + Integer.toString(seasonCount) + " seasons consist of " + Integer.toString(seriesCount) + " episodes.");
        }
        System.out.println("Can't find file for:\n");
        for(String str : notFound) {
            System.out.println(str);
        }
        System.out.println();
        System.out.println();
    }
}
