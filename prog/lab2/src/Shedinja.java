import ru.ifmo.se.pokemon.Type;

public class Shedinja extends Nincada {
    public Shedinja(String name, int level) {
        super(name, level);
        setType(Type.BUG, Type.GHOST);
        setStats(1.0, 90.0, 45.0, 30.0, 30.0, 40.0);
        addMove(new ShadowClaw());
    }
}
