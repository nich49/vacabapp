package com.example.vocablarybuilderapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by auk on 2017/06/11.
 */

public class ListAdapter extends BaseAdapter implements Serializable {
  private Context context = null;
  private ArrayList<ListItem> listItems = null;
  private int resource = 0;

  // コンストラクタ
  public ListAdapter(Context context, ArrayList<ListItem> listitems, int resource) {
    this.context = context;
    this.listItems = listitems;
    this.resource = resource;
  }

  // データの個数を取得
  @Override
  public int getCount() {
    return listItems.size();
  }

  // 指定された項目を取得
  @Override
  public Object getItem(int position) {
    return listItems.get(position);
  }

  @Override
  public long getItemId(int position) {
    return listItems.get(position).getId();
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Activity activity = (Activity) context;
    ListItem item = (ListItem) getItem(position);
    if (view == null) {
      view = activity.getLayoutInflater()
          .inflate(this.resource, null);
    }
    ((TextView) view.findViewById(R.id.word)).setText(item.getWord());
    ((TextView) view.findViewById(R.id.meaning)).setText(item.getMeaning());
    ((TextView) view.findViewById(R.id.createdDate)).setText(item.getCreatedDate().toString());
    return view;
  }
}
