package me.loyalty.loyaltylogin;

import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.UUID;

public class AuthManager {
    // ត្រូវមាន Map នេះដើម្បីរក្សាទុក Password
    private final HashMap<UUID, String> registeredPasswords = new HashMap<>();

    public void register(UUID uuid, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        registeredPasswords.put(uuid, hashedPassword);
    }

    public boolean checkPassword(UUID uuid, String password) {
        if (!registeredPasswords.containsKey(uuid)) return false;
        String hashedPassword = registeredPasswords.get(uuid);
        return BCrypt.checkpw(password, hashedPassword);
    }

    public boolean isRegistered(UUID uuid) {
        return registeredPasswords.containsKey(uuid);
    }
}
