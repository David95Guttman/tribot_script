package scripts;

import org.tribot.script.sdk.Waiting;
import org.jetbrains.annotations.NotNull;
import org.tribot.script.sdk.Login;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.script.ScriptConfig;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@TribotScriptManifest(name = "VerifyScript", author = "Andre Dowdell", category = "Template", description = "Account Verify Script")
public class VerifyScript implements TribotScript {

	@Override
	public void configure(@NotNull ScriptConfig config) {
		config.setRandomsAndLoginHandlerEnabled(true);
		config.setBreakHandlerEnabled(true);
	}

	@Override
	public void execute(final String args) {
		Optional<Login.LoginMessage> message;
		System.out.println("Login Start");
		try {
			if (!Login.login()) {
				message = Login.getLoginMessage();
				System.out.println("Login Failed" + message);
				if (message.isPresent()) {
					System.out.println("Login Failed : " + message.get().toString());
					reportResult(args + ":" + message.get().toString());
				} else {
					System.out.println("Login Failed : UNKNOWN");
					reportResult(args + ":UNKNOWN");
				}
				return;
			}
			message = Login.getLoginMessage();
			System.out.println("Login Success" + message);
			Waiting.wait(1000);
			Login.logout();
			reportResult(args + ":ENTER");
		} catch (Exception e) {
			System.out.println("Login Exception" + e);
		}
	}

	public boolean reportResult(String result) {
		try {
			InetAddress host = InetAddress.getLocalHost();
			Socket socket = new Socket(host.getHostName(), 3001);
			socket.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));
			socket.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}