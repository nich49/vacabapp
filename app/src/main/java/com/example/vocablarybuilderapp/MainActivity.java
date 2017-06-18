package com.example.vocablarybuilderapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private DatabaseHelper databaseHelper = null;
  private ArrayList<ListItem> listItems = null;
  private ListAdapter listAdapter = null;
  private int position = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 単語をリスト表示
    databaseHelper = new DatabaseHelper(this);
    SQLiteDatabase database = databaseHelper.getReadableDatabase();
    Cursor cursor = null;
    try {
      // リストに表示するword, meaning, created_dateを取得
      String[] columns = {"word", "meaning", "created_date"};
      cursor = database.query("vocabulary_list", columns, null, null, null, null, null);
      System.out.println(cursor.getCount());
      // DBの内容をリスト表示
      if (cursor.moveToFirst()) {
        listItems = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
          ListItem listItem = new ListItem();
          listItem.setId(i);
          listItem.setWord(cursor.getString(0));
          listItem.setMeaning(cursor.getString(1));
          listItem.setCreatedDate(cursor.getLong(2));
          listItems.add(listItem);
          cursor.moveToNext();
        }
        listAdapter = new ListAdapter(this, listItems, R.layout.list_item);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(listAdapter);

        // リスト項目が長押しされた時、deleteCheckダイアログを表示
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView,
                                         View view, int position, long id) {
            setPosition(position);
            checkDelete();
            return false;
          }
        });
      }
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
    RegisterDialogFragment dialog = new RegisterDialogFragment();
    Bundle args = new Bundle();
    args.putSerializable("listAdapter", listAdapter);
    args.putSerializable("listItems", listItems);

    dialog.setArguments(args);
    dialog.show(getFragmentManager(), "registerDialog_button");
  }

  /**
   * deleteCheckダイアログを表示して確認
   */
  private void checkDelete() {
    ListItem listItem = findListItem(position);
    final String deletedWord = listItem.getWord();

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Delete List Item");
    builder.setMessage("Will you delete the word " + deletedWord + "?");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        deleteItem();
        Toast.makeText(MainActivity.this, deletedWord + " was deleted.", Toast.LENGTH_SHORT)
            .show();
      }
    });
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {}
    });

    AlertDialog dialog = builder.create();
    dialog.show();
  }

  /**
   * リスト項目を削除して、DBに更新
   */
  private void deleteItem() {
    int position = getPosition();
    // データベースから単語を削除
    SQLiteDatabase database = new DatabaseHelper(this).getWritableDatabase();
    String[] params = {listItems.get(position).getWord()};
    try {
      database.delete("vocabulary_list", "word = ?", params);
    } finally {
      database.close();
    }

    // リストから単語を削除して、更新
    listItems.remove(position);
    listAdapter.notifyDataSetChanged();
  }

  /**
   * 選択されたリストの位置をセット
   * @param position リスト項目の位置
   */
  private void setPosition(int position) {
    this.position = position;
  }

  /**
   *  選択されたリスト項目を取得
   * @param position リスト項目の位置
   * @return listItem リスト項目を返す
   */
  private ListItem findListItem(int position) {
    return getListItems().get(position);
  }

  private ArrayList<ListItem> getListItems() {
    return this.listItems;
  }

  /**
   * 選択されたリストの位置を取得
   * @return position リスト項目の位置
   */
  private int getPosition() {
    return this.position;
  }
}