package org.example.assignment2demo.ADT;

public class LinkedNode<N> {
    public LinkedNode<N> next = null;

    public N contents;

    public N getContents(){
        return contents;
    }

    public void setContents(N contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return contents.toString();
    }
}
