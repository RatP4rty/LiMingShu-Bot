package dog.aonehax.commands;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.TextChannel;
import dog.aonehax.GuildChannelStorage;
import dog.aonehax.PermissionStorage;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.MarkdownComponent;

public class ActiveCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User sender, Object[] arguments, Message message) {
        if (message instanceof TextChannelMessage) {
            TextChannel channel = ((TextChannelMessage) message).getChannel();
            Guild guild = channel.getGuild();
            if (PermissionStorage.hasPermission(sender, guild)) {
                GuildChannelStorage.set(guild, channel);
                message.reply(new MarkdownComponent("操作成功。"));
            } else {
                message.reply(new MarkdownComponent("无权操作。"));
            }
        }
    }
}
