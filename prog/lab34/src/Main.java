public class Main {
    public static void main(String[] args) {

        Parrot parrot = new Parrot("", 6, new WorldPoint(10, 10));
        Cat cat = new Cat("", 10);
        Human princess = new Human("Bright", 20, new WorldPoint(10, 11));

        parrot.speak("А ты отошли его обратно", Loudness.NORMAL);
        parrot.speak("А если он не захочет, убеги от него", Loudness.NORMAL);

        WalkGroup group = new WalkGroup(new Animal[]{princess, cat}, new WorldPoint(324, 645));
        princess.move(cat.getPosition(), 16);
        try {
            group.walk(12);
        } catch (GroupMembersNotInPlaceException e) {
            System.out.println();
            System.out.println(e.getMessage());
            System.out.print("Принцесса и кот хотели пойти на прогулку, но кто-то из них не пришел. ");
            System.exit(0);
        }

        Building castle = new Building(new WorldPoint(800, 10), 24, "замок");

        if (princess.tryToConvince(cat)) {
            System.out.print("Счастливый конец!");
        } else {
            try {
                princess.hit(cat, 1);
            } catch (RuntimeException e) {
                System.out.print("В сценарии ошибка! " + e.getMessage());
                System.exit(0);
            }
            cat.makeSound(Loudness.SHOUT);
            cat.move(castle.getPosition(), 22);
            castle.moveIn(cat);
        }
    }
}