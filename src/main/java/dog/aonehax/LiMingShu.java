package dog.aonehax;

import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;
import java.awt.Desktop;
import java.net.URI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LiMingShu extends BasePlugin {
    @Override
    public void onEnable() {
        getLogger().info(getConfig().getString("msg")); /* 2 */
        getConfig().set("msg", "fine"); /* 3 */

        getLogger().info("李铭舒Bot-1.0");
        String phone_number = "18910800763";
        String media = "www.bilibili.com/video/BV1i44y197oS";
        String help = "/get_phone 获取李铭舒母亲手机号 " + "/get_media 获取李铭舒追悼会视频 " + "/esu 短信压测李铭舒母亲 " + "/停止 停止压测" +"/人品 查看人品 " + "/李铭舒人品 查看李铭舒人品 " + "/骰子 摇骰子 " + "/舔狗1号 获取舔狗信息 ";
        String esu = "已经开始压测";
        String stop = "已停止压测";
        String tiangou = "实名:王催 " +
                "手机号:13269158216" +
                "QQ:344903013";


        getLogger().info("李铭舒机器人 By SunriseMC Team");

        new JKookCommand("帮助")
                .executesUser((sender, argyments, message) -> {
                    message.reply(help);
                })
                .register(this);

        new JKookCommand("get_phone")
                .executesUser((sender, argyments, message) -> {
                    message.reply(phone_number);
                })
                .register(this);

        new JKookCommand("get_media")
                .executesUser((sender, argyments, message) -> {
                    message.reply(media);
                })
                .register(this);

        new JKookCommand("esu")
                .executesUser((sender, argyments, message) -> {
                    String url = "https://tf0719.cn/dx/index.php?hm=18910800763&ok=";
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    message.reply(esu);
                })
                .register(this);

        new JKookCommand("停止")
                .executesUser((sender, argyments, message) -> {


                    message.reply(stop);
                })
                .register(this);

        new JKookCommand("人品")
                .executesUser((sender, argyments, message) -> {
                    int rp = (int) (Math.random() * 100);
                    message.reply(String.valueOf(rp));
                })
                .register(this);

        new JKookCommand("李铭舒人品")
                .executesUser((sender, argyments, message) -> {
                    int lmsrp = (int) (Math.random() * -1000);
                    message.reply(String.valueOf(lmsrp));
                })
                .register(this);

        new JKookCommand("骰子")
                .executesUser((sender, argyments, message) -> {
                    int sz = (int) (Math.random() * 6);
                    message.reply(String.valueOf(sz));
                })
                .register(this);

        new JKookCommand("舔狗1号")
                .executesUser((sender, argyments, message) -> {


                    message.reply(tiangou);
                })
                .register(this);

    }

}
