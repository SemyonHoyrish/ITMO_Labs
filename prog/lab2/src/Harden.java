import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class Harden extends StatusMove {
    public Harden() {
        super(Type.NORMAL, 0.0, 0.0);
    }

    @Override
    protected String describe() {
        return "Harden";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(-1).stat(Stat.DEFENSE, Math.min((int)pokemon.getStat(Stat.DEFENSE) + 1, 6)));
    }
}
