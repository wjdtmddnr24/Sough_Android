package moe.chocola.sough.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    private static final String url = "http://chocola.moe:3000/";
    static public Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit != null) return retrofit;
        else
            return retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static SoughService getSoughService() {
        return getRetrofit().create(SoughService.class);
    }

    public static String getDecompressString(String compressed) throws IOException {

        byte[] byteContent = Base64.decode(compressed, 0);
        final LzmaInputStream compressedIn = new LzmaInputStream(new BufferedInputStream(new ByteArrayInputStream(byteContent)), new Decoder());
        final OutputStream decompressedOut = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = compressedIn.read(buffer);
        while (len != -1) {
            decompressedOut.write(buffer, 0, len);
            len = compressedIn.read(buffer);
        }
        String decompressedResult = decompressedOut.toString();
        compressedIn.close();
        decompressedOut.close();
        return decompressedResult;
    }

    public static String getUserWorkspaceName(Context context) {
        String result = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("sough", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user", "");
    }

    public static void setUserWorkspaceName(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("sough", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", name);
        editor.commit();
    }
}
