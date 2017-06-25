package com.lily.animation;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, new String[] {
                        "自定义动画View", "引导页动画", "PropertyValuesHolder"}));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent intent = null;
        if (position == 0) {
            intent = new Intent(this, CustomAnimViewActivity.class);
        } else if (position == 1) {
            intent = new Intent(this, BootPageAnimActivity.class);
        } else {
            intent = new Intent(this, TestAnimActivity3.class);
        }
        startActivity(intent);
    }
}
