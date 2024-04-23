package com.example.englishwords.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "english";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.android.uraall.learingenglish";
    public static final String PATH_WORDS = "Words";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse(SCHEME + AUTHORITY);

    public static final class Word implements BaseColumns {
        public static final String TABLE_NAME = "Words";
        public static String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_TRANSLATION = "translation";
        public static final String COLUMN_DEFINITION = "definition";
        public static final String COLUMN_EXAMPLE = "example";
        public static final String COLUMN_SYNONYMS = "synonyms";
        public static final String COLUMN_QUESTION = "question";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_WORDS);

        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_WORDS;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_WORDS;



    }
}
