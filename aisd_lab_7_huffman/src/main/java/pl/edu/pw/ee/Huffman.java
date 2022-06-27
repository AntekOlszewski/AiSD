package pl.edu.pw.ee;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Huffman {

    private static class Node implements Comparable<Node> {
        private int frequency;
        private char character;
        private Node leftChild;
        private Node rightChild;
        private final Node nil = null;

        public Node(char character, int frequency) {
            this.frequency = frequency;
            this.character = character;
            this.leftChild = nil;
            this.rightChild = nil;
        }

        public static Node merge(Node nodeA, Node nodeB) {
            Node head = new Node('0', nodeA.frequency + nodeB.frequency);
            head.setLeftChild(nodeA);
            head.setRightChild(nodeB);
            return head;
        }

        public void setLeftChild(Node child) {
            this.leftChild = child;
        }

        public Node getLeftChild() {
            return this.leftChild;
        }

        public void setRightChild(Node child) {
            this.rightChild = child;
        }

        public Node getRightChild() {
            return this.rightChild;
        }

        @Override
        public int compareTo(Node o) {
            return this.frequency - o.frequency;
        }

        public static void getCodes(Node root, List<DictElem> dictionary) {
            if (root == null) {
                return;
            }
            if (Node.isLeaf(root)) {
                DictElem newElem = new DictElem(root.character, "0");
                dictionary.add(newElem);
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            getCodes(root.getLeftChild(), '0', stringBuilder, dictionary);
            getCodes(root.getRightChild(), '1', stringBuilder, dictionary);

        }

        private static void getCodes(Node node, char code, StringBuilder stringBuilder, List<DictElem> dictionary) {
            if (node == null) {
                return;
            }
            StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
            stringBuilder2.append(code);

            if (Node.isLeaf(node)) {
                dictionary.add(new DictElem(node.character, stringBuilder2.toString()));
                return;
            }

            getCodes(node.getLeftChild(), '0', stringBuilder2, dictionary);
            getCodes(node.getRightChild(), '1', stringBuilder2, dictionary);
        }

        private static boolean isLeaf(Node node) {
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return true;
            }
            return false;
        }
    }

    private static class DictElem {
        private char character;
        private String code;

        private DictElem(char c, String code) {
            this.character = c;
            this.code = code;
        }
    }

    private class Elem {
        private char character;
        private int frequency;

        private Elem(char c, int f) {
            this.character = c;
            this.frequency = f;
        }
    }

    private class HashMap {
        private Elem[] elems;
        private int nElems;
        private final double correctLoadFactor = 0.75;

        public HashMap() {
            this.elems = new Elem[127];
            this.nElems = 0;
        }

        public void put(char c) {
            reSizeIfNeeded();

            int key = c;
            int i = 0;
            int hashId = hashFunc(key, i);

            while (elems[hashId] != null && elems[hashId].character != c) {
                i = (i + 1) % nElems;
                hashId = hashFunc(key, i);
            }

            if (elems[hashId] == null || elems[hashId].character != c) {
                nElems++;
                elems[hashId] = new Elem(c, 1);
            }

            elems[hashId].frequency++;
        }

        private void put(char c, int f) {
            int key = c;
            int i = 0;
            int hashId = hashFunc(key, i);

            while (elems[hashId] != null && elems[hashId].character != c) {
                i = (i + 1) % nElems;
                hashId = hashFunc(key, i);
            }

            elems[hashId] = new Elem(c, f);
        }

        private void reSizeIfNeeded() {
            if (nElems / elems.length >= correctLoadFactor) {
                Elem[] newElems = new Elem[elems.length * 2];
                for (Elem elem : elems) {
                    if (elem != null) {
                        put(elem.character, elem.frequency);
                    }
                }
                this.elems = newElems;
            }
        }

        private int hashFunc(int key, int i) {
            int m = elems.length;
            int hash = (firstHash(key) + i * secondHash(key)) % m;
            hash = hash < 0 ? -hash : hash;
            return hash;
        }

        private int firstHash(int key) {
            int m = elems.length;
            return key % m;
        }

        private int secondHash(int key) {
            int m = elems.length;
            return 1 + 2 * (key % (m - 3));
        }
    }

    private void mergeForestIntoTree(List<Node> forest) {

        if (forest.size() <= 1) {
            return;
        }

        else {
            while (forest.size() > 1) {
                Collections.sort(forest);
                forest.add(Node.merge(forest.get(0), forest.get(1)));
                forest.remove(1);
                forest.remove(0);
            }
        }
    }

    private int compress(String pathToRootDir) throws FileNotFoundException, IOException {
        List<Node> forest = new ArrayList<>();
        HashMap characterOcurrances = new HashMap();

        Path fileName = Path.of(pathToRootDir + "input.txt");
        String content = Files.readString(fileName);
        for (char character : content.toCharArray()) {
            characterOcurrances.put(character);
        }

        for (Elem elem : characterOcurrances.elems) {
            if (elem != null) {
                forest.add(new Node(elem.character, elem.frequency));
            }
        }

        mergeForestIntoTree(forest);
        List<DictElem> dictionary = new ArrayList<>();
        if (forest.size() == 1) {
            Node.getCodes(forest.get(0), dictionary);
        }

        StringBuilder binaryCode = new StringBuilder();

        for (char c : content.toCharArray()) {
            for (int i = 0; i < dictionary.size(); i++) {
                if (c == dictionary.get(i).character) {
                    binaryCode.append(dictionary.get(i).code);
                    break;
                }
            }
        }

        int emptyBits = saveCompressedData(pathToRootDir, binaryCode.toString());
        saveDictionary(pathToRootDir, dictionary, emptyBits);

        return binaryCode.length() + emptyBits;
    }

    private int saveCompressedData(String pathToRootDir, String data) throws IOException {
        int emptyBits = 8 - (data.length() % 8);
        Path fileName = Path.of(pathToRootDir + "compressed.txt");
        byte[] bytes = new byte[data.length() / 8 + 1];

        for (int i = 0; i < emptyBits; i++) {
            data += "0";
        }
        for (int i = 0; i < data.length(); i += 8) {
            String s_byte = data.substring(i, i + 8);
            byte byte_data = (byte) Integer.parseInt(s_byte, 2);
            bytes[i / 8] = byte_data;
        }
        Files.write(fileName, bytes);

        return emptyBits;
    }

    private int decompress(String pathToRootDir)
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        List<DictElem> dictionary = new ArrayList<>();
        int emptyBits = readDictionary(pathToRootDir, dictionary);
        if (dictionary.size() == 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToRootDir + "output.txt"));
            writer.write("");
            writer.close();

            return 0;
        }

        StringBuilder contentSb = new StringBuilder();
        try {
            FileInputStream in = new FileInputStream(pathToRootDir + "compressed.txt");
            BufferedInputStream bStream = new BufferedInputStream(in);
            int c;
            String data;
            while ((c = bStream.read()) != -1) {
                data = Integer.toBinaryString(c);
                data = addLeftZeros(data, 8);
                contentSb.append(data);
            }
            bStream.close();
        } catch (Exception e) {
            System.out.println("Error opening compressed data file!");
        }
        String binaryCode = contentSb.toString();

        int shortestCode = dictionary.get(0).code.length();
        int longestCode = dictionary.get(0).code.length();
        for (DictElem elem : dictionary) {
            if (elem.code.length() < shortestCode) {
                shortestCode = elem.code.length();
            }
            if (elem.code.length() > longestCode) {
                longestCode = elem.code.length();
            }
        }

        int i = 0;
        StringBuilder decompressed = new StringBuilder();
        while (i < binaryCode.length() - emptyBits) {
            boolean falg = false;
            for (int j = shortestCode; j <= longestCode; j++) {
                falg = false;
                String code = binaryCode.substring(i, i + j);
                for (DictElem elem : dictionary) {
                    if (elem.code.equals(code)) {
                        falg = true;
                        decompressed.append(elem.character);
                        i += j;
                        break;
                    }
                }
                if (falg) {
                    break;
                }
            }
        }

        int result = decompressed.length();
        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToRootDir + "output.txt"));
        writer.write(decompressed.toString());
        writer.close();

        return result;
    }

    private String addLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    private void saveDictionary(String pathToRootDir, List<DictElem> dictionary, int empytBits) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(empytBits + ",");
        for (DictElem dictElem : dictionary) {
            stringBuilder.append((int) dictElem.character);
            int lenght = dictElem.code.length();
            int decimal = Integer.parseInt(dictElem.code, 2);
            String hexStr = Integer.toString(decimal, 16);
            stringBuilder.append(':' + hexStr + ':' + lenght + ',');
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToRootDir + "dictionary.txt"));
        writer.write(stringBuilder.toString());
        writer.close();
    }

    private int readDictionary(String pathToRootDir, List<DictElem> dictionary)
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String line;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathToRootDir + "dictionary.txt")))) {
            line = in.readLine();
        }
        String[] dictionaryEntries = line.split(",");
        int emptyBits = Integer.parseInt(dictionaryEntries[0]);
        for (int i = 1; i < dictionaryEntries.length; i++) {
            String[] values = dictionaryEntries[i].split(":");
            char character = (char) (Integer.parseInt(values[0]));
            String hexStr = values[1];
            int decimal = Integer.parseInt(hexStr, 16);
            String binStr = Integer.toBinaryString(decimal);
            binStr = addLeftZeros(binStr, Integer.parseInt(values[2]));
            dictionary.add(new DictElem(character, binStr));
        }
        return emptyBits;
    }

    public int huffman(String pathToRootDir, boolean compress) throws FileNotFoundException, IOException {
        int result;
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Path can't be null!");
        }

        if (compress) {
            File input = new File(pathToRootDir + "input.txt");
            if (!input.exists()) {
                throw new IllegalArgumentException("Input file doesn't exist in given directory!");
            }
            result = compress(pathToRootDir);
        }

        else {
            File dictionary = new File(pathToRootDir + "dictionary.txt");
            if (!dictionary.exists()) {
                throw new IllegalArgumentException("Dictionary file doesn't exist in given directory!");
            }
            File compressed = new File(pathToRootDir + "compressed.txt");
            if (!compressed.exists()) {
                throw new IllegalArgumentException("Compressed file doesn't exist in given directory!");
            }
            result = decompress(pathToRootDir);
        }

        return result;
    }
}
