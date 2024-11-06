
package com.mycompany.ahorro_seguro;


public class Cola {
    private Node first;//donde sacamos
    private Node last; //donde metemos

    public Cola() {
    }

    public boolean isEmpty()
    {
        return first==null && last==null;
    }
    
    public void Enqueue(Object data)
    {
        if(isEmpty())
        {
            first=new Node(data);
            last=first;
        }
        else
        {
            Node n= new Node(data);
            last.setLink(n);
            last=n;
        }
    }
    
    public Cliente Dequeue()
    {
        Object data=null;
        
        if(!isEmpty())
        {
            data = first.getLink();
            first = first.getLink();
            
            if(first==null)
                last=null;
        }
        return data;
    } 
    

    
    
}
