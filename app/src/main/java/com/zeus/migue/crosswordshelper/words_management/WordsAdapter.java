package com.zeus.migue.crosswordshelper.words_management;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeus.migue.crosswordshelper.R;

/**
 * Created by 79812 on 09/06/2018.
 */

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ItemView> {
    private WordsAdapterActions listener;
    private DataObserver dataObserver;
    private WordsManagementContract.Presenter presenter;

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new ItemView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        //Bind data to list
        presenter.bindHolderData(holder, position);
    }

    @Override
    public int getItemCount() {
        if (dataObserver != null){
            dataObserver.onChanged(presenter.getItemCount());
        }
        return presenter.getItemCount();
    }

    public interface DataObserver {
        void onChanged(int listSize);
    }

    public interface WordsAdapterActions{
        void onItemLongClick(View v, int position);
        void onItemClick(View v, int position);
    }

    public WordsAdapter(WordsManagementContract.Presenter presenter){
        this.presenter = presenter;
    }

    public void setListener(WordsAdapterActions listener){
        this.listener = listener;
    }

    public void setDataObserver(DataObserver dataObserver){
        this.dataObserver = dataObserver;
    }

    public class ItemView extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, WordsManagementContract.ItemView{
        private TextView titleText, idText;

        ItemView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            titleText = itemView.findViewById(R.id.title);
            idText = itemView.findViewById(R.id.word_id);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public void setTitle(String title) {
            titleText.setText(String.format("%s%s", title.substring(0, 1).toUpperCase(), title.substring(1)));
        }

        @Override
        public void setId(long id) {
            idText.setText(String.valueOf(id));
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

}
