import ru.ifmo.se.pokemon.Type;

public class NightSlash extends FreqCritPhysicalMove {
    public NightSlash() {
        super(Type.DARK, 70.0, 1.0);
    }

    @Override
    protected String describe() {
        return "Night Slash";
    }
}
