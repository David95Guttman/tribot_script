package scripts;

import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Magic;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;

public class MagicTask extends Task {
    private static final String NPC_NAME_GUIDER = "Magic Instructor";
    private static final String NPC_NAME_CHICKEN = "Chicken";
    private static final String MAGIC_NAME_STRIKE = "Wind Strike";
    private static final String[] CHAT_MESSAGES_1 = {
            "Hello.",
            "Good day, newcomer.",
    };
    private static final String[] CHAT_MESSAGES_2 = {
            "Currently you can only cast",
            "gives you some",
    };
    private static final String[] CHAT_MESSAGES_3= {
             "Well, you're all finished here",
    };
    private static final String[] CHAT_MESSAGES_4= {
            "Were you planning to become an",
    };
    private static final String[] CHAT_MESSAGES_5= {
            "No, I'm not planning to do that",
            "When you get to the mainland",
            "a rucksack full of scrolls",
            "When you get ",
    };

    private static final WorldTile POSITION_1 = new WorldTile(3120, 3088);
    private static final WorldTile POSITION_2 = new WorldTile(3140, 3088);
    private static final String NOTICE_MESSAGE_1 = "Open up your final menu";
    private static final String NOTICE_MESSAGE_2 = "This is your magic interface";
    private static final String NOTICE_MESSAGE_3 = "Magic casting";
    private static final String NOTICE_MESSAGE_4 = "To the mainland";
//    private static final String NOTICE_MESSAGE_5 = "he'll teleport you to Lumbridge.";

    private static final String SELECT_OPTION_1 = "Yes";
    private static final String SELECT_OPTION_2 = "No, I'm not planning to do that";

    private boolean walkToGuider() {
        Tools.log("walk to guider");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToTile(POSITION_1) && Tools.walkToTile(POSITION_2) &&Tools.walkToGuider(NPC_NAME_GUIDER))
                return true;
            Tools.error("cannot walk to guider");
        }
        return false;
    }

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

    private boolean openMagic() {
        Tools.log("open magic menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 57) && Tools.waitNotice(NOTICE_MESSAGE_2))
                return true;
            Tools.error("cannot open magic menu");
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

    private boolean useMagic() {
        Tools.log("use magic");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Magic.ensureSpellSelected(MAGIC_NAME_STRIKE) && Tools.clickNpc(NPC_NAME_CHICKEN) && Tools.waitNotice(NOTICE_MESSAGE_4))
                return true;
            Tools.error("cannot use magic");
        }
        return false;
    }

    private boolean goMainLand() {
        Tools.log("go to main land");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER)
                    && Tools.continueChat(CHAT_MESSAGES_3)
                    && Tools.selectOption(SELECT_OPTION_1)
                    && Tools.continueChat(CHAT_MESSAGES_4)
                    && Tools.selectOption(SELECT_OPTION_2)
                    && Tools.continueChat(CHAT_MESSAGES_5)
//                    && Tools.waitNotice(NOTICE_MESSAGE_5)
            ) {
                return true;
            }
            Tools.error("cannot go to main land");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("MAGIC TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider()) {
                    Tools.error("MAGIC TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!talkFirst()) {
                    Tools.error("MAGIC TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!openMagic()) {
                    Tools.error("MAGIC TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!talkSecond()) {
                    Tools.error("MAGIC TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!useMagic()) {
                    Tools.error("MAGIC TASK FAILED... ");
                    return false;
                }
            case 5:
                if (!goMainLand()) {
                    Tools.error("MAGIC TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("MAGIC TASK EXCEPTION... ");
                return false;
        }
        Tools.log("MAGIC TASK FINISH...");
        Waiting.waitNormal(2000, 100);
        return true;
    }
}
