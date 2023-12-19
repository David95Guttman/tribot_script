package scripts;

import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.LocalTile;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;

public class MiningTask extends  Task{
    private final static int OBJECT_ID_GATE = 9718;
    private final static WorldTile MIDDLE_POS_1 = new WorldTile(3078, 9515, 0);
    private final static WorldTile MIDDLE_POS_2 = new WorldTile(3080, 9508, 0);

    private final static String NPC_NAME_GUIDER = "Mining Instructor";
    private final static String OBJECT_NAME_FURNACE = "Furnace";
    private final static String OBJECT_NAME_ANVIL = "Anvil";
    private final static String OBJECT_NAME_TIN_ROCKS = "Tin rocks";
    private final static String OBJECT_NAME_COPPER_ROCKS = "Copper rocks";

    private final static String NOTICE_MESSAGES_1 = "It's quite simple really";
    private final static String NOTICE_MESSAGES_2 = "Now that you have some tin ore";
    private final static String NOTICE_MESSAGES_3 = "You now have some tin ore and some copper ore";
    private final static String NOTICE_MESSAGES_4 = "You've made a bronze bar!";
    private final static String NOTICE_MESSAGES_5 = "Smithing a dagger";
    private final static String NOTICE_MESSAGES_6 = "Moving on";
    private final static String NOTICE_MESSAGES_7 = "Combat";

    private final static String CONFIRM_MESSAGES_1 = "You manage to mine some tin";
    private final static String CONFIRM_MESSAGES_2 = "You manage to mine some copper";

    private final static String[] CHAT_MESSAGES_1 = {
            "Hi there. You must be new around here.",
            "You can call me",
            "Ok then, ",
            "Mining is very simple,",
            "The mining instructor"
    };
    private final static String[] CHAT_MESSAGES_2 = {
            "I have a bronze bar. What now?",
            "Now that you've got a bar,",
            "The mining instructor gives you",
    };

    private boolean walkToGuider() {
        Tools.log("walk to guider");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToTile(MIDDLE_POS_1) && Tools.walkToTile(MIDDLE_POS_2) && Tools.walkToGuider(NPC_NAME_GUIDER))
                return true;
            Tools.error("cannot walk to guider");
        }
        return false;
    }

    private boolean talkFirst(){
        Tools.log("talk first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_1) && Tools.waitNotice(NOTICE_MESSAGES_1))
                return true;
            Tools.error("cannot talk first");
        }
        return false;
    }

    private boolean mineTin() {
        Tools.log("mine tine rock");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_NAME_TIN_ROCKS) && Tools.clickObject(OBJECT_NAME_TIN_ROCKS) && Tools.waitConfirm(CONFIRM_MESSAGES_1) && Tools.waitNotice(NOTICE_MESSAGES_2))
                return true;
            Tools.error("cannot mine tin rock");
        }
        return false;
    }

    private boolean mineCopper() {
        Tools.log("mine copper rock");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_NAME_COPPER_ROCKS) && Tools.clickObject(OBJECT_NAME_COPPER_ROCKS) && Tools.waitConfirm(CONFIRM_MESSAGES_2) && Tools.waitNotice(NOTICE_MESSAGES_3))
                return true;
            Tools.error("cannot mine copper rock");
        }
        return false;
    }

    private boolean smeltOre() {
        Tools.log("smelt ore");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_NAME_FURNACE) && Tools.useObject(OBJECT_NAME_FURNACE) && Tools.waitNotice(NOTICE_MESSAGES_4))
                return true;
            Tools.error("cannot smelt ore");
        }
        return false;
    }

    private boolean talkSecond() {
        Tools.log("talk second");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToGuider(NPC_NAME_GUIDER) && Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_2) && Tools.waitNotice(NOTICE_MESSAGES_5))
                return true;
            Tools.error("cannot talk second");
        }
        return false;
    }

    private boolean smithAnvil() {
        Tools.log("smith anvil");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_NAME_ANVIL) && Tools.useObject(OBJECT_NAME_ANVIL) && Tools.clickWidget(312, 9) && Tools.waitNotice(NOTICE_MESSAGES_6))
                return true;
            Tools.error("cannot smith anvil");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("go to next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_GATE) && Tools.openDoor(OBJECT_ID_GATE) && Tools.waitNotice(NOTICE_MESSAGES_7)) {
                return true;
            }
            Tools.error("cannot go to next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("MINING TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!talkFirst()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!mineTin()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!mineCopper()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!smeltOre()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 5:
                if (!talkSecond()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 6:
                if (!smithAnvil()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
            case 7:
                if (!gotoNext()) {
                    Tools.error("MINING TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("MINING TASK EXCEPTION... ");
                return false;
        }
        Tools.log("MINING TASK FINISH...");
        return true;
    }
}
