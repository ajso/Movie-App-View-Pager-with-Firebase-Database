package net.icraftsystems.app.auth.android.firebaseviewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.icraftsystems.app.auth.android.firebaseviewpager.Interface.IFirebaseLoadDone;
import net.icraftsystems.app.auth.android.firebaseviewpager.adapter.MyAdapter;
import net.icraftsystems.app.auth.android.firebaseviewpager.model.Movie;
import net.icraftsystems.app.auth.android.firebaseviewpager.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IFirebaseLoadDone, ValueEventListener {

    ViewPager viewPager;
    MyAdapter adapter;

    DatabaseReference movies;
    IFirebaseLoadDone iFirebaseLoadDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init FireBase
        movies = FirebaseDatabase.getInstance().getReference("Movies"); //'Movies' is our database name

        //init event
        iFirebaseLoadDone = this;

        loadMovie();

        viewPager = findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());

    }

    private void loadMovie() {
        /*
        This is a different approach but also correct.
        movies.addListenerForSingleValueEvent(new ValueEventListener() {

            List<Movie> movieList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot movieSnapShot:dataSnapshot.getChildren())
                movieList.add(movieSnapShot.getValue(Movie.class));

                iFirebaseLoadDone.onFirebaseLoadSuccess(movieList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });*/
        movies.addValueEventListener(this);
    }

    @Override
    public void onFirebaseLoadSuccess(List<Movie> movieList) {
            adapter = new MyAdapter(this,movieList);
            viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        List<Movie> movieList = new ArrayList<>();
        for (DataSnapshot movieSnapShot:dataSnapshot.getChildren())
            movieList.add(movieSnapShot.getValue(Movie.class));

        iFirebaseLoadDone.onFirebaseLoadSuccess(movieList);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
    }

    @Override
    protected void onDestroy() {
        movies.removeEventListener(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        movies.addValueEventListener(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        movies.removeEventListener(this);
        super.onStop();
    }
}
