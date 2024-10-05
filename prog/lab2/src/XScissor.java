import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class XScissor extends PhysicalMove {
    public XScissor() {
        super(Type.BUG, 80.0, 1.0);
    }

    @Override
    protected String describe() {
        return "XScissor";
    }
}
