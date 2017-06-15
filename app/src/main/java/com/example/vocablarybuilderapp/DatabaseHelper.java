package com.example.vocablarybuilderapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by auk on 2017/06/13.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
  // DBの名前
  static final private String DBNAME = "vocabulary.sqlite";
  static final private int VERSION = 1;

  // コンストラクタ
  public DatabaseHelper(Context context) {
    super(context, DBNAME, null, VERSION);
  }

  @Override
  public void onOpen(SQLiteDatabase sqLiteDatabase) {
    super.onOpen(sqLiteDatabase);
  }

  /**
   * データベース作成時にテーブルを作成、テストデータを挿入
   * @param sqLiteDatabase データベース
   */
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    // テーブルを作成
    sqLiteDatabase.execSQL("CREATE TABLE vocabulary_list "
                          + "(id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, meaning TEXT, "
                          + "image_path TEXT, created_date INTEGER)");
    // テストデータを用意
    ArrayList<ListItem> listItems = new ArrayList<>();
    listItems.add(generateListItem(1, "achieve",
        "to successfully complete something or get a good result, especially by working hard", "1path"));
    listItems.add(generateListItem(2, "complete",
        "used to emphasize that a quality or situation is as great as it could possibly be", "2path"));
    listItems.add(generateListItem(3, "emphasize",
         "to say something in a strong way", "3path"));

    sqLiteDatabase.beginTransaction();
    try {
      SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(
          "INSERT INTO vocabulary_list(id, word, meaning, image_path, created_date) VALUES (?, ?, ?, ?, ?)");
      for (ListItem listItem: listItems) {
        sqLiteStatement.bindLong(1, listItem.getId());
        sqLiteStatement.bindString(2, listItem.getWord());
        sqLiteStatement.bindString(3, listItem.getMeaning());
        sqLiteStatement.bindString(4, listItem.getImagePath());
        sqLiteStatement.bindLong(5, listItem.getCreatedDate());
        sqLiteStatement.executeInsert();
      }
      sqLiteDatabase.setTransactionSuccessful();
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    } finally {
      sqLiteDatabase.endTransaction();
    }
  }

  /**
   * データベースをバージョンアップした時、テーブルを再作成
   * @param sqLiteDatabase データベース
   * @param oldVersion 前のバージョン
   * @param newVersion 新しいバージョン
   */
  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS vocabulary_list");
    onCreate(sqLiteDatabase);
  }

  /**
   * ListItemを生成する
   * @param id id
   * @param word 単語
   * @param meaning 単語の意味
   * @param path 画像のファイルパス
   * @return listItem
   */
  private ListItem generateListItem(long id, String word, String meaning, String path) {
    ListItem listItem = new ListItem();
    listItem.setId(id);
    listItem.setWord(word);
    listItem.setMeaning(meaning);
    listItem.setImagePath(path);
    listItem.setCreatedDate(new Date().getTime());
    return listItem;
  }
}
