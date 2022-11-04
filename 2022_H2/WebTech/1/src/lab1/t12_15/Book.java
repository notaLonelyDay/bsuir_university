package lab1.t12_15;

import java.util.*;

public class Book implements Cloneable, Comparable<Book> {
        private String title;
        private String author;
        private int price;
        private static int edition;
        private int isbn;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass() ) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        return  Objects.equals(title, ((Book) obj).title) &&
                Objects.equals(author, ((Book) obj).author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.author, this.price, edition, this.isbn);
    }

    @Override
    public String toString() {
        return "{" + "author : " + author + "\n" +
                "title : " + title + "\n" +
                "price : " + price + "\n" +
                "edition : " + edition + "\n" +
                "isbn : " + isbn + "}";
    }

    protected Object clone() throws CloneNotSupportedException {
        return  super.clone();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int compareTo(Book o) {
        return isbn - o.isbn;
    }

}
