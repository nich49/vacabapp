package com.example.vocablarybuilderapp;

import android.app.DialogFragment;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private DatabaseHelper databaseHelper = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // TODO リスト表示された単語の長押しで、単語を編集するダイアログを表示する

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
        }
        ListAdapter listAdapter = new ListAdapter(this, listItems, R.layout.list_item);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    } finally {
      if (cursor != null) {
        cursor.close();
      }
      database.close();
    }
  }

  /**
   * Registerダイアログを表示する
   * @param view ビュー
   */
  public void btnRegister_onClick(View view) {
    DialogFragment dialogFragment = new RegisterDialogFragment();
    dialogFragment.show(getFragmentManager(), "dialog_register");
  }
}
