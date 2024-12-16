public class Parrot extends Animal implements Speakable {
    public Parrot(String name, int maxHp) {
        super(name, maxHp);
    }

    public Parrot(String name, int maxHp, WorldPoint position) {
        super(name, maxHp, position);
    }

    @Override
    public String defaultName() {
        return "попугай";
    }

    @Override
    public void makeSound(Loudness loudness) {
        String s = switch (loudness) {
            case NORMAL -> "пародирует";
            case SHOUT -> "изъявляет недовольство";
        };

        System.out.print(getName() + " " + s + ". ");
    }

    @Override
    public void move(WorldPoint point, int speed) {
        position = point;

        if (speed <= SLOW_MOVE_MAX) {
            System.out.print(getName() + " прыгает. ");
        } else if (speed <= NORMAL_MOVE_MAX) {
            System.out.print(getName() + " летит. ");
        } else {
            System.out.print(getName() + " быстро летит. ");
        }
    }

    @Override
    public void speak(String msg, Loudness loudness) {
        System.out.print(getName() + " " + loudness.getInStoryText() + ": " + msg + ". ");
    }
}
