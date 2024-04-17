package com.example.englishwords;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.englishwords.data.Contract;
import com.example.englishwords.data.WordsCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.englishwords.data.Contract.Word;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {

    FloatingActionButton button;
    ListView listView;
    WordsCursorAdapter adapter;
    private static final int WORD_LOADER = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.floatingActionButton);
        listView = findViewById(R.id.words_table);
        String [] projection  = {
          Word._ID, Word.COLUMN_WORD,Word.COLUMN_TRANSLATION,Word.COLUMN_DEFINITION,Word.COLUMN_EXAMPLE,Word.COLUMN_SYNONYMS,Word.COLUMN_QUESTION
       };

        adapter = new WordsCursorAdapter(this,null,false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( MainActivity.this,AddWordActivity.class);
                Uri currentMemberUri = ContentUris.withAppendedId(Word.CONTENT_URI,id);
                intent.setData(currentMemberUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(WORD_LOADER,null,this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.play:
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                Intent intent1 = new Intent(MainActivity.this,ActivityHelp.class);
                startActivity(intent1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String [] projection  = {
                Word._ID, Word.COLUMN_WORD, Word.COLUMN_TRANSLATION,Word.COLUMN_DEFINITION,Word.COLUMN_EXAMPLE,Word.COLUMN_SYNONYMS,Word.COLUMN_QUESTION
        };
        CursorLoader cursorLoader = new CursorLoader(this,Word.CONTENT_URI,projection,null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
    

}