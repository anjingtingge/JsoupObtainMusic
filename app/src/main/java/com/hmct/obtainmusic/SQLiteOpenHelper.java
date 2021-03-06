package com.hmct.obtainmusic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {


    //数据库版本号
    private static Integer Version = 1;


    //在SQLiteOpenHelper的子类当中，必须有该构造函数
    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }
    //参数说明
    //context:上下文对象
    //name:数据库名称
    //param:factory
    //version:当前数据库的版本，值必须是整数并且是递增的状态

    public SQLiteOpenHelper(Context context, String name, int version)
    {
        this(context,name,null,version);
    }


    public SQLiteOpenHelper(Context context, String name)
    {
        this(context, name, Version);
    }

    //当数据库创建的时候被调用
    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("创建数据库和表");
        //创建了数据库并创建一个叫records的表
        //SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
        //此条数据库只建立int 型id和varchar 类型name（数据长度200），
        String sql = "create table songlist(type varchar(40), name varchar(200),url varchar(300),des varchar(2000),id int(1000))";

        String sqlist = "create table listdetail(name varchar(200),number varchar(30),songtitle varchar(300),singer varchar(300),songtime varchar(200),songid varchar(200))";

        //execSQL用于执行SQL语句
        //完成数据库的创建
        db.execSQL(sql);
        db.execSQL(sqlist);
        //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开


    }

    //数据库升级时调用
    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade（）方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("更新数据库版本为:"+newVersion);
    }


}

