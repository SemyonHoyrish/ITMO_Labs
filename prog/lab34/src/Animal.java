public abstract class Animal {
    public static final int SLOW_MOVE_MAX = 15;
    public static final int NORMAL_MOVE_MAX = 20;

    private int maxHp;
    protected int hp;
    private String name;
    protected WorldPoint position;
    protected double convinceResistance;


    public Animal(String name, int maxHp) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.position = new WorldPoint(0, 0);
        this.convinceResistance = Math.random() / 2;
    }

    public Animal(String name, int maxHp, WorldPoint position) {
        this(name, maxHp);
        this.position = position;
    }

    public int getHp() { return hp; }
    public abstract String defaultName();
    public String getName() { return name.isEmpty() ? defaultName() : name; }
    public WorldPoint getPosition() { return position; }
    public double getConvinceResistance() { return convinceResistance; }
    public void setConvinceResistance(double convinceResistance) {
        this.convinceResistance = Math.max(0.0, Math.min(convinceResistance, 1.0));
    }


    public abstract void makeSound(Loudness loudness);
    public abstract void move(WorldPoint point, int speed);
    void move_silent(WorldPoint point, int speed) { position = point; }

    public void hit(Animal target, int damage) {
        target.hp -= damage;
        if (target.hp <= 0) {
            throw new RuntimeException("Character died after hit! Cannot proceed with a story.");
        }
        System.out.print(getName() + " ударил " + target.getName() + ". ");
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Animal other = (Animal) obj;
        if (maxHp != other.maxHp) return false;
        if (hp != other.hp) return false;
        if (name == null) return false;
        else if (!name.equals(other.name)) return false;
        if (position == null) return false;
        else if (!position.equals(other.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (name.hashCode() << 8) + (position.hashCode()) | (int)convinceResistance;
    }

    @Override
    public String toString() {
        return "Живое существо " + name + " нахожится в точке " + position;
    }

}
