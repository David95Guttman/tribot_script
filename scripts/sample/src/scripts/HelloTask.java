package scripts;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Waiting;

public class HelloTask extends Task {

    private static final String NPC_NAME_GUIDER =  "Gielinor Guide";
    private static final int OBJECT_ID_DOOR = 9398;
    private static final String [] CHAT_MESSAGE_1 = {
            "Greetings!",
            "You have already learned",
            "You will find many inhabitants",
            "Before we get going",
            "To answer, simply click"
    };
    private static final String [] CHAT_MESSAGE_2 = {
            "I am an experienced player",
            "Wonderful! Thank you.",
            "Now then, let's start by",
    };
    private static final String [] CHAT_MESSAGE_3 = {
            "Looks like you're making good progress!",
            "Anyway, I'd say it's time for you to go",
    };

    private static final String NOTICE_MESSAGE_1 = "This will display your settings menu";
    private static final String NOTICE_MESSAGE_2 = "you can now see a variety of game settings";
    private static final String SELECT_OPTION_EXPERIENCED = "I am an experienced player.";

    private boolean startGuide() {
        Tools.log("start guide");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER)
                    && Tools.continueChat(CHAT_MESSAGE_1)
                    && Tools.selectOption(SELECT_OPTION_EXPERIENCED)
                    && Tools.continueChat(CHAT_MESSAGE_2)
                    && Tools.waitNotice(NOTICE_MESSAGE_1))
                return true;
        }
        return false;
    }

    private boolean openSettings() {
        Tools.log("open settings");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 46) && Tools.waitNotice(NOTICE_MESSAGE_2))
                return true;
            Tools.error("cannot open settings.");
        }
        return false;
    }

    private boolean finishGuide() {
        Tools.log("finish guide");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGE_3))
                return true;
            Tools.error("cannot finish hello.");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("open door");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.openDoor(OBJECT_ID_DOOR))
                return true;
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("HELLO TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!startGuide()) {
                    Tools.error("HELLO TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!openSettings()) {
                    Tools.error("HELLO TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!finishGuide()) {
                    Tools.error("HELLO TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!gotoNext()) {
                    Tools.error("HELLO TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("HELLO TASK EXCEPTION... ");
                return false;
        }
        Waiting.waitNormal(2000, 10);
        Tools.log("HELLO TASK FINISH... ");
        return true;
    }
}
