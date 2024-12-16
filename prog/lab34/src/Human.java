public class Human extends Animal implements Speakable {
    public Human(String name, int maxHp) {
        super(name, maxHp);
    }

    public Human(String name, int maxHp, WorldPoint position) {
        super(name, maxHp, position);
    }

    @Override
    public String defaultName() {
        return "человек";
    }

    @Override
    public void makeSound(Loudness loudness) {
        System.out.print(getName() + " " + loudness.getInStoryText() + ". ");
    }

    @Override
    public void move(WorldPoint point, int speed) {
        position = point;

        if (speed <= SLOW_MOVE_MAX) {
            System.out.print(getName() + " прогуливается. ");
        } else if (speed <= NORMAL_MOVE_MAX) {
            System.out.print(getName() + " идет. ");
        } else {
            System.out.print(getName() + " бежит. ");
        }
    }

    @Override
    public void speak(String msg, Loudness loudness) {
        System.out.print(getName() + " " + loudness.getInStoryText() + ": " + msg + ". ");
    }

    public boolean tryToConvince(Animal target) {
        if (Math.random() > target.getConvinceResistance()) {
            System.out.print(getName() + " смог убедить " + target.getName() + ". ");
            return true;
        } else {
            System.out.print(getName() + " не смог убедить " + target.getName() + ". ");
            return false;
        }
    }
}
