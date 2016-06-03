package test.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.util.LinkedList;

/**
 * Created by dmytr on 03.06.2016.
 */
@Root
public class channel {
    @Element
    private String title;
    @Element
    private String link;
    @Element
    private String description;
    @Namespace(prefix="dc")
    @Element
    private String creator;
    @Element
    private Image image;
    @ElementList(inline=true)
    private LinkedList<item> items;

    public LinkedList<item> getItems() {
        return items;
    }

    public void setItems(LinkedList<item> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
