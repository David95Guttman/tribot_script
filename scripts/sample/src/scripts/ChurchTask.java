package scripts;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;

public class ChurchTask extends Task {
    private static final int OBJECT_ID_DOOR = 9723;
    private static final String NPC_NAME_GUIDER = "Brother Brace";
    private static final String[] CHAT_MESSAGES_1 = {
            "Good day, brother",
            "Hello, I'm Brother Brace",
    };
    private static final String[] CHAT_MESSAGES_2 = {
            "This is your Prayer list",
            "Active prayers will drain your",
            "As you noticed, most enemies will drop",
            "I'm also the community officer",
    };
    private static final String[] CHAT_MESSAGES_3 = {
            "Right. Now I'll tell you a little about each list",
            "You remove people from the lists",
            "your friends list shows the online",
            "Okay, thanks",
    };
    private static final String NOTICE_MESSAGE_1 = "Click on the flashing icon";
    private static final String NOTICE_MESSAGE_2 = "Talk with Brother Brace";
    private static final String NOTICE_MESSAGE_3 = "You should now see another";
    private static final String NOTICE_MESSAGE_4 = "These two lists can be very helpful";
    private static final String NOTICE_MESSAGE_5 = "Your final instructor";
    private static final String NOTICE_MESSAGE_6 = "Follow the path to the wizard";
    private static final WorldTile POSITION_1 = new WorldTile(3130, 3106);
    private static final WorldTile POSITION_2 = new WorldTile(3126, 3107);

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

    private boolean openPrayer() {
        Tools.log("open prayer menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 56) && Tools.waitNotice(NOTICE_MESSAGE_2))
                return true;
            Tools.error("cannot open prayer menu");
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

    private boolean openFriend() {
        Tools.log("open friend menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 45) && Tools.waitNotice(NOTICE_MESSAGE_4))
                return true;
            Tools.error("cannot open friend menu");
        }
        return false;
    }

    private boolean talkThird() {
        Tools.log("talk third");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_3) && Tools.waitNotice(NOTICE_MESSAGE_5))
                return true;
            Tools.error("cannot talk third");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("go to next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_DOOR) && Tools.openDoor(OBJECT_ID_DOOR) && Tools.waitNotice(NOTICE_MESSAGE_6))
                return true;
            Tools.error("cannot go to next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("CHURCH TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!talkFirst()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!openPrayer()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!talkSecond()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!openFriend()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
            case 5:
                if (!talkThird()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
            case 6:
                if (!gotoNext()) {
                    Tools.error("CHURCH TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("CHURCH TASK EXCEPTION... ");
                return false;
        }
        Tools.log("CHURCH TASK FINISH...");
        return true;
    }
}
