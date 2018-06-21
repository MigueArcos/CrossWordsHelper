package com.zeus.migue.crosswordshelper.main;

import java.util.List;

import com.zeus.migue.crosswordshelper.Word;

/**
 * Created by 79812 on 09/06/2018.
 */

public class MainContract {
    interface View{
        void onPermutationsReady(List<Word> words);
        void showBalancedParentheses(List<String> combinations);
    }
    interface Presenter{
        void requestCombinations(String expression, String dictionary);
        void requestBalancedParentheses(int number);
    }
    interface Model{
        List<Word> getPermutations(String expression, String dictionary);
        List<String> calculateBalancedParentheses(int number);
    }
}
