package me.loyalty.loyaltylogin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // ពិនិត្យមើលថាតើអ្នកវាយ Command គឺជា Player ឬអត់
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command នេះប្រើបានតែក្នុងហ្គេមប៉ុណ្ណោះ!");
            return true;
        }

        Player player = (Player) sender;

        // ពិនិត្យមើលថាតើអ្នកលេងបានវាយ Password មកដែរឬទេ
        if (args.length == 0) {
            player.sendMessage("សូមវាយ៖ /register <password>");
            return true;
        }

        // យក Password ដែលអ្នកលេងវាយ (args[0])
        String password = args[0];

        // បញ្ជាក់ប្រាប់អ្នកលេង
        player.sendMessage("កំពុងចុះឈ្មោះលេខសម្ងាត់សម្រាប់អ្នក...");
        
        // នៅទីនេះបងអាចហៅ AuthManager មកប្រើដើម្បី Save Password
        // ឧទាហរណ៍៖ authManager.register(player.getUniqueId(), password);
        
        return true;
    }
}
