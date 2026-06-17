package me.loyalty.loyaltylogin;

import java.util.HashMap;
import java.util.UUID;

public class AuthManager {

    // នេះជាកន្លែងទុក Password បណ្ដោះអាសន្ន (ក្នុងដំណាក់កាលបន្ទាប់យើងនឹងប្រើ Database)
    private final HashMap<UUID, String> registeredPasswords = new HashMap<>();

    // សម្រាប់ចុះឈ្មោះ Password ថ្មី
    public void register(UUID uuid, String password) {
        registeredPasswords.put(uuid, password);
    }

    // សម្រាប់ពិនិត្យមើល Password ថាតើត្រឹមត្រូវដែរឬទេ
    public boolean checkPassword(UUID uuid, String password) {
        return registeredPasswords.containsKey(uuid) && registeredPasswords.get(uuid).equals(password);
    }

    // ពិនិត្យថាតើអ្នកលេងបានចុះឈ្មោះហើយឬនៅ
    public boolean isRegistered(UUID uuid) {
        return registeredPasswords.containsKey(uuid);
    }
}
