package src;

import src.DictionaryCommandline;
import src.DictionaryApplication;

public class Main {
    public static void main(String[] args) {
        DictionaryCommandline commandline = new DictionaryCommandline();
        commandline.dictionaryAdvance();
        DictionaryApplication application = new DictionaryApplication();
        application.runApplication();
    }
}

