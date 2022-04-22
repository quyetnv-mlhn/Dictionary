package src;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import src.Word;
import src.Dictionary;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();

    /**
     * Them tu vung vao tu dien.
     *
     */
    public Dictionary insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng từ cần thêm vào từ điển: ");
        int wordNumber = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < wordNumber; i++) {
            System.out.printf("Nhập từ tiếng Anh [%d]: ", i + 1);
            String en = sc.nextLine();
            System.out.printf("Nhập nghĩa tiếng Việt [%d]: ", i + 1);
            String vi = sc.nextLine();
            Word word = new Word(en, vi);
            for (int j = 0; j < dictionary.listWord.size(); j++) {
                if (en.compareTo(dictionary.listWord.get(j).getWord_target()) < 0) {
                    dictionary.listWord.add(j, word);
                    break;
                }
            }
        }
        System.out.println("Done");
        return dictionary;
    }

    /**
     * Doc du lieu tu file txt.
     *
     */
    public Dictionary insertFromFile(Dictionary dictionary) {
        try {
            File file = new File("src/dictionaries.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split("\t");
                Word wordData = new Word();
                wordData.setWord_target(data[0]);
                wordData.setWord_explain(data[1]);
                dictionary.listWord.add(wordData);
            }
            System.out.println("Done");
        } catch (IOException e) {}
        return dictionary;
    }

    /**
     * Ghi du lieu vao file txt.
     *
     */
    public Dictionary writeToFile(Dictionary dictionary) {
        try {
            File file = new File("src/dictionaries.txt");
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            for (int i = 0; i < dictionary.listWord.size(); i++) {
                outputStreamWriter.write(dictionary.listWord.get(i).getWord_target() + "\t" + dictionary.listWord.get(i).getWord_explain() + "\n");
            }
            outputStreamWriter.flush();
            System.out.println("Done");
        } catch (IOException e) {}
        return dictionary;
    }

    /**
     * Tim kiem tu vung bang thuat toan tim kiem nhi phan.
     * Neu tim thay tra ve vi tri trong danh sach
     * Neu khong tim thay tra ve -1
     */
    public int binarySearch(Dictionary dictionary, String str) {
        int l = 0, r = dictionary.listWord.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (dictionary.listWord.get(m).getWord_target().equals(str)) {
                return m;
            }
            if (dictionary.listWord.get(m).getWord_target().compareTo(str) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    /**
     * Tim kiem tu vung bang cach nhap chinh xac.
     *
     */
    public void dictionaryLookup(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ cần tra cứu: ");
        String word = sc.nextLine();
        int i = binarySearch(dictionary, word);
        if (i == -1) {
            System.out.println("Không tìm thấy từ đã nhập");
        } else {
            System.out.format("%-5s %-50s %-50s \n",
                    i, dictionary.listWord.get(i).getWord_target(), dictionary.listWord.get(i).getWord_explain());
            this.pronounceWord(dictionary.listWord.get(i).getWord_target());
        }
    }

    /**
     * Sua tu vung.
     *
     */
    public src.Dictionary fixWord(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ cần sửa: ");
        String word = sc.nextLine();
        int j = binarySearch(dictionary, word);
        if (j == -1) {
            System.out.println("Không tìm thấy từ đã nhập!");
        } else {
            System.out.format("%-5s %-50s %-50s \n",
                    j + 1, dictionary.listWord.get(j).getWord_target(), dictionary.listWord.get(j).getWord_explain());
            System.out.println("Bạn cần sửa lại: ");
            System.out.println("1. Tiếng Anh");
            System.out.println("2. Tiếng Việt");
            int num = sc.nextInt();
            sc.nextLine();
            if (num == 1) {
                System.out.println("Nhập từ cần thay thế: ");
                String wordNew = sc.nextLine();
                dictionary.listWord.get(j).setWord_target(wordNew);
                System.out.format("%-5s %-50s %-50s \n",
                        j + 1, dictionary.listWord.get(j).getWord_target(), dictionary.listWord.get(j).getWord_explain());
            } else if (num == 2) {
                System.out.println("Nhập từ cần thay thế: ");
                String wordNew = sc.nextLine();
                dictionary.listWord.get(j).setWord_explain(wordNew);
                System.out.format("%-5s %-50s %-50s \n",
                        j + 1, dictionary.listWord.get(j).getWord_target(), dictionary.listWord.get(j).getWord_explain());
            }
        }
        return dictionary;
    }

    /**
     * Xoa tu vung.
     *
     */
    public Dictionary removeWord(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ cần xóa: ");
        String word = sc.nextLine();
        int i = 0;
        while (i < dictionary.listWord.size()) {
            if (dictionary.listWord.get(i).getWord_target().equals(word)) {
                dictionary.listWord.remove(i);
                System.out.println("Done");
                break;
            }
            i++;
        }
        if (i == dictionary.listWord.size()) {
            System.out.println("Không tìm thấy từ đã nhập!");
        }
        return dictionary;
    }

    /**
     * Phat am tu vung.
     *
     */
    public void pronounceWord(String text) {
        Voice voice;
        VoiceManager vm = VoiceManager.getInstance();
        voice = vm.getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
    }
}