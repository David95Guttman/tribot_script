package scripts;

import org.tribot.script.sdk.ChatScreen;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.GameTab;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.types.WorldTile;

public class CombatTask extends Task{
    private static final int OBJECT_ID_GATE = 9720;
    private static final int OBJECT_ID_LADDER = 9727;
    private static final String NPC_NAME_GUIDER = "Combat Instructor";
    private static final String NPC_NAME_RAT = "Giant rat";
    private static final String ITEM_NAME_DAGGER = "Bronze dagger";
    private static final String ITEM_NAME_SWORD = "Bronze sword";
    private static final String ITEM_NAME_SHIELD = "Wooden shield";
    private static final String ITEM_NAME_BOW = "Shortbow";
    private static final String ITEM_NAME_ARROW = "Bronze arrow";

    private static final String[] CHAT_MESSAGES_1 = {
            "Hi! My name's",
            "Do I look like I care?",
            "I am Vannaka, the greatest swordsman alive,",
    };

    private static final String[] CHAT_MESSAGES_2 = {
            "very good, but that little butter knife",
            "The combat instructor gives you",
    };
    private static final String[] CHAT_MESSAGES_3 = {
            "I did it!",
            "I saw. You seem better",
            "Next up we have ranged combat",
            "The combat instructor gives you",
    };
    private static final String NOTICE_MESSAGE_1 = "Equipping items";
    private static final String NOTICE_MESSAGE_2 = "Equipment stats";
    private static final String NOTICE_MESSAGE_3 = "You're now holding your dagger";
    private static final String NOTICE_MESSAGE_4 = "Unequipping items";
    private static final String NOTICE_MESSAGE_5 = "Click on the flashing crossed swords icon";
    private static final String NOTICE_MESSAGE_6 = "This is your combat interface";
    private static final String NOTICE_MESSAGE_7 = "Attacking";
    private static final String NOTICE_MESSAGE_12 = "Sit back and watch";
    private static final String NOTICE_MESSAGE_8 = "Well done";
    private static final String NOTICE_MESSAGE_9 = "Rat ranging";
    private static final String NOTICE_MESSAGE_10 = "Moving on";
    private static final String NOTICE_MESSAGE_11 = "Banking";

    private static final WorldTile POSITION_1 = new WorldTile(3108, 9512);

    private boolean walkToGuider() {
        Tools.log("talk first");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToGuider(NPC_NAME_GUIDER))
                return true;
            Tools.error("cannot talk first");
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

    private boolean equipDagger() {
        Tools.log("equip dagger");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
//            if (Tools.clickWidget(164, 55) && Tools.clickWidget(387, 1)  && Tools.clickWidget(85, 0, 8) && Tools.waitNotice(NOTICE_MESSAGE_2) && Tools.clickWidget(84, 3, 11) && Tools.waitNotice(NOTICE_MESSAGE_3) )
            if (Tools.clickWidget(164, 55) && Tools.clickWidget(387, 1) && Tools.equipItem(ITEM_NAME_DAGGER) && Tools.waitNotice(NOTICE_MESSAGE_2) && Tools.clickWidget(84, 3, 11) && Tools.waitNotice(NOTICE_MESSAGE_3) )
                return true;
            Tools.error("cannot equip dagger");
        }
        return false;
    }

    private boolean talkSecond() {
        Tools.log("talk second");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_2) && Tools.waitNotice(NOTICE_MESSAGE_4))
                return true;
            Tools.error("cannot talk second");
        }
        return false;
    }

    private boolean equipArms() {
        Tools.log("equip arms");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 55) && Tools.clickWidget(387, 1)   && Tools.clickWidget(84, 13) && Tools.equipItem(ITEM_NAME_SWORD) && Tools.equipItem(ITEM_NAME_SHIELD)  && (Tools.clickWidget(84, 3, 11) || true) && Tools.waitNotice(NOTICE_MESSAGE_5))
//                if (Tools.clickWidget(164, 55) && Tools.clickWidget(387, 1) && Tools.clickWidget(85, 0, 8) && Tools.clickWidget(85, 0, 10)  && (Tools.clickWidget(84, 3, 11) || true) && Tools.waitNotice(NOTICE_MESSAGE_5))
                return true;
            Tools.error("cannot equip arms");
        }
        return false;
    }

    private boolean openCombat() {
        Tools.log("open combat menu");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 51) && Tools.waitNotice(NOTICE_MESSAGE_6))
                return true;
            Tools.error("cannot open combat menu");
        }
        return false;
    }

    private boolean openGate() {
        Tools.log("open gate");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_GATE) && Tools.openDoor(OBJECT_ID_GATE) && Tools.waitNotice(NOTICE_MESSAGE_7))
                return true;
            Tools.error("cannot open gate");
        }
        return false;
    }
    private boolean attackRat() {
        Tools.log("attack rat");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickAnimal(NPC_NAME_RAT) && Tools.waitNotice(NOTICE_MESSAGE_12) && Tools.waitNotice(NOTICE_MESSAGE_8))
                return true;
            Tools.error("cannot attack rat");
        }
        return false;
    }

    private boolean outGate() {
        Tools.log("talk third");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_GATE) && Tools.openDoor(OBJECT_ID_GATE))
                return true;
            Tools.error("cannot talk third");
        }
        return false;
    }

    private boolean talkThird() {
        Tools.log("talk third");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToGuider(NPC_NAME_GUIDER) && Tools.talkNpc(NPC_NAME_GUIDER) && Tools.continueChat(CHAT_MESSAGES_3) && Tools.waitNotice(NOTICE_MESSAGE_9))
                return true;
            Tools.error("cannot talk third");
        }
        return false;
    }

    private boolean equipBow() {
        Tools.log("equip bow");
        if (Equipment.contains(ITEM_NAME_BOW) && Equipment.contains(ITEM_NAME_ARROW))
            return true;
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.clickWidget(164, 55) && Tools.clickWidget(387, 1)   && Tools.clickWidget(84, 13) && Tools.clickWidget(84, 15) && Tools.equipItem(ITEM_NAME_BOW) && Tools.equipItem(ITEM_NAME_ARROW) && Tools.clickWidget(84, 3, 11))
                return true;
            Tools.error("cannot equip bow");
        }
        return false;
    }

    private boolean shootRat() {
        Tools.log("shoot rat");

        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            ChatScreen.clickContinue();
            if (Tools.walkToTile(POSITION_1) && Tools.clickAnimal(NPC_NAME_RAT) && Tools.waitNotice(NOTICE_MESSAGE_10))
                return true;
            Tools.error("cannot shoot rat");
        }
        return false;
    }

    private boolean gotoNext() {
        Tools.log("go to next");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Tools.walkToObject(OBJECT_ID_LADDER) && Tools.useObject(OBJECT_ID_LADDER) && Tools.waitNotice(NOTICE_MESSAGE_11))
                return true;
            Tools.error("cannot go to next");
        }
        return false;
    }


    @Override
    boolean execute(int subTaskId) {
        Tools.log("COMBAT TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!walkToGuider() || !talkFirst()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!equipDagger()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 2:
                if (!talkSecond()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 3:
                if (!equipArms()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 4:
                if (!openCombat()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 5:
                if (!openGate()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 6:
                if (!attackRat()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 7:
                if (!outGate()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 8:
                if (!talkThird()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 9:
                if (!equipBow()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 10:
                if (!shootRat()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
            case 11:
                if (!gotoNext()) {
                    Tools.error("COMBAT TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("COMBAT TASK EXCEPTION... ");
                return false;
        }
        Tools.log("COMBAT TASK FINISH...");
        return true;
    }
}
