public record WalkGroup(Animal[] participants, WorldPoint destination) {

    public void walk(int speed) throws GroupMembersNotInPlaceException {
        if (participants.length > 1) {
            WorldPoint starPoint = participants[0].getPosition();
            for (int i = 1; i < participants.length; i++) {
                if (starPoint != participants[i].getPosition()) {
                    throw new GroupMembersNotInPlaceException("At least 2 participants: " + participants[0].getName() + " and " + participants[1].getName() + " are not on the same position");
                }
            }
        }

        boolean first = true;
        for (Animal animal : participants) {
            animal.move_silent(destination, speed);
            System.out.print((first ? "" : "и ") + animal.getName() + " ");
            first = false;
        }
        System.out.print("отправились на прогулку. ");
    }
}
