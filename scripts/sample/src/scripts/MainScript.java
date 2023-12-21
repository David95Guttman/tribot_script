package scripts;

import org.jetbrains.annotations.NotNull;
import org.tribot.script.sdk.Camera;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;

@TribotScriptManifest(name = "TutorScript", author = "Andre Dowdell", category = "Template", description = "Tutorial Island Script")
public class MainScript implements TribotScript {

	private String[] noticeMessages = {
			// task step 0 : setting
			"Before you started",
			"Setting your appearance",
			// task step 1 : hello
			"Getting started",
			"This will display your settings menu",    // Settings menu --- Please click on the flashing spanner icon found at the bottom right of your screen. This will display your settings menu
			"the all settings button to see all available settings",    // Settings menu --- On the side panel, you can now see a variety of game settings. You can also click the all settings button to see all available settings. Talk to the Gielinor Guide to continue.
			"It's time to meet your first instructor",
			// task step 2 : fishing
			"Follow the path to find the next instructor",
			"To view the item you've been given",
			"Let's use it to catch some shrimp",
			"You've gained some experience",
			"On this menu you can view your skills",
			"you require a fire to do that which means you need some logs",
			"it's time to light a fire",
			"Now it's time to get cooking",
			"you've just cooked your first meal",
			// cooking stage
			"Follow the path until you get to the door with the yellow arrow above it",
			"He will teach you the more advanced aspects of Cooking",
			"To make dough you must mix flour with water",
			"you can bake it into some bread",
			"You've baked your first loaf of bread",
			// quest stage
			"You can use the flashing orb next to the minimap",
			"It's time to learn about quests",
			"Click on the flashing icon to the left of your inventory",
			"This is your quest journal",
			"It's time to enter some caves",
			// mining stage
			"you can make your first weapon yourself",
			"try mining some tin",
			"you just need some copper",
			"You can smelt these into a bronze bar",
			"You've made a bronze bar",
			"To smith you'll need a hammer and enough metal bars",
			"Use an anvil to open the smithing menu",
			"Congratulations, you've made your first weapon",
			// combat stage
			"you will find out about melee and ranged combat",
			"You now have access to a new interface",
			"You're now holding your dagger",
			"go to your worn inventory and click on the item",
			"Click on the flashing crossed swords icon to open the combat interface",
			"you can select the attack style that you'll use in combat",
			"It's time to slay some rats",
			"You will continue to attack the rat until it's dead or you do something else",
			"you've made your first kill",
			"Now you have a bow and some arrows",
			"To move on, click on the indicated ladder",
			// banking stage
			"This is the Bank of Gielinor",
			"You can store things here for safekeeping",
			"Now it's time for a quick look at polls",
			"Polls are run periodically to let",
			// account stage
			"The guide here will tell you all about your account",
			"Click on the flashing icon to open your Account Management menu",
			"Talk to the Account Guide to learn more",
			"Continue through the next door",
			// prayer stage
			"Follow the path to the chapel and enter it",
			"Click on the flashing icon to open the Prayer menu",
			"Talk with Brother Brace and he'll tell you about prayers",
			"Click on the flashing face icon to open your friends and ignore lists",
			"These two lists can be very helpful for keeping track of your friends",
			"You're almost finished on tutorial island",
			// magic stage
			"Follow the path to the wizard's house",
			"Open up the magic interface",
			"This is your magic interface",
			"All spells require runes to cast them",
			"All you need to do now is move on to the mainland"
	};

	private int[][] taskSteps = {
			{0, 0},
			{0, 1},
			{1, 0},
			{1, 1},
			{1, 2},
			{1, 3},
			{2, 0},
			{2, 1},
			{2, 2},
			{2, 3},
			{2, 4},
			{2, 5},
			{2, 6},
			{2, 7},
			{2, 8},
			{3, 0},
			{3, 1},
			{3, 2},
			{3, 3},
			{3, 4},
			{4, 0},
			{4, 2},
			{4, 3},
			{4, 4},
			{4, 5},
			{5, 0},
			{5, 2},
			{5, 3},
			{5, 4},
			{5, 5},
			{5, 6},
			{5, 6},
			{5, 7},
			{6, 0},
			{6, 1},
			{6, 2},
			{6, 3},
			{6, 4},
			{6, 5},
			{6, 6},
			{6, 6},
			{6, 7},
			{6, 9},
			{6, 11},
			{7, 0},
			{7, 2},
			{7, 2},
			{7, 3},
			{8, 0},
			{8, 1},
			{8, 2},
			{8, 3},
			{9, 0},
			{9, 2},
			{9, 3},
			{9, 4},
			{9, 5},
			{9, 6},
			{10, 0},
			{10, 2},
			{10, 3},
			{10, 4},
			{10, 5},
	};

	@Override
	public void configure(@NotNull ScriptConfig config) {
		config.setRandomsAndLoginHandlerEnabled(true);
		config.setBreakHandlerEnabled(true);
	}

	@Override
	public void execute(final String args) {
		Task[] tasks = {
				new SettingTask(),
				new HelloTask(),
				new FishingTask(),
				new CookingTask(),
				new QuestTask(),
				new MiningTask(),
				new CombatTask(),
				new BankingTask(),
				new AccountTask(),
				new ChurchTask(),
				new MagicTask(),
		};
		int taskId = 0, subTaskId = 0;
		if (!Login.login()) {
			Tools.error("Login Failed : " + Login.getLoginMessage());
			Tools.reportResult(args + ":WRONG");
			return;
		}
		Camera.setZoomPercent(20);
		String notice = Tools.getNotice();
		if (notice.equals("NONE")) {
			Tools.reportResult(args + ":SUCCESS");
			return;
		}
		for (int k = 0; k < noticeMessages.length; k++) {
			if (notice.indexOf(noticeMessages[k]) != -1) {
				taskId = taskSteps[k][0];
				subTaskId = taskSteps[k][1];
				break;
			}
		}
		Tools.log("Params : " + notice + " : " + taskId + " : " + subTaskId);
		for (int j = taskId; j < tasks.length; j++) {
			Tools.clearScreen();
			if (!tasks[j].execute(subTaskId)) {
				Tools.log("!!!!!!! FAIL !!!!!!");
				Tools.reportResult(args + ":FAIL" + j);
				return;
			}
			subTaskId = 0;
		}
		Login.logout();
		Waiting.wait(2000);
		Tools.reportResult(args + ":SUCCESS");
	}
}