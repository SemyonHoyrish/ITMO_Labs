import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Happiny extends Pokemon {
    public Happiny(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(100.0, 5.0, 5.0, 15.0, 65.0, 30.0);
        setMove(new Confide(), new Facade());
    }
}
