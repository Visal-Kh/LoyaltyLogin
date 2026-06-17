package me.loyalty.loyaltylogin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.UUID;

public class LoyaltyLogin extends JavaPlugin implements Listener, CommandExecutor {

    private final HashSet<UUID> pendingLogin = new HashSet<>();
    private final AuthManager authManager = new AuthManager(); // ភ្ជាប់ទៅ AuthManager

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("register").setExecutor(this);
        getCommand("login").setExecutor(this);
        getLogger().info("LoyaltyLogin បានដំណើរការ!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (authManager.isRegistered(player.getUniqueId())) {
            pendingLogin.add(player.getUniqueId());
            player.sendMessage("§eសូមវាយ /login <password> ដើម្បីចូល!");
        } else {
            pendingLogin.add(player.getUniqueId());
            player.sendMessage("§eសូមវាយ /register <password> ដើម្បីចុះឈ្មោះ!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("register")) {
            if (args.length == 0) return false;
            authManager.register(player.getUniqueId(), args[0]);
            pendingLogin.remove(player.getUniqueId());
            player.sendMessage("§aចុះឈ្មោះជោគជ័យ!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("login")) {
            if (args.length == 0) return false;
            if (authManager.checkPassword(player.getUniqueId(), args[0])) {
                pendingLogin.remove(player.getUniqueId());
                player.sendMessage("§aLogin ជោគជ័យ!");
            } else {
                player.sendMessage("§cPassword មិនត្រឹមត្រូវ!");
            }
            return true;
        }
        return false;
    }

    // ផ្នែក Events (Move, Chat, Inventory) នៅដដែលដូចកូដមុន...
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (pendingLogin.contains(event.getPlayer().getUniqueId())) event.setCancelled(true);
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (pendingLogin.contains(event.getPlayer().getUniqueId())) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (pendingLogin.contains(event.getWhoClicked().getUniqueId())) event.setCancelled(true);
    }
}
