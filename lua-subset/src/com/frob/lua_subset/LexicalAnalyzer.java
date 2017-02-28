package com.frob.lua_subset;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Frob on 2/27/2017.
 */
public class LexicalAnalyzer {
    LexemeTest tester;
    public LexicalAnalyzer() {
        tester = new LexemeTest();
    }
    public ArrayList<String> analyze(String filename) {
        ArrayList<String> lexemes = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath(filename));
            for (String l : lines) {
                String currentFragment = "";
                String previousFragment = "";
                int currentFragmentStart = 0;
                int currentFragmentEnd = 0;
                String line = l.replaceAll("\\s", "").concat(" ");
                while (currentFragmentEnd < line.length()) {
                    previousFragment = currentFragment;
                    currentFragmentEnd++;
                    currentFragment = line.substring(currentFragmentStart, currentFragmentEnd);
                    System.out.println(previousFragment);
                    System.out.println(currentFragment);
                    System.out.println(tester.getPossibilities(currentFragment));
                    if (tester.getPossibilities(currentFragment).size() > 0) {
                        previousFragment = currentFragment;
                    } else {
                        if (previousFragment.length() > 0) {
                            lexemes.add(previousFragment);
                            currentFragment = "";
                            previousFragment = "";
                            currentFragmentStart = currentFragmentEnd - 1;
                            currentFragmentEnd = currentFragmentStart;
                            System.out.println(lexemes);
                        }
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
