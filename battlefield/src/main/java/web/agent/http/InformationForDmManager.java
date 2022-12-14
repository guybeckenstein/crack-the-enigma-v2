package web.agent.http;

import jar.enigmaEngine.interfaces.EnigmaEngine;
import javafx.util.Pair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InformationForDmManager {
    private final Map<String, EnigmaEngine> enigmaEngineMap; // Map<Allies' Username, Enigma Engine JSON>
    private final Map<String, Integer> taskSizeMap; // Map<Allies' Username, Task Size>
    private final Map<String, String> encryptionInputMap; // Map<Allies' Username, Encryption Input (original)>
    public InformationForDmManager() {
        this.enigmaEngineMap = new ConcurrentHashMap<>();
        taskSizeMap = new ConcurrentHashMap<>();
        encryptionInputMap = new ConcurrentHashMap<>();
    }
    /** When Allies add information (contest starts) **/
    public void addInformation(String alliesUsername, EnigmaEngine engine, int taskSize, String encryptionInput) {
        enigmaEngineMap.put(alliesUsername, engine);
        taskSizeMap.put(alliesUsername, taskSize);
        encryptionInputMap.put(alliesUsername, encryptionInput);
    }
    /** When contest ends **/
    public void removeInformation(String alliesUsername) {
        enigmaEngineMap.remove(alliesUsername);
        taskSizeMap.remove(alliesUsername);
        encryptionInputMap.remove(alliesUsername);
    }

    /** When an Agent needs information for his DecryptionManager Task **/
    public Pair<Pair<Integer, EnigmaEngine>, String> getInformation(String alliesUsername) {
        return new Pair<>(new Pair<>(taskSizeMap.get(alliesUsername), enigmaEngineMap.get(alliesUsername)), encryptionInputMap.get(alliesUsername));
    }
}
