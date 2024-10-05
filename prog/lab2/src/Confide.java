import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class Confide extends StatusMove {
    public Confide() {
        super(Type.NORMAL, 0.0, 1.0);
    }

    @Override
    protected String describe() {
        return "Confide";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(-1).stat(Stat.ATTACK, Math.max((int)pokemon.getStat(Stat.ATTACK) - 1, -6)));
    }
}
