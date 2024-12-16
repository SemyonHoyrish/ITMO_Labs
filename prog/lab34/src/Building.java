import java.util.ArrayList;
import java.util.List;

public class Building {
    private WorldPoint position;
    private int capacity;
    private int occupancy;
    private List<Animal> occupiedBy;
    private String name;


    public Building(WorldPoint position, int capacity, String name) {
        this.position = position;
        this.capacity = capacity;
        this.occupancy = 0;
        this.occupiedBy = new ArrayList<Animal>();
        this.name = name;
    }

    public WorldPoint getPosition() {
        return position;
    }
    public String getName() { return name; }

    public boolean moveIn(Animal animal) {
        if (occupancy >= capacity) {
            System.out.print(animal.getName() + " не смог попасть в " + name + ". ");
            return false;
        }
        occupiedBy.add(animal);
        occupancy++;
        System.out.print(animal.getName() + " зашел в " + name + ". ");
        return true;
    }

    public boolean moveOut(Animal animal) {
        if (!occupiedBy.contains(animal)) return false;
        occupiedBy.remove(animal);
        occupancy--;
        System.out.print(animal.getName() + " покинул " + name + ". ");
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Building building = (Building) obj;
        if (!position.equals(building.position)) return false;
        if (capacity != building.capacity) return false;
        if (occupancy != building.occupancy) return false;
        if (!name.equals(building.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (name.hashCode() << 8) + (position.hashCode() << 4) + capacity * occupancy ;
    }

    @Override
    public String toString() {
        return "Здание названное " + name + " находится в точке " + position;
    }
}
