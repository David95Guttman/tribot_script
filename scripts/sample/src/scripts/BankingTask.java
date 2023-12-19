package scripts;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;

public class BankingTask extends Task {
    private static final WorldTile POSITION_1 = new WorldTile(3117, 3114);
    private static final WorldTile POSITION_2 = new WorldTile(3123, 3122);

    private static final String NPC_NAME_GUIDER = "Banker";
    private static final String OBJECT_NAME_BOOTH = "Poll booth";
    private static final String[] CHAT_MESSAGES_1 = {
            "Poll booths are found in towns across the world",
            "Voting is open to",
            "A flag appears on the booth"
    };

    private static final String NOTICE_MESSAGE_1 = "This is your bank.";
    private static final String NOTICE_MESSAGE_2 = "Moving on";
    private static final String NOTICE_MESSAGE_3 = "The guide here will tell you";

    private static final int OBJECT_ID_DOOR = 9721;

    private boolean walkToGuider() {
        Tools.log("walk to guider");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToTile(POSITION_1) && Tools.walkToTile(POSITION_2) && Tools.walkToGuider(NPC_NAME_GUIDER))
                return true;
            Tools.error("cannot walk to guider");
        }
        return false;
    }

    private boolean talkFirst() {
        Tools.log("talk first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
//            if (Tools.clickNpc(NPC_NAME_GUIDER) && Tools.closeBankWidget() && Tools.waitNotice(NOTICE_MESSAGE_1))
            if (Tools.clickNpc(NPC_NAME_GUIDER) && Tools.clickWidget(289, 7, 8) && Tools.clickWidget(12, 2, 11) && Tools.waitNotice(NOTICE_MESSAGE_1))
                return true;
            Tools.error("cannot talk first");
        }
        return false;
    }

    private boolean useBooth() {
        Tools.log("use booth");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_NAME_BOOTH) && Tools.useObject(OBJECT_NAME_BOOTH) && Tools.continueChat(CHAT_MESSAGES_1) && Tools.waitNotice(NOTICE_MESSAGE_2)) {
                return true;
            }
        }
        Tools.error("cannot use booth");
        return false;
    }

    private boolean gotoNext() {
        Tools.log("go to next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_DOOR) && Tools.openDoor(OBJECT_ID_DOOR) && Tools.waitNotice(NOTICE_MESSAGE_3)) {
                return true;
            }
            Tools.error("cannot go to next");
        }
        return false;
    }

    @Override
    boolean execute(int subTaskId) {
        Tools.log("BANK TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider()) {
                    Tools.error("BANK TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!talkFirst()) {
                    Tools.error("BANK TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!useBooth()) {
                    Tools.error("BANK TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!gotoNext()) {
                    Tools.error("BANK TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("BANK TASK EXCEPTION... ");
                return false;
        }
        Tools.log("BANK TASK FINISH...");
        return true;
    }

}
