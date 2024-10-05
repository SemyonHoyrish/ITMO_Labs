import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Farfetchd extends Pokemon {
    public Farfetchd(String name, int level) {
        super(name, level);
        setType(Type.NORMAL, Type.FLYING);
        setStats(52, 90.0, 55.0, 58.0, 62.0, 60.0);
        setMove(new AirSlash(), new SteelWing(), new FuryAttack(), new NightSlash());
    }
}
