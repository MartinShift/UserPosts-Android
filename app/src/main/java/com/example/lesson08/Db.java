package com.example.lesson08;

import com.example.lesson08.models.Post;
import com.example.lesson08.models.User;
import com.example.lesson08.retrofit.ApiService;
import com.example.lesson08.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Db {
    public static void getAllPosts(final PostFetchListener postListener) {
        ApiService apiService = RetrofitClient.getInstance().getApiService();
        Call<List<Post>> call = apiService.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postListener.onPostsFetched(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                postListener.onError(t);
            }
        });
    }

    public static void getUser(int id, final UserFetchListener userListener) {
        ApiService apiService = RetrofitClient.getInstance().getApiService();
        Call<User> call = apiService.getUser(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userListener.onUserFetched(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userListener.onError(t);
            }
        });
    }

    public interface PostFetchListener {
        void onPostsFetched(List<Post> posts);
        void onError(Throwable t);
    }

    public interface UserFetchListener {
        void onUserFetched(User user);
        void onError(Throwable t);
    }
}