package com.ming.part6_17;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Lab17_3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab17_3);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.lab3_recycler);
        List<String> list = new ArrayList<>();
        for(int i=0; i<20; i++){
            list.add("Item=" +i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));


        recyclerView.addItemDecoration(new MyItemDecoration());
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        // 항목 구성 데이터
        private  List<String> list;

        // 생성자
        public MyAdapter(List<String> list){
            this.list = list;
        }

        // 항목을 구성하기 위한 layout xml 파일 inflate
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position){
            String text = list.get(position);
            viewHolder.title.setText(text);
        }
        @Override
        public int getItemCount(){
            return list.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public MyViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        //각 항목을 배치할 때 호출
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state){ // 사각형 정보, 구성하기 위한 뷰
            super.getItemOffsets(outRect, view, parent, state);
            //항목의 index 값 획득
            int index = parent.getChildAdapterPosition(view)+1;

            if(index % 3 == 0) //3개씩 묶어 세로 방향 여백을 60으로 지정
                outRect.set(20, 20, 20, 60);
            else //각 항목을 네 방향으로 20 픽셀로 여백을 둠
                outRect.set(20, 20, 20, 20);

            view.setBackgroundColor(0xFFECE9E9); //각 항목의 바탕색 지정
            ViewCompat.setElevation(view, 20.0f);
        }

        // 항목이 출력되기 전 RecyclerView에 그림그리기
        @Override
        public void onDraw(Canvas c,RecyclerView parent, RecyclerView.State state){
            super.onDraw(c, parent, state);
            //RecyclerView의 사이즈 계산
            int width = parent.getWidth();
            int height = parent.getHeight();
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            c.drawRect(0, 0, width/3, height, paint);
            paint.setColor(Color.BLUE);
            c.drawRect(width/3, 0, width/3*2, height, paint);
            paint.setColor(Color.GREEN);
            c.drawRect(width/3*2, 0, width, height, paint);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state){
            super.onDrawOver(c, parent, state);
            //RecyclerView의 사이즈 계산
            int width = parent.getWidth();
            int height = parent.getHeight();
            //이미지 사이즈 계산
            Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.android, null);
            int drWidth = dr.getIntrinsicWidth();
            int drHeight = dr.getIntrinsicHeight();
            int left = width/2 - drWidth/2;
            int top = height/2 - drHeight/2;
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.android), left, top, null);
       }
    }

}
