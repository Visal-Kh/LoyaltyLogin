package me.loyalty.loyaltylogin;

import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.UUID;

public class AuthManager {

    private final HashMap<UUID, String> registeredPasswords = new HashMap<>();

    public void register(UUID uuid, String password) {
    // ប្រើ BCrypt.hashpw ដើម្បីលាក់ Password
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
    registeredPasswords.put(uuid, hashedPassword);
}

    public boolean checkPassword(UUID uuid, String password) {
    if (!registeredPasswords.containsKey(uuid)) return false;
    String hashedPassword = registeredPasswords.get(uuid);
    // ប្រើ BCrypt.checkpw ដើម្បីផ្ទៀងផ្ទាត់
    return BCrypt.checkpw(password, hashedPassword);
}


    public boolean isRegistered(UUID uuid) {
        return registeredPasswords.containsKey(uuid);
    }
}
