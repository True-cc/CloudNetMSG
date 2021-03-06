package uwu.smsgamer.cloudnetmsg;

import net.md_5.bungee.config.*;

import java.io.*;
import java.lang.reflect.Field;

public class Vars {
    public static String toMSG = "&b&lYOU&r -> %receiver%: &r%msg%";
    public static String fromMSG = "%sender% -> &b&lYOU&r: %msg%";
    public static String disabledMSG = "%receiver%&c has disabled messages.";

    public static String broadcast = "&c[&l&4Broadcast&c]  &6%msg%";
    public static String staffChat = "&7[&6SC&7]&r %sender%  &r%msg%";
    public static String globalChat = "&7[&bGlobal&7]&r %sender%  &r%msg%";

    public static String gcOnMsg = "Global chat for each message &aenabled&r.";
    public static String gcOffMsg = "Global chat for each message &cdisabled&r.";

    public static String gcOn = "Global chat &aenabled&r.";
    public static String gcOff = "Global chat &cdisabled&r.";
    public static String scOn = "Staff chat &aenabled&r.";
    public static String scOff = "Staff chat &cdisabled&r.";

    public static String csOn = "ChatSpy &aenabled&r.";
    public static String csOff = "ChatSpy &cdisabled&r.";
    public static String chatSpy = "&6Chat&eSpy&7>&r %sender%&r -> %receiver%&r %msg%";

    public static String noPermission = "&cYou do not have permission to execute that command.";
    public static String noPlayer = "&cThat player does not exist.";
    public static String replyNoLast = "&cNobody in last message.";

    public static String joinAlert = "%sender% joined the game.";
    public static String jaOn = "Join alerts &aenabled&r.";
    public static String jaOff = "Join alerts &cdisabled&r.";

    public static String usageReply = "Replies to last player you messaged. Usage: /%cmd% (msg).";
    public static String usageMsg = "Messages someone. Can be on another sub server. Usage: /%cmd% (player) (msg).";
    public static File dataFolder;

    // calling it load yaml just encase we use json as well
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void loadYaml(File df) {
        try {
            dataFolder = df;
            if (!df.exists())
                df.mkdirs();
            File file = new File(df, "config.yml");
            if (!file.exists())
                file.createNewFile();
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            Configuration msgs = config.getSection("messages");
            // probably not best way to do it but idfc I think it works (if it doesn't I'll do it another way)
            // sets the variable to the string in the config with a default of
            // that variable (so if it doesn't exist, just defaults to itself)
            // and then sets that in the config (because if it doesn't exist,
            // then it won't save on disk, so we set it to that and then we save)

            Class<Vars> clazz = Vars.class;

            for(Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                if (!f.getType().equals(File.class)) {
                    String name = f.getName();
                    try {
                        f.set(null, msgs.getString(name, (String) f.get(null)));
                        msgs.set(name, f.get(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            /*msgs.set("toMSG", toMSG = msgs.getString("toMSG", toMSG));
            msgs.set("fromMSG", fromMSG = msgs.getString("fromMSG", fromMSG));
            msgs.set("disabledMSG", disabledMSG = msgs.getString("disabledMSG", disabledMSG));

            msgs.set("broadcast", broadcast = msgs.getString("broadcast", broadcast));
            msgs.set("staffChat", staffChat = msgs.getString("staffChat", staffChat));
            msgs.set("globalChat", globalChat = msgs.getString("globalChat", globalChat));

            msgs.set("gcOnMsg", gcOnMsg = msgs.getString("gcOnMsg", gcOnMsg));
            msgs.set("gcOffMsg", gcOffMsg = msgs.getString("gcOffMsg", gcOffMsg));

            msgs.set("gcOn", gcOn = msgs.getString("gcOn", gcOn));
            msgs.set("gcOff", gcOff = msgs.getString("gcOff", gcOff));
            msgs.set("scOn", scOn = msgs.getString("scOn", scOn));
            msgs.set("scOff", scOff = msgs.getString("scOff", scOff));


            msgs.set("csOn", csOn = msgs.getString("csOn", csOn));
            msgs.set("csOff", csOff = msgs.getString("csOff", csOff));
            msgs.set("chatSpy", chatSpy = msgs.getString("chatSpy", chatSpy));

            msgs.set("joinAlert", joinAlert = msgs.getString("joinAlert", joinAlert));
            msgs.set("jaOn", jaOn = msgs.getString("jaOn", jaOn));
            msgs.set("jaOff", jaOff = msgs.getString("jaOff", jaOff));

            msgs.set("noPermission", noPermission = msgs.getString("noPermission", noPermission));
            msgs.set("noPlayer", noPlayer = msgs.getString("noPlayer", noPlayer));
            msgs.set("replyNoLast", replyNoLast = msgs.getString("replyNoLast", replyNoLast));

            msgs.set("usageReply", usageReply = msgs.getString("usageReply", usageReply));
            msgs.set("usageMsg", usageMsg = msgs.getString("usageMsg", usageMsg));*/
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
