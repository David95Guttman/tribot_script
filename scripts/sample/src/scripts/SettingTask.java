package scripts;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.input.Keyboard;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;

public class SettingTask extends Task {

    private static final String CONFIRM_BUTTON = "Confirm";

    public SettingTask() {
        super();
    }

    private boolean setName() {
        Tools.log("set name");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            for (int j = 0; i < 12; i ++)
                Keyboard.pressBack();
            Keyboard.typeLine(Tools.getRandomStr(3));
            if (Waiting.waitUntil(() -> Query.widgets().inIndexPath(558, 15).findFirst().isPresent() && Query.widgets().inIndexPath(558, 15).findFirst().get().isVisible())
                    && Query.widgets().inIndexPath(558, Tools.getRandom(15, 17)).findFirst().map(Widget::click).orElse(false)
                    && Waiting.waitUntil(() -> Query.widgets().textContains("Set name").findFirst().isPresent() && Query.widgets().textContains("Set name").findFirst().get().isVisible())
                    && Query.widgets().textContains("Set name").findFirst().map((w) ->w.click()).orElse(false)) {
                return true;
            }
            Tools.error("cannot set name");
        }
        return false;

    }

    private boolean setShape() {
        Tools.log("set shape");
        for (int i = 0; i < 3; i ++) {
            Waiting.waitNormal(2000, 10);
            if (Waiting.waitUntil(() -> Query.widgets().textContains(CONFIRM_BUTTON).findFirst().isPresent() && Query.widgets().textContains(CONFIRM_BUTTON).findFirst().get().isVisible())
                && Query.widgets().textContains(CONFIRM_BUTTON).findFirst().map((w) ->w.click()).orElse(false)) {
                return true;
            }
            Tools.error("cannot set shape");
        }
        return false;
    }

    @Override
    public boolean execute(int subTaskId) {
        Tools.log("SETTING TASK START...");
        Waiting.waitNormal(2000, 10);
        switch (subTaskId) {
            case 0:
                if (!setName()) {
                    Tools.error("SETTING TASK FAILED... ");
                    return false;
                }
            case 1:
                if (!setShape()) {
                    Tools.error("SETTING TASK FAILED... ");
                    return false;
                }
                break;
            default:
                Tools.error("SETTING TASK EXCEPTION... ");
                return false;
        }
        Tools.log("SETTING TASK FINISH...");
        return true;

    }
}
