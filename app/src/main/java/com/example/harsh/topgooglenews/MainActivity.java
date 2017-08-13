package com.example.harsh.topgooglenews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //display array of block 1 at a time
    private ListView listSearch;
    private String newsUrl = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey='Your key should be here'";

    private int feedLimit = 10;
    private String feedCachedUrl = "INVALIDATED";
    public static final String STATE_LIMIT = "feedLimit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listSearch = (ListView) findViewById(R.id.ListView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DownloadData downloadData = new DownloadData();
        downloadData.execute(newsUrl);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_LIMIT, feedLimit);
        super.onSaveInstanceState(outState);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";
           private ArrayList<FeedEntry> applications;
           public DownloadData() {
               this.applications = new ArrayList<>();
           }
           public ArrayList<FeedEntry> getApplications() {
               return applications;
           }
           FeedEntry currentRecord = null;

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                String response = streamToString(urlConnection.getInputStream());
                  parseResult(response);

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        String streamToString(InputStream stream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String data;
            String result = "";
            while ((data = bufferedReader.readLine()) != null) {
                result += data;
            }
            if (null != stream) {
                stream.close();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          FeedAdapter<FeedEntry> feedAdapter = new FeedAdapter<>(MainActivity.this, R.layout.list_record,
                  getApplications());
            listSearch.setAdapter(feedAdapter);
        }
       //parse json data
        private void parseResult(String result) {
            JSONObject response = null;
            try {
                response = new JSONObject(result);
                JSONArray articles = response.optJSONArray("articles");
                currentRecord = new FeedEntry();
                for (int i = 0; i < articles.length(); i++) {
                    currentRecord = new FeedEntry();
                    applications.add(currentRecord);
                    JSONObject article = articles.optJSONObject(i);
                   // String author = article.optString("author");
                   // Log.i("Author", author);
                    String title = article.optString("title");
                    //Log.i("Titles", title);
                    currentRecord.setTitle(title);
                    String description = article.optString("description");
                    //Log.i("Description", description);
                    currentRecord.setDescription(description);
                    String url = article.optString("url");
                    //Log.i("Url", url);
                    currentRecord.setUrl(url);
                    String urlToImage = article.optString("urlToImage");
                    //Log.i("Image Url", urlToImage);
                    currentRecord.setImageURL(urlToImage);
                    String publishedDate = article.optString("publishedAt");
                    //Log.i("Published Date", publishedDate);
                    currentRecord.setPublishedAt(publishedDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
