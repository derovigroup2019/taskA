package by.derovi.group2019a.analyzer;

public class AnalyzerUtils {
    public static int extractNumber(String str) {
        String answer = "";
        for(int idx = 0; idx < str.length(); ++ idx)
            if(str.charAt(idx) >= '0' && str.charAt(idx) <= '9')
                answer += str.charAt(idx);
        return Integer.parseInt(answer);
    }
}
