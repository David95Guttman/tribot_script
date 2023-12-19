package scripts;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Waiting;

public class AccountTask extends Task{
    private static final int OBJECT_ID_DOOR = 9722;
    private static final String NPC_NAME_GUIDER = "Account Guide";
    private static final String[] CHAT_MESSAGES_1 = {
            "Hello. Who are you?",
            "I'm the Account Guide.",
            "What's an account?",
            "Your character IS your account!",
            "There's various things you can do"
    };
    private static final String[] CHAT_MESSAGES_2 = {
            "As you can see,",
            "First, you have membership.",
            "What's a world?",
            "A world is what you play",
            "You are currently on a free world",
            "Bonds are another thing",
            "But why would I use a Bond instead of",
            "Unlike membership, Bonds can",
            "So if I used my gold to buy a Bond",
            "Exactly!",
            "Next up, you have your inbox",
            "The name changer allows",
            "Awesome. Is there anything else",
            "Your account is very valuable",
            "You'll find more information",
            "Anyway, that's everything I have to tell you"
    };
    private static final String NOTICE_MESSAGE_1 = "Click on the flashing icon";
    private static final String NOTICE_MESSAGE_2 = "This is your Account Management";
    private static final String NOTICE_MESSAGE_3 = "Moving on";
    private static final String NOTICE_MESSAGE_4 = "Prayer";

    private boolean talkFirst() {
        Tools.log("talk first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_1) && Tools.waitNotice(NOTICE_MESSAGE_1))
                return true;
            Tools.error("cannot talk first");
        }
        return false;
    }

    private boolean openAccount() {
        Tools.log("open account menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 44) && Tools.waitNotice(NOTICE_MESSAGE_2))
                return true;
            Tools.error("cannot open account menu");
        }
        return false;
    }

    private boolean talkSecond() {
        Tools.log("talk second");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_2) && Tools.waitNotice(NOTICE_MESSAGE_3))
                return true;
            Tools.error("cannot talk second");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("go to next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.openDoor(OBJECT_ID_DOOR) && Tools.waitNotice(NOTICE_MESSAGE_4))
                return true;
            Tools.error("cannot go to next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("ACCOUNT TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!talkFirst()) {
                    Tools.error("ACCOUNT TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!openAccount()) {
                    Tools.error("ACCOUNT TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!talkSecond()) {
                    Tools.error("ACCOUNT TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!gotoNext()) {
                    Tools.error("ACCOUNT TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("ACCOUNT TASK EXCEPTION... ");
                return false;
        }
        Tools.log("ACCOUNT TASK FINISH...");
        return true;
    }
}
