package scripts;

import org.tribot.script.sdk.Waiting;

public class CookingTask extends Task{

    private static final int OBJECT_ID_INDOOR = 9709;
    private static final int OBJECT_ID_OUTDOOR = 9710;
    private static final String OBJECT_NAME_RANGE = "Range";

    private static final String NPC_NAME_GUIDER = "Master Chef";

    private static final String ITEM_NAME_FLOUR = "Pot of flour";
    private static final String ITEM_NAME_WATER = "Bucket of water";
    private static final String ITEM_NAME_DOUGH = "Bread dough";
    private static final String ITEM_NAME_BREAD = "Bread";

    private static final String [] CHAT_MESSAGES_1 = {
            "Ah! Welcome, newcomer.",
            "I already know how to cook.",
            "Hahahahahaha!",
            "The master chef gives you some"
    };

    private static final String CONFIRM_MESSAGE_1 = "You make some dough";
    private static final String CONFIRM_MESSAGE_2 = "You manage to bake some bread";

    private static final String NOTICE_TEXT_1 = "Cooking";
    private static final String NOTICE_TEXT_2 = "Making dough";
    private static final String NOTICE_TEXT_3 = "Cooking dough";
    private static final String NOTICE_TEXT_4 = "Moving on";
    private static final String NOTICE_TEXT_5 = "Fancy a run";

    boolean walkToGuider() {
        Tools.log("walk to guider");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_INDOOR) && Tools.openDoor(OBJECT_ID_INDOOR) && Tools.waitNotice(NOTICE_TEXT_1))
                return true;
            Tools.error("cannot walk to guider");
        }
        return false;
    }

    boolean talkFirst() {
        Tools.log("talk first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_1) && Tools.waitNotice(NOTICE_TEXT_2))
                return true;
            Tools.error("cannot talk first");
        }
        return false;
    }

    boolean makeDough() {
        Tools.log("make dough");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.takeItem(ITEM_NAME_FLOUR)
                    && Tools.takeItem(ITEM_NAME_WATER)
                    && Tools.waitConfirm(CONFIRM_MESSAGE_1)
                    && Tools.waitNotice(NOTICE_TEXT_3))
                return true;
            Tools.error("cannot make dough");
        }
        return false;
    }

    boolean cookDough() {
        Tools.log("cook dough");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.useObject(OBJECT_NAME_RANGE) && Tools.waitConfirm(CONFIRM_MESSAGE_2) && Tools.waitNotice(NOTICE_TEXT_4))
                return true;
            Tools.error("cannot cook dough");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("go to next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_OUTDOOR) && Tools.openDoor(OBJECT_ID_OUTDOOR) && Tools.waitNotice(NOTICE_TEXT_5))
                return true;
            Tools.error("cannot go to next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("COOKING TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider()) {
                    Tools.error("COOKING TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!talkFirst()) {
                    Tools.error("COOKING TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!makeDough()) {
                    Tools.error("COOKING TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!cookDough()) {
                    Tools.error("COOKING TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!gotoNext()) {
                    Tools.error("COOKING TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("COOKING TASK EXCEPTION... ");
                return false;
        }
        Tools.log("COOKING TASK FINISH...");
        return true;
    }
}
