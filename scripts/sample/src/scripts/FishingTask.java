package scripts;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.Inventory;

public class FishingTask extends Task{

    private static final String NPC_NAME_GUIDER = "Survival Expert";
    private static final String NPC_NAME_SPOT = "Fishing spot";

    private static final String ITEM_NAME_FISH = "Raw shrimps";
    private static final String ITEM_NAME_AXE = "Bronze axe";
    private static final String ITEM_NAME_NET = "Small fishing net";
    private static final String ITEM_NAME_TINDER = "Tinderbox";
    private static final String ITEM_NAME_LOGS = "Logs";
    private static final String ITEM_NAME_SHRIMP = "Shrimps";

    private static final int OBJECT_ID_DOOR = 9708;
    private static final String OBJECT_NAME_TREE = "Tree";
    private static final String OBJECT_NAME_FIRE = "Fire";

    private static final String[] CHAT_MESSAGE_1 = {
            "Hello there, newcomer.",
            "The first skill we're going to ",
            "The survival expert gives you",
    };

    private static final String[] CHAT_MESSAGE_2 = {
            "I've managed to catch some shrimp.",
            "Excellent work.",
            "The survival expert gives you",
    };
    private static final String CONFIRM_MESSAGE_1 = "You manage to catch some shrimp";
    private static final String CONFIRM_MESSAGE_2 = "You manage to cut some logs";
    private static final String CONFIRM_MESSAGE_3 = "You manage to cook some shrimp.";

    private static final String NOTICE_TEXT_1 = "To view the item";
    private static final String NOTICE_TEXT_2 = "This is your inventory";
    private static final String NOTICE_TEXT_3 = "You've gained some experience";
    private static final String NOTICE_TEXT_4 = "Skills and Experience";
    private static final String NOTICE_TEXT_5 = "Woodcutting";
    private static final String NOTICE_TEXT_6 = "Firemaking";
    private static final String NOTICE_TEXT_7 = "Cooking";
    private static final String NOTICE_TEXT_8 = "Moving on";
    private static final WorldTile[] POSITION_1 = {
            new WorldTile(3093, 3094),
            new WorldTile(3092, 3094),
            new WorldTile(3093, 3093),
            new WorldTile(3092, 3093),
    };


    private boolean talkFirst() {
        Tools.log("talk first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToGuider(NPC_NAME_GUIDER)
                    && Tools.talkNpc(NPC_NAME_GUIDER)
                    && Tools.continueChat(CHAT_MESSAGE_1)
                    && Tools.waitNotice(NOTICE_TEXT_1)) {
                return true;
            }

            Tools.error("cannot talk first");
        }
        return false;
    }

    private boolean openInventory() {
        Tools.log("open inventory menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 54)
                    && Tools.waitNotice(NOTICE_TEXT_2)) {
                Tools.log(Tools.getNotice());
                return true;
            }
            Tools.error("cannot open inventory menu");
        }
        return false;
    }

    private boolean catchShrimp() {
        int i;
        Tools.log("catch the shrimp");
        for (i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToGuider(NPC_NAME_SPOT)
                    && Tools.takeItem(ITEM_NAME_NET)
                    && Tools.clickNpc(NPC_NAME_SPOT)
                    && Tools.waitConfirm(CONFIRM_MESSAGE_1)
                    && Tools.waitNotice(NOTICE_TEXT_3))
                return true;
            Tools.error("cannot catch the shrimp");
        }
        return false;
    }

    private boolean openSkillMenu() {
        Tools.log("open skill menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 52) && Tools.waitNotice(NOTICE_TEXT_4))
                return true;
            Tools.error("cannot open skill menu");
        }
        return false;
    }

    private boolean talkSecond() {
        Tools.log("talk second");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGE_2) && Tools.waitNotice(NOTICE_TEXT_5))
                return true;
            Tools.error("cannot talk second");
        }
        return false;
    }

    private boolean chopDownTree() {
        Tools.log("chop down tree");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickObject(OBJECT_NAME_TREE) && Tools.waitConfirm(CONFIRM_MESSAGE_2) && Tools.waitNotice(NOTICE_TEXT_6))
                return true;
            Tools.error("cannot chop down tree");
        }
        return false;
    }

    private boolean makeFire() {
        Tools.log("make fire");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (!Inventory.contains(ITEM_NAME_LOGS))
                Tools.clickObject(OBJECT_NAME_TREE);
            if (Tools.walkToTile(POSITION_1[Tools.getRandom(0, 2)]) && Tools.clickWidget(164, 54) && Tools.takeItem(ITEM_NAME_TINDER) && Tools.takeItem(ITEM_NAME_LOGS) && Tools.waitNotice(NOTICE_TEXT_7))
                return true;
            Tools.error("cannot make fire");
        }
        return false;
    }

    private boolean cookFish() {
        int i;
        Tools.log("cook fish");
        for (i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.takeItem(ITEM_NAME_SHRIMP)
                    && Tools.clickObject(OBJECT_NAME_FIRE)
                    && Tools.waitConfirm(CONFIRM_MESSAGE_3)
                    && Tools.waitNotice(NOTICE_TEXT_8))
                return true;
            Tools.error("cannot cook fish");
        }
        return false;
    }

    private boolean gotoNext() {
        int i;
        Tools.log("go to next");
        for (i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_DOOR)
                    && Tools.openDoor(OBJECT_ID_DOOR))
                return true;
            Tools.error("cannot go to next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("FISHING TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!talkFirst()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!openInventory()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!catchShrimp()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!openSkillMenu()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!talkSecond()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 5:
                if (!chopDownTree()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 6:
                if (!makeFire()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 7:
                if (!cookFish()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
            case 8:
                if (!gotoNext()) {
                    Tools.error("FISHING TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("FISHING TASK EXCEPTION... ");
                return false;
        }
        Waiting.waitNormal(2000, 10);
        Tools.log("FISHING TASK FINISH...");
        return true;
    }
}
