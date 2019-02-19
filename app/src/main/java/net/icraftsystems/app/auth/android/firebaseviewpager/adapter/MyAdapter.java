package net.icraftsystems.app.auth.android.firebaseviewpager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.icraftsystems.app.auth.android.firebaseviewpager.R;
import net.icraftsystems.app.auth.android.firebaseviewpager.model.Movie;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    Context context;
    List<Movie> movieList;
    LayoutInflater inflater;

    public MyAdapter() {
    }

    public MyAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflate view
        View view = inflater.inflate(R.layout.view_pager_item,container,false);

        //view
        ImageView movie_image = view.findViewById(R.id.movie_image);
        TextView movie_title = view.findViewById(R.id.movie_title);
        TextView movie_description = view.findViewById(R.id.movie_description);
        FloatingActionButton btn_fav = view.findViewById(R.id.btn_fav);

        //set date
        Picasso.get().load(movieList.get(position).getImage()).into(movie_image);
        movie_title.setText(movieList.get(position).getName());
        movie_description.setText(movieList.get(position).getDescription());

        //event
        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Favourite Clicked", Toast.LENGTH_LONG).show();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Movie Clicked", Toast.LENGTH_LONG).show();
            }
        });

        container.addView(view);

        return view;

    }
}
