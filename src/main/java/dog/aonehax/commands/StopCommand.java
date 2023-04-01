package dog.aonehax.commands;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.component.MarkdownComponent;
import snw.jkook.message.component.card.*;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import java.io.IOException;

public class StopCommand implements UserCommandExecutor {
    @Override
    public void onCommand(User sender, Object[] arguments, Message message) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            process.waitFor();
            System.out.println("Chrome has been stopped.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MultipleCardComponent card = new CardBuilder()
                .setTheme(Theme.WARNING)
                .setSize(Size.LG)
                .addModule(new HeaderModule(new PlainTextElement("压测系统", false)))
                .addModule(new SectionModule(new PlainTextElement("结束压测"),null, null))
                .newCard()
                .build();
        message.sendToSource(card);
    }
}
