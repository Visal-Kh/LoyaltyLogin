package me.loyalty.loyaltylogin;

import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.UUID;

public class AuthManager {

    private final HashMap<UUID, String> registeredPasswords = new HashMap<>();

    public void register(UUID uuid, String password) {
        // Hash password មុនរក្សាទុក
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        registeredPasswords.put(uuid, hashedPassword);
    }

    public boolean checkPassword(UUID uuid, String password) {
        if (!registeredPasswords.containsKey(uuid)) return false;
        String hashedPassword = registeredPasswords.get(uuid);
        // ផ្ទៀងផ្ទាត់ជាមួយ BCrypt
        return BCrypt.checkpw(password, hashedPassword);
    }

    public boolean isRegistered(UUID uuid) {
        return registeredPasswords.containsKey(uuid);
    }
}
