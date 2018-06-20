package com.zeus.migue.crosswordshelper.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import com.zeus.migue.crosswordshelper.Database;
import com.zeus.migue.crosswordshelper.R;
import com.zeus.migue.crosswordshelper.Word;
import com.zeus.migue.crosswordshelper.words_management.WordsManagementView;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private Database database;
    private EditText expressionEdit, dictionaryEdit;
    private Button calculate, getAll;
    private MainContract.Presenter presenter;
    private AlertDialog.Builder message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Database.getInstance(this);
        presenter = new MainPresenter(this);

        expressionEdit = findViewById(R.id.expression);
        dictionaryEdit = findViewById(R.id.dictionary);
        calculate = findViewById(R.id.calculate);
        getAll = findViewById(R.id.get_all);
        message = new AlertDialog.Builder(this);
        message.setTitle(R.string.default_alert_dialog_title);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestCombinations(
                        expressionEdit.getText().toString(),
                        dictionaryEdit.getText().toString()
                );
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WordsManagementView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPermutationsReady(List<Word> words) {


        if (words == null){
            Toast.makeText(this, R.string.main_error_more_blanks_than_dictionary_size, Toast.LENGTH_LONG).show();
            return;
        }
        //Creating helper arrays
        final String allWords[] = new String[words.size()];
        final boolean validWords[] = new boolean[words.size()];
        int c = 0;

        for (Word word : words){
            allWords[c] = word.getWord();
            validWords[c] = word.isValid();
            c++;
        }
        message.setMultiChoiceItems(allWords, validWords, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    long id = database.addValidWord(allWords[which]);
                    /*if (id == -1) {
                        Toast.makeText(MainActivity.this, R.string.error_repeated_word, Toast.LENGTH_LONG).show();
                    }*/
                    //Toast.makeText(MainActivity.this, "Guardado " + id, Toast.LENGTH_LONG).show();
                }
                else{
                    database.removeWord(allWords[which]);
                    //Toast.makeText(MainActivity.this, "Removido alv", Toast.LENGTH_LONG).show();
                }
            }
        });

// add OK and Cancel buttons
        message.setPositiveButton("OK", null);
        message.setNegativeButton("Cancel", null);

        message.show();
    }
}
