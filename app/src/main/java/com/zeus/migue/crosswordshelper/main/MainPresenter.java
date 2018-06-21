package com.zeus.migue.crosswordshelper.main;

/**
 * Created by 79812 on 09/06/2018.
 */

public class MainPresenter implements MainContract.Presenter{
    private MainContract.View view;
    private MainContract.Model model;

    public MainPresenter(MainContract.View view){
        this.view = view;
        this.model = new MainModel(this);
    }

    @Override
    public void requestCombinations(String expression, String dictionary) {
        view.onPermutationsReady(model.getPermutations(expression, dictionary));
    }

    @Override
    public void requestBalancedParentheses(int number) {
        view.showBalancedParentheses(model.calculateBalancedParentheses(number));
    }


}
