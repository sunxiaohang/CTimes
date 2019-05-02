package com.sunxiaohang.root.ctimes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sunxiaohang.root.ctimes.R;
import com.sunxiaohang.root.ctimes.pojo.Book;
import com.sunxiaohang.root.ctimes.pojo.Movies;
import com.sunxiaohang.root.ctimes.pojo.Music;
import com.sunxiaohang.root.ctimes.pojo.News;
import com.sunxiaohang.root.ctimes.pojo.Role;
import com.sunxiaohang.root.ctimes.pojo.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public final static String GITHUBADDRESS = "https://github.com/sunxiaohang/CTimes.git";
    public final static String GITHUBBLOG = "https://sunxiaohang.github.io";
    public final static String ANDROID = "https://sunxiaohang.github.io/about.html";
    public final static String TOPMUSICREQUESTADDRESS = "https://api.bzqll.com/music/netease/songList?key=579621905&id=3778678";
    public final static String TOPMVLISTREQUESTADDRESS = "https://api.bzqll.com/music/netease/topMvList?key=579621905&limit=100&offset=0";
    private static final String REQUEST_STORIES_URL = "https://sunxiaohang.github.io/assets/nytimes/";
    private static final String API_KEY = "api-key=owT6IxIazx3ylrlQEAh953VLdPZIMhBN";
    public static final String AWORDRANDOMREQUESTADDRESS = "https://v1.hitokoto.cn/";
    public static final String REQUEST_BOOKS_URL = "https://api.douban.com/v2/book/search?count=15&q=";
    public static final String REQUEST_BOOKS_URL_KEY = "&apikey=0df993c66c0c636e29ecbb5344252a4a";
    public static final String REQUEST_MOVIES_URL_COMING = "http://api.douban.com/v2/movie/coming_soon?count=20?apikey=0df993c66c0c636e29ecbb5344252a4a";
    public static final String REQUEST_MOVIES_URL_NOW_PLAYING = "http://api.douban.com/v2/movie/in_theaters?apikey=0df993c66c0c636e29ecbb5344252a4a";
    public static final String REQUEST_MOVIES_URL_TOP = "https://api.douban.com/v2/movie/top250?count=20?apikey=0df993c66c0c636e29ecbb5344252a4a";

    private static ACache aCacheInstance;
//    private static ArrayList<String> bookCatalogs = new ArrayList<>();
    public static void initialACache(Context context){
        aCacheInstance = ACache.get(context);
    }
    static {
        random = new Random();
//        bookCatalogs.add("中国文学");
//        bookCatalogs.add("外国文学");
//        bookCatalogs.add("散文");
//        bookCatalogs.add("经典名著");
//        bookCatalogs.add("历史");
//        bookCatalogs.add("教育");
//        bookCatalogs.add("人物传记");
//        bookCatalogs.add("哲学");
//        bookCatalogs.add("计算机");
//        bookCatalogs.add("心理学");
    }
    public static ACache getACacheInstance(){
        return aCacheInstance;
    }
    private static final String[] colors = {
            "#009688","#5fb878","#393d49","#ffb800","#ff5722","#2f4056"
    };
    private static final String[] mediaTypes = {"books","musics","movies"};
    private static final Random random;
    private static String[] subjects;
    public static ArrayList<Drawable> getAutoScrollImageDrawables(Context context){
        ArrayList<Drawable> autoScrollDrawable = new ArrayList<>();
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.android));
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.adcar));
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.adhuan));
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.adjie));
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.adjiejie));
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.home));
        autoScrollDrawable.add(context.getResources().getDrawable(R.mipmap.book));
        return autoScrollDrawable;
    }
    public static ArrayList<String> getMediaType(){
        ArrayList<String> medias = new ArrayList<>();
        for (int i = 0; i < mediaTypes.length; i++) {
            medias.add(mediaTypes[i]);
        }
        return medias;
    }
    public static int getRandomNumber(int range){
        return random.nextInt(range);
    }
    public static String getRandomColor(){
        int randomNumber = random.nextInt(colors.length);
        return colors[randomNumber];
    }
    public static String[] getSubjects(Context context){
        subjects = context.getResources().getStringArray(R.array.CONTENT_SUBJECT);
        return subjects;
    }
    public static void setAlphaStatusBar(Activity activity){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    public static String getRequestAddress(String subject){
        StringBuilder builder = new StringBuilder(REQUEST_STORIES_URL);
        builder.append(subject+".json");
        return builder.toString();
    }
    public static String getBookRequestAddress(String id){
        StringBuilder builder = new StringBuilder(REQUEST_BOOKS_URL);
        builder.append(id+".json");
        return builder.toString();
    }
    public static String getCapColorText(String text){
        String html = "<font color='"+Color.parseColor(getRandomColor())+"'><B><big><I>"+text.substring(0,1)+"</I></big></B></font><font color='#aaffffff'><big>"+text.substring(1,text.length())+"</big></font>";
        return html;
    }
    public static ArrayList<News> processResultToList(String requestResult){
        ArrayList<News> newsList = new ArrayList<>();
        String title;
        String newsAbstract;
        String url;
        String author;
        String published_date;
        String type;
        String imageOneUrl = "";
        try {
            JSONObject object = new JSONObject(requestResult);
            if(object.getString("status").equals("OK")){
                JSONArray results = object.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    title = item.getString("title");
                    newsAbstract = item.getString("abstract");
                    url = item.getString("url");
                    author = item.getString("byline");
                    published_date = item.getString("published_date");
                    type = item.getString("item_type");
                    JSONArray multimedia = item.getJSONArray("multimedia");
                    if(multimedia.length()>0){
                        imageOneUrl =((JSONObject) multimedia.get(1)).getString("url");
                    }
                    News news = new News(title,newsAbstract,url,author,published_date,type,imageOneUrl);
                    newsList.add(news);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
    public static ArrayList<Book> processBookJsonResult(String respondResult){
        ArrayList<Book> bookList = new ArrayList<>();
        String title;
        String author;
        String publishDate;
        String publisher;
        String price;
        String rateStar;
        String numberRaters;
        String imageUrl;
        String authorIntroduction;
        String bookIntroduction;
        try {
            JSONObject object = new JSONObject(respondResult);
            JSONArray results = object.getJSONArray("books");
            for (int i = 0; i < results.length(); i++) {
                JSONObject item = results.getJSONObject(i);
                title = item.getString("title");
                author = item.getJSONArray("author").get(0).toString();
                publishDate = item.getString("pubdate");
                publisher = item.getString("publisher");
                price = item.getString("price");
                rateStar = item.getJSONObject("rating").getString("average");
                numberRaters = item.getJSONObject("rating").getString("numRaters");
                imageUrl = item.getString("image");
                authorIntroduction = item.getString("author_intro");
                bookIntroduction = item.getString("summary");
                Book book = new Book(title,author,publishDate,publisher,price,rateStar,numberRaters,imageUrl,authorIntroduction,bookIntroduction);
                bookList.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookList;
    }
    public static ArrayList<Movies> processMoviesJsonResult(String moviesResultJson) {
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        String title;
        String genres;
        String ratingScore;
        String collectionCount;
        String years;
        String imgUrl;
        String duration;
        ArrayList<ArrayList<Role>> allRoles = new ArrayList<>();
        ArrayList<ArrayList<Role>> allDirectors = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(moviesResultJson);
            JSONArray results = object.getJSONArray("subjects");
            System.out.println("TAG:Movies "+results.length());
            for (int i = 0; i < results.length(); i++) {
                allDirectors.add(new ArrayList<Role>());
                allRoles.add(new ArrayList<Role>());
                JSONObject item = (JSONObject) results.get(i);
                title = item.getString("title");
                genres = item.getJSONArray("genres").get(0).toString();
                ratingScore = item.getJSONObject("rating").getString("average");
                collectionCount = item.getString("collect_count");
                JSONArray castsArray = item.getJSONArray("casts");
                for (int j = 0; j < castsArray.length(); j++) {
                    String avatarsImgUrl = castsArray.getJSONObject(j).getJSONObject("avatars").getString("medium");
                    String roleName = ((JSONObject)castsArray.get(j)).getString("name");
                    String id = ((JSONObject)castsArray.get(j)).getString("id");
                    allRoles.get(i).add(new Role(id,roleName,avatarsImgUrl));
                }
                JSONArray directorArray = item.getJSONArray("directors");
                for (int j = 0; j < directorArray.length(); j++) {
                    String avatarsImgUrl = directorArray.getJSONObject(j).getJSONObject("avatars").getString("medium");
                    String roleName = ((JSONObject)directorArray.get(j)).getString("name");
                    String id = ((JSONObject)directorArray.get(j)).getString("id");
                    allDirectors.get(i).add(new Role(id,roleName,avatarsImgUrl));
                }
                years = item.getString("year");
                imgUrl = item.getJSONObject("images").getString("medium");
                if(item.has("durations")){
                    duration = item.getJSONArray("durations").get(0).toString();
                }else {
                    duration ="unKnown minutes";
                }
                Movies movies = new Movies(title,genres,ratingScore,collectionCount,allRoles.get(i),allDirectors.get(i),years,imgUrl,duration);
                moviesArrayList.add(movies);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("TAG:Movies size"+moviesArrayList.size());
        return moviesArrayList;
    }
    public static ArrayList<Music> processMusicJsonResult(String musicResultJson) {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        final int MAX_CAPACITY = 27;
        String name;
        String singer;
        String picUrl;
        String lrcUrl;
        String musicUrl;
        int musicDuration;
        try {
            JSONObject object = new JSONObject(musicResultJson);
            if(object.getString("result").equals("SUCCESS")){
                JSONObject data = object.getJSONObject("data");
                JSONArray results = data.getJSONArray("songs");
                for (int i = 0; i < MAX_CAPACITY; i++) {
                    JSONObject item = results.getJSONObject(i);
                    name = item.getString("name");
                    singer = item.getString("singer");
                    picUrl = item.getString("pic");
                    lrcUrl = item.getString("lrc");
                    musicDuration = item.getInt("time");
                    musicUrl = item.getString("url");
                    Music music = new Music(name,singer,picUrl,lrcUrl,musicUrl,musicDuration);
                    musicArrayList.add(music);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musicArrayList;
    }
    public static Bundle processMusicLyrics(String lyrics){
        ArrayList<String> processingLists = new ArrayList<>();
        ArrayList<String> lyricsLists = new ArrayList<>();
        lyrics = lyrics.trim();
        Pattern p = Pattern.compile("^\\[.*?by.*?\\]");
        Matcher m = p.matcher(lyrics);
        lyrics = m.replaceAll("");
        String[] lists = lyrics.split("\\[");
        for (int i = 1; i < lists.length; i++) {
            String[] item = lists[i].split("]");
            if(item.length<2) {
                System.out.println("TAG: lists source process:"+lists[i]);
                System.out.println("TAG: processed:"+item[0]);
                continue;
            }
            processingLists.add(item[0]);
            lyricsLists.add(item[1]);
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("lyricsList",lyricsLists);
        bundle.putStringArrayList("processingLists",processingLists);
        System.out.println("TAG:"+lyrics);
        System.out.println("TAG:lyricsList length:"+lyricsLists.size());
        System.out.println("TAG:processingLists length:"+processingLists.size());
        return bundle;
    }

    public static String processSecondTime(int musicDuration) {
        return musicDuration/60+":"+musicDuration%60;
    }
    public static String processLyricsRequestAddress(String address){
       // https://api.itooi.cn/music/netease/lrc?id=1345848098&key=579621905
        String result = address.split("id=")[1].split("&")[0];
        System.out.println("TAG:processLyricsRequestAddress result:"+result);
        return result;
    }
    public static ArrayList<Video> processVideoJsonResult(String videoJsonResult){
        ArrayList<Video> videoArrayList = new ArrayList<>();
        String name;
        String singer;
        String picUrl;
        String playCount;
        String videoUrl;
        try {
            JSONObject object = new JSONObject(videoJsonResult);
            if(object.getString("result").equals("SUCCESS")){
                JSONArray results = object.getJSONArray("data");
                int length = results.length()/3;
                length = length>8?8:length;
                for (int i = 0; i < length; i++) {
                    JSONObject item = results.getJSONObject(i);
                    name = item.getString("name");
                    singer = item.getString("singer");
                    playCount = item.getString("playCount");
                    picUrl = item.getString("pic");
                    videoUrl = item.getString("url");
                    Video video = new Video(name,singer,playCount,picUrl,videoUrl);
                    videoArrayList.add(video);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videoArrayList;
    }
    public static ArrayList<String> processWallpaperResultJson(String jsonResult){
        ArrayList<String> imageUrlLists = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonResult);
            if(object.getString("error").equals("false")){
                JSONArray results = object.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    String imgUrl = item.getString("url");
                    imageUrlLists.add(imgUrl);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageUrlLists;
    }

    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String processAWordJsonResult(String jsonResult){
        StringBuilder builder = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            String hitokoto = jsonObject.getString("hitokoto");
            String from = jsonObject.getString("from");
            builder.append(hitokoto).append("&&&").append(from);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
