package board;

public enum BorderValue {
    ROW("12345678".toCharArray()),
    COLUMN("ABCDEFGHI".toCharArray());

    final char[] value;

    BorderValue(final char[] value) {
        this.value = value;
    }

    public char getValue(final int index) {
        return value[index];
    }
}
