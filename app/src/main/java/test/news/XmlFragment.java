package test.news;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.DocumentsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dmytr on 02.06.2016.
 */
public class XmlFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    Rss rss = null;
    ListView simpleList;
    View view;
    SwipeRefreshLayout swipeLayout;
    CustomAdapter customAdapter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_progress, container, false);
        simpleList = (ListView) view.findViewById(R.id.list);
        simpleList.setOnItemClickListener(this);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        if(rss==null && isNetworkConnected())
            new ProgressTask().execute();

        else {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String content = sharedPref.getString("xml","null");


            Serializer serializer = new Persister();
            try {
                rss = serializer.read(Rss.class, content);
            } catch (Exception e) {

            }
            if (rss != null) {
                Context cont = view.getContext();
                customAdapter = new CustomAdapter(cont, rss.getChannel().getItems());
                simpleList.setAdapter(customAdapter);
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

        String item = rss.getChannel().getItems().get(position).getLink();
        Toast.makeText(getActivity(), "CLICK: " + item, Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(getActivity(),SelectedItemActivity.class);
        myIntent.putExtra("linc", item); //Optional parameters
        getActivity().startActivity(myIntent);
    }
    @Override
    public void onRefresh() {
       if(isNetworkConnected()) {
           new ProgressTask().execute();
           swipeLayout.setRefreshing(false);
       }
        else{
           SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
           String content = sharedPref.getString("xml","");
            Serializer serializer = new Persister();
            try {
                rss = serializer.read(Rss.class, content);
            } catch (Exception e) {

            }
            if (rss != null) {
                Context cont = view.getContext();
                customAdapter = new CustomAdapter(cont, rss.getChannel().getItems());
                simpleList.setAdapter(customAdapter);
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT)
                        .show();
            }
           swipeLayout.setRefreshing(false);

       }
    }
@Override
public void onAttach(Context context) {
    super.onAttach(context);

}


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            }

 class ProgressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... path) {

            String content;
            try{

                content = getContent("http://24tv.ua/rss/all.xml");
            }
            catch (IOException ex){
                content = ex.getMessage();
            }

            return content;
        }
        @Override
        protected void onProgressUpdate(Void... items) {
        }
        @Override
        protected void onPostExecute(String content) {
            saveXmlString(content);
            Serializer serializer = new Persister();
            try {
                rss = serializer.read(Rss.class, content);
            } catch (Exception e) {}

            if (rss != null) {

                Context cont = view.getContext();
                customAdapter = new CustomAdapter(cont, rss.getChannel().getItems());
                simpleList.setAdapter(customAdapter);
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        private String getContent(String path) throws IOException {
            OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();

        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

private void  saveXmlString(String xml)
{
    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString("xml", xml);
    editor.commit();
}
}
