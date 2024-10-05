import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70.0, 1.0);
    }

    @Override
    protected String describe() {
        return "Facade";
    }

    @Override
    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        double mod = 1.0;
        Status cond = att.getCondition();

        if (cond == Status.BURN || cond == Status.POISON || cond == Status.PARALYZE)
            mod = 2.0;

        return super.calcBaseDamage(att, def) * mod;
    }
}
