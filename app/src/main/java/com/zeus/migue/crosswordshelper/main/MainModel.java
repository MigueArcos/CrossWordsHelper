package com.zeus.migue.crosswordshelper.main;

import com.zeus.migue.crosswordshelper.Database;
import com.zeus.migue.crosswordshelper.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 79812 on 09/06/2018.
 */

public class MainModel implements MainContract.Model {
    private MainContract.Presenter presenter;
    private List<String> allValidWords;
    private List<Word> wordPermutations;

    public MainModel(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public List<Word> getPermutations(String expression, String dictionary) {
        expression = expression.toLowerCase();
        dictionary = dictionary.toLowerCase();
        allValidWords = Database.getInstance().getValidWords();
        List<String> rawPermutations = permute(dictionary, getExpressionEmptySpaces(expression));
        if (rawPermutations == null) {
            return null;
        }
        List<Word> permutations = new ArrayList<>();
        for (String rawWord : rawPermutations) {
            Word word = new Word(generateFinalWord(expression, rawWord));
            if (allValidWords.contains(word.getWord())) {
                word.setValid(true);
            }
            permutations.add(word);
        }
        return permutations;
    }

    //Helper method to provide all parameters to real method
    @Override
    public List<String> calculateBalancedParentheses(int number) {
        List<String> combinations = new ArrayList<>();
        balancedParentheses(number, number, 0, "", combinations);
        return combinations;
    }

    //This is the real method, inside it there is all the logic between the balanced parentheses
    private void balancedParentheses(int opened, int closed, int pendingCloses, String currentChain, List<String> combinations){
        if (opened == 0 && closed == 0){
            combinations.add(currentChain);
        }
        else{
            if (opened > 0){
                balancedParentheses(opened - 1, closed, pendingCloses + 1, currentChain + "(", combinations);
            }
            if (closed > 0 && pendingCloses > 0){
                balancedParentheses(opened, closed - 1, pendingCloses - 1, currentChain + ")", combinations);
            }
        }
    }

    private int getExpressionEmptySpaces(String expression) {
        int emptySpaces = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '_' || expression.charAt(i) == '.' || expression.charAt(i) == '-' || expression.charAt(i) == '*') {
                emptySpaces++;
            }
        }
        return emptySpaces;
    }

    //example: This converts _E_R_ to PERRO receiving as input {P, R, O}
    private String generateFinalWord(String expression, String rawWord) {
        String word = "";
        int indexRawWord = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '_' || expression.charAt(i) == '.' || expression.charAt(i) == '-' || expression.charAt(i) == '*') {
                word += rawWord.charAt(indexRawWord);
                indexRawWord++;
            } else {
                word += expression.charAt(i);
            }
        }
        return word;
    }

    //Permute is only a helper method to validate the input and create the list, the real logic is inside permutations(...) method
    private List<String> permute(String dictionary) {
        List<String> allWords = new ArrayList<>();
        permutations("", dictionary, dictionary.length(), allWords);
        return allWords;
    }

    private List<String> permute(String dictionary, int blanks) {
        if (blanks > dictionary.length()) {
            return null;
        }
        List<String> allWords = new ArrayList<>();
        permutations("", dictionary, blanks, allWords);
        return allWords;
    }

    private void permutations(String currentWord, String dictionary, int blanks, List<String> allWords) {
        for (int i = 0; i < dictionary.length(); i++) {
            char c = dictionary.charAt(i);
            if (blanks > 1) {
                permutations(currentWord + c, dictionary.replaceFirst(String.valueOf(c), ""), blanks - 1, allWords);
            } else {
                allWords.add(currentWord + c);
            }
        }
    }

}
