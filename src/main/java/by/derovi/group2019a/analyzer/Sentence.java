package by.derovi.group2019a.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Sentence {
    private List<String> words = new ArrayList<>();

    public Sentence() {

    }

    public Sentence(List<String> words) {
        this.words = words;
    }

    public Sentence(String str) {
        String[] strings = str.split("[^0-9a-zA-Z']+");
        for(String word : strings) {
            if(word.length() == 0) continue;
            word = word.toLowerCase();
            if (isWord(word)) {
                if(word.contains("'")) {
                    String[] words2 = word.split("'");
                    if(words2.length != 2) {
                        words.add(word);
                        continue;
                    }
                    String full = getFullWord(words2[1]);
                    if(full == null) {
                        words.add(word);
                        continue;
                    }
                    if(full.equals("not"))
                        words2[0] = words2[0].substring(0, words2[0].length() - 1);
                    words.add(words2[0]);
                    words.add(full);
                } else words.add(word);
            } else
            if(isNumeric(word)) {
                try {
                    String[] words2 = numberToWord(Integer.parseInt(word)).split("\\s");
                    for(String wrd : words2) words.add(wrd);
                } catch (Exception ex) { }
            }
        }
    }

    public String getFullWord(String str) {
        if(str.equals("s")) return "is";
        if(str.equals("ve")) return "have";
        if(str.equals("m")) return "am";
        if(str.equals("re")) return "are";
        if(str.equals("t")) return "not";
        if(str.equals("ll")) return "will";
        if(str.equals("d")) return "had";
        return null;
    }

    public static boolean isWord(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z']+$");
        return pattern.matcher(str).find();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).find();
    }

    public List<String> getWords() {
        return words;
    }

    private static String numberToWord(int number) {
        String words = "";
        String unitsArray[] = { "zero", "one", "two", "three", "four", "five", "six",
                "seven", "eight", "nine", "ten", "eleven", "twelve",
                "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
                "eighteen", "nineteen" };
        String tensArray[] = { "zero", "ten", "twenty", "thirty", "forty", "fifty",
                "sixty", "seventy", "eighty", "ninety" };
        if (number == 0) {
            return "zero";
        }
        if (number < 0) {
            String numberStr = "" + number;
            numberStr = numberStr.substring(1);
            return "minus " + numberToWord(Integer.parseInt(numberStr));
        }
        if ((number / 1000000) > 0) {
            words += numberToWord(number / 1000000) + " million ";
            number %= 1000000;
        }
        if ((number / 1000) > 0) {
            words += numberToWord(number / 1000) + " thousand ";
            number %= 1000;
        }
        if ((number / 100) > 0) {
            words += numberToWord(number / 100) + " hundred ";
            number %= 100;
        }
        if (number > 0) {
            if (number < 20) {
                words += unitsArray[number];
            } else {
                words += tensArray[number / 10];
                if ((number % 10) > 0) {
                    words += " " + unitsArray[number % 10];
                }
            }
        }
        return words;
    }
}
