package com.zeus.migue.crosswordshelper.words_management;

import com.zeus.migue.crosswordshelper.Word;

/**
 * Created by 79812 on 09/06/2018.
 */

public class WordsManagementPresenter implements WordsManagementContract.Presenter{
    private WordsManagementContract.View view;
    private WordsManagementContract.Model model;

    public WordsManagementPresenter(WordsManagementContract.View view){
        this.view = view;
        model = new WordsManagementModel(this);
    }

    @Override
    public void bindHolderData(WordsManagementContract.ItemView itemView, int position) {
        model.bindHolderData(itemView, position);
    }


    @Override
    public void onFABClick(String word) {
        long id  = model.createWord(word);
        if (id == -1){
            view.notifyErrorOnInsert();
        }else{
            view.notifyItemCreated();
        }
    }

    @Override
    public void onItemClick(String word, int position) {
        model.updateWord(word, position);
        view.notifyChanges();
        view.notifyItemUpdated(position);
    }

    @Override
    public void onItemLongClick(int position) {
        model.deleteWord(position);
        view.notifyItemDeleted(position);
    }

    @Override
    public int getItemCount() {
        return model.getItemCount();
    }

    @Override
    public void filterResults(String filter) {
        model.filterResults(filter);
        view.notifyChanges();
    }

    @Override
    public void notifyErrorOnInsert() {
        view.notifyErrorOnInsert();
    }

    @Override
    public Word getItem(int position) {
        return model.getItem(position);
    }
}
