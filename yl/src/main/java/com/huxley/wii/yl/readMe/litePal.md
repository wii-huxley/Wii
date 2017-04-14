#### 1. 介绍LitePal
##### 使用 Eclipse
 * 下载最新的jar包. 或者点击查看所有的版本 **[here](https://github.com/LitePalFramework/LitePal/tree/master/downloads)** 选择一个下载使用.
 * 将jar包导入你的android项目中.
 
##### 使用 Android Studio
编辑你的 **build.gradle** 文件:
``` groovy
dependencies {
    compile 'org.litepal.android:core:1.4.1'
}
```
#### 2. 配置 litepal.xml
在 **assets** 文件夹下创建一个 **litepal.xml**.
``` xml
<?xml version="1.0" encoding="utf-8"?>
<litepal>

    <dbname value="demo" />
    <version value="1" />
    
    <list>
        <mapping class="com.test.model.Reader" />
        <mapping class="com.test.model.Magazine" />
    </list>
</litepal>
```
 * **dbname** 配置数据库的名称.
 * **version** 配置数据库的版本，修改可以更新数据库.
 * **list** 罗列数据库的表名.
 * **storage** 配置数据库的路径.
 
#### 3. 配置 LitePalApplication
方法一：
``` xml
<manifest>
	<application
		android:name="org.litepal.LitePalApplication"
		...
	>
    ...
	</application>
</manifest>
```
方法二：
``` xml
<manifest>
	<application
		android:name="com.example.MyOwnApplication"
		...
	>
    ...
	</application>
</manifest>
```
```java
public class MyOwnApplication extends AnotherApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		LitePal.initialize(this);
	}
	...
}
```
#### 4. 开始使用
##### 4.1. 创建数据库
第一次使用数据库的时候，会自动创建数据库
``` java
public class Album extends DataSupport {
	
	@Column(unique = true, defaultValue = "unknown")
	private String name;
	
	private float price;
	
	private byte[] cover;
	
	private List<Song> songs = new ArrayList<Song>();

	// generated getters and setters.
	...
}
```
``` java
public class Song extends DataSupport {
	
	@Column(nullable = false)
	private String name;
	
	private int duration;
	
	@Column(ignore = true)
	private String uselessField;
	
	private Album album;

	// generated getters and setters.
	...
}
```
Then add these models into the mapping list in **litepal.xml**:
``` xml
<list>
    <mapping class="org.litepal.litepalsample.model.Album"></mapping>
    <mapping class="org.litepal.litepalsample.model.Song"></mapping>
</list>
```
OK! The tables will be generated next time you operate database. For example, gets the **SQLiteDatabase** with following codes:
``` java
SQLiteDatabase db = LitePal.getDatabase();
```
Now the tables will be generated automatically with SQLs like this:
``` sql
CREATE TABLE album (
	id integer primary key autoincrement,
	name text unique default 'unknown',
	price real,
	cover blob
);

CREATE TABLE song (
	id integer primary key autoincrement,
	name text not null,
	duration integer,
	album_id integer
);
```

#### 2. Upgrade tables
Upgrade tables in LitePal is extremely easy. Just modify your models everyway you want:
```java
public class Album extends DataSupport {
	
	@Column(unique = true, defaultValue = "unknown")
	private String name;
	
	@Column(ignore = true)
	private float price;
	
	private byte[] cover;
	
	private Date releaseDate;
	
	private List<Song> songs = new ArrayList<Song>();

	// generated getters and setters.
	...
}
```
A **releaseDate** field was added and **price** field was annotated to ignore.
Then increase the version number in **litepal.xml**:
```xml
<!--
    Define the version of your database. Each time you want 
    to upgrade your database, the version tag would helps.
    Modify the models you defined in the mapping tag, and just 
    make the version value plus one, the upgrade of database
    will be processed automaticly without concern.
    For example:    
    <version value="1" ></version>
-->
<version value="2" ></version>
```
The tables will be upgraded next time you operate database. A **releasedate** column will be added into **album** table and the original **price** column will be removed. All the data in **album** table except those removed columns will be retained.

But there are some upgrading conditions that LitePal can't handle and all data in the upgrading table will be cleaned:
 * Add a field which annotated as `unique = true`.
 * Change a field's annoation into `unique = true`.
 * Change a field's annoation into `nullable = false`.

Be careful of the above conditions which will cause losing data.

#### 3. Save data
The saving API is quite object oriented. Each model which inherits from **DataSupport** would have the **save()** method for free:
``` java
Album album = new Album();
album.setName("album");
album.setPrice(10.99f);
album.setCover(getCoverImageBytes());
album.save();
Song song1 = new Song();
song1.setName("song1");
song1.setDuration(320);
song1.setAlbum(album);
song1.save();
Song song2 = new Song();
song2.setName("song2");
song2.setDuration(356);
song2.setAlbum(album);
song2.save();
```
This will insert album, song1 and song2 into database with associations.

#### 4. Update data
The simplest way, use **save()** method to update a record found by **find()**:
``` java
Album albumToUpdate = DataSupport.find(Album.class, 1);
albumToUpdate.setPrice(20.99f); // raise the price
albumToUpdate.save();
```
Each model which inherits from **DataSupport** would also have **update()** and **updateAll()** method. You can update a single record with a specified id:
``` java
Album albumToUpdate = new Album();
albumToUpdate.setPrice(20.99f); // raise the price
albumToUpdate.update(id);
```
Or you can update multiple records with a where condition:
``` java
Album albumToUpdate = new Album();
albumToUpdate.setPrice(20.99f); // raise the price
albumToUpdate.updateAll("name = ?", "album");
```

#### 5. Delete data
You can delete a single record using the static **delete()** method in **DataSupport**:
``` java
DataSupport.delete(Song.class, id);
```
Or delete multiple records using the static **deleteAll()** method in **DataSupport**:
``` java
DataSupport.deleteAll(Song.class, "duration > ?" , "350");
```

#### 6. Query data
Find a single record from song table with specified id:
``` java
Song song = DataSupport.find(Song.class, id);
```
Find all records from song table:
``` java
List<Song> allSongs = DataSupport.findAll(Song.class);
```
Constructing complex query with fluent query:
``` java
List<Song> songs = DataSupport.where("name like ?", "song%").order("duration").find(Song.class);
```

#### 7. Multiple databases
If your app needs multiple databases, LitePal support it completely. You can create as many databases as you want at runtime. For example:
```java
LitePalDB litePalDB = new LitePalDB("demo2", 1);
litePalDB.addClassName(Singer.class.getName());
litePalDB.addClassName(Album.class.getName());
litePalDB.addClassName(Song.class.getName());
LitePal.use(litePalDB);
```
This will create a **demo2** database with **singer**, **album** and **song** tables.

If you just want to create a new database but with same configuration as **litepal.xml**, you can do it with:
```java
LitePalDB litePalDB = LitePalDB.fromDefault("newdb");
LitePal.use(litePalDB);
```
You can always switch back to default database with:
```java
LitePal.useDefault();
```
And you can delete any database by specified database name:
```java
LitePal.deleteDatabase("newdb");
```
