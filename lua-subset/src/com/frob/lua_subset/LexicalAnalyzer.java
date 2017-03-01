package com.frob.lua_subset;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Frob on 2/27/2017.
 */

/*
 * This class will start the process of analyzing the string and passing it
 * to test the string for any lexemes.
 */
public class LexicalAnalyzer {
    LexemeTest tester;
    public LexicalAnalyzer() {
        tester = new LexemeTest();
    }
    /*
     * This function will scan the string in the test.lua, deleting all whitespace
     * and testing for any lexemes in the file. 
     */
    public LinkedHashMap<String, LexemeType> analyze(String filename) {
        LinkedHashMap<String, LexemeType> lexemes = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath(filename));
            for (String l : lines) {
                String currentFragment = "";
                String previousFragment;
                int currentFragmentStart = 0;
                int currentFragmentEnd = 0;
                ArrayList<LexemeType> candidates = new ArrayList<>();
                String line = l.replaceAll("\\s", "").concat(" "); //Whitespace deleted
                while (currentFragmentEnd < line.length()) {
                    previousFragment = currentFragment;
                    currentFragmentEnd++;
                    currentFragment = line.substring(currentFragmentStart, currentFragmentEnd); //Newly created string without whitespaces
                    ArrayList<LexemeType> possibleCandidates = tester.getPossibilities(currentFragment); //Calling the function for lexeme testing
                    if (possibleCandidates.size() > 0) {
                        candidates = possibleCandidates;
                    }
                    else if (previousFragment.length() > 0) {
                        LexemeType tp;
                        if (candidates.size() > 1) {
                            tp = tester.strictMatch(previousFragment);
                        }
                        else {
                            tp = candidates.get(0);
                        }
                        lexemes.put(previousFragment, tp);
                        currentFragment = "";
                        currentFragmentStart = currentFragmentEnd - 1;
                        currentFragmentEnd = currentFragmentStart;
                        //System.out.println(lexemes);
                    }
                }
            }
            /*
            Files.lines(FileSystems.getDefault().getPath(filename)).forEach(line -> {
                for (int i=0; i < line.length(); i++) {
                    if (currentFragment[0].length() == 0) {
                        previousFragment[0] = currentFragment[0];
                        currentFragment[0] = line.substring()
                    }
                }
            });
            */
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lexemes;
    }
}
