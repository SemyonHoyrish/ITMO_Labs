import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class AirSlash extends SpecialMove {
    public AirSlash() {
        super(Type.FLYING, 75.0, 0.95);
    }

    @Override
    protected String describe() {
        return "Air Slash";
    }
}
