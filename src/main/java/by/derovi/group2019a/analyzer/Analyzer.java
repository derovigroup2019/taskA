package by.derovi.group2019a.analyzer;

import by.derovi.group2019a.SubtitleUtils;
import by.derovi.group2019a.dialog.OutputLine;
import by.derovi.group2019a.managers.Managers;

import java.io.File;
import java.util.*;

public class Analyzer {
    private boolean prepared = false;
    private List<Serial> serials = new ArrayList<>();
    private WordTypeRecognizer wordTypeRecognizer;

    public Analyzer() {
        wordTypeRecognizer = new WordTypeRecognizer();
    }

    public void prepare() {
        if(prepared) return;
        System.out.println("Not prepared, preparing..");
        File subtitlesFolder = new File(Managers.getSettingsManager().getSubtitlesFolder());
        if(!subtitlesFolder.exists()) return;
        File[] files = subtitlesFolder.listFiles();
        if(files != null) for(File serialFolder : files) {
            if(!serialFolder.exists()) continue;
            if(!serialFolder.isDirectory()) continue;
            serials.add(new Serial(serialFolder));
        }
        prepared = true;
        System.out.println("\nPrepared.");
    }

    public void serialsStatistics() {
        prepare();
        System.out.println();
        int seasonsCount = 0, episodesCount = 0;
        List<OutputLine> outputLines = new ArrayList<>();
        for(Serial serial : serials) {
            int count = 0;
            for(Season season : serial.getSeasons()) {
                count += season.getEpisodes().size();
            }
            outputLines.add(new OutputLine(serial.getName() + " - " + Integer.toString(serial.getSeasons().size()) + " seasons, " + Integer.toString(count) + " episodes.", count));
            episodesCount += count;
            seasonsCount += serial.getSeasons().size();
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println("------------------------\n" + Integer.toString(serials.size()) + " serials, " + Integer.toString(seasonsCount) + " seasons, " + Integer.toString(episodesCount) + " episodes.");
    }

    public void wordsCount() {
        prepare();
        System.out.println();
        int wordsCount = 0, wordsLength = 0;
        long wordsDuration = 0;
        List<OutputLine> outputLines = new ArrayList<>();
        for(Serial serial : serials) {
            int count = 0, length = 0;
            long duration = 0;
            for(Season season : serial.getSeasons()) {
                for(Episode episode : season.getEpisodes()) {
                    for(Phrase phrase : episode.getPhrases()) {
                        duration += phrase.getDuration();
                        for(Sentence sentence : phrase.getSentences()) {
                            count += sentence.getWords().size();
                            for(String word : sentence.getWords())
                                length += word.length();
                        }
                    }
                }
            }
            outputLines.add(new OutputLine(serial.getName() + " - " + Integer.toString(count) + " words. Average word length is " +
                    getAverage(length, count) + " letters, average duration is " + getAverage(duration / 1000, count) + " s. (" +
                    getAverage(60, Double.parseDouble(getAverage(duration / 1000, count))) + " words per minute)", count));
            wordsCount += count;
            wordsLength += length;
            wordsDuration += duration;
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println("------------------------\n" + Integer.toString(serials.size()) + " serials, " + Integer.toString(wordsCount) +
                " words. Average word length is " + getAverage(wordsLength, wordsCount) +
                " letters, average duration is " + getAverage(wordsDuration / 1000, wordsCount) +
                " s. (" + getAverage(60, Double.parseDouble(getAverage(wordsDuration / 1000, wordsCount))) + " words per minute)");
    }

    public void wordsTypes() {
        prepare();
        System.out.println();
        List<OutputLine> outputLines = new ArrayList<>();
        Map<String, Integer> globalTypes = new HashMap<>();
        int globalCount = 0;
        for(Serial serial : serials) {
            Map<String, Integer> types = new HashMap<>();
            int count = 0;
            for(Season season : serial.getSeasons()) {
                for(Episode episode : season.getEpisodes()) {
                    for(Phrase phrase : episode.getPhrases()) {
                        for(Sentence sentence : phrase.getSentences()) {
                            count += sentence.getWords().size();
                            for(String word : sentence.getWords()) {
                                for(String type : wordTypeRecognizer.defineWordType(word)) {
                                    types.put(type, types.getOrDefault(type, 0) + 1);
                                }
                            }
                        }
                    }
                }
            }
            for(Map.Entry<String, Integer> entry : types.entrySet()) {
                globalTypes.put(entry.getKey(), globalTypes.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
            StringBuilder output = new StringBuilder();
            output.append(serial.getName() + " - " + Integer.toString(count) + " words.\n- Adjectives count is " + types.get("adjectives") + ";");
            output.append("\n- Verbs count is " + types.get("verbs") + ";");
            output.append("\n- Nouns count is " + types.get("nouns") + ";");
            output.append("\n- Adverbs count is " + types.get("adverbs") + ";");
            output.append("\n- Pronouns count is " + types.get("pronouns") + ";\n");

            outputLines.add(new OutputLine(output.toString(), count));

            globalCount += count;
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        StringBuilder output = new StringBuilder();
        outputLines.clear();
        output.append("------------------------\n" + Integer.toString(serials.size()) + " serials, " + Integer.toString(globalCount) + " words.\n");
        outputLines.add(new OutputLine("\n- Verbs count is " + globalTypes.get("verbs") + ";", globalTypes.get("verbs")));
        outputLines.add(new OutputLine("\n- Adjectives count is " + globalTypes.get("adjectives") + ";", globalTypes.get("adjectives")));
        outputLines.add(new OutputLine("\n- Nouns count is " + globalTypes.get("nouns") + ";", globalTypes.get("nouns")));
        outputLines.add(new OutputLine("\n- Adverbs count is " + globalTypes.get("adverbs") + ";", globalTypes.get("adverbs")));
        outputLines.add(new OutputLine("\n- Pronouns count is " + globalTypes.get("pronouns") + ";", globalTypes.get("pronouns")));
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println(output.toString());
    }

    public void sentencesCount() {
        prepare();
        System.out.println();
        int wordsCount = 0, sentencesCount = 0;
        List<OutputLine> outputLines = new ArrayList<>();
        for(Serial serial : serials) {
            int countW = 0, countS = 0;
            for(Season season : serial.getSeasons()) {
                for(Episode episode : season.getEpisodes()) {
                    for(Phrase phrase : episode.getPhrases()) {
                        countS += phrase.getSentences().size();
                        for(Sentence sentence : phrase.getSentences()) {
                            countW += sentence.getWords().size();
                        }
                    }
                }
            }
            outputLines.add(new OutputLine(serial.getName() + " - " + Integer.toString(countS) + " sentences. Average sentence length is " + getAverage(countW, countS) + " words", countS));
            wordsCount += countW;
            sentencesCount += countS;
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println("------------------------\nThere are " + Integer.toString(sentencesCount) + " sentences in all serials. Average sentence length is " + getAverage(wordsCount, sentencesCount) + " words");
    }

    public void countWord(String countableWord) {
        prepare();
        System.out.println();
        List<OutputLine> outputLines = new ArrayList<>();
        int wordsCount = 0;
        for(Serial serial : serials) {
            int count = 0;
            for(Season season : serial.getSeasons()) {
                for(Episode episode : season.getEpisodes()) {
                    for(Phrase phrase : episode.getPhrases()) {
                        for(Sentence sentence : phrase.getSentences()) {
                            for(String word : sentence.getWords())
                                if(word.equalsIgnoreCase(countableWord))
                                    count ++;
                        }
                    }
                }
            }
            outputLines.add(new OutputLine(serial.getName() + " - word " + countableWord + " occurs " + Integer.toString(count) + " times", count));
            wordsCount += count;
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println("------------------------\n Word " + countableWord + " occurs " + Integer.toString(wordsCount) + " times");
    }

    private String getAverage(double param, double count) {
        double ans = param / count;
        int intAns = (int) (ans * 100);
        return Double.toString((double) intAns / 100f);
    }

    public void uniqueWordsCount() {
        prepare();
        System.out.println();
        List<OutputLine> outputLines = new ArrayList<>();
        Set<String> uniqueWords = new TreeSet<>();
        for(Serial serial : serials) {
            Map<String, Integer> words = new HashMap<>();
            for(Season season : serial.getSeasons()) {
                for(Episode episode : season.getEpisodes()) {
                    for(Phrase phrase : episode.getPhrases()) {
                        for(Sentence sentence : phrase.getSentences()) {
                            for(String word : sentence.getWords()) {
                                if(!words.containsKey(word.toLowerCase()))
                                    words.put(word.toLowerCase(), 1);
                                else
                                    words.put(word.toLowerCase(), words.get(word.toLowerCase()) + 1);
                            }
                        }
                    }
                }
            }
            String mostUsed = "";
            int usedCount = 0;
            for(Map.Entry<String, Integer> entry : words.entrySet()) {
                uniqueWords.add(entry.getKey());
                if(entry.getValue() > usedCount) {
                    mostUsed = entry.getKey();
                    usedCount = entry.getValue();
                }
            }
            outputLines.add(new OutputLine(serial.getName() + " - " + Integer.toString(words.size()) + " unique words. Most used is " +
                    mostUsed + ", " + usedCount + " entries.", words.size()));
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println("------------------------\n" + Integer.toString(serials.size()) + " serials, " + Integer.toString(uniqueWords.size()) + " unique words.");
    }

    public void uniqueWordsTop() {
        prepare();
        System.out.println();
        List<OutputLine> outputLines = new ArrayList<>();
        Map<String, Integer> words = new HashMap<>();
        for(Serial serial : serials) {
            for(Season season : serial.getSeasons()) {
                for(Episode episode : season.getEpisodes()) {
                    for(Phrase phrase : episode.getPhrases()) {
                        for(Sentence sentence : phrase.getSentences()) {
                            for(String word : sentence.getWords()) {
                                if(!words.containsKey(word.toLowerCase()))
                                    words.put(word.toLowerCase(), 1);
                                else
                                    words.put(word.toLowerCase(), words.get(word.toLowerCase()) + 1);
                            }
                        }
                    }
                }
            }
        }
        List<OutputLine> popularWords = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : words.entrySet()) {
            popularWords.add(new OutputLine(entry.getKey(), entry.getValue()));
        }
        Collections.sort(popularWords, Collections.reverseOrder());
        for(int idx = 0; idx < Math.min(30, popularWords.size()); ++ idx) {
            outputLines.add(new OutputLine(Integer.toString(idx + 1) + ". " + popularWords.get(idx).getLine() + " - " +
                    Integer.toString(popularWords.get(idx).getPriority()) + " entries", popularWords.get(idx).getPriority()));
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        System.out.println("------------------------\n" + Integer.toString(serials.size()) + " serials, " + Integer.toString(words.size()) + " unique words.");
    }

    public void showDuration() {
        prepare();
        System.out.println();
        List<OutputLine> outputLines = new ArrayList<>();
        long duration = 0, speechDuration = 0;
        for(Serial serial : serials) {
            long serialDuration = 0, serialSpeechDuration = 0;
            for(Season season : serial.getSeasons()) {
                for (Episode episode : season.getEpisodes()) {
                    long episodeDuration = 0;
                    for (Phrase phrase : episode.getPhrases()) {
                        episodeDuration = SubtitleUtils.localTimeToMilliseconds(phrase.getTime().getEnd());
                        serialSpeechDuration += phrase.getDuration();
                    }
                    serialDuration += episodeDuration;
                }
            }
            serialDuration /= 1000 * 60;
            serialSpeechDuration /= 1000 * 60;
            int percent = (int) (((double) serialSpeechDuration / (double) serialDuration) * 100 + 0.5);
            outputLines.add(new OutputLine(serial.getName() + " lasts " + Long.toString((serialDuration + 30) / 60) + " hours " + humanizeMinutes(serialDuration) + ",\n speech duration is " + Long.toString((serialSpeechDuration + 30) / 60) + "h " + humanizeMinutes(serialSpeechDuration) + ". Speech percent is " + percent + "%.\n", (int) serialDuration / 60));
            duration += serialDuration;
            speechDuration += serialSpeechDuration;
        }
        Collections.sort(outputLines, Collections.reverseOrder());
        for(OutputLine outputLine : outputLines)
            outputLine.print();
        int percent = (int) (((double) speechDuration / (double) duration) * 100 + 0.5);
        System.out.println("------------------------\n" + Long.toString((duration + 30) / 60) + " hours " + humanizeMinutes(duration) + ",\n speech duration is " + Long.toString((speechDuration + 30) / 60) + "h " + humanizeMinutes(speechDuration) + ". Speech percent is " + percent + "%.\n");
    }


    private String humanizeMinutes(long duration) {
        return "(" + Long.toString(duration / 60 / 24) + " days, " + Long.toString(duration / 60 % 24) + " hours, " + Long.toString(duration % 60)+ " minutes)";
    }

    public void setPrepared(boolean ready) {
        this.prepared = ready;
    }

    public boolean isPrepared() {
        return prepared;
    }
}
