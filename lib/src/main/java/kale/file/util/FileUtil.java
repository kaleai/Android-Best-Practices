package kale.file.util;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Jack Tony
 * @date 2015/7/20
 */
public class FileUtil {

    /**
     * @param array eg:R.array.country_names
     */
    public static String[] getStringArr(Context context, @ArrayRes int array) {
        return context.getResources().getStringArray(array);
    }

    /**
     * @return 从assets中获得字符串(用到了StringBuilder)
     */
    public String getAssetsFile(Context context, String fileName) {
        StringBuilder sBuilder = new StringBuilder();
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            InputStreamReader inputReader = new InputStreamReader(is);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                sBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sBuilder.toString();
    }

    /**
     * @param url 网页的url
     * @return html转换成的byte[]
     */
    public static byte[] getHtmlByteArray(final @NonNull String url) {
        URL htmlUrl;
        InputStream inStream = null;
        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;
            int responseCode = httpConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                inStream = httpConnection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream2Byte(inStream);
    }

    public static byte[] inputStream2Byte(InputStream is) {
        try{
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte bytes[] = bytestream.toByteArray();
            bytestream.close();
            return bytes;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
