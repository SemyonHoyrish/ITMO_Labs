import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class SeismicToss extends PhysicalMove {
    private final int pokemonLevel;

    public SeismicToss(int pokemonLevel) {
        super(Type.FIGHTING, 0.0, 1.0);
        this.pokemonLevel = pokemonLevel;
    }

    @Override
    protected String describe() {
        return "Seismic Toss";
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        pokemon.setMod(Stat.HP, pokemonLevel);
    }
}
