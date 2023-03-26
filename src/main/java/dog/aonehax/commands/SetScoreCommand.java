package dog.aonehax.commands;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import dog.aonehax.PermissionStorage;
import dog.aonehax.Scoreboard;
import dog.aonehax.ScoreboardStorage;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.MarkdownComponent;

public class SetScoreCommand implements UserCommandExecutor {

    // expected:
    // arguments[0] = User
    // arguments[1] = int
    @Override
    public void onCommand(User sender, Object[] arguments, Message message) {
        if (message instanceof TextChannelMessage) {
            TextChannelMessage txtmsg = (TextChannelMessage) message;
            User target = (User) arguments[0];
            int newScore = (int) arguments[1];
            Guild guild = txtmsg.getChannel().getGuild();
            if (PermissionStorage.hasPermission(sender, guild)) {
                Scoreboard scoreboard = ScoreboardStorage.getScoreboard(guild);
                scoreboard.setScore(target, newScore);
                message.reply(new MarkdownComponent("操作成功。"));
            } else {
                message.reply(new MarkdownComponent("无权操作。"));
            }
        }
    }
}
