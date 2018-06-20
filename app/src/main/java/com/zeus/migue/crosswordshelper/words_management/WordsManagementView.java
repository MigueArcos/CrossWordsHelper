package com.zeus.migue.crosswordshelper.words_management;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zeus.migue.crosswordshelper.R;

public class WordsManagementView extends AppCompatActivity implements WordsManagementContract.View, WordsAdapter.WordsAdapterActions{
    private RecyclerView list;
    private TextView emptyListLabel;
    private FloatingActionButton create;
    private WordsAdapter wordsAdapter;
    private WordsManagementContract.Presenter presenter;
    private AlertDialog.Builder message;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_management);
        //Orientation changes mut be with a method to subscribe view to presenter
        presenter = new WordsManagementPresenter(this);

        list = findViewById(R.id.list);
        emptyListLabel = findViewById(R.id.empty_list_label);
        create = findViewById(R.id.create);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        list.setLayoutManager(llm);
        list.setHasFixedSize(true);


        wordsAdapter = new WordsAdapter(presenter);

        wordsAdapter.setDataObserver(new WordsAdapter.DataObserver() {
            @Override
            public void onChanged(int listSize) {
                emptyListLabel.setVisibility(listSize == 0 ? View.VISIBLE : View.GONE);
            }
        });
        wordsAdapter.setListener(this);

        list.setAdapter(wordsAdapter);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = new AlertDialog.Builder(WordsManagementView.this);
                message.setTitle(R.string.default_alert_dialog_title);
                final EditText wordEdit = new EditText(WordsManagementView.this);
                wordEdit.setHint(R.string.words_management_word_hint);
                message.setView(wordEdit);
                message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onFABClick(wordEdit.getText().toString());
                    }
                });
                message.setNegativeButton("Cancel", null);
                message.show();
            }
        });
    }

    @Override
    public void notifyItemCreated() {
        wordsAdapter.notifyItemInserted(wordsAdapter.getItemCount());
        Toast.makeText(this, R.string.word_success_on_insert , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyChanges() {
        wordsAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemUpdated(int position) {
        //wordsAdapter.notifyItemChanged(position);
        Toast.makeText(this, R.string.info_about_update, Toast.LENGTH_LONG).show();
    }


    @Override
    public void notifyItemDeleted(int position) {
        wordsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyErrorOnInsert() {
        Toast.makeText(this, R.string.error_repeated_word, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemLongClick(View v, final int position) {
        message = new AlertDialog.Builder(this);
        message.setTitle(R.string.default_alert_dialog_title);
        message.setMessage(R.string.words_management_deletion_confirmed);
        message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onItemLongClick(position);
            }
        });
        message.show();
    }

    @Override
    public void onItemClick(View v, final int position) {
        message = new AlertDialog.Builder(WordsManagementView.this);
        message.setTitle(R.string.default_alert_dialog_title);
        final EditText wordEdit = new EditText(WordsManagementView.this);
        wordEdit.setHint(R.string.words_management_word_hint);
        message.setView(wordEdit);
        wordEdit.setText(presenter.getItem(position).getWord());
        message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onItemClick(wordEdit.getText().toString(), position);
            }
        });
        message.setNegativeButton("Cancel", null);
        message.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_words_management, menu);
        MenuItem searchable = menu.findItem(R.id.search_view);
        searchView = (SearchView) MenuItemCompat.getActionView(searchable);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    create.setVisibility(View.VISIBLE);
                }else{
                    create.setVisibility(View.GONE);
                }
                presenter.filterResults(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
