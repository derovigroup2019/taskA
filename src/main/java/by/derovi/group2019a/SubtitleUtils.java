package by.derovi.group2019a;

import com.github.dnbn.submerge.api.parser.SRTParser;
import com.github.dnbn.submerge.api.subtitle.srt.SRTSub;
import com.github.dnbn.submerge.api.subtitle.srt.SRTTime;

import java.io.InputStream;
import java.time.LocalTime;

public class SubtitleUtils {
    public static SRTSub getSubs(InputStream inputStream) {
        return new SRTParser().parse(inputStream, "lol");
    }

    public static long calculateDuration(SRTTime srtTime) {
        return localTimeToMilliseconds(srtTime.getEnd()) - localTimeToMilliseconds(srtTime.getStart());
    }

    public static long localTimeToMilliseconds(LocalTime localTime) {
        return ((localTime.getHour() * 60L + localTime.getMinute()) * 60L + localTime.getSecond()) * 1000L;
    }
}
