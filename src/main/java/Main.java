package main.java;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        /*TextReplace textReplace = new TextReplace(args[0],args[1]);*/
        TextReplace textReplace = new TextReplace("textfile.txt",
                "replacement.properties");

        textReplace.replaceTextAndWriteToFile();
    }
}
