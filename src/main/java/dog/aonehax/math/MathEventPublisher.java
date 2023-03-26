package dog.aonehax.math;

import snw.jkook.entity.Guild;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import dog.aonehax.GuildChannelStorage;
import dog.aonehax.LiMingShu;
import dog.aonehax.ScoreboardStorage;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.scheduler.JKookRunnable;
import snw.jkook.scheduler.Task;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MathEventPublisher extends JKookRunnable implements Listener {
    private final Guild guild;

    public MathEventPublisher(Guild guild) {
        this.guild = guild;
    }

    @Override
    public void run() {
        MathEventContainer.question = new RandomMath();
        String msgid = Objects.requireNonNull(GuildChannelStorage.get(guild)).sendComponent(
                "答题时间！" +
                        // replace * using \* to disable Markdown syntax
                        MathEventContainer.question.getCalcString().replace("*", "\\*")
                        + "=?" + "\n" +
                        "抢先答对的奖励 10 积分！"
        );
        // Delete the question if there is nobody answered it, if there is too many question message, it is a spam!
        Task delQuestionTask = new JKookRunnable() {
            @Override
            public void run() {
                try {
                    // Delete message
                    LiMingShu.getInstance().getCore().getUnsafe().getTextChannelMessage(msgid).delete();
                } catch (Exception e) {
                    LiMingShu.getInstance().getLogger().warn("Unable to delete question", e);
                }
            }
        }.runTaskLater(LiMingShu.getInstance(), TimeUnit.MINUTES.toMillis(1));
        // The core logic of user input
        Listener l = new Listener() {
            @EventHandler
            public void onMessage(ChannelMessageEvent event) {
                if (event.getChannel() == GuildChannelStorage.get(guild)) {
                    if (event.getMessage().getComponent() instanceof MarkdownComponent) {
                        if (MathEventContainer.question == null) return; // no question now
                        if (Objects.equals(MathEventContainer.question.getResult(), event.getMessage().getComponent().toString())) {
                            ScoreboardStorage.getScoreboard(guild).calc(event.getMessage().getSender(), i -> i + 10);
                            MathEventContainer.question = null;
                            if (!delQuestionTask.isCancelled() && !delQuestionTask.isExecuted()) {
                                // if the answer is right, the question should not be deleted
                                // Why?
                                // If the message history is only the answer, no question, it is strange.
                                delQuestionTask.cancel();
                            }
                            event.getMessage().reply("正确！加 10 分！");
                        }
                    }
                }
            }
        };
        tempListener(l);
    }

    public void start() {
        runTaskTimer(LiMingShu.getInstance(), TimeUnit.MINUTES.toMillis(10), TimeUnit.MINUTES.toMillis(10));
        LiMingShu.getInstance().getCore().getEventManager().registerHandlers(LiMingShu.getInstance(), this);
    }

    private static void tempListener(Listener listener) {
        LiMingShu.getInstance().getCore().getEventManager().registerHandlers(LiMingShu.getInstance(), listener);
        new JKookRunnable() {
            @Override
            public void run() {
                LiMingShu.getInstance().getCore().getEventManager().unregisterHandlers(listener);
            }
        }.runTaskLater(LiMingShu.getInstance(), TimeUnit.MINUTES.toMillis(1L));
    }
}
