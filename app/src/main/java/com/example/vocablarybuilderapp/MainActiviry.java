package com.example.vocablarybuilderapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActiviry extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // TODO 単語のリストをDBから取得してリストして表示
    // TODO 単語の長押しで、単語を編集するダイアログを表示する
    // test
  }

  // TODO 単語を登録するダイアログを表示する
  public void btnRegister_onClick(View view) {

  }
}
