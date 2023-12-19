package scripts;

import org.tribot.script.sdk.*;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.walking.LocalWalking;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Tools {
    public static int getRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static String getRandomStr(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static boolean talkNpc(String name) {
        System.out.println("****** : talk npc");
        try {
            if (ChatScreen.isClickContinueOpen())
                ChatScreen.clickContinue();
            return Waiting.waitUntil(() -> Query.npcs()
                    .nameEquals(name)
                    .findBestInteractable().isPresent())
                    && Query.npcs()
                    .nameEquals(name)
                    .findBestInteractable()
                    .map(npc -> npc.click())
                    .orElse(false) && Waiting.waitUntil(() -> ChatScreen.isClickContinueOpen());
        } catch (Exception e) {
            return false;
        }
    }

    public static void clearScreen() {
        if (ChatScreen.isClickContinueOpen())
            ChatScreen.clickContinue();
    }

    public static boolean clickWidget(int... idPath) {
        System.out.println("****** : click widget");
        Waiting.wait(2000);
        Waiting.waitUntil(() -> Query.widgets().inIndexPath(idPath).findFirst().isPresent());
        Query.widgets().inIndexPath(idPath).findFirst().map(w -> w.click()).orElse(false);
        return true;
    }

    public static boolean openDoor(int id) {
        System.out.println("****** : open door");
        return Query.gameObjects()
                .idEquals(id)
                .findClosestByPathDistance()
                .map(obj -> obj.interact("Open"))
                .orElse(false) && Waiting.waitUntil(() -> !MyPlayer.isMoving());
    }

    public static boolean clickNpc(String name) {
        System.out.println("****** : click npc");
        return Query.npcs()
                .nameEquals(name)
                .findBestInteractable()
                .map(npc -> npc.click())
                .orElse(false) && Waiting.waitUntil(() -> !MyPlayer.isMoving());
    }

    public static boolean clickAnimal(String name) {
        System.out.println("****** : click animal");
        return Query.npcs()
                .nameEquals(name)
                .findClosest()
                .map(npc -> npc.click())
                .orElse(false) && Waiting.waitUntil(() -> !MyPlayer.isMoving());
    }


    // wait until the notice text is appeared.
    public static boolean waitNotice(String text) {
        System.out.println("****** : wait notice");
        return Waiting.waitUntil(20000, () -> Query.widgets().inIndexPath(263,1, 0).textContains(text).findFirst().isPresent());
    }

    public static boolean waitConfirm(String text) {
        System.out.println("****** : wait confirm");
        return Waiting.waitUntil(() -> ChatScreen.isClickContinueOpen())
                && ChatScreen.containsMessage(text)
                && ChatScreen.clickContinue();
    }

    public static String getNotice() {
        try {
            return Query.widgets().inIndexPath(263, 1, 0).findFirst().get().getText().toString();
        } catch (Exception e) {
            return "NONE";
        }
    }

    public static boolean clickObject(String name) {
        System.out.println("****** : click object by name");
        return Query.gameObjects()
                .nameEquals(name)
                .findClosestByPathDistance()
                .map(obj -> obj.click())
                .orElse(false) && Waiting.waitUntil(() -> !MyPlayer.isMoving());
    };

    public static boolean useObject(String name) {
        System.out.println("****** : click object by name");
        return Waiting.waitUntil(() -> Query.gameObjects()
                .nameEquals(name)
                .findBestInteractable().isPresent())
                && Query.gameObjects()
                .nameEquals(name)
                .findBestInteractable()
                .map(obj -> obj.click())
                .orElse(false) && Waiting.waitUntil(() -> !MyPlayer.isMoving());
    };

    public static boolean equipItem(String name) {
        Waiting.wait(1000);
        return Equipment.equip(name);
    }

    public static boolean useObject(int id) {
        System.out.println("****** : click object by name");
        return Waiting.waitUntil(() -> Query.gameObjects()
                .idEquals(id)
                .findBestInteractable().isPresent())
                && Query.gameObjects()
                .idEquals(id)
                .findBestInteractable()
                .map(obj -> obj.click())
                .orElse(false) && Waiting.waitUntil(() -> !MyPlayer.isMoving());
    };
    public static boolean continueChat(String [] messages) {
        for (String msg: messages) {
            Waiting.waitNormal(100, 10);
            if (!Waiting.waitUntil(() -> ChatScreen.containsMessage(msg)) || !ChatScreen.clickContinue())
                return false;
            log("process -> " + msg);
        }
        return true;
    }

    public static boolean selectOption(String option) {
        if (!Waiting.waitUntil(() -> ChatScreen.containsOption(option)))
            return false;
        return ChatScreen.selectOption(option);
    }

    public static boolean walkToGuider(String name) {
        try {
            return LocalWalking.walkTo(Query.npcs().nameEquals(name).findClosestByPathDistance().get())
                    && Waiting.waitUntil(20000, () -> !MyPlayer.isMoving());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean walkToTile(WorldTile tile) {
        try {
            return LocalWalking.walkTo(tile.toLocalTile()) && Waiting.waitUntil(20000, () -> !MyPlayer.isMoving());
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean walkToObject(int id) {
        try {
            return LocalWalking.walkTo(Query.gameObjects().idEquals(id).findFirst().get())
                    && Waiting.waitUntil(20000, () -> !MyPlayer.isMoving());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean walkToObject(String name) {
        try {
            return LocalWalking.walkTo(Query.gameObjects().nameEquals(name).findFirst().get())
                    && Waiting.waitUntil(20000, () -> !MyPlayer.isMoving());
        } catch (Exception e) {
            return false;
        }
    }

    public static void log(String logStr) {
        System.out.println("### : " + logStr);
    }


    public static void error(String logStr) {
        System.err.println("$$$ : " + logStr);
    }

    public static boolean takeItem(String name) {
        System.out.println("****** : take item " + name);
        return Query.inventory()
                .nameContains(name)
                .findFirst()
                .map(item -> item.click())
                .orElse(false);
    }

    public static void appendAccount(String text) {
        File file = new File("C:/Work/111.txt");
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;
        try {
            // to append to file, you need to initialize FileWriter using below constructor
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);
            pr.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pr.close();
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean closeBankWidget() {
        if (Query.widgets().inIndexPath(289, 7, 8).findFirst().isPresent()) {
            return clickWidget(289, 7, 8) && clickWidget(12, 2, 11);
        } else {
            return clickWidget(12, 2, 11);
        }
    }

    public static boolean reportResult(String result) {
        try {
            log("Report Result");
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 3000);
            socket.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
            socket.close();
            return true;
        } catch (Exception e) {
            error(e.toString());
            return false;
        }
    }
}
