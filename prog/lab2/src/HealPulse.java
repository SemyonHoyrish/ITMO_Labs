import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class HealPulse extends StatusMove {
    public HealPulse() {
        super(Type.PSYCHIC, 0.0, 1.0);
    }

    @Override
    protected String describe() {
        return "Heal Pulse";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.HP, (int)pokemon.getStat(Stat.HP) / 2);
    }
}
