package test.news;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by dmytr on 04.06.2016.
 */

 @Root(name = "rss")
 public class Rss {
    @Attribute
    String version;

    @Element
    private Channel channel;
    @Namespace(prefix = "xmlns")
    @Attribute(required = false)
    private String dc;
    public Channel getChannel() {
        return channel;
    }
    public void setChannel(test.news.Channel channel) {
        this.channel = channel;
    }

}

