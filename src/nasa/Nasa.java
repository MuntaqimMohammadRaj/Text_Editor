package nasa;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Modifier;
import java.util.StringTokenizer;
import javax.swing.text.DefaultEditorKit;

public class Nasa extends JFrame implements ActionListener {

    JTextArea textArea;
    JMenuBar menuBar;
    JMenu menuFile, menuEdit, menuAbout;

    JMenuItem Open, Save, SaveAs, Exit, cut, copy, paste, team, selectall;
    JFileChooser fileChooser;
    File fp;
    ImageIcon icon;
    BufferedWriter bufferedWriter = null;

    Nasa() {

        initComponents();
        addComponents();
        addlistener();
    }

    private void initComponents() {

        //about textarea
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setFont(new Font("Cambria", Font.PLAIN, 14));

        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuAbout = new JMenu("About");

        Open = new JMenuItem("Open");
        Save = new JMenuItem("Save");
        SaveAs = new JMenuItem("Save as");
        Exit = new JMenuItem("Exit");
        team = new JMenuItem("Team");
        //selectall=new JMenuItem("SelectAll");

        cut = new JMenuItem(new DefaultEditorKit.CutAction());

        copy = new JMenuItem(new DefaultEditorKit.CopyAction());

        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text file only", "txt"));
        fileChooser.setDialogTitle("Choose a text file");
        icon = new ImageIcon(getClass().getResource("note.png"));
        this.setIconImage(icon.getImage());

        //Add shortCut to the menu item
        Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        //Add menu item in the menu
        menuFile.add(Open);
        menuFile.add(Save);
        menuFile.add(SaveAs);
        menuFile.add(Exit);

        menuEdit.add(cut);
        cut.setText("Cut");
        copy.setText("Copy");
        paste.setText("Paste");

        menuEdit.add(copy);
        menuEdit.add(paste);
        //menuEdit.add(selectall);

        menuAbout.add(team);

        // setting font of menu
        menuFile.setFont(new Font("Cambria", Font.PLAIN, 14));
        menuEdit.setFont(new Font("Cambria", Font.PLAIN, 14));
        menuAbout.setFont(new Font("Cambria", Font.PLAIN, 14));

        //setting font of menuitem
        JMenuItem[] item = {Open, Save, SaveAs, Exit, cut, copy, paste, team};

        for (int i = 0; i < item.length; i++) {

            item[i].setFont(new Font("Cambria", Font.PLAIN, 14));
        }

        //Add menu in the menu bar
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuAbout);

    }

    private void addComponents() {
        setJMenuBar(menuBar);
        getContentPane().add(textArea);
        getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void addlistener() {
        Open.addActionListener(this);
        Save.addActionListener(this);
        SaveAs.addActionListener(this);
        Exit.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        team.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Exit) {
            System.exit(1);
        } else if (e.getSource() == Save) {

            save();

        } else if (e.getSource() == Open) {
            String line, total = "";
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fp = fileChooser.getSelectedFile();
                setTitle(fp.getName());
                setFont(new Font("Cambria", Font.PLAIN, 14));
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(fp));
                    while ((line = bufferedReader.readLine()) != null) {

                        total += line + "\n";
                    }

                } catch (Exception err) {

                } finally {
                    textArea.setText(total);
                }
            }

        } else if (e.getSource() == SaveAs) {
            saveAs();
        } else if (e.getSource() == team) {
            JOptionPane jp = new JOptionPane();
            jp.showMessageDialog(this, "Nasar_Muntaqim_Omi_Suma", "The Brogrammers", 1);

        } else if (e.getSource() == selectall) {

            String content = textArea.getText();

        }
    }

    private void save() {

        if (fp == null) {

            saveAs();
        } else {

            try {
                bufferedWriter = new BufferedWriter(new FileWriter(fp));
                String s = textArea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    bufferedWriter.write(st.nextToken());
                    bufferedWriter.newLine();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    JOptionPane.showMessageDialog(this, "Your file is saved");

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private void saveAs() {

        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fp = fileChooser.getSelectedFile();
            setTitle(fp.getName());
            setFont(new Font("Cambria", Font.PLAIN, 14));
            save();
        }
    }

}

class Mai {

    public static void main(String args[]) {

        Nasa text = new Nasa();

        text.setVisible(true);
        text.setTitle("Notepad");
        text.setFont(new Font("Cambria", Font.PLAIN, 14));
        text.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        text.setBounds(100, 10, 600, 700);
    }
}
