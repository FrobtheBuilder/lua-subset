package com.frob.lua_subset;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Scans an input file and extracts lexemes.
 */
public class LexicalAnalyzer {
    LexemeTest tester;
    public ArrayList<String> lexemes;
    public ArrayList<Token> tokens;
    public LexicalAnalyzer() {
        tester = new LexemeTest();
    }
    /**
     * Analyze the input sequence and return a map of the extracted lexemes and their types.
     * @param filename path to the input file
     * @return Ordered map of the extracted lexemes and their respective types
     */
    public ArrayList<Token> analyze(String filename) {
        lexemes = new ArrayList<>();
        tokens = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath(filename));
            for (String l : lines) {
                String currentFragment = "";
                String previousFragment;
                int currentFragmentStart = 0;
                int currentFragmentEnd = 0;
                ArrayList<Token> candidates = new ArrayList<>();
                String line = l.replaceAll("\\s", "").concat(" "); //Whitespace deleted
                while (currentFragmentEnd < line.length()) {
                    previousFragment = currentFragment;
                    currentFragmentEnd++;
                    currentFragment = line.substring(currentFragmentStart, currentFragmentEnd); //Newly created string without whitespaces
                    ArrayList<Token> possibleCandidates = tester.getPossibilities(currentFragment); //Calling the function for lexeme testing
                    if (possibleCandidates.size() > 0) {
                        candidates = possibleCandidates;
                    }
                    else if (previousFragment.length() > 0) {
                        Token tp;
                        if (candidates.size() > 1) {
                            tp = tester.strictMatch(previousFragment);
                        }
                        else {
                            tp = candidates.get(0);
                        }
                        lexemes.add(previousFragment);
                        tokens.add(tp);
                        currentFragment = "";
                        currentFragmentStart = currentFragmentEnd - 1;
                        currentFragmentEnd = currentFragmentStart;
                        //System.out.println(lexemes);
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tokens;
    }
}
