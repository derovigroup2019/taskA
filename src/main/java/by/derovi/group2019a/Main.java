package by.derovi.group2019a;

import by.derovi.group2019a.managers.DialogManager;
import by.derovi.group2019a.managers.Managers;

public class Main {
    public static void main(String[] args) {
        Bootloader.start();
        Managers.getDialogManager().start();
        /*try {
            int cur = 10;
            while(true) {
                SubtitleInfo subtitleInfo = null;
                try {
                     subtitleInfo = Managers.getOpenSubtitlesManager().findMostSuitable("Game Of Thrones", 1, cur);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if(subtitleInfo == null) {
                    break;
                }
                ++ cur;
                System.out.println(Managers.getOpenSubtitlesManager().extractSubtitles(subtitleInfo));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }


}
