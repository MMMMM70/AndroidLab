package com.ming.part4_10;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DriveHolder {
    public ImageView typeImageView;
    public TextView titleView;
    public TextView dateView;
    public ImageView menuImageView;

    // 뷰 획득 시 성능 이슈 발생
    // 한 번 findViewByID() 함수로 뷰를 획득하고 개발자 알고리즘으로 메모리에 유지한 다음,
    // 필요할 때 함수 호출없이 저장된 뷰를 그대로 이용
    public DriveHolder(View root){
        typeImageView = (ImageView)root.findViewById(R.id.custom_item_type_image);
        titleView = (TextView)root.findViewById(R.id.custom_item_title);
        dateView = (TextView)root.findViewById(R.id.custom_item_date);
        menuImageView = (ImageView)root.findViewById(R.id.custom_item_menu);
    }
}

