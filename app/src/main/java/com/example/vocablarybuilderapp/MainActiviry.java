package com.example.vocablarybuilderapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActiviry extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // TODO 単語のリストをDBから取得してリストして表示
    // TODO 単語の長押しで、単語を編集するダイアログを表示する
    // test
    // とりあえずのリストを表示
    ArrayList<ListItem> listItems = new ArrayList<>();
    listItems.add(generateListItem(1, "achieve",
        "to successfully complete something or get a good result, especially by working hard"));
    listItems.add(generateListItem(2, "complete",
        "used to emphasize that a quality or situation is as great as it could possibly be"));
    listItems.add(generateListItem(3, "emphasize",
        "to say something in a strong way"));
    ListAdapter listAdapter = new ListAdapter(this, listItems, R.layout.list_item);
    ListView listView = (ListView) findViewById(R.id.list);
    listView.setAdapter(listAdapter);
  }

  // TODO 単語を登録するダイアログを表示する
  public void btnRegister_onClick(View view) {

  }

  /**
   * ListItemを生成する
   * @param id id
   * @param name 単語
   * @param meaning 単語の意味
   * @return ListItem
   */
  private ListItem generateListItem(long id, String name, String meaning) {
    ListItem listItem = new ListItem();
    listItem.setId(id);
    listItem.setName(name);
    listItem.setMeaning(meaning);
    listItem.setCreatedDate(new Date());
    return listItem;
  }
}
