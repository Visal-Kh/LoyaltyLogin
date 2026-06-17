package me.loyalty.loyaltylogin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // ពិនិត្យមើល Account Premium (ឈ្មោះបង)
        if (player.getName().equals("Visal-Kh")) {
            player.sendMessage("ស្វាគមន៍ Premium Player! បងត្រូវបាន Auto-Login រួចរាល់។");
        } else {
            player.sendMessage("សូមវាយ /register <password> ដើម្បីចុះឈ្មោះ។");
        }
    }
}
