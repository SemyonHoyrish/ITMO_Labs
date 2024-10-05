import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Nincada extends Pokemon {
    public Nincada(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.GROUND);
        setStats(31, 45.0, 90.0, 30.0, 30.0, 40.0);
        setMove(new XScissor(), new Harden(), new Confide());
    }
}
