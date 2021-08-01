package test.java;

import main.java.TextReplace;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextReplaceTest {

    @Test
    public void replaceTextAndWriteToFile_textFilePropertyFileFound_success() throws IOException {
        TextReplace textReplace = new TextReplace("textfile.txt",
                "replacement.properties");
        textReplace.replaceTextAndWriteToFile();

        Path resultFilePath = Paths.get("result.txt").toAbsolutePath();
        File file = resultFilePath.toFile();
        Assert.assertTrue(file.exists());
    }

    @Test(expected = Exception.class)
    public void replaceTextAndWriteToFile_noFile_exception() throws IOException {
        TextReplace textReplace = new TextReplace("textfile.txt",
                "");
        textReplace.replaceTextAndWriteToFile();
    }

    @Test
    public void replaceWithWords_textForReplace_keyValueMapping() {
        TextReplace textReplace = new TextReplace("textfile.txt",
                "replacement.properties");

        List<String> testlist = new ArrayList<>();
        testlist.add("hand=foot");
        testlist.add("business=occupation");
        testlist.add("sydney=NSW");
        Map<String, String> actual = textReplace.replaceWithWords(testlist);
        Assert.assertEquals("foot", actual.get("hand"));
        Assert.assertEquals("occupation", actual.get("business"));
        Assert.assertEquals("NSW", actual.get("sydney"));
    }

    @Test
    public void replaceWithWords_emptyList_emptyMapping() {
        TextReplace textReplace = new TextReplace("textfile.txt",
                "replacement.properties");
        List<String> testlist = new ArrayList<>();
        Map<String, String> actual = textReplace.replaceWithWords(testlist);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void replaceWords_textForReplaceExistsInData_matchFoundTextReplaced() {
        TextReplace textReplace = new TextReplace("textfile.txt",
                "replacement.properties");
        String data = "this is a test file;data in the test file,";
        Map<String, String> map = new HashMap<>();
        map.put("test", "TEST");
        map.put("file", "FILE");
        String actual = textReplace.replaceWords(data, map);
        Assert.assertEquals("this is a TEST FILE;data in the TEST FILE,", actual);
    }

    @Test
    public void replaceWords_textForReplaceExistsNotFoundData_noMatchTextNotReplaced() {
        TextReplace textReplace = new TextReplace("textfile.txt",
                "replacement.properties");
        String data = "this is a test file;data in the test file,";
        Map<String, String> map = new HashMap<>();
        map.put("business", "TEST");
        map.put("occupation", "FILE");
        String actual = textReplace.replaceWords(data, map);
        Assert.assertEquals("this is a test file;data in the test file,", actual);
    }
}