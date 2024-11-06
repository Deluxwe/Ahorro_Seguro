package com.mycompany.ahorro_seguro;

public class Stack {

    private Node tope;

    public Stack() {
    }

    public boolean isEmpty() {
        return tope == null;
    }

    public void Push(Object data) {
        if (isEmpty()) {
            tope = new Node(data);
        } else {
            Node n = new Node(data);
            n.setLink(tope);
            tope = n;
        }
    }
    
     public Object Pop()
   {
       Object data=null;
       if(!isEmpty())
       {
           data=tope.getData();
           tope=tope.getLink();
           return data;
       }
       return null;
   }


}
