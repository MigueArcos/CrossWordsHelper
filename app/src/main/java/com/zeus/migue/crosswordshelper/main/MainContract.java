package com.zeus.migue.crosswordshelper.main;

import java.util.List;

import com.zeus.migue.crosswordshelper.Word;

/**
 * Created by 79812 on 09/06/2018.
 */

public class MainContract {
    interface View{
        void onPermutationsReady(List<Word> words);
    }
    interface Presenter{
        void requestCombinations(String expression, String dictionary);
    }
    interface Model{
        List<Word> getPermutations(String expression, String dictionary);
    }
}
