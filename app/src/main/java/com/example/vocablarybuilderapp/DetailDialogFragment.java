package com.example.vocablarybuilderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by auk on 2017/06/17.
 */

public class DetailDialogFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity())
        .inflate(R.layout.fragment_register_dialog, null);

    // TODO 単語の詳細を表示
    AlertDialog.Builder builder = new AlertDialog.Builder();
    builder.setTitle("Detail word");

    return super.onCreateDialog(savedInstanceState);
  }
}
