package me.loyalty.loyaltylogin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.UUID;

public class LoyaltyLogin extends JavaPlugin implements Listener {

    // បញ្ជីអ្នកលេងដែលមិនទាន់បាន Login
    private final HashSet<UUID> pendingLogin = new HashSet<>();

    @Override
    public void onEnable() {
        // ចុះឈ្មោះ Event Listener ដើម្បីឱ្យ Server ស្គាល់សកម្មភាពរបស់យើង
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("LoyaltyLogin បានដំណើរការដោយសុវត្ថិភាព!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // បន្ថែមអ្នកលេងចូលក្នុងបញ្ជីរង់ចាំ Login
        pendingLogin.add(player.getUniqueId());
        player.sendMessage("§c[LoyaltyLogin] សូមវាយ /register <password> ដើម្បីចុះឈ្មោះ!");
    }

    // ឃាត់មិនឱ្យអ្នកលេងដើរបាន រហូតទាល់តែ Login រួច
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (pendingLogin.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    // ឃាត់មិនឱ្យនិយាយក្នុង Chat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (pendingLogin.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cអ្នកមិនអាចនិយាយបានទេ រហូតដល់អ្នក Login!");
        }
    }

    // ឃាត់មិនឱ្យប៉ះពាល់ Inventory
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (pendingLogin.contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
