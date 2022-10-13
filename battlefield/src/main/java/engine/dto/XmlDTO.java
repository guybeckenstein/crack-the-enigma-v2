package engine.dto;

import java.util.List;
import java.util.Set;

public class XmlDTO {
    private final List<Integer> rotorsFromXML;
    private final List<String> reflectorsFromXML;
    private final List<Character> ABCFromXML;

    private final Set<String> dictionaryWordsFromXML;
    private final String excludedCharacters;

    public XmlDTO(List<Integer> rotorsFromXML, List<String> reflectorsFromXML, List<Character> ABCFromXML,
                  Set<String> dictionaryWordsFromXML, String excludedCharacters) {
        this.rotorsFromXML = rotorsFromXML;
        this.reflectorsFromXML = reflectorsFromXML;
        this.ABCFromXML = ABCFromXML;
        this.dictionaryWordsFromXML = dictionaryWordsFromXML;
        this.excludedCharacters = excludedCharacters;
    }

    public List<Integer> getRotorsFromXML() { // All rotors IDs in XML
        return this.rotorsFromXML;
    }
    public List<String> getReflectorsFromXML() { // All reflectors IDs in XML
        return this.reflectorsFromXML;
    }
    public List<Character> getABCFromXML() { // All ABC characters in XML
        return this.ABCFromXML;
    }
    public Set<String> getDictionaryWordsFromXML() {
        return this.dictionaryWordsFromXML;
    }
    public String getExcludedCharacters() {
        return excludedCharacters;
    }
}
