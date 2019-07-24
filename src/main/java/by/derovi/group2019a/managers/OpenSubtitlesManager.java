package by.derovi.group2019a.managers;

import by.derovi.group2019a.NetworkUtils;
import by.derovi.group2019a.SubtitleUtils;
import com.github.dnbn.submerge.api.subtitle.srt.SRTSub;
import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.impl.OpenSubtitlesClientImpl;
import com.github.wtekiela.opensub4j.response.MovieInfo;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public class OpenSubtitlesManager {
    private OpenSubtitlesClient client;
    private boolean ready = false;
    private String login, password;

    public OpenSubtitlesManager(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void auth() {
        try {
            URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
            client = new OpenSubtitlesClientImpl(serverUrl);
            System.out.println("Authorization...");

            client.login(login, password, "en", "TemporaryUserAgent");
            ready = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SubtitleInfo findMostSuitable(String name, int season, int series) throws Exception {
        return findMostSuitable(name, season, series, new HashSet<>());
    }

    public SubtitleInfo findMostSuitable(String name, int season, int series, Set<String> ignoreSet) throws Exception {
        if(!ready) {
            try {
                auth();
            } catch (Exception ex) {
                System.out.println("Authorization error!");
                return null;
            }
        }
        SubtitleInfo best = null;
        int downloadsCount = -1;
        List<SubtitleInfo> subtitles = client.searchSubtitles("eng", name, Integer.toString(season), Integer.toString(series));
        for(SubtitleInfo info : subtitles) {
            if(ignoreSet.contains(info.getZipDownloadLink()))
                continue;
            if(!info.getFormat().equals("srt"))
                continue;
            if(!isSubtitleFits(name, info.getFileName()))
                continue;
            if(info.getDownloadsNo() > downloadsCount) {
                downloadsCount = info.getDownloadsNo();
                best = info;
            }
        }
        return best;
    }

    public boolean isSubtitleFits(String name, String fileName) {
        try {
            name = name.toLowerCase();
            String[] words = name.split(" ");
            String regex = "^";
            for(String word : words) {
                regex += word + "(\\W|_)*";
            }
            regex += "s?\\d";
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(fileName.toLowerCase()).find();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public SRTSub extractSubtitles(SubtitleInfo subtitleInfo) throws Exception {
        ZipInputStream stream = new ZipInputStream(NetworkUtils.sendGet(subtitleInfo.getZipDownloadLink()));
        if(stream.getNextEntry() != null) {
            return SubtitleUtils.getSubs(stream);
        }
        throw new Exception("No files found in zip");
    }

    public boolean isReady() {
        return ready;
    }

    public OpenSubtitlesClient getClient() {
        return client;
    }
}
