package com.example.harsh.topgooglenews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class FeedAdapter<T extends FeedEntry> extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<T> applications;

    public FeedAdapter(Context context, int resource, List<T> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            Log.d(TAG, "getView: called with null convertView");
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: provided a convertView");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.tvImage);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);


        T currentApp = applications.get(position);

        viewHolder.tvTitle.setText(currentApp.getTitle());
        viewHolder.tvDate.setText(currentApp.getPublishedAt());

        new MainActivity.ImageLoadTask( currentApp.getImageURL(), tvImage ).execute();

        viewHolder.tvDescription.setText(currentApp.getDescription());


        return convertView;
    }

    private class ViewHolder {
        final TextView tvTitle;
        final TextView tvDate;
        final ImageView tvImage;
        final TextView tvDescription;



        ViewHolder(View v) {
            this.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            this.tvDate = (TextView) v.findViewById(R.id.tvDate);
            this.tvImage = (ImageView) v.findViewById(R.id.tvImage);
            this.tvDescription = (TextView) v.findViewById(R.id.tvDescription);

        }

    }
}





















