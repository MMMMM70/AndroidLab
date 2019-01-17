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
```java
<permission android:name="com.test.permission.SOME_PERMISIION"
  android:lable="SOME Permission" // 퍼미션 이름
  android:description="@String/permission" // 퍼미션에 대한 설명(사용자에게 보이는 문자열)
  android:protectionLevel="normal"/> // 보호 수준(normal, dangerous, signature, signatrueOrSystem)
```

> 컴포넌트에 퍼미션 적용
```java
<activity android:name=.SomeActivity"
  android:permission="com.text.permission.SOME_PERMISSION">
  <intent-filter>
    <action android:name="AAA"/>
    <category android:name="android.intent.category.DEFAULT"/>
  </intent-filter>
</activity>
```

> 보호된 컴포넌트를 이용하는 앱은 AndroidManifest.xml에 \<uses-permission>이 등록되어 있어야함
```java
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

![permission](https://user-images.githubusercontent.com/23471262/51177753-dcb5a980-1903-11e9-9c2c-8f18496198da.JPG)

