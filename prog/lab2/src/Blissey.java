public class Blissey extends Chansey {
    public Blissey(String name, int level) {
        super(name, level);
        setStats(255.0, 10.0, 10.0, 75.0, 135.0, 55.0);
        addMove(new HealPulse());
    }
}
