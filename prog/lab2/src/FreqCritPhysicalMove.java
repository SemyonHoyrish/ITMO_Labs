import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class FreqCritPhysicalMove extends PhysicalMove {
    public FreqCritPhysicalMove(Type t, double pow, double acc) {
        super(t, pow, acc);
    }

    @Override
    protected double calcCriticalHit(Pokemon pokemon, Pokemon pokemon1) {
        // Night Slash deals damage and has an increased critical hit ratio (1⁄8 instead of 1⁄24).
        // Pokemon.jar lib implements crits differently, so we just make then proc 3 times more frequently
        Pokemon p = new Pokemon();
        p.setStats(0, 0, 0, 0, 0, pokemon.getStat(Stat.SPEED) * 3);
        return super.calcCriticalHit(p, pokemon1);
    }
}
