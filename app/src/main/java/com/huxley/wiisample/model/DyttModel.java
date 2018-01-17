package com.huxley.wiisample.model;

import com.huxley.wiisample.common.Tools;
import com.huxley.wiisample.model.netBean.DyttDetailInfo;
import com.huxley.wiisample.model.netBean.DyttListBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * DyttModel
 * Created by huxley on 16/7/20.
 */
public class DyttModel {

    private static DyttModel instance;

    public static final String BaseUrl = "http://www.ygdy8.net";

    private DyttModel() {
    }

    public static DyttModel getInstance() {
        if (instance == null) {
            synchronized (DyttModel.class) {
                if (instance == null) {
                    instance = new DyttModel();
                }
            }
        }
        return instance;
    }

    public Observable<DyttListBean> getMovieList(String url) {
        return Observable.just(url)
                .map(this::loadMovieList)
                .compose(io_main());
    }

    private DyttListBean loadMovieList(String url) {
        DyttListBean movieListInfo = new DyttListBean();
        try {
            Document doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            Element element = doc.select("div.co_content8").first();
            Elements tables = element.select("table");
            DyttListBean.MovieInfo movie;
            for (Element table : tables) {
                movie = new DyttListBean.MovieInfo();
                Element a = table.select("a").last();
                String aText = a.text();
                movie.name = Tools.getCenterString("《", "》", aText);
                movie.url = a.attr("href");
                movie.content = table.select("td").last().text();
                movieListInfo.mMovieInfos.add(movie);
            }
            Element x = element.select("div.x").first();
            Elements as = x.select("a");
            Element a = as.get(as.size() - 2);
            if ("下一页".equals(a.text())) {
                movieListInfo.nextUrl = a.attr("href");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieListInfo;
    }

    public Observable<DyttDetailInfo> getMovieDetailInfo(String url) {
        return Observable.just(url)
                .map(this::loadMovieDetailInfo);
    }

    private DyttDetailInfo loadMovieDetailInfo(String url) {
        DyttDetailInfo movie = new DyttDetailInfo();
        try {
            Document doc = Jsoup.parse(new URL(BaseUrl + url).openStream(), "GBK", BaseUrl + url);
            Element co_content8 = doc.select("div.co_content8").first();
            Element zoom = co_content8.select("div#Zoom").first();
            Elements imgs = zoom.select("img").remove();
            if (imgs != null) {
                for (Element img : imgs) {
                    if (img != null) {
                        String src = img.attr("src");
                        if (src != null) {
                            movie.pics.add(src);
                        }
                    }
                }
            }
            Elements as = zoom.select("table>tbody>tr>td>a");
            for (Element a : as) {
                movie.urls.add(a.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }


    public String encode(String url) {
        StringBuilder builder = new StringBuilder("AA");
        for (char ch : url.toCharArray()) {
            if (Tools.isChinese2(ch)) {
                builder.append(Tools.urlEncode(String.valueOf(ch), "utf-8"));
            } else {
                builder.append(ch);
            }
        }
        return "thunder://" + Tools.base64encode(Tools.utf16to8(builder.append("ZZ").toString()));
    }

    public String decode(String content) {
        return Tools.urlDecode(
                Tools.utf8to16(
                        Tools.base64decode(
                                content.substring(10, content.length())
                        )
                ).substring(2, content.length()).substring(0, content.length() - 2),
                "utf-8"
        );
    }

    public Observable<DyttListBean> search(String content) {
        return Observable.just(content)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMap(s -> Observable.just(loadSearch(s)))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DyttListBean> search_1(String content) {
        return Observable.just(content)
                .subscribeOn(Schedulers.io())
                .map(this::loadSearch_1)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final  String SBaseUrl  = "http://s.dydytt.net";
    private static final String SearchUrl = SBaseUrl + "/plus/search.php?keyword=";//搜索url

    private DyttListBean loadSearch(String s) {
        DyttListBean movies = new DyttListBean();
        String url = SearchUrl + Tools.urlEncode(s, "gb2312");
        System.out.println(url);
        try {
            Document doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            Element element = doc.select("div.co_content8").first();
            Elements tables = element.select("table");
            Element remove = tables.remove(tables.size() - 1);
            Elements pageAs = remove.select("a");
            Element nextA = pageAs.get(pageAs.size() - 2);
            System.out.println(nextA);
            if ("下一页".equals(nextA.text())) {
                movies.nextUrl = nextA.attr("href");
            }
            DyttListBean.MovieInfo movie;
            for (Element table : tables) {
                movie = new DyttListBean.MovieInfo();
                Element a = table.select("a").first();
                String aText = a.text();
                movie.name = Tools.getCenterString("《", "》", aText);
                movie.url = a.attr("href");
                movie.content = table.select("tr").get(1).select("td").first().text();
                movies.mMovieInfos.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    private DyttListBean loadSearch_1(String url) {
        DyttListBean movies = new DyttListBean();
        try {
            Document doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
            Element element = doc.select("div.co_content8").first();
            Elements tables = element.select("table");
            Element remove = tables.remove(tables.size() - 1);
            Elements pageAs = remove.select("a");
            Element nextA = pageAs.get(pageAs.size() - 2);
            System.out.println(nextA);
            if ("下一页".equals(nextA.text())) {
                movies.nextUrl = nextA.attr("href");
            }
            DyttListBean.MovieInfo movie;
            for (Element table : tables) {
                movie = new DyttListBean.MovieInfo();
                Element a = table.select("a").first();
                String aText = a.text();
                movie.name = Tools.getCenterString("《", "》", aText);
                movie.url = a.attr("href");
                movie.content = table.select("tr").get(1).select("td").first().text();
                movies.mMovieInfos.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }




    public <T> Observable.Transformer<T, T> io_main() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}