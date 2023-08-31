# Baader Computer spol. s r.o.

Základní funkční požadavky:

Definice voleb: Knihovna by měla umožnit definovat všechny potřebné vlastnosti volby (povinnost, popis, aliasy, defaultní hodnoty apod.) na jednom místě.
Typy voleb a omezení: Knihovna by měla podporovat různé typy voleb (integer, string, boolean, enum, custom) a možnost definice omezení pro některé typy (minValue, maxValue).
Rezervovaná volba: Automatická definice rezervované volby "-h" resp. "--help", která zobrazí nápovědu s konfigurací všech voleb.
Výjimky a validace: Uživatel knihovny by měl být veden k použití správně. Knihovna by měla poskytovat výjimky pro nesprávné vstupy a zajišťovat validaci vstupních argumentů.
Návrh řešení:

Definice třídy Option: Vytvořte třídu Option, která bude reprezentovat jednu volbu s vlastnostmi (povinnost, popis, aliasy, defaultní hodnoty) a typem.

Deklarace různých typů voleb: Pro různé typy voleb (integer, string, boolean, enum, custom) vytvořte třídy, které dědí od třídy Option a poskytují specifické metody pro validaci a zpracování hodnoty daného typu.

Zpracování argumentů: Vytvořte třídu pro zpracování argumentů (např. ArgumentParser). Tato třída by přijímala definice voleb a vstupní argumenty z příkazového řádku. Pro každý argument by se vyhledala odpovídající volba a provedla by se validace a zpracování hodnoty.

Rezervovaná volba -h resp. --help: Přidání této volby by mělo být jednoduché, a pokud je použita, knihovna by měla vypsat nápovědu s konfigurací všech voleb.

Rozšířitelnost: Pro podporu vlastních datových typů a omezení vytvořte rozhraní nebo třídy, které uživatelé knihovny mohou implementovat a rozšířit tak funkcionalitu.

Výjimky: Definujte vlastní výjimky pro různé chybové scénáře, např. chybějící povinný argument, nesprávný formát argumentu, překročení omezení apod.

```
public class Option {
    private String shortName;
    private String longName;
    private boolean required;
    private String description;
    private String[] aliases;
    private Object defaultValue;
    private OptionType type;
    private OptionValidator validator;
    // případně další metody a atributy
}

public interface OptionValidator {
    boolean validate(Object value);
}

public class IntegerOption extends Option {
    private Integer minValue;
    private Integer maxValue;
    // případně další metody a atributy
}

public class BooleanOption extends Option {
    private String[] trueValues;
    private String[] falseValues;
    // případně další metody a atributy
}

public class ArgumentParser {
    private List<Option> options;
    // metoda pro registraci voleb
    public void addOption(Option option) {
        // ...
    }
    // metoda pro zpracování argumentů
    public void parse(String[] args) {
        // ...
    }
}

```
