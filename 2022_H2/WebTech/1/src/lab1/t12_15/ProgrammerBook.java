package lab1.t12_15;

import java.util.Objects;

public class ProgrammerBook extends Book {
    private String language;
    private int level;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        if (this == o) {
            return true;
        }

        return Objects.equals(level, ((ProgrammerBook) o).level) &&
                Objects.equals(language, ((ProgrammerBook) o).language) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language.hashCode(), level, super.hashCode());
    }

    @Override
    public String toString() {
        return "language : " + language + '\n' +
                "level : " + level + '\n' +
                super.toString();
    }
}
