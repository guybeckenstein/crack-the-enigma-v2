package jar.enigmaEngine.impl;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlugBoardImpl implements Serializable {
    private final HashMap<Character, Character> abcPairs;
    private final List<Pair<Character, Character>> pairList;

    public PlugBoardImpl() {
        this.abcPairs = new HashMap<>();
        this.pairList = new ArrayList<>();
    }

    public PlugBoardImpl(List<Pair<Character, Character>> pairList) {
        this.pairList = pairList;
        this.abcPairs = generateInputIntoPairs(pairList);
    }


    private HashMap<Character, Character> generateInputIntoPairs(List<Pair<Character, Character>> pairList) {
        HashMap<Character, Character> abcPairs = new HashMap<>();
        for (Pair<Character, Character> pair : pairList) {
            abcPairs.put(pair.getKey(), pair.getValue());
            abcPairs.put(pair.getValue(), pair.getKey());
        }
        return abcPairs;
    }

    public char returnCharacterPair(char input) {
        char res = input;

        if (abcPairs.containsKey(input))
            res = abcPairs.get(input);

        return res;
    }

    public void addPair(char a, char b) {
        this.abcPairs.put(a, b);
        this.abcPairs.put(b, a);
    }

    @SuppressWarnings("unchecked")
    public HashMap<Character, Character> getPairs() {
        return (HashMap<Character, Character>)this.abcPairs.clone();
    }

    public boolean containsPair(Pair<Character, Character> pair) {
        return this.abcPairs.containsKey(pair.getKey()) && this.abcPairs.containsKey(pair.getValue());
    }

    public List<Pair<Character, Character>> getPairList() {
        return this.pairList;
    }

    public PlugBoardImpl clone() {
        try {
            super.clone();
            PlugBoardImpl clone;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            clone = (PlugBoardImpl) objectInputStream.readObject();
            return clone;
        } catch (CloneNotSupportedException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}