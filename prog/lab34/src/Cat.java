public class Cat extends Animal {
    public Cat(String name, int maxHp) {
        super(name, maxHp);
    }

    public Cat(String name, int maxHp, WorldPoint position) {
        super(name, maxHp, position);
    }

    @Override
    public String defaultName() {
        return "кот";
    }


    @Override
    public void makeSound(Loudness loudness) {
        String s = switch (loudness) {
            case NORMAL -> "мяукнул";
            case SHOUT -> "громко мяукнул";
        };

        System.out.print(getName() + " " + s + ". ");
    }

    @Override
    public void move(WorldPoint point, int speed) {
        position = point;

        if (speed <= SLOW_MOVE_MAX) {
            System.out.print(getName() + " медленно идет. ");
        } else if (speed <= NORMAL_MOVE_MAX) {
            System.out.print(getName() + " идет. ");
        } else {
            System.out.print(getName() + " бежит. ");
        }
    }
}
