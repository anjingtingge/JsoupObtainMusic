package com.hmct.obtainmusic;




import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.io.IOException;




public class MainActivity extends AppCompatActivity {

    private SQLiteOpenHelper dbHelper;
//    private SQLiteOpenHelper dbHelperlist;
    private SQLiteDatabase sqliteDatabase;
    private  ContentValues values,valueslist;
    private EditText editText;

    private String name,htmlurl;
    private String type;
   private String[] args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.edi_url);
        dbHelper = new SQLiteOpenHelper(this,"专辑主页",1);
//        dbHelperlist = new SQLiteOpenHelper(this,"专辑列表");
        sqliteDatabase = dbHelper.getWritableDatabase();
        htmlurl="https://y.qq.com/n/yqq/playsquare/1142247783.html#stat=y_new.playlist.dissname";
        type="国语 校园";

    }

    private void SearchList(){

        new Thread(){
            @Override
            public void run() {

                try {

                    String list_html =
                            "https://y.qq.com/n/yqq/song/000sfEQz1JSVkT.html\n" +
                                    "https://y.qq.com/n/yqq/song/0010RDBs1XqIXD.html\n" +
                                    "https://y.qq.com/n/yqq/song/001OgF581rJcMl.html\n" +
                                    "https://y.qq.com/n/yqq/song/002JIsZK48QlQH.html\n" +
                                    "https://y.qq.com/n/yqq/song/001cyhDV2cMwjZ.html\n" +
                                    "https://y.qq.com/n/yqq/song/003lelrf1yJHWo.html\n" +
                                    "https://y.qq.com/n/yqq/song/00151Jj12xPXe8.html\n" +
                                    "https://y.qq.com/n/yqq/song/003iDKD91Dealp.html\n" +
                                    "https://y.qq.com/n/yqq/song/0006KmSF26dooz.html\n" +
                                    "https://y.qq.com/n/yqq/song/0015whrz35bPDb.html\n" +
                                    "https://y.qq.com/n/yqq/song/003QYB7b3LswUY.html\n" +
                                    "https://y.qq.com/n/yqq/song/000kaFWx2Bou4e.html\n" +
                                    "https://y.qq.com/n/yqq/song/003HIigz1c1Gf2.html\n" +
                                    "https://y.qq.com/n/yqq/song/004DG5Fz44rqyp.html\n" +
                                    "https://y.qq.com/n/yqq/song/000Xv5h73iuXmb.html\n" +
                                    "https://y.qq.com/n/yqq/song/000oAJGZ1FtXFt.html\n" +
                                    "https://y.qq.com/n/yqq/song/000HGKCA1A6jzU.html\n" +
                                    "https://y.qq.com/n/yqq/song/0032FRAO0GxAdc.html\n";

//                        list_html=list_html.replace("\n","");
//                        Log.e("list_html", list_html);
                    String[] listhttp =list_html.split("\n");
//                        Log.e("ser",listhttp[0]+listhttp.length);

                    Document document = Jsoup.connect(htmlurl).get();

                    Elements stitle =document.select("span.songlist__songname_txt").select("a");
                    Elements sname = document.select("div.songlist__artist");
                    name = document.select("span.data__cover").select("img").attr("alt");
                    Elements stime = document.select("div.songlist__time");

                    Log.e("name", name);


                    for (int i=0;i<stitle.size();i++){

                        String songtitle =stitle.get(i).attr("title");
                        String singer = sname.get(i).select("a").get(0).text();
                        String songtime = stime.get(i).text();

                        Log.e("songtitle", songtitle);

                        Log.e("singer", singer);
                        Log.e("songtime", songtime);
//                        AnalysisXNL(tian,i,num);

//                            sqliteDatabase = dbHelper.getWritableDatabase();
                        valueslist = new ContentValues();

                        String number =null;

                        if (i+1>9){

                            number = String.valueOf(i+1);
                        }else {
                            number = "0"+(i+1);
                        }

                        valueslist.put("number",number);
                        valueslist.put("name", name);
                        valueslist.put("songtitle", songtitle);
                        valueslist.put("singer",singer );
                        valueslist.put("songtime",songtime );

                        String songid = listhttp[i]  .substring(listhttp[i].indexOf("song/") + 5, listhttp[i].lastIndexOf(".html"));
                        valueslist.put("songid",songid );

                        sqliteDatabase.insert("listdetail", null, valueslist);

                    }

//
//                Log.e("director", director);
//                Log.e("des", des);



                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("listall", "报错");
                }





            }

        }.start();

    }


    public void MainClick(View v) {
        switch (v.getId()){

            case R.id.btn_search:

                SearchList();
                break;

            case R.id.btn_creat:

                Search();

                break;
        }
    }



    private void Search(){

        new Thread(){
            @Override
            public void run() {
                try {

                    Document document = Jsoup.connect(htmlurl).get();

                    String url = document.select("span.data__cover").select("img").attr("src");
                    name = document.select("span.data__cover").select("img").attr("alt");
                    String des = String.valueOf(document.select("div.popup_data_detail__cont").select("p").text());
//                    type = String.valueOf(document.select("div.data_tag_box"));

                    Log.e("tian",url);
                    Log.e("title", name);
                    Log.e("tap", des);
                    Log.e("type", type);


                    values = new ContentValues();
                    values.put("name", name);
                    values.put("url", url);
                    values.put("des",des );
                    values.put("type",type );
                    sqliteDatabase.insert("songlist", null, values);



//                    for (int i=0;i<tian.size();i++){

//                        AnalysisXNL(tian,i,num);
//                    }

//                    if (last_html.length()!=0) {
//                        Document doclast = Jsoup.parse(last_html);
//                        Elements tianlast = doclast.select("a.sn");
//                        Elements numlast = doclast.select("span.sn_num");
//
//                        Log.e("linklast", String.valueOf(tianlast));
//                        for (int n = 0; n < tianlast.size(); n++) {
//                            AnalysisXNL(tianlast, n, numlast);
//
//                        }
//
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }





    private void AnalysisXNL(Elements elements,int i,Elements num){

        String linkHref = elements.get(i).attr("href");
//        linkHref = linkHref.substring(linkHref.indexOf("id_") + 3, linkHref.lastIndexOf(".ht"));
        linkHref = linkHref.substring(linkHref.indexOf("id_") + 3, linkHref.lastIndexOf("=="));
        linkHref = "http://player.youku.com/embed/"+linkHref;
        String count  = num.get(i).ownText();

        Log.e("count", count);
        Log.e("nini", linkHref);
        values = new ContentValues();
        values.put("type", name);
        values.put("count", count);
        values.put("url",linkHref );
        sqliteDatabase.insert("user", null, values);

    }



}
