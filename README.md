## 6. 사용자 이벤트 처리
### 6.1 델리케이션 이벤트 모델
뷰에서 발생하는 이벤트를 처리하기 위한 모델
#### 6.1.1 이벤트 프로그램 구조
이벤트 소스와 이벤트 핸들러를 리스너(Listener)로 연결하여 처리하는 구조
* 이벤트 소스(Event Source) : 이벤트가 발생한 뷰 객체
* 이벤트 핸들러(Event Handler) : 이벤트 처리 내용을 가지는 객체
* 리스너(Listener) : 이벤트 소스와 이벤트 핸들러를 연결하는 작업 <br>

모두 액티비티의 터치 이벤트로 처리하지 않고, 이벤트가 발생하는 뷰를 직접 지칭하여 각 이벤트의 성격별로 이벤트 이름을 다르게 처리하면 훨씬 명료해짐. 
이벤트가 발생한 객체를 명료하게 지칭하고자 이벤트 소스를 사용하고, 이벤트 성격을 명료하게 지칭하고자 리스너를 사용함  

### 6.2 하이어라키 이벤트 모델
액티비티에서 발생하는 사용자의 터치나 키 이벤트를 직접 처리하기 위한 모델<br>
델리게이션 이벤트 모델처럼 이벤트 소스와 이벤트 헨들러를 리스너로 연결하여 처리하는 구조가 아님 <br>
이벤트 발생 시 자동 호출되는 함수만 액티비티 내에 재정의하면 됨
#### 6.2.1 터치 이벤트
#### 6.2.2 키 이벤트

## 7.리소스 활용 및 스마트폰 크기 호환성
### 7.1 안드로이드 리소스
#### 7.1.1 리소스 종류
>res 폴더 하위에 있어야 하며, 개발자가 임의로 폴더를 정의하는 것이 아니라 리소스별 폴더명이 지정되어 있음. <br>
>서브 폴더도 작성 불가. 각 폴더에 리소스를 추가하면 추가한 리소스를 식별하기 위한 int 형 변수가 R.java 파일에 추가됨 
* drawable : 이미지, 이미지와 관련된 XML, 그림을 표현한 XML
* layout : 화면 UI를 정의한 레이아웃 XML
* values : 문자열, 색상, 크기 등 여러 가지 값
* menu : 액티비티의 메뉴를 구성하기 위한 XML
* xml : 특정 폴더가 지정되어 있지 않은 기타 XML
* anim : 애니메이션을 위한 XML
* raw : 바이트 단위로 직접 이용되는 이진 파일
* mipmap : 앱 아이콘 이미지
#### 7.2.1 리소스 폴더명 조건 명시법
#### 7.2.2 DisplayMetrics
>개발자 코드에서 직접 스마트폰의 크기 정보를 획득해야 하는 경우 DisplayMetrics 클래스 사용
#### 7.2.3 논리적 단위로 스마트폰 크기 호환성 확보
>안드로이드는 dp 권장. 문자열 폰트의 크기는 sp 권장 <br>
>px는 물리적인 크기를 지칭하지만, dp와 sp는 논리적인 크기를 계산하여 적용하기 때문임
* dp(dip) : Desity-Independent Pixels. 스크린의 물리적 밀도에 기초한 단위. 160dpi(dots per inch)에 상대적 수치
* sp(sip) : Scale-Independent Pixels. dp와 유사하면 폰트 크기에 적용
* pt : Points. 화면 크기의 1/72 1pt
* px : 픽셀
* mm : 밀리미터
* in : 인치

## 8. DBMS을 이용한 데이터 영속화
### 8.1 SQLite을 이용한 영속화
오픈소스로 만들어진 관계형 데이터베이스로 애플리케이션 데이터를 저장하고 관리함 <br>
프로세스가 아닌 라이브러리를 이용하므로 데이터베이스는 애플리케이션의 일부로 통합됨 <br>
서버가 아니라 애플리케이션에 넣어 사용하는 비교적 가벼운 데이터베이스 <br>
#### 8.1.1 SQLiteDatabase 클래스
>SQLiteDatabase 객체의 함수를 이용해 SQL문을 수행
``` java
SQLiteDatabase db = openOrCreateDatabase("memodb", MODE_PRIVATE, null); // 객체 얻음, 첫번째 매개변수는 데이터베이스 파일명
```

* execSQL(String sql) : insert, update 등 select 문이 아닌 나머지 SQL 수행
```java
db.execSQL("insert into tb_memo (title, content) values (?.?)", String[]{title, content});
```
* rawQuery(String sql, String[] selectionArgs, Object[] bindArgs) : select SQL 수행
```java
Cursor cursor = db.rawQuery("select title, content from tb_memo order by _id desc limit 1", null);
```

```java
while(cursor.moveToNext()){
  titleView.setText(cursor.getString(0));
  contentView.setText(cursor.getString(1));
 }
 ```
> moveToNext() 함수를 이용하여 행을 선택하고, 선택된 행의 getString() 함수를 이용하여 열 데이터를 가져옴
#### 8.1.2 SQLiteOpenHelper 클래스
데이터베이스 관리만을 목적으로 하는 클래스. 
데이터 저장이나 획득 등의 코드는 SQLiteDatabase 클래스를 이용하여 insert, select 작업을 하고, 테이블 생성이나 스키마 변경 등의 작업은 SQLiteOpenHelper 클래스로 일원화하는 구조 <br>
select, insert 작업을 수행할 때 테이블 생성 여부를 판단하지 않아도 되는 이점이 있음 <br>
SQLiteOpenHelper 클래스는 추상 클래스이므로 서브 클래스를 만들어 사용해야 함
```java
public class DBHelper extends SQLiteOpenHelper {
    
    public static final int DATABASE_VERSION = 1;
    
    public DBHelper(Context context){
        super(context, "memodb", null, DATABASE_VERSION);
    }
    // SQLiteOpenHelper 작성하려면 onCreate()와 onUpgrade() 함수 재정의 필요
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 앱이 설치된 후 SQLiteOpenHelper가 최초로 이용되는 순간 한 번 호출
        // 대부분 테이블 생성하는 코드 작성
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // 데이터베이스 버전이 변경될 때마다 호출
        // 생성자에 전달되는 데이터베이스 버전 정보가 변경될 떄마다 반복해서 호출
        // 테이블의 스키마 부분을 변경하기 위한 용도로 사용
    }
}
```
```java
DBHelper helper = new DBHelper(this); // 객체 
SQLiteDatabase db = helper.getWritableDatabase();
```
#### 8.1.3 inert(), query(), update(), delete() 함수 이용
>  rawQuery(), execSQL() 함수는 개발자가 직접 SQL문을 매개변수로 주어야 하지만, 해당 함수들은 SQL문을 만들기 위한 정보만 매개변수로 주면 자동으로 SQL문을 만들어 실행해줌
* insert(String table, String nullColumnHack, ContentValues values)
* update(String table, ContentValues values, String whereClause, String[] whereArgs)
* delete(String table, String whereCause, String[] whereArgs)
* query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)

> ContentValues 클래스는 insert, update 데이터를 표현하는 집합 객체임. Key-value 형식으로 이용되며 Key 값이 실제 테이블의 열(column)임
```java
ContentValues values = new ContentValues();
values.put("name", "mina");
values.put("phone", "29947");
db.insert("USER_TB", null, values);
```
> query() 함수의 예. USER_TB 테이블에서 ID가 mina인 사용자를 선택하여 name과 phonew 값을 불러옴
```java
Cursor c = db.query("USER_TB", new String[]{"name", "phone"}, "ID=?", new String[]{"mina"}, null, null, null);
```

### 8.2 Realm을 이용한 데이터 영속화
#### 8.2.1 Realm 소개
SQLite와 마찬가지로 안드로이드 앱에서 데이터 영속화 목적으로 사용하는 로컬 데이터베이스 <br>
안드로이드 표준에서 제공하는 것은 아니며, https://realm.io 에서 오픈소스로 만들어지고 있는 데이터베이스 <br>
앱에서 이용하려면 플러그인 방식으로 앱에 추가해야 사용할 수 있음
> SQLite와 가장 큰 차이는 ORM(Object-Relational Mappings)을 제공한다는 것.(Hibernate나 iBatis 같은 것) <br>
> SQLite는 데이터의 저장과 획득 등의 작업이 SQL문으로 이루어지지만, Realm은 자바 객체를 해석해 그 객체의 데이터를 그대로 저장, 획득해 줌.
> Realm은 안드로이드만을 목적으로 하지 않고, 다양한 곳에서 모바일 데이터베이스를 목적으로 함. 또한 속도면에서 훨씬 더 빠름
#### 8.2.2 Realm 사용 설정
플러그인 방식으로 이용해야해서 그레이들 파일에 설정해야 함. 프로젝트 수준의 그레이들 파일에 의존성을 설정해야 함
> 프로젝트명으로 된 그레이들 파일이 프로젝트 수준임. 해당 파일에 의존성 설정을 해야 함. 설정하면 realm 플러그인이 설치됨
``` java
  dependencies {
      classpath 'com.android.tools.build:gradle:3.0.0-alpha8'
      classpath 'io.realm:realm-gradle-plugin:2.2.0'
  }
```
> 설치된 플러그인을 모듈에 적용함. 적용하려는 모듈의 gradle 파일에 아래처럼 설정함
```java
 apply plugin : 'realm-android'
```
#### 8.2.3 Realm 사용
> Relam이 기본 ORM을 목적으로 하므로 Realm이 관리할 VO(Value-Object) 클래스를 만들어줌 
```java
public class MemoVO extends RealmObject {
  public String title;
  public String content;
}
```
> 데이터 저장, 획득을 위한 Realm 객체 획득
```java
Realm.init(this);
Realm mRealm = Realm.getDefaultInstance();
```
 
> Realm 객체를 이용해 VO 객체의 데이터를 저장
``` java
Realm.init(this);
Realm mRealm = Realm.getDefaultInstance(); // Realm 객체 획득
mRealm.executeTransaction(new Realm.Transaction(){
    @Override
    public void execute(Realm realm){ // vo에 데이터 저장
        MemoVO vo = realm.createObject(MemoVO.class); //Relam과 연결된 VO 객체 획득
        vo.title = title;
        vo.content = content;
    }
});
```

> Realm 객체를 이용해 저장된 데이터 획득. Relam에서 제공하는 findFirst()함수를 이용해 첫 번째 데이터를 가져옴
``` java
Realm mRealm = Realm.getDefaultInstance(); // Relam 객체 획득
MemoVO vo = mRealm.where(MemoVO.class).equalTo("Title", title).findFirst(); //저장된 데이터 획득
```

* deleteFromRealm() : 획득한 VO 객체의 데이터 삭제 함수
* delete() : 특정 타입의 모든 데이터를 삭제하는 

## 9. 파일 및 SharedPreferences을 이용한 데이터 영속화
### 9.1 퍼미션
#### 9.1.1 퍼미션이란
개발자 코드의 알고리즘이 아니라 AndroidManifest.xml에 들어가는 설정 <br>
앱을 외부에서 이용할 때 권한을 부여하고 권한을 설정했을 때만 실행되게 하기 위한 설정<br>
어떤 앱이 \<permission>을 부여했다면 그 앱을 이용하는 앱은 \<uses-permission>을 선언해야 함 <br>
앱과 앱 사이 또는 시스템에서 특정 기능에 퍼미션을 부여한 경우도 있음<br>
안드로이드 시스템의 정책적인 측면임

**퍼미션 이용**
> \<permission>은 자신의 앱을 외부에서 이용할 때 권한을 부여하여 해당 권한을 가지고 들어올 때만 실행되게 하기 위한 설정.
\<uses-permission>은 퍼미션이 선언된 앱을 이용하는 앱에 선언되는 설정. 선언하지 않으면 에러 발생함 <br> 
* \<permission> : 앱의 컴포넌트를 보호하고 싶을 때 선언
* \<uses-permission> : 퍼미션이 선언된 앱을 이용하려면 선언

> AndroidManifest.xml에 새로운 퍼미션을 선언
```xml
<permission android:name="com.test.permission.SOME_PERMISIION"
  android:lable="SOME Permission" // 퍼미션 이름
  android:description="@String/permission" // 퍼미션에 대한 설명(사용자에게 보이는 문자열)
  android:protectionLevel="normal"/> // 보호 수준(normal, dangerous, signature, signatrueOrSystem)
```

> 컴포넌트에 퍼미션 적용
```xml
<activity android:name=.SomeActivity"
  android:permission="com.text.permission.SOME_PERMISSION">
  <intent-filter>
    <action android:name="AAA"/>
    <category android:name="android.intent.category.DEFAULT"/>
  </intent-filter>
</activity>
```

> 보호된 컴포넌트를 이용하는 앱은 AndroidManifest.xml에 \<uses-permission>이 등록되어 있어야함
```xml
<uses-permission android:name="com.test.permission.SOME_PERMISSION"/>
```

**protectionLevel 속성** 
* normal : 낮은 수준의 보호. 사용자에게 권한 부여 요청이 필요 없는 경우
* dangerous : 높은 수준의 보호. 사용자에게 권한 부여 요청이 필요한 경우
* signature : 동일한 키로 서명된 앱만 실행
* signatureOrSystem : 안드로이드 시스템 앱이거나 동일 키로 서명된 앱만 실행

**시스템의 퍼미션**
> 특정 기능을 시스템에서 보호하고 있어 앱에서 해당 기능을 사용할 때 \<uses-permission>을 선언하지 않으면 에러 발생함.
예로 외부 저장 공간에 파일을 읽거나 쓸 때. 안드로이드는 관련된 퍼미션을 묶어서 관리하고 사용자에게 그룹 단위로 알려줌

![permission](https://user-images.githubusercontent.com/23471262/51177753-dcb5a980-1903-11e9-9c2c-8f18496198da.JPG) <br>

### 9.2 파일에 읽고 쓰기
#### 9.2.1 외부 저장 공간 이용
> 스마트폰에서 외부 저장 공간을 제공하는지 판단
``` java
String state = Environment.getExternalStorageState(); // 외부 저장 공간 상태
if(state.equals(Environment.MEDIA_MOUNTED)) { // 상태값 일치하면 외부 저장 공간 제공된다는 의미
  if(state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){ // 일치하면 파일을 읽거나 쓸 수 있는 상황이라는 의미
    externalStorageReadable = true;
    externalStorageWritable = false;
   }
   else{
    externalStorageReadable = true;
    externalStorageWritable = true;
   }
}else{
  externalStorageReadable = externalStorageWritable = false;
}
```

> 퍼미션 설정
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!-- 파일을 쓰려면 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <!-- 파일을 읽으려면 -->
```

> 외부 저장 공간 파일에 데이터 쓰기
``` java
FileWriter writer; // 문자열 데이터를 저장하기 위해 FileWriter 사용
try{
    //외부 저장 공간 root 하위에 myApp이라는 폴더 경로 획득
    String dirPathe = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp";
    File dir = new File(dirPathe);

    //폴더가 없다면 새로 만들어줌
    if(!dir.exists()){
        dir.mkdir();
    }
    //myApp폴더 밑에 myfile.txt 파일 지정
    File file = new File(dir + "/myfile.txt");
    // File tempFile = File.createTempFile("IMG", ".jpg", dir); // 파일명 중복되지 않게
    
    //파일이 없다면 새로 만들어 준다
    if(!file.exists()){
        file.createNewFile();
    }

    //파일에 쓰기
    writer = new FileWriter(file, true);
    writer.write(content);
    writer.flush();
    writer.close();
} 
catch (Exception e){
    e.printStackTrace();
}
```

> 외부 저장 공간 파일을 읽기
``` java
File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myApp/myfile.txt");

try{
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuffer buffer = new StringBuffer();
    String line;
    while((line=reader.readLine()) != null){
        buffer.append(line);
    }
    textView.setText(buffer.toString());
    reader.close();
}
catch (Exception e){
    e.printStackTrace();
}
```
#### 9.2.2 내부 저장 공간 이용

### 9.3 SharedPreferences와 앱 설정 자동화
#### 9.3.1 SharedPreferences
앱의 데이터를 영속적으로 저장하기 위한 클래스. DBMS 방식의 데이터 영속화는 테이블 구조를 저장하지만, SharedPreferences는 데이터를 간단하게 키-값(key-value) 성격으로 저장. 저장 데이터는 파일(XML)로 저장되지만, 개발자가 직접 파일을 읽고 쓰는 코드를 작성하지 않고 SharedPreferences 객체를 이용해서 간단하게 이용 가능

> getPreferences() 함수는 별도의 파일명을 지정하지 않으므로 자동으로 액티비티 이름의 파일내에 저장함. 결국, 하나의 액티비티만을 위한 저장 공간이 되어 다른 액티비티에서는 데이터를 이용할 수 없음
```java
SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE); //모드가 자기 앱 내에서 사용. 외부 앱에서 접근 불가로 설정
```
> getSharedPreferences()는 다른 액티비티나 컴포넌트들이 데이터를 공유해서 이용할 수 있음. 데이터를 각각의 파일로 나누어 구분하여 저장하고자 할 때 사용
```java
SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE); // 매개변수 파일명 지정
```
> PreferenceManager.getDefaultSharedPreferences()함수 기본으로 앱의 패키지명을 파일명으로 사용함
``` java
SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
```
SharedPreferences로 데이터를 저장하려면 Editor 클래스의 함수를 이용함. Editor 클래스의 putter 함수를 이용하여 키-값 형태로 데이터를 저장함
``` java
SharedPreferences.Editor editor = sharedPref.edit();
editor.putString("data1", "hello"); // 문자열 데이터 타입 함수
editor.pputInt("data2", 100); // 숫자 데이터 타입 함수
editor.commit(); // 저장한 데이터 최종 반영 함수
```

저장된 데이터를 획득할 때는 SharedPreference 클래스의 getter 함수 이용함
```java
String data1 = sharedPref.getString("data1", "none");
int data2 = sharedPref.getInt("data2", 0);
```
#### 9.3.2 앱 설정 자동화
앱의 환경설정(카카오톡 알림을 소리 아니면 진동으로 할 것인지)을 위해서 액티비티에서 설정 화면을 구성하고, 화면에서 발생하는 다양한 사용자 이벤트를 처리하여 설정한 데이터를 영속적으로 저장해야함. 이러한 부분을 대행해주는 것이 앱 설정 자동화임
<br>
많은 앱이 스마트폰의 환경설정과 비슷한 UI로 환경설정 화면을 제공함. 그러한 스타일로 앱의 설정 화면을 제공해준다면 직접 UI를 구성하지 않고도 PreferenceFragment 클래스의 도움을 받아 쉽게 작성할 수 있음

> 설정 화면을 위한 XML 파일을 준비하고 res 하위에 "xml"이라는 폴더에 만듬. 그리고 설정 XML 파일에서 태그들로 설정화면을 구성함
* \<PreferenceScreen> : 설정 화면 단위. 중첩 가능하며 중첩된 내용은 별도의 화면에 나옴
* \<PreferenceCategory> : 설정 여러 개를 시각적으로 묶어서 표현
* \<CheckboxPreference> : 체크박스가 나오는 설정
* \<EditTextPrefernece> : 글 입력을 위한 설정
* \<ListPreference> : 항목 다이얼로그를 위한 설정
* \<MutiSelectListPreference> : 항목 다이얼로그인데 체크박스가 자동 추가되어 여러 선택 가능
* \<RingtonPreference> : 알림음 선택을 위한 설정
* \<SwitchPreference> : 스위치를 이용한 설정
  
#### 9.3.3 앱 설정 자동화 적용
**PreferenceFragment** <br>
설정 XML 파일을 적용하려면 PreferenceFragment를 상속받는 개발자 서브 클래스를 만들어야 함
> PreferenceFragment 서브 클래스의 onCreate() 함수 내에서 addPreferencesFromResource() 함수 호출만으로 XML 파일을 적용 가능함
``` java
public class SettingPreferenceFragment extends PreferenceFragment {
  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings_preference); // 매개변수 설정 XML 파일 정보
    }
  }
```
> Activity 클래스에서 설정을 위한 PreferenceFragment 서브 클래스를 화면에 띄워야 함. 액티비티의 레이아웃 XML 파일에서 <fragment> 태그를 이용하여 PreferenceFragment 서브 클래스를 등록함. 이때 android:name 속성값에 전체 패키지명을 입력함
```xml
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
  android:id="@id/setting_fragment"
  android:name="com.example.test3_9.SettingPreferenceFragment"
  android:layout_width="match_parent"
  android:layout_height="match_parent"/>
```

액티비티가 실행되면서 PreferenceFragment 클래스를 화면에 출력하며, PreferenceFragment 클래스에서 설정을 위한 XML을 적용하는 구조

**summary 변경** <br>
코드에서 자동 저장된 설정 내용을 획득해야 하는 경우. 대표적인 예가 설정 내용에 따라 summary가 변경되는 경우
> 코드에서 사용자 설정값을 가져와야할 때, SharedPreferences를 이용할 수 있음
```java
prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
if(!prefs.getString("sound", "").equals(""))
  soundPreference.setSummary(prefs.getString("sound", "카톡"));
```

**Preference 이벤트 처리** <br>
사용자가 환경을 설정하는 순간에 이벤트 처리가 필요할 때
> 사용자가 설정을 변경한 순간, 설정의 summary를 변경하는 코드
```java
prefs.registerOnSharedPreferenceChangeListener(prefListener);
```
```java
SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if(key.equals("sound")){
      soundPreference.setSummary(prefs.getString("sound", "카톡"));
    }
  }
};
```

## 10. AdpterView 활용
### 10.1 Adapter와 AdapterView
#### 10.1.1 AdapterView의 구조
AdapterView는 항목을 나열하는 뷰를 지칭함. 하나의 뷰에 여러 데이터를 나열하고 그중 하나를 사용자에게 선택받는 뷰임.
AdapterView들은 구조적으로 라이브러리의 AdapterView를 상속받아 작성된 뷰를 의미함<br>
> AdapterView
![image](https://user-images.githubusercontent.com/23471262/51369439-effc8b00-1b36-11e9-8521-5c3b814e80eb.png) <br>

Adapter에게 일을 시키고 Adapter가 AdapterView를 완성해주는 구조임.
AdapterView를 이용하려면 Adapter 클래스를 활용해야 함.
Adapter 클래스는 Adapter 타입으로 표현되는 클래스를 지칭하며, 라이브러리에서 ArrayAdapter, SimpleAdapter, CursorAdapter 제공됨 <br>
> Adapter
![image](https://user-images.githubusercontent.com/23471262/51370601-fc82e280-1b3a-11e9-9a05-f40a33e9fa0e.png)

#### [10.1.2 라이브러리의 Adapter](/part4_10/src/main/java/com/ming/part4_10/MainActivity.java)
**ArrayAdapter** <br>
**SimpleAdapter**<br>
**CursorAdapter**<br>

### [10.2 커스텀 Adapter](/part4_10/src/main/)
문자열 이상의 ListView를 만들어야 한다면 개발자가 직접 커스텀 Adapter를 만들어서 적용해야 함
* 개발자 알고리즘대로 항목의 데이터가 설정되어야할 때
* 개발자 알고리즘대로 항목별 뷰의 이벤트를 다르게 처리해야할 때
* 개발자 알고리즘대로 항목별 레이아웃을 다르게 적용해야할 때

[VO 작성](/part4_10/src/main/java/com/ming/part4_10/DriveVO.java)
      
> 커스텀 Adapter를 만들려면 항목별 데이터를 추상화한 VO 클래스를 정의

[Adapter 만들기](/part4_10/src/main/java/com/ming/part4_10/DriveAdapter.java)
> 커스텀 Adapter 작성하려면 라이브러리에서 제공하는 Adapter 중 하나를 상속받아야 함<br>
BaseAdapter 또는 ArrayAdapter, SimpleAdapter 등<br>
생성자를 작성하고 기본적으로 getCount() 함수와 getView() 함수를 작성해야 함<br>
해당 함수는 내부적으로 자동 호출됨
```java
Context context;
int resId;
ArrayList<DriveVO> datas;
    
public DriveAdapter(Context context, int resId, ArrayList<DriveVO> datas){
  //생성자 (Context 항목, 항목 레이아웃 XML 정보, 항목 구성 데이터)
}
@Override
public int getCount() {
  //전체 항목의 개수를 판단하기 위해 호출
    return datas.size();
}

@NonNull
@Override
public View getView(int position, View convertView, ViewGroup parent){
 //한 항목을 구성하기 위해 자동 호출됨
}
```
## 11. 다양한 뷰 활용
### 11.1 Spannable
Spannable은 뷰가 아님. 문자열 데이터를 표현하기 위한 클래스들임
#### 11.1.1 Spannable의 필요성
안드로이드에서 문자열의 기초 타입은 CharSequence임. String 클래스보다 상위 타입이 있는 이유는 문자열이 데이터뿐만 아니라, UI정보까지 포함해야 하기 때문임. String과 StringBuffer 클래스들은 문자열 데이터 자체만 표현함
>CharSequence
![image](https://user-images.githubusercontent.com/23471262/51377041-31983080-1b4d-11e9-9a82-434fd3fe05d7.png)

해당 클래스들도 문자열 데이터를 표현함. 상위 타입은 CharSequence임. Editable, SpannableString, SpannableStringBuilder 클래스는 문자열 데이터와 UI 정보인 Spannable을 함께 표현할 수 있음
>Spannable
![image](https://user-images.githubusercontent.com/23471262/51377303-eaf70600-1b4d-11e9-887e-fbb403c26992.png)
#### [11.1.2 Spannable 적용](/part4_11/src/main/java/com/ming/part4_11/MainActivity.java)

## 14. 인텐트와 구글 기본 앱 연동
### [14.1 인텐트](/part5_14/src/main/)
#### 14.1.1 인텐트의 기본 개념
안드로이드는 액티비티, 서비스, 콘텐츠 프로바이더, 브로드캐스트 리시버 4개의 컴포넌트로 구성됨. 컴포넌트라고 부르는 이유는 자바 클래스로 작성되었더라도 각 클래스가 독립적으로 실행되어 직접 결합이 발생하지 않는 구조이기 때문임. 직접 실행하지 않고 안드로이드 시스템이 의뢰를 받아 컴포넌트를 실행하는 구조로 동작함. 또한 컴포넌트 클래스의 생명주기를 시스템이 관리함. <br>
컴포넌트를 직접 자바 코드로 생성해서 실행하지 못하고 실행하고자 하는 컴포터넌트 정보를 담은 인텐트를 구성해서 시스템에 넘기면 시스템에서 인텐트 정보를 분석해 맞는 컴포넌트를 실행하는 구조임. 즉, 인텐트는 "컴포넌트를 실행하기 위해 시스템에 넘기는 정보"로 볼 수 있음
#### 14.1.2 명시적 인텐트, 암시적 인텐트
인텐트에 어떤 정보를 담는지에 따라 크게 명시적 인텐트와 암시적 인텐트로 구분됨 <br>
* 명시적 인텐트 <br>
실행하고자 하는 컴포넌트의 클래스명을 인텐트에 담는 방법. 주로 같은 앱의 컴포넌트를 실행할 때 이용하는 방법
```java
Intent intent = new Intent(this, DetailActivity.class); // 생성자에 클래스명
startActivity(intent); // 인텐트를 시스템에 의뢰하는 함수, 매개변수로 인텐트 객체 
```
* 암시적 인텐트 <br>
클래스명이 아닌 Intent Filter 정보를 활용함. 주로 클래스명을 알 수 없는 외부앱의 컴포넌트를 실행할 때 이용하는 방법
 Intent Filter는 AndroidManifext.xml 파일에 등록된 컴포넌트 정보임
```xml
<activity android:name=".DetailActivity">
  <intent-filter>
    <action android:name="com.example.ACTION_VIEW"/>
    <action android:name="android.intent.category.DEFAULT"/>
  </intent-filter>
</activity>
```
```java
 Intent intent = new Intent();
 intent.setAction("com.example.ACTION_VIEW"); // intent-filter 정보와 동일한 값을 주어 실행
 startActivity(intent);
 ```
 #### 14.1.3 인텐트 필터(Intent Filter)
 * \<action> : 컴포넌트가 어떤 능력을 갖추고 있는지에 대한 문자열. 개발자가 임의로 지정하는 단어도 가능하며, 라이브러리에서 지정한 문자열을 이용해도 됨
 * \<category> :  컴포넌트에 대한 추가 정보로 어느 범주의 컴포넌트인지를 표현하는데 사용됨. 개발자가 임의로 지정하는 단어도 가능하지만, 거의 대부분 라이브러리 내에서 준비된 단어를 사용함
* \<data> : 컴포넌트를 실행하기 위해 필요한 데이터에 대한 상세 정보를 명시하기 위해 사용됨. URL 형식으로 표현되어 android:scheme, android:host, android:port, android:mimeType 등으로 선언됨 <br>
3가지 모두 등록될 필요는 없으며, 개발자가 원하는대로 등록하여 사용함. action만 등록하거나, action과 data 등록을 많이 함
#### 14.1.4 Extra 데이터
목록화면에서 상세보기 화면의 액티비티를 실행할 때 글번호를 넘겨줘야할 때와 같이 컴포넌트를 실행하면서 데이터를 전달해야하는 때
> 인텐트를 발생하기 전에 putExtra() 함수를 이용하여 데이터를 인텐트 객체에 담아주면 됨.
key, value의 성격으로 담음. 문자열, 숫자, boolean, 객체 등의 모든 타입의 데이터를 넘길 수 있음 <br>
[ListActivity.java](/part5_14/src/main/java/com/ming/part5_14/MainActivity.java)
```java
Intent intent = new Intent(this, DetailActivity.class); // ListActivity -> DetailActivity
intent.putExtra("data1", "hello");
intent.putExtra("data2", 100);
startActivity(intent);
```
> 자신을 실행했던 인텐트를 얻어서 그 인텐트 객체에 담긴 데이터를 얻는 구조임 <br>
[DetailActivity.java](/part5_14/src/main/java/com/ming/part5_14/DetailActivity.java)
```java
Intent intent = getIntent(); // 인텐트 객체 획득
String data = intent.getStringExtra("data1"); // 데이터
int data2 = intent.getIntExtra("data2", 0); // 데이터
```
#### 14.1.5 결과 되돌리기
사용자가 뒤로가기 버튼을 누르지 않고도 자바 코드에서 자동으로 화면이 되돌아오게 처리해야할 때 <br>
> startActivity() 함수를 이용하지 않고, startActivityForResult() 함수를 이용함
```java
Intent intent = new Intent(this, DetailActivity.class);
startActivityForResult(intent, 10); // 0이상의 숫자를 지정하여 어느 요청이 돌아온 것인지 구분
```
> 종료하기 전에 인텐트 객체에 Extra 데이터로 결과 데이터를 담을 수 있음, 자신의 상태를 setResult() 함수를 이용해 지정할 수 있음
```java
Intent intent = getIntent();
intent.putExtra("location", textView.getText().toString());
setResult(RESULT_OK, intent); // 정상으로 처리되어 되돌린 것임을 명시
finish(); // 자신을 종료
```
액티비티가 종료되면 이전 화면으로 되돌아가며, 이전 액티비티의 onActivityResult() 함수가 자동으로 호출됨
```java
@Ovverride
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// 인텐트를 발생시킨 곳에서 지정한 값, 호출되었던 곳에서 되돌리기 전에 지정한 값
  if(requestCode==10 && resultCode==RESULT_OK){
    //...
   }
 }
```
### [14.2 구글 기본 앱 연동](/part5_14/src/main/java/com/ming/part5_14/Lab14_2Activity.java)
#### 14.2.1 주소록 앱
#### 14.2.2 카메라 앱
**이미지 로딩으로 인한 OutOfMemoryException 문제** <br>
크기가 큰 데이터를 로딩하다 앱의 메모리 부족으로 실행 시 에러가 발생하는 경우. 대표적으로 이미지 로딩 부분에서 자주 발생함. 이 문제를 피하려면 이미지의 크기를 줄여서 로딩해야 함. 이미지 크기를 줄이는 방법은 API에서 제공함
```java
Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath())
```
Bitmap은 이미지를 표현하는 클래스로 BitmapFactory 클래스로 생성함. BitmapFactory의 decodeXXX() 함수로 생성하여 이용함.
* BitmapFactory.decodeByteArray() : byte[] 배열로 Bitmap 생성
* BitmapFactory.decodeFile() :  파일 경로를 주면 FileInputStream을 만들어서 decodeStream 이용
* BitmapFactory.decodeResource() : Resource 폴더에 지정된 파일
* BitmapFactory.decodeStream() : InputStream으로 Bitmap 생성
> 이때 옵션을 설정할 수 있음. Options 클래스의 inSampleSize 속성값을 설정해서 decodeXXX() 함수의 두 번째 매개변수에 option을 주면 이미지 크기를 자동으로 줄여서 로딩함
```java
BitmapFactory.Options imgOptions = new BitmapFactory.Options();
imgOptions.inSampleSize = 10;
Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), imgOptions);
```
> 고정된 값이 아닌 이미지의 크기와 프로그램에서 사용하려는 이미지 크기를 비교해서 비율을 동적으로 결정되게함. 로딩하려는 이미지의 크기를 먼저 얻고, Options에 이미지 정보를 담는 코드임. 이미지의 가로 세로 크기를 얻는 예
```java
BitmapFactory.Options options = new BitmapFactory.Options();
options.inJustDecodeBounds = ture;
try {
    InputStream in = new FileInputStream(filePath);
    BitmapFactory.decodeStream(in, null, options);
    in.close();
    in = null;
}
catch (Exception e){
    e.printStackTrace();
}

final int hegith = options.outHeiht;
final int width = options.outWidth;
```
> 이미지 크기와 개발자가 원하는 이미지 크기를 비교해서 적절한 inSampleSize를 계산
```java
if(height > reqHeight || width > reqWidth){
  final int heightRatio = Math.round((float)height / (float)reqHeight);
  final int widthRatio = Math.round((float)width / (float)reqWidth);
  inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
}
```
#### 14.2.3 갤러리 앱
#### 14.2.4 음성인식 앱
#### 14.2.5 기타 앱 연동
