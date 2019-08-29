package worddao;

import java.util.Objects;

public class Word {
    private String hunWord;
    private String engWord;

    public Word(String hunWord, String engWord) {
        this.hunWord = hunWord;
        this.engWord = engWord;
    }

    public String getHunWord() {
        return hunWord;
    }

    public void setHunWord(String hunWord) {
        this.hunWord = hunWord;
    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    @Override
    public String toString() {
        return "Word{" +
                "hunWord='" + hunWord + '\'' +
                ", engWord='" + engWord + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(hunWord, word.hunWord) &&
                Objects.equals(engWord, word.engWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hunWord, engWord);
    }
}

