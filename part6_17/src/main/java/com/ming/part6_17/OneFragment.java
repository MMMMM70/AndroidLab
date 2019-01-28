package com.ming.part6_17;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OneFragment extends ListFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        String[] datas = {"예나", "우주", "서준", "기준"};
        ArrayAdapter<String> array = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, datas);
        setListAdapter(array);
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id){
        super.onListItemClick(list, view,position, id);
        Toast toast = Toast.makeText(getActivity(),
                (String)list.getAdapter().getItem(position), Toast.LENGTH_SHORT);
        toast.show();
    }
}
