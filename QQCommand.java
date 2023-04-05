package dog.aonehax.commands;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class QQCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User sender, Object[] arguments, Message message) {
        try {
            URL url = new URL("https://zy.xywlapi.cc/qqapi?qq=1729779040"); // 要请求的网页地址
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // 设置请求方法为GET
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            message.sendToSource(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}