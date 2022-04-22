package src;

import src.Dictionary;
import src.DictionaryManagement;
import sun.misc.JavaLangAccess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class DictionaryApplication implements ActionListener {

    private JFrame mainFrame;
    private JTextField field;
    private JPanel controlPanel;
    private JPanel controlPanel2;
    private JPanel controlPanel3;

    private JButton button;

    /**
     * runApplication.
     *
     */
    public void runApplication() {
        mainFrame = new JFrame("Dictionaries");
        mainFrame.setSize(400, 600);
        mainFrame.setLayout(new GridLayout(2, 1));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlPanel2 = new JPanel();
        controlPanel2.setLayout(new FlowLayout());
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        Dictionary dictionary = new Dictionary();
        dictionaryManagement.insertFromFile(dictionary);

        field = new JTextField();
        field.setPreferredSize(new Dimension(150,30));
        button = new JButton("Translate");
        button.addActionListener(this);
        controlPanel2.add(field);
        controlPanel2.add(button);

        mainFrame.add(controlPanel);
        mainFrame.add(controlPanel2);

        DefaultListModel colorsName = new DefaultListModel();
        for (int i = 0 ; i < dictionary.listWord.size(); i++) {
            String word = String.format("%-40s%40s", dictionary.listWord.get(i).getWord_target(), dictionary.listWord.get(i).getWord_explain());
            colorsName.addElement(word);
        }
        final JList wordList = new JList(colorsName);
        wordList.setVisibleRowCount(15);
        JScrollPane wordListScrollPane = new JScrollPane(wordList);

        controlPanel.add(wordListScrollPane);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * xu ly su kien ghi bam nut button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            DictionaryManagement dictionaryManagement = new DictionaryManagement();
            Dictionary dictionary = new Dictionary();
            dictionaryManagement.insertFromFile(dictionary);

            JFrame newWindows = new JFrame("Translate");
            JButton voice = new JButton("voice" );

            String word_target = field.getText();
            String word_explain = "";
            JLabel label;
            if (dictionaryManagement.binarySearch(dictionary, word_target) != -1) {
                word_explain = dictionary.listWord.get(dictionaryManagement.binarySearch(dictionary, word_target)).getWord_explain();
            }
            if (word_explain.equals("") == false) {
                label = new JLabel( "                          " + word_target + "              " + word_explain);
            } else {
                label = new JLabel("                           Không tìm thấy từ");
            }

            String finalWord_explain = word_explain;
            voice.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == voice) {
                        if (finalWord_explain.equals("") == false) {
                            dictionaryManagement.pronounceWord(word_target);
                        } else {
                            dictionaryManagement.pronounceWord("no found word");
                        }
                    }
                }
            });

            newWindows.setSize(300, 150);
            newWindows.setLayout(new GridLayout(2, 1));
            newWindows.add(label);
            newWindows.add(voice, BorderLayout.CENTER);
            newWindows.setLocationRelativeTo(null);
            newWindows.setVisible(true);
        }
    }
}