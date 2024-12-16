public enum Loudness {
    NORMAL ("сказал"),
    SHOUT ("крикнул");

    private final String inStoryText;

    Loudness(String s) {
        inStoryText = s;
    }

    public String getInStoryText() { return inStoryText; }
}
