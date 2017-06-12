package com.example.vocablarybuilderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by auk on 2017/06/12.
 */

public class RegisterDialogFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getActivity())
        .inflate(R.layout.dialog_register, null);
    // ダイアログの生成
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // ダイアログの設定
    return builder.setTitle("単語を登録する")
        .setView(linearLayout)
        .setPositiveButton("登録する",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                // TODO DBに単語を登録
              }
            })
        .setNeutralButton("キャンセル",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {}
            })
        .create();
  }
}
