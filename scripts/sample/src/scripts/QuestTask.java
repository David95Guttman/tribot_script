package scripts;

import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;

public class QuestTask extends Task{

    private final static WorldTile MIDDLE_POS_1 = new WorldTile(3076, 3126);
    private final static WorldTile MIDDLE_POS_2 = new WorldTile(3086, 3127);
    private final static int OBJECT_ID_INDOOR = 9716;
    private final static int OBJECT_ID_LADDER = 9726;
    private final static String NPC_NAME_GUIDER = "Quest Guide";

    private final static String CONFIRM_MESSAGE_1 ="Ah. Welcome, adventurer.";
    private final static String[] CHAT_MESSAGES_1 = {
            "Now that you have the quest list open,",
            "If you haven't started the quest",
            "It's very easy to find quest start points.",
            "The minimap in the top right corner of the screen",
            "The quests themselves can vary greatly",
            "There's not a lot more I can tell"
    };
    private final static String NOTICE_TEXT_1 = "Quests";
    private final static String NOTICE_TEXT_2 = "Quest journal";
    private final static String NOTICE_TEXT_3 = "Moving on";
    private final static String NOTICE_TEXT_4 = "Mining and Smithing";

    private boolean walkToGuider() {
        Tools.log("walk to guider");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToTile(MIDDLE_POS_1) && Tools.walkToTile(MIDDLE_POS_2) &&Tools.walkToObject(OBJECT_ID_INDOOR))
                return true;
            Tools.error("cannot walk to guider");
        }
        return false;
    }

    private boolean openDoor() {
        Tools.log("open door");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.openDoor(OBJECT_ID_INDOOR) && Tools.waitNotice(NOTICE_TEXT_1))
                return true;
            Tools.error("cannot open door");
        }
        return false;

    }

    private boolean talkFirst(){
        Tools.log("talk to first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToGuider(NPC_NAME_GUIDER)
                    && Tools.talkNpc(NPC_NAME_GUIDER)
                    && Tools.waitConfirm(CONFIRM_MESSAGE_1)
                    && Tools.waitNotice(NOTICE_TEXT_2)) {
                return true;
            }
            Tools.error("cannot talk first");
        }
        return false;
    }

    private boolean openQuest(){
        Tools.log("open quest");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 53) && Tools.waitNotice(NOTICE_TEXT_2)) {
                return true;
            }

            Tools.error("cannot open quest");
        }
        return false;
    }

    private boolean talkSecond() {
        Tools.log("talk to guide");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_1) && Tools.waitNotice(NOTICE_TEXT_3))
                return true;
            Tools.error("cannot talk second");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("goto next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_LADDER) && Tools.useObject(OBJECT_ID_LADDER) && Tools.waitNotice(NOTICE_TEXT_4))
                return true;
            Tools.error("cannot goto next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("QUEST TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider()) {
                    Tools.error("QUEST TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!openDoor()) {
                    Tools.error("QUEST TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!talkFirst()) {
                    Tools.error("QUEST TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!openQuest()) {
                    Tools.error("QUEST TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!talkSecond()) {
                    Tools.error("QUEST TASK FAILED... ");
                    return false;
                }
            case 5:
                if (!gotoNext()) {
                    Tools.error("QUEST TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("QUEST TASK EXCEPTION... ");
                return false;
        }
        Tools.log("QUEST TASK FINISH...");
        return true;
    }
}
