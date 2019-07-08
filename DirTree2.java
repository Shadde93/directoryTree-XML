
package dirtree;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import dirtree.MyNode;
import java.util.Scanner;

public class DirTree2 extends JFrame implements ActionListener{


   private JCheckBox box;
   private JTree tree;
   private DefaultMutableTreeNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog= " Liv ";  // ändrat namnet på katalog till liv. Från "." som betyder denna katalog.
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";
   private String s;
   private static Scanner scan;

   public DirTree2() {
      Container c = getContentPane();//metod ger oss frame
      // Build the tree and a mouse listener to handle click
      root = readNode(); // anropar hela trädet
      treeModel = new DefaultTreeModel( root );// modell av ett träd med oder
      tree = new JTree( treeModel ); //JTree en kontroll som visar hierkiskt all data och outliner dem.
      MouseListener ml = //listener interface som mottar intressanta mus händelser
        new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                if ( box.isSelected() )
                    showDetails( tree.getPathForLocation( e.getX(),e.getY() ) );//visar detaljerna av klickarna

            }
        };
      tree.addMouseListener( ml ); //trädet skom skapas kan man klicka på för detaljer
      //panel the JFrame to hold controls and the tree
      controls = new JPanel();
      box = new JCheckBox( showString );
      init(); //set colors, fonts, etc. and add buttons och dess postitioner
      c.add( controls, BorderLayout.NORTH );// lägger till på framet
      c.add( tree, BorderLayout.CENTER );
      setVisible( true ); // display the framed window
   }

   public void actionPerformed( ActionEvent e ) {
        String cmd = e.getActionCommand();
        if ( cmd.equals( closeString ) )//när man klickar på "Close" på fönstret så ska den stängas
            dispose();
   }

   private void init() {
      tree.setFont( new Font( "Dialog", Font.BOLD, 12 ) );//första sidan av fönstret
      controls.add( box );
      addButton( closeString );
      controls.setBackground( Color.CYAN );
      controls.setLayout( new FlowLayout() );
      setSize( 400, 400 );   // storleken på hela appen.
   }

   private void addButton( String n ) {
      JButton b = new JButton( n );//lägger ut knapparna
      b.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
      b.addActionListener( this );
      controls.add( b );
   }




private void showDetails(TreePath p){
        if (p == null){
            return;
        }
        JOptionPane.showMessageDialog(this,
             p.getLastPathComponent() //ger första namnet 
            +" "
            + getAttributeText((MyNode) p.getLastPathComponent()));// och ger texten deräfter som finns i sin specifika nod, enligt getMyText
    }
    
    private String getAttributeText(MyNode f){
        return f.getMyText();//hämtar texten som andvänds ovan
    }
    
    public static void main(String[] args){
        try{
           scan = new Scanner(new File("Liv.xml"));
            new DirTree2();
            scan.close();
        }
        catch(FileNotFoundException e){
             System.out.println("FILE NOT FOUND!");
        }
    }   

    public MyNode readNode() {
        String name;
        String level;
        String text;

        
        level = scan.next().substring(1);//tar alla levels(hela ordet) som sträng

        name = scan.next();//name = andra delen av strängen tills mellanrum

        if(!name.endsWith(">")){//if satsen är om det förekommer delord returnera det
            name = name + " " +scan.next();
        }
        name = name.substring(6, name.length()-2);//tar namnen på arterna
        
        text = scan.nextLine();//tar resten av texten från raderna
         
        text = text.substring(1);//tar bort mellanrummet i början

        MyNode retNode = new MyNode(level, name, text);
        while(scan.hasNext()) { //skannar alla levels tills:  
            if(scan.hasNext("</"+level+">")){   // så länge nästa scannade ser ut som: </level>
                scan.nextLine();// läser nästa rad </level>
                return retNode; //på nästa rad </klass> returnera <klassen ovan----.
            }else{
                retNode.add(readNode());//om inte</level> kolla nästa rad
            }
        }

        return retNode;
    }
}
