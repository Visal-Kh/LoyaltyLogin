package me.loyalty.loyaltylogin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.*;

public class LoyaltyLogin extends JavaPlugin implements Listener, CommandExecutor {
    private final HashSet<UUID> pendingLogin = new HashSet<>();
    private final AuthManager authManager = new AuthManager();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("register").setExecutor(this);
        getCommand("login").setExecutor(this);
        getLogger().info("§a[LoyaltyLogin] បានដំណើរការជោគជ័យ!");
    }

    // បន្ថែមបញ្ជីឈ្មោះ Premium Player របស់អ្នកនៅទីនេះ
    private final List<String> premiumPlayers = Arrays.asList("Visal-Kh", "Player2", "FriendName");

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    // ពិនិត្យមើលថាតើ Player នោះចូលតាមរយៈគណនី Premium ឬអត់
    // ក្នុង Minecraft, ប្រសិនបើ Server បើក mode (online-mode=true) វានឹងស្គាល់ Premium ដោយស្វ័យប្រវត្តិ
    // ប៉ុន្តែបើបងប្រើ Server Cracked បងត្រូវប្រើវិធីនេះ៖
    
    if (player.getAddress().getAddress().isLoopbackAddress() || player.getName().length() > 0) {
        // កូដសម្រាប់ពិនិត្យ Profile របស់ Mojang
        if (player.getUniqueId().version() == 4) { 
            // UUID version 4 ជាធម្មតាគឺជា UUID ដែលបង្កើតពីគណនី Premium
            player.sendMessage("§bស្វាគមន៍ Premium Player! បងត្រូវបាន Auto-Login រួចរាល់។");
            return; // ចេញពីកូដនេះ មិនបាច់ Lock ទេ
        }
    }
    
    // បើមិនមែន Premium ទេ ទើប Lock ពួកគេ
    pendingLogin.add(player.getUniqueId());
    player.sendMessage("§eសូមវាយ /register <password> ដើម្បីចុះឈ្មោះ។");
}

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (pendingLogin.contains(event.getPlayer().getUniqueId())) {
            event.setTo(event.getFrom()); // បិទការដើរ
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (pendingLogin.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cសូម Login ជាមុនសិន!");
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
            player.sendMessage("§aចុះឈ្មោះជោគជ័យ!");
        } else if (cmd.getName().equalsIgnoreCase("login")) {
            if (authManager.checkPassword(player.getUniqueId(), args[0])) {
                pendingLogin.remove(player.getUniqueId());
                player.sendMessage("§aLogin ជោគជ័យ!");
            } else {
                player.sendMessage("§cលេខសម្ងាត់ខុស!");
            }
        }
        return true;
    }
}
