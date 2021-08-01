package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextReplace {
    String textFile;
    String propertyFile;

    public TextReplace(String textFile, String propertyFile) {
        this.textFile = textFile;
        this.propertyFile = propertyFile;
    }

    public void replaceTextAndWriteToFile() throws IOException {
        Path textFilePath = Paths.get(textFile).toAbsolutePath();
        Path propertyFilePath = Paths.get(propertyFile).toAbsolutePath();
        Path resultFilePath = Paths.get("result.txt").toAbsolutePath();

        //Convert the properties file into key/value pair
        List<String> list = Files.lines(propertyFilePath).collect(Collectors.toList());
        Map<String, String> replacedWordsMap = replaceWithWords(list);

        //Replace the text as per the property file and write to a outfile
        List<String> lines = Files.lines(textFilePath).collect(Collectors.toList());
        String replacedContents = replaceWords(lines.toString(), replacedWordsMap);
        Files.write(resultFilePath, Collections.singleton(replacedContents));
    }

    public Map<String, String> replaceWithWords(List<String> list) {
        Map<String, String> map = new HashMap<>();
        list.forEach(word -> map.put(word.split("=")[0], word.split("=")[1]));
        return map;
    }

    public String replaceWords(String data, Map<String, String> map) {
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (data.contains(entry.getKey())) {
                int occurrences = data.split(entry.getKey()).length - 1;
                wordCount.put(entry.getKey(), occurrences);
                data = data.replace(entry.getKey(), entry.getValue());
            }
        }
        wordCount.forEach((key, value) -> System.out.println(key.toUpperCase() + " is replaced " + value + " times"));
        return data;
    }
}

