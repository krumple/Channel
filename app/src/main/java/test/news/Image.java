package test.news;

import org.simpleframework.xml.Element;

/**
 * Created by dmytr on 03.06.2016.
 */

public class Image {
    @Element
    private String title;
    @Element
    private  String url;
    @Element
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
