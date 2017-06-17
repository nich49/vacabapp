package com.example.vocablarybuilderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by auk on 2017/06/17.
 */

public class RegisterDialogFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity())
        .inflate(R.layout.fragment_register_dialog, null);

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("Record a new word");
    builder.setView(layout);
    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(getActivity(), "No button was pressed", Toast.LENGTH_SHORT).show();
      }
    });
    builder.setPositiveButton("RECORD", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        // リストとリストアダプタを取得
        // TODO unchecked cast を修正する
        @SuppressWarnings("unchecked")
        ArrayList<ListItem> listItems =
            (ArrayList<ListItem>) getArguments().getSerializable("listItems");
        ListAdapter listAdapter = (ListAdapter) getArguments().getSerializable("listAdapter");

        // 入力された単語を取得してlistItemにセット
        String word = ((EditText) layout.findViewById(R.id.wordEditText)).getText().toString();
        String meaning = ((EditText) layout.findViewById(R.id.meaningEditText)).getText().toString();
        String imagePath = ((EditText) layout.findViewById(R.id.imageEditText)).getText().toString();
        Long currentTime = new Date().getTime();

        ListItem listItem = new ListItem();
        listItem.setWord(word);
        listItem.setMeaning(meaning);
        listItem.setImagePath(imagePath);
        listItem.setCreatedDate(currentTime);

        // DBに入力された単語を挿入
        SQLiteDatabase database = new DatabaseHelper(getActivity()).getWritableDatabase();
        try {
          ContentValues content = new ContentValues();
          content.put("word", word);
          content.put("meaning", meaning);
          content.put("image_path", imagePath);
          content.put("created_date", currentTime);
          database.insert("vocabulary_list", null, content);
          Toast.makeText(getActivity(), "Recoded successfully", Toast.LENGTH_SHORT).show();
        } finally {
          database.close();
        }

        // リストを更新
        if (listItems != null && listAdapter != null){
          listItems.add(listItem);
          listAdapter.notifyDataSetChanged();
        }
      }
    });
    return builder.create();
  }
}

/**
 * 登録ボタンの処理(sample)
 */
//  public void btnRecord_onClick(View view) {
//    EditText wordEditText = (EditText) findViewById(R.id.wordEditText);
//    EditText meaningEditText = (EditText) findViewById(R.id.meaningEditText);
//    EditText imagePathEditText = (EditText) findViewById(R.id.imageEditText);
//
//    SQLiteDatabase database = new DatabaseHelper(this).getWritableDatabase();
//    try {
//      ContentValues content = new ContentValues();
//      content.put("word", wordEditText.getText().toString());
//      content.put("meaning", meaningEditText.getText().toString());
//      content.put("image_path", imagePathEditText.getText().toString());
//      database.insert("vocabulary_list", null, content);
//      Toast.makeText(this, "単語を登録しました。",
//          Toast.LENGTH_SHORT).show();
//    } finally {
//      database.close();
//    }
//    finish();
//  }