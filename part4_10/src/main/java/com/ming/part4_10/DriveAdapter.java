package com.ming.part4_10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DriveAdapter extends ArrayAdapter<DriveVO> {
    Context context;
    int resId;
    ArrayList<DriveVO> datas;

    public DriveAdapter(Context context, int resId, ArrayList<DriveVO> datas){
        super(context, resId);
        this.context = context;
        this.resId = resId;
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){ //레이아웃 초기화를 최초에 한번만 수행, null이면 최초 호출
            //항목 layout 초기화
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resId, null);

            //Holder를 Adapter에서 메모리에 유지하는 방법
            DriveHolder holder = new DriveHolder(convertView);
            convertView.setTag(holder); // 임의의 데이터 저장
        }
        DriveHolder holder = (DriveHolder)convertView.getTag(); // 저장된 뷰 객체 다시 획득

        ImageView typeImageView = holder.typeImageView;
        TextView titleView = holder.titleView;
        TextView dateView = holder.dateView;
        ImageView menuImageView = holder.menuImageView;

        final DriveVO vo = datas.get(position);

        titleView.setText(vo.title);
        dateView.setText(vo.date);

        if(vo.type.equals("doc")){
            typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_type_doc, null));
        }
        else if(vo.type.equals("file")){
            typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_type_file, null));
        }
        else if(vo.type.equals("img")){
            typeImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_type_image, null));
        }

        menuImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast toast = Toast.makeText(context, vo.title + "menu click", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return convertView;

    }
}
