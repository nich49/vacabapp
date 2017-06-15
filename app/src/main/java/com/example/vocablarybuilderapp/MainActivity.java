package com.example.vocablarybuilderapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private DatabaseHelper databaseHelper = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // 単語をリスト表示
    listUpItems();
  }

  @Override
  protected void onResume() {
    super.onResume();
    // 単語をリスト表示
    listUpItems();
    Log.d("TAG", "onResume is called");
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    Log.d("TAG", "onPostResume is called");
  }

  /**
   * Registerダイアログを表示する
   * @param view ビュー
   */
  public void btnRegister_onClick(View view) {
    Intent intent = new Intent(this, com.example.vocablarybuilderapp.RegisterActivity.class);
    startActivity(intent);
  }

  public void listUpItems() {
    databaseHelper = new DatabaseHelper(this);
    SQLiteDatabase database = databaseHelper.getReadableDatabase();
    Cursor cursor = null;
    try {
      // word, meaning, created_dateを選択
      String[] columns = {"word", "meaning", "created_date"};
      cursor = database.query("vocabulary_list", columns, null, null, null, null, null);
      System.out.println(cursor.getCount());
      // DBの内容をリスト表示
      if (cursor.moveToFirst()) {
        ArrayList<ListItem> listItems = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
          ListItem listItem = new ListItem();
          listItem.setId(i);
          listItem.setWord(cursor.getString(0));
          listItem.setMeaning(cursor.getString(1));
          listItem.setCreatedDate(cursor.getLong(2));
          listItems.add(listItem);
          cursor.moveToNext();
        }
        // TODO リスト表示された単語の押下で、単語の詳細を表示
        // TODO リスト表示された単語の長押しで、単語を編集するダイアログを表示する
        ListAdapter listAdapter = new ListAdapter(this, listItems, R.layout.list_item);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      exception.getMessage();
    } finally {
      if (cursor != null) {
        cursor.close();
      }
      database.close();
    }
  }
}
