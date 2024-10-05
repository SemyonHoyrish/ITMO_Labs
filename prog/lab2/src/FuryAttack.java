import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class FuryAttack extends PhysicalMove {
    public FuryAttack() {
        super(Type.NORMAL, 15, 0.85, 0, 3);
    }

    @Override
    protected String describe() {
        return "Fury Attack";
    }
}

