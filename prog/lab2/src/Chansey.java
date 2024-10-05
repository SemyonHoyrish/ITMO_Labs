public class Chansey extends Happiny {
    public Chansey(String name, int level) {
        super(name, level);
        setStats(250.0, 5.0, 5.0, 35.0, 105.0, 50.0);
        addMove(new SeismicToss(level));
    }
}
