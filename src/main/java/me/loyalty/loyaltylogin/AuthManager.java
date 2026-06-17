package me.loyalty.loyaltylogin;

import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.UUID;

public class AuthManager {
    private final HashMap<UUID, String> registeredPasswords = new HashMap<>();

    public void register(UUID uuid, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        registeredPasswords.put(uuid, hashedPassword);
    }

    public boolean checkPassword(UUID uuid, String password) {
        if (!registeredPasswords.containsKey(uuid)) return false;
        return BCrypt.checkpw(password, registeredPasswords.get(uuid));
    }

    public boolean isRegistered(UUID uuid) {
        return registeredPasswords.containsKey(uuid);
    }
}
