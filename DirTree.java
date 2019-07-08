package dirtree;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;        
import java.awt.*;
import java.awt.event.*;
import dirtree.MyScanner;
import dirtree.MyNode;

public class DirTree extends JFrame implements ActionListener{
    
    
   private JCheckBox box;
   private JTree tree;
   private DefaultMutableTreeNode root;
   private DefaultTreeModel treeModel;
   private JPanel controls;
   private static String katalog= " Liv ";  // ändrat namnet på katalog till liv. Från "." som betyder denna katalog.
   private static final String closeString = " Close ";
   private static final String showString = " Show Details ";

   public DirTree() {
      Container c = getContentPane();//metod ger oss frame
      // Build the tree and a mouse listener to handle clicks
      root = new DefaultMutableTreeNode(katalog);// skapar noder i trädet
      treeModel = new DefaultTreeModel( root );// modell av ett träd med oder
      tree = new JTree( treeModel ); //JTree en kontroll som visar hierkiskt all data och outliner dem. 
      MouseListener ml = //listener interface som mottar intressanta mus händelser
        new MouseAdapter() {
          public void mouseClicked( MouseEvent e ) {
            if ( box.isSelected() )
                showDetails( tree.getPathForLocation( e.getX(),//visar detaljerna av klickarna 
                                                    e.getY() ) );
          }
        };
      tree.addMouseListener( ml ); //trädet skom skapas kan man klicka på för detaljer
      //bygger trädet med mha nodernar
      buildTree();
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
 

    private void buildTree() {     // buildTree är den viktigaste delen!
     
                
        String [] children = {    // Lista skapad med tre strängar.
                               " Växter  ",
                               " Djur    ",
                               " Svampar "
                             };
        String [] underChild = {  // en till lista skapad med tre strängar
                                " familjer ",
                                " släkten  ",
                                " arter    "
                              };
       
        for ( int i = 0; i < children.length; i++ ){    // använder defaultMutableTreeNode för att skapa trädet.
           // defaultMutableTreeNode ärver från OBject. (DVS inbyggd) 
           DefaultMutableTreeNode overRoot = new // skapar ett objekt av typen DefaultMutableTreeNode, döper den till overRoot
                DefaultMutableTreeNode (children[i]); // tar första strängen i listan children gör den till nod.
           
                root.add( overRoot );  // lägger till overRoot i root.
           
        for ( int k = 0; k < underChild.length ; k++ ){
                   
        DefaultMutableTreeNode underRoot = new 
                DefaultMutableTreeNode ( underChild [k] );
                overRoot.add( underRoot );    //  lägger till underRoot till overRoot
                   
                   
                }
               
        }
        
    }  
   
   private void showDetails( TreePath p ) {
      if ( p == null )
        return;
      File f = new File( p.getLastPathComponent().toString() );
      JOptionPane.showMessageDialog( this, f.getPath() + 
                                     "\n   " +          // /n betyder "ny rad" 
                                     getAttributes( f ) );
   }

   private String getAttributes( File f ) {      // hela denna delen är bara "show details" boxen.
      String t = "";
      if ( f.isDirectory() )
        t += "Directory";
      else
        t += "Nondirectory file";
      t += "\n   ";
      if ( !f.canRead() )
        t += "not ";
      t += "Readable\n   ";
      if ( !f.canWrite() )
        t += "not ";
      t += "Writeable\n  ";
      if ( !f.isDirectory() )
        t += "Size in bytes: " + f.length() + "\n   ";
      else {
        t += "Contains files: \n     ";
        String[ ] contents = f.list();
        for ( int i = 0; i < contents.length; i++ )
           t += contents[ i ] + ", ";
        t += "\n";
      } 
      return t;
   }

   public static void main( String[ ] args ) {
       if(args.length>0) katalog=args[0];
       new DirTree();
   }

   
}