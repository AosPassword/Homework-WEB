package org.redrock.kebiao.send;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Send {
    public static String sendPost(String strUrl, String content) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            con.getOutputStream(), "UTF-8"
                    )
            );
            writer.write(content);
            writer.flush();
            writer.close();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}