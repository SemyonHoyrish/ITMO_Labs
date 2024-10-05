import ru.ifmo.se.pokemon.Type;

public class ShadowClaw extends FreqCritPhysicalMove {
    public ShadowClaw() {
        super(Type.GHOST, 70.0, 1.0);
    }

    @Override
    protected String describe() {
        return "Shadow claw";
    }
}
