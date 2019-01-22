package com.ming.part5_14;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Lab14_2Activity extends AppCompatActivity implements View.OnClickListener {

    Button contactsBtn;
    Button cameraDataBtn;
    Button cameraFileBtn;
    File filePath;
    Button speechBtn;
    Button mapBtm;
    Button browserBtn;
    Button callBtn;
    ImageView resultImageView;
    TextView resultView;
    int reqHeight;
    int reqWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab14_2);
        contactsBtn = (Button)findViewById(R.id.btn_contacts);
        cameraDataBtn = (Button)findViewById(R.id.btn_camera_data);
        cameraFileBtn = (Button)findViewById(R.id.btn_camera_file);
        speechBtn = (Button)findViewById(R.id.btn_speech);
        mapBtm = (Button)findViewById(R.id.btn_map);
        browserBtn = (Button)findViewById(R.id.btn_browser);
        callBtn = (Button)findViewById(R.id.btn_call);
        resultImageView = (ImageView)findViewById(R.id.resultImageView);
        resultView = (TextView)findViewById(R.id.resultView);

        contactsBtn.setOnClickListener(this);
        cameraDataBtn.setOnClickListener(this);
        cameraFileBtn.setOnClickListener(this);
        speechBtn.setOnClickListener(this);
        mapBtm.setOnClickListener(this);
        browserBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        // 1) 주소록 목록 띄우기
        if(v==contactsBtn){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI); // 사람 이름과 전화번호
            startActivityForResult(intent, 10);
        }
        // 2) 카메라 앱 intent - data 획득
        else if(v==cameraDataBtn){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        }
        // 3) 카메라 앱 intent - file 공유
        else if(v==cameraFileBtn){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                try{
                    //외부 메모리 공간에 myApp 폴더를 만듬
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp";
                    File dir = new File(dirPath);
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                    // 파일 만듬
                    filePath = File.createTempFile("IMG",".jpg", dir);
                    if(!filePath.exists()){
                        filePath.createNewFile();
                    }
                    //FileProvider 클래스를 통해 URI에 대하 임시 엑세스 권한을 부여
                    //FileProvider 클래스는 Support 라이브러리에서 제공하는 콘텐츠 프로바이더
                    // XML로 제공하는 설정을 기반으로 파일들에 대한 Content URI를 생성해줌
                    Uri photoURI = FileProvider.getUriForFile(Lab14_2Activity.this,
                            BuildConfig.APPLICATION_ID + ".provider", filePath);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI); //URI 값 인텐트 Extra 데이터로 설정
                    startActivityForResult(intent, 40);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
        // 4) 음성인식
        else if(v==speechBtn){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식 테스트");
            startActivityForResult(intent, 50);
        }
        // 5) 지도 연동
        else if(v==mapBtm){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662962,126,9779451"));
            startActivity(intent);
        }
        // 6) 브라우저
        else if(v==browserBtn){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"));
            startActivity(intent);
        }
        // 7) 전화
        else if(v==callBtn){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"));
                startActivity(intent);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        }
        // 8) 사진 보여주기
        else if(v==resultImageView){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            //파일 정보를 갤러리 앱에 전달하기 위해 FileProvider 이용
            Uri photoURI = FileProvider.getUriForFile(Lab14_2Activity.this, BuildConfig.APPLICATION_ID + ".provider", filePath);
            intent.setDataAndType(photoURI, "image/*");
            //외부 앱에서 우리 데이터를 이용하는 것이므로 퍼미션 정보 추가
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==10 && resultCode==RESULT_OK){
            String result = data.getDataString(); // 넘어온 URI 값 얻음
            resultView.setText(result);
        }
        else if(requestCode==30 && resultCode==RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data"); // 촬영한 결과를 얻음
            resultImageView.setImageBitmap(bitmap);
        }
        else if(requestCode==40 && resultCode==RESULT_OK){
            if(filePath != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try {
                    InputStream in = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(in, null, options); // InputStream으로 Bitmap 생성
                    in.close();
                    in = null;
                } catch(Exception e){
                    e.printStackTrace();
                }
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;
                // 이미지 크기와 개발자가 원하는 이미지 크기 비교해서 적절한 inSampleSize 계산
                if(height > reqHeight || width > reqWidth){
                    final int heightRatio = Math.round((float)height / (float)reqHeight);
                    final int widthRatio = Math.round((float)width / (float)reqWidth);

                    inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
                }
                // Options 클래스의 inSampleSize 속성값을 설정
                // decodeXXX() 함수의 두 번째 매개변수에 option을 주어 이미지 크기를 자동으로 줄임
                BitmapFactory.Options imgOptions = new BitmapFactory.Options();
                imgOptions.inSampleSize = inSampleSize;
                Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);
                resultImageView.setImageBitmap(bitmap);
            }
        }
        else if(requestCode==50 && resultCode==RESULT_OK){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String result = results.get(0);
            resultView.setText(result);
        }
    }
}
