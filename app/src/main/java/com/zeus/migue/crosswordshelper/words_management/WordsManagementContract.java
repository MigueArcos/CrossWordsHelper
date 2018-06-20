package com.zeus.migue.crosswordshelper.words_management;

import com.zeus.migue.crosswordshelper.Word;
import com.zeus.migue.crosswordshelper.utilities.FilterableAdapterModelContract;

/**
 * Created by 79812 on 09/06/2018.
 */

public class WordsManagementContract {

    interface View{
        void notifyItemCreated();

        void notifyChanges();

        void notifyItemUpdated(int position);

        void notifyItemDeleted(int position);

        void notifyErrorOnInsert();

    }

    interface Presenter {
        void bindHolderData(ItemView itemView, int position);
        void onFABClick(String word);
        void onItemClick(String word, int position);
        void onItemLongClick(int position);
        int getItemCount();
        void filterResults(String filter);
        void notifyErrorOnInsert();
        Word getItem(int position);
    }

    interface Model extends FilterableAdapterModelContract<Word> {
        void bindHolderData(ItemView itemView, int position);
        long createWord(String word);
        void deleteWord(int position);
        Word getItem(int position);
        void updateWord(String word, int position);
    }

    interface ItemView{
        void setTitle(String title);
        void setId(long id);
    }
}
