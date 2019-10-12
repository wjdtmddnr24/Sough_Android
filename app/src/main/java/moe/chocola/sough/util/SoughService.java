package moe.chocola.sough.util;

import moe.chocola.sough.data.Result;
import moe.chocola.sough.data.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SoughService {
    @GET("{user}")
    Call<User> getUser(@Path("user") String user);

    @FormUrlEncoded
    @POST("{user}")
    Call<Result> postWork(@Path("user") String user, @Field("title") String title, @Field("content") String content);

}
