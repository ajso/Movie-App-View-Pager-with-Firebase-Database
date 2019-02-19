package net.icraftsystems.app.auth.android.firebaseviewpager.Interface;

import net.icraftsystems.app.auth.android.firebaseviewpager.model.Movie;

import java.util.List;

public interface IFirebaseLoadDone {

    //an interface to load movie from the firebase Database
    void onFirebaseLoadSuccess(List<Movie> movieList);
    void onFirebaseLoadFailed(String message);
}
