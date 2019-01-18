package com.ming.part4_11;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView spanView = (TextView)findViewById(R.id.spanView);
        spanView.setMovementMethod(new ScrollingMovementMethod());

        String data ="복수초 \n img \n 이른봄 설산에서 만나는 복수초는 " +
                    "모든 야생화 찍사들의 로망은 무슨.. 뭔소리야 ㅎ";

        //Spannable을 포함하는 문자열
        SpannableStringBuilder builder = new SpannableStringBuilder(data);
        //img 문자열의 시작위치
        int start = data.indexOf("img");
        if(start >-1){
            //img 문자열의 끝 위치
            int end = start+"img".length();
            //이미지 획득
            Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.img1, null);
            //이미지 화면 출력정보 설정
            dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            //ImageSpan 준비
            ImageSpan span = new ImageSpan(dr);
            //SpannableStringBuilder에 ImageSpan 적용
            builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        start=data.indexOf("복수초");
        if(start >-1){
            int end = start+"복수초".length();
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(2.0f);
            builder.setSpan(styleSpan, start, end+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //왼쪽 제거, 오른쪽 제거
            builder.setSpan(sizeSpan, start, end+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//왼쪽 제거, 오른쪽 제거
        }
        spanView.setText(builder);

        TextView htmlView = (TextView)findViewById(R.id.htmlView);
        String html = "<font color='RED'> 얼레지</font> <br/>" +
                "       <img src = 'img1'/> <br/> 봄꽃이네엥";
        htmlView.setText(Html.fromHtml(html, new MyImageGetter(), null));
    }
    class MyImageGetter implements Html.ImageGetter {
        //이미지 획득을 위해 자동 호출
        @Override
        public Drawable getDrawable(String source) {
            if (source.equals("img1")) {
                //이미지 획득
                Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.img2, null);
                //이미지의 화면 출력정보 설정
                dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
                //리턴시킨 Drawable 객체가 <img> 위치에 출력
                return dr;
            }
            // 이미지 없다는 의미
            return null;
        }
    }
}
