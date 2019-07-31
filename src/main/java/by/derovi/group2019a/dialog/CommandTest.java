package by.derovi.group2019a.dialog;

import by.derovi.group2019a.analyzer.Analyzer;
import by.derovi.group2019a.managers.Managers;
import by.derovi.group2019a.managers.OpenSubtitlesManager;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;

import java.util.List;

public class CommandTest extends Command{
    public CommandTest() {
        super("test", "test command", 2);
    }

    @Override
    public void execute(String[] args) throws CommandArgumentsException {
        int season = Integer.parseInt(args[0]);
        int series = Integer.parseInt(args[1]);

        try {
            Managers.getOpenSubtitlesManager().auth();
            List<SubtitleInfo> subtitles = Managers.getOpenSubtitlesManager().getClient().searchSubtitles("eng", "house", Integer.toString(season), Integer.toString(series));
            for(SubtitleInfo info : subtitles) {
                System.out.println(info.getFileName());
                if(Managers.getOpenSubtitlesManager().isSubtitleFits("house", info.getFileName())) {
                    //System.out.println("FITS!!!");
                    //System.out.println(info.getZipDownloadLink());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
