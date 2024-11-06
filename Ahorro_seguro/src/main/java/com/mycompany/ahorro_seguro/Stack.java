package com.mycompany.ahorro_seguro;

public class Stack {

    private Node top;

    public Stack() {
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void Push(Object data) {
        if (isEmpty()) {
            top = new Node(data);
        } else {
            Node n = new Node(data);
            n.setLink(top);
            top = n;
        }
    }
    
     public Object Pop()
   {
       Object data=null;
       if(!isEmpty())
       {
           data=top.getData();
           top=top.getLink();
           return data;
       }
       return null;
   }


}
