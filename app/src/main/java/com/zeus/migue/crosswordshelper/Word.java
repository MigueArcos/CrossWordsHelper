package com.zeus.migue.crosswordshelper;

import com.zeus.migue.crosswordshelper.utilities.MyFilterable;
import com.zeus.migue.crosswordshelper.utilities.Updatable;

/**
 * Created by 79812 on 09/06/2018.
 */

public class Word implements MyFilterable<Word>{
    private String word;
    private boolean isValid;
    private long creationDate;
    private long id;

    public Word(String word) {
        this.word = word;
    }


    public Word(long id, String word, long creationDate) {
        this.word = word;
        this.creationDate = creationDate;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean passFilter(String filter) {
        filter = filter.toLowerCase();
        if (String.valueOf(id).concat(word).contains(filter)){
            return true;
        }
        return false;
    }

    @Override
    public void updateContents(Word word){
        this.word = word.getWord();
    }
}
