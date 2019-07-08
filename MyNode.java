
package dirtree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MyNode extends DefaultMutableTreeNode{
     public String myLevel;
     public String myText;

    public MyNode(String name, String myLevel, String myText) {
        super(name);
        this.myLevel = myLevel;
        this.myText = myText;
    }


    public void setMyLevel(String input){ 
        myLevel = input;
    }
    
    public void setMyText(String input){
        myText = input;
    }

    public String getMyText() {
        return myText;
    }

    public String toString() {
        return myLevel;
    } 
}

