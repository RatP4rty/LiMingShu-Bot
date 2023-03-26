package org.example;

import snw.jkook.command.JKookCommand;
import snw.jkook.plugin.BasePlugin;


public class LiMingShu extends BasePlugin {
    private static LiMingShu INSTANCE;

    public static LiMingShu getInstance() {
        return INSTANCE;
    }

    String phone_number = "18910800763";
    String media = "https://www.bilibili.com/video/BV1i44y197oS";

    @Override
    public void onEnable() {


        getLogger().info("李铭舒机器人 By SunriseMC Team");

        new JKookCommand("h")
                .executesUser((sender, argyments, message) -> {
                    message.reply("/get_phone 获取李铭舒母亲手机号" +
                            "/get_media 获取李铭舒追悼会视频" +
                            "/esu 短信压测李铭舒母亲" +
                            "/stop 停止压测");
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
                    message.reply("已经开始压测");
                })
                .register(this);

        new JKookCommand("stop")
                .executesUser((sender, argyments, message) -> {
                    message.reply("停止压测");
                })
                .register(this);
    }
}