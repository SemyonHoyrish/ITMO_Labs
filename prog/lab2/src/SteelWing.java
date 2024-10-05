import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Stat;

public class SteelWing extends PhysicalMove {
    public SteelWing() {
        super(Type.STEEL, 70.0, 0.9);
    }

    @Override
    protected String describe() {
        return "Steel Wing";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        if (Math.random() <= 0.1) {
            pokemon.addEffect(new Effect().turns(-1).stat(Stat.DEFENSE, Math.min((int) pokemon.getStat(Stat.DEFENSE) + 1, 6)));
        }
    }
}
