package test.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Item> items;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<Item> items) {
        this.context = applicationContext;
        this.items = items;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
private String getImmageString(String description){
    String start = "src='";
    String finish = "'>";
    String str = description.substring(description.indexOf(start) + 5, description.indexOf(finish));
    return str;
}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView date = (TextView) view.findViewById(R.id.date);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        title.setText(items.get(i).getTitle());
        date.setText(items.get(i).getDate());
       Glide.with(context).load(getImmageString(items.get(i).getDescription())).into(image);
        return view;
    }
}