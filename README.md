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
// OptionType definuje různé typy voleb
enum OptionType {
    INTEGER, STRING, BOOLEAN, ENUM, CUSTOM
}

// Třída reprezentující jednu volbu
class Option {
    private String name;
    private OptionType type;
    private boolean required;
    private String description;
    private String[] aliases;
    private Object defaultValue;
    private OptionValidator validator;
    // Další atributy a metody
}

// Rozhraní pro validaci hodnot volby
interface OptionValidator {
    boolean validate(Object value);
}

// Podtřída pro celočíselné volby
class IntegerOption extends Option {
    private Integer minValue;
    private Integer maxValue;
    // Metody pro validaci a zpracování
}

// Podtřída pro volby typu boolean
class BooleanOption extends Option {
    private String[] trueValues;
    private String[] falseValues;
    // Metody pro validaci a zpracování
}

// ArgumentParser pro zpracování vstupních argumentů
class ArgumentParser {
    private List<Option> options;
    // Metoda pro registraci voleb
    public void addOption(Option option) {
        // ...
    }
    // Metoda pro zpracování argumentů
    public void parse(String[] args) {
        // ...
    }
}

// Ukázková aplikace kalkulačky s využitím knihovny pro zpracování vstupních argumentů
public class CalculatorApp {
    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser();
        // Registrace voleb do parseru
        
        // Zpracování argumentů
        parser.parse(args);
        
        // Získání hodnot a provádění kalkulací
        int leftOperand = parser.getIntegerOptionValue("left");
        int rightOperand = parser.getIntegerOptionValue("right");
        Operator operator = parser.getEnumOptionValue("operator", Operator.class);
        boolean verbose = parser.getBooleanOptionValue("verbose");
        
        // Provádění kalkulace a výpis výsledku
    }
}

```
