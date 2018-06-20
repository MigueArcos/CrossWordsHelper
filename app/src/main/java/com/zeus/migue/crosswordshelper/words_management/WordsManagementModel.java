package com.zeus.migue.crosswordshelper.words_management;


import android.util.Log;

import com.zeus.migue.crosswordshelper.Database;
import com.zeus.migue.crosswordshelper.Word;
import com.zeus.migue.crosswordshelper.utilities.FilterableAdapterModel;

/**
 * Created by 79812 on 09/06/2018.
 */

public class WordsManagementModel extends FilterableAdapterModel<Word> implements WordsManagementContract.Model {
    private WordsManagementContract.Presenter presenter;

    public WordsManagementModel(WordsManagementContract.Presenter presenter){
        super(Database.getInstance().getWords());
        this.presenter = presenter;
    }

    @Override
    public void bindHolderData(WordsManagementContract.ItemView itemView, int position) {
        itemView.setTitle(currentData.get(position).getWord());
        itemView.setId(currentData.get(position).getId());
    }


    @Override
    public long createWord(String word) {
        long id = Database.getInstance().addValidWord(word.toLowerCase());
        if (id != -1){
            Word tWord = new Word(id, word, System.currentTimeMillis());
            addItem(tWord);
        }
        return id;
    }

    @Override
    public void deleteWord(int position) {
        Log.d("Id to be deleted: ", ""+currentData.get(position).getId());
        Database.getInstance().removeWord(currentData.get(position).getId());
        deleteItem(position);
    }

    @Override
    public Word getItem(int position) {
        return currentData.get(position);
    }

    @Override
    public void updateWord(String word, int position) {
        //Database.getInstance().updateWord(currentData.get(position).getId(), word.toLowerCase());
        //currentData.get(position).setWord(word.toLowerCase());
        Word tWord = new Word(currentData.get(position).getId(), word, System.currentTimeMillis());
        updateItem(tWord, position);
    }

}
