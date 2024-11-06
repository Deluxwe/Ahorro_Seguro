
package com.mycompany.ahorro_seguro;

public class Node {
    private Object data;
    private Node link;
    public Node(Object data) {
        this.data = data;
        link = null;  //por defecto en null
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public Node getLink() {
        return link;
    }
    public void setLink(Node link) {
        this.link = link;
    }  
    
}
