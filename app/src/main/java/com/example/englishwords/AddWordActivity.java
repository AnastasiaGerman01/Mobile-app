package com.example.englishwords;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishwords.data.Contract.Word;

public class AddWordActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    EditText wordEditText;
    EditText translationEditText;
    EditText definitionEditText;
    EditText exampleEditText;
    EditText synonymsEditText;
    EditText questionEditText;
    Uri currentMembersUri;
    private static final int EDIT_WORD_LOADER = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        currentMembersUri = intent.getData();
        if (currentMembersUri == null){
            setTitle("Add the word");
            invalidateOptionsMenu();
        }
        else{
            setTitle("Edit the word");
            getSupportLoaderManager().initLoader(EDIT_WORD_LOADER,null,this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        wordEditText = findViewById(R.id.word);
        translationEditText = findViewById(R.id.translation);
        definitionEditText = findViewById(R.id.definition);
        exampleEditText = findViewById(R.id.example);
        synonymsEditText = findViewById(R.id.synonyms);
        questionEditText = findViewById(R.id.question);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (currentMembersUri == null){
            MenuItem menuItem = menu.findItem(R.id.delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                add_word();
                return true;
            case R.id.delete:
                showDeleteMemberDiologe();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void add_word(){


        String word = wordEditText.getText().toString().trim();
        String translation = translationEditText.getText().toString().trim();
        String definition = definitionEditText.getText().toString().trim();
        String example = exampleEditText.getText().toString().trim();
        String synonyms = synonymsEditText.getText().toString().trim();
        String question = questionEditText.getText().toString().trim();

        if (TextUtils.isEmpty(word)){
            Toast.makeText(this,"Введите слово", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(translation)){
            Toast.makeText(this, "Введите перевод слова", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(definition)){
            Toast.makeText(this, "Введите определение слова", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(example)){
            Toast.makeText(this, "Введите пример предложения", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(synonyms)){
            Toast.makeText(this, "Введите синонимы слова", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(question)){
            Toast.makeText(this, "Введите вопрос ", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Word.COLUMN_WORD,word);
        values.put(Word.COLUMN_TRANSLATION,translation);
        values.put(Word.COLUMN_DEFINITION,definition);
        values.put(Word.COLUMN_EXAMPLE,example);
        values.put(Word.COLUMN_SYNONYMS,synonyms);
        values.put(Word.COLUMN_QUESTION,question);

        if(currentMembersUri ==null){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(Word.CONTENT_URI,values);
        if (uri == null){
            Toast.makeText(this,"Insertion of data in the table failed for " + uri,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Data was saved ",Toast.LENGTH_LONG).show();
        }}
        else{
            int rowsChanged = getContentResolver().update(currentMembersUri,values,null,null);
            if (rowsChanged ==0){
                Toast.makeText(this,"Saving of data in the table failed for ",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Member updated ",Toast.LENGTH_LONG).show();
            }
        }

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String projection [] = {
                Word.COLUMN_ID, Word.COLUMN_WORD, Word.COLUMN_TRANSLATION, Word.COLUMN_DEFINITION,
                Word.COLUMN_EXAMPLE, Word.COLUMN_SYNONYMS, Word.COLUMN_QUESTION
        };
        return new  CursorLoader(this, currentMembersUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()){
            int WordColumnIndex = data.getColumnIndex(Word.COLUMN_WORD);
            int TranslationColumnIndex = data.getColumnIndex(Word.COLUMN_TRANSLATION);
            int DefinitionColumnIndex = data.getColumnIndex(Word.COLUMN_DEFINITION);
            int ExampleColumnIndex = data.getColumnIndex(Word.COLUMN_EXAMPLE);
            int SynonymsColumnIndex = data.getColumnIndex(Word.COLUMN_SYNONYMS);
            int QuestionColumnIndex = data.getColumnIndex(Word.COLUMN_QUESTION);

            wordEditText.setText(data.getString(WordColumnIndex));
            translationEditText.setText(data.getString(TranslationColumnIndex));
            definitionEditText.setText(data.getString(DefinitionColumnIndex));
            exampleEditText.setText(data.getString(ExampleColumnIndex));
            synonymsEditText.setText(data.getString(SynonymsColumnIndex));
            questionEditText.setText(data.getString(QuestionColumnIndex));

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void showDeleteMemberDiologe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want delete the word?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMember();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!= null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void deleteMember(){
        if (currentMembersUri != null){
            int rowsDeleted = getContentResolver().delete(currentMembersUri,null,null);
            if (rowsDeleted == 0){
                Toast.makeText(this,"Deleting of data from the table failed ",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"The member was deleted",Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }


}