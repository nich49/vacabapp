package com.example.vocablarybuilderapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
  }

  /**
   * キャンセルボタンを押した時、登録を中断
   * @param view view
   */
  public void btnCancelRecord_onClick(View view) {
    finish();
  }

  /**
   * 登録ボタンを押した時、DBに単語を登録
   * TODO EditTextに不正な入力の時に、警告をする
   * @param view view
   */
  public void btnRecord_onClick(View view) {
    EditText wordEditText = (EditText) findViewById(R.id.wordEditText);
    EditText meaningEditText = (EditText) findViewById(R.id.meaningEditText);
    EditText imagePathEditText = (EditText) findViewById(R.id.imageEditText);

    SQLiteDatabase database = new DatabaseHelper(this).getWritableDatabase();
    try {
      ContentValues content = new ContentValues();
      content.put("word", wordEditText.getText().toString());
      content.put("meaning", meaningEditText.getText().toString());
      content.put("image_path", imagePathEditText.getText().toString());
      database.insert("vocabulary_list", null, content);
      Toast.makeText(this, "単語を登録しました。",
          Toast.LENGTH_SHORT).show();
    } finally {
      database.close();
    }
    finish();
  }
}
