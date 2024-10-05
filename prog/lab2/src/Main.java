import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon a1 = new Happiny("h", 10);
        Pokemon a2 = new Chansey("c", 10);
        Pokemon a3 = new Blissey("b", 10);
        Pokemon f1 = new Farfetchd("f", 10);
        Pokemon f2 = new Nincada("n", 10);
        Pokemon f3 = new Shedinja("s", 10);
        b.addAlly(a1);
        b.addAlly(a2);
        b.addAlly(a3);
        b.addFoe(f1);
        b.addFoe(f2);
        b.addFoe(f3);
        b.go();
    }
}
