package me.loyalty.loyaltylogin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.UUID;

public class LoyaltyLogin extends JavaPlugin implements Listener, CommandExecutor {
    private final HashSet<UUID> pendingLogin = new HashSet<>();
    private final AuthManager authManager = new AuthManager();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("register").setExecutor(this);
        getCommand("login").setExecutor(this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        pendingLogin.add(player.getUniqueId());
        if (authManager.isRegistered(player.getUniqueId())) {
            player.sendMessage("សូមវាយ /login <password>");
        } else {
            player.sendMessage("សូមវាយ /register <password>");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length == 0) return false;

        if (cmd.getName().equalsIgnoreCase("register")) {
            authManager.register(player.getUniqueId(), args[0]);
            pendingLogin.remove(player.getUniqueId());
            player.sendMessage("ចុះឈ្មោះជោគជ័យ!");
        } else if (cmd.getName().equalsIgnoreCase("login")) {
            if (authManager.checkPassword(player.getUniqueId(), args[0])) {
                pendingLogin.remove(player.getUniqueId());
                player.sendMessage("Login ជោគជ័យ!");
            } else {
                player.sendMessage("លេខសម្ងាត់ខុស!");
            }
        }
        return true;
    }
}
