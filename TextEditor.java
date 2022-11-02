import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class TextEditor extends  JFrame implements ActionListener {

     JTextArea textArea ;
     JScrollPane scrollPane ; // scrollpane to scriing text

     JSpinner  fontSizeSpinner ; // to manage fontSize

     JLabel fontlabel ; // label for font dpinner

     JButton fontColorButton ; // to choose a color using button

     JComboBox fontBox ;   // les the user choose which font they want

     JMenuBar menuBar ;
     JMenu fileMenu  ;
     JMenuItem openItem ;
     JMenuItem saveItem ;
     JMenuItem exitItem ;




       TextEditor(){
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           this.setTitle("My text Editor ");
           this.setSize(500,500);
           this.setLayout(new FlowLayout());
           this.setVisible(true);
           this.setLocationRelativeTo(null);
           textArea = new JTextArea() ;
           //textArea.setPreferredSize( new Dimension(450,450 )); no nees when we do it for scrollpane
           textArea.setLineWrap(true); // it moves from onr linr to nextone when over
           textArea.setWrapStyleWord(true);
           textArea.setFont(new Font("Arial",Font.PLAIN,20)); // add the size of the text
           this.setVisible(true);
           //this.add(textArea); // no need when textarea addet to scrollpna nd that to frame
           scrollPane = new JScrollPane(textArea) ; // we added textArea to scrollPane
           scrollPane.setPreferredSize( new Dimension(450,450 ));
           scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

           fontlabel = new JLabel("Font : ") ;
           // we have to allow the user to change the font
               fontSizeSpinner = new JSpinner() ;
               fontSizeSpinner.setPreferredSize(new Dimension(50 , 25)); // this is the range of font Size spinner value
               fontSizeSpinner.setValue(20); // set our default fontSize spinner
               fontSizeSpinner.addChangeListener(new ChangeListener() {
                   @Override
                   public void stateChanged(ChangeEvent e) {
                       textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
                   }
               });
           fontColorButton = new JButton("Color") ;
           fontColorButton.addActionListener(this);
           String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames() ;
        // this will get all the fontsize aavailable in java in font array
           fontBox = new JComboBox(font) ; // we need to creat an option for that lets create an array with all available option for fontbox
           fontBox.addActionListener(this);
           fontBox.setSelectedItem("Arial"); // we can set initially selected value in combobox "arial'
           // ......................Menubar .......................................
             menuBar = new JMenuBar() ;
             fileMenu = new JMenu("File") ;
             openItem = new JMenuItem("Open") ;
             saveItem = new JMenuItem("Save") ;
             exitItem = new JMenuItem("Exit") ;
             // add all this menu to file menu and file menu to menuBar and finally that menu Bar to Frame
             // add actionlistener
            openItem.addActionListener(this);
            saveItem.addActionListener(this);
            exitItem.addActionListener(this) ;

           fileMenu.add(openItem) ;
           fileMenu.add(saveItem) ;
           fileMenu.add(exitItem) ;
           menuBar.add(fileMenu) ;

           // ......................Menubar .......................................
            this.setJMenuBar(menuBar); // adding menuBar to frame
           //this.add(menuBar) ;  this also work
            this.add(fontlabel) ;
           this.add(fontSizeSpinner);
           this.add(fontColorButton) ;
           this.add(fontBox) ;
           this.add(scrollPane); // now added scrollPane to frame
           this.setVisible(true);
          //  now to add label to search text in textArea  after scrollpane


       }



    @Override
 public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fontColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a Color ", Color.BLACK);
            textArea.setForeground(color);
        }
        if (e.getSource() == fontBox) {
            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }
        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser() ;
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter =  new FileNameExtensionFilter("Test files " , "txt") ;
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null) ;

            if(response == JFileChooser.APPROVE_OPTION ){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath()) ;
                 Scanner fileIn = null ;
                try {
                    fileIn = new Scanner(file) ;
                    if(file.isFile()) {
                        while(fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n" ;
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileIn.close();
                }
            }
        }
        if (e.getSource() == saveItem){
            // with the help of Jfile chooser class , we will select our file location to save our file
             JFileChooser fileChooser = new JFileChooser() ;
              fileChooser.setCurrentDirectory(new File(".")); // Dafault file location to your project folder  or file path C: .......etc
               int responce = fileChooser.showSaveDialog(null) ;
               if(responce == JFileChooser.APPROVE_OPTION /* 0 */ ){
                    File file ;
                   PrintWriter  fileOut = null ;
                   file = new File(fileChooser.getSelectedFile().getAbsolutePath()) ;
                   try{
                       fileOut = new PrintWriter(file);
                       fileOut.println(textArea.getText()) ;
                   }catch (FileNotFoundException e1){
                       // TODO auto generated catch block
                       e1.printStackTrace();
                   }
                   finally {
                       fileOut.close();                   }
               }
        }
        if(e.getSource() == exitItem ){
               System.exit(0);
        }


 }
}