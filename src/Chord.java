/**
 * Created by jolpatrik on 2015-04-02.
 */
public class Chord {
    public int root;
    public int third;
    public int fifth;

    public Chord (int root, int third, int fifth){
        this.root = root;
        this.third = third;
        this.fifth = fifth;
    }

    public boolean equals(Chord c){
        return (this.root==c.root) && (this.third==c.third) && (this.fifth==c.fifth);
    }

    public String toString(){
        return root + "-" + third + "-" + fifth;
    }

}

