/**
 * CSCI 2110
 * @author - Rahul Kumar
 * @description: This program uses to the huffman's algorithm to encode and decode text
 */

import javax.xml.stream.events.Characters;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HuffmanTree {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the name of the file with letters and probability: ");
        String filename = in.nextLine();

        File file = new File(filename);
        Scanner fileReader = new Scanner(file);
        ArrayList<BinaryTree<Pair>> treeList = new ArrayList<>();
        ArrayList<BinaryTree<Pair>> resultList = new ArrayList<>();

        char value = ' ';
        double prob = 0.0;

        while (fileReader.hasNext()) {
            StringTokenizer token = new StringTokenizer(fileReader.nextLine(), "\t");
            while (token.hasMoreTokens()) {
                value = token.nextToken().charAt(0);
                prob = Double.parseDouble(token.nextToken());
            }
            Pair newPair = new Pair(value, prob);
            BinaryTree<Pair> newTree = new BinaryTree<>();
            newTree.makeRoot(newPair);
            treeList.add(newTree);
        }

        BinaryTree<Pair> first = null;
        BinaryTree<Pair> second = null;
        char letter = '0';

        while (!treeList.isEmpty()) {
            // when resultList is empty, use the first two elements of treeList
            if (resultList.isEmpty()) {
                first = treeList.get(0);
                second = treeList.get(1);
                Pair sumOfNodes = new Pair(letter, first.getData().getProb() + second.getData().getProb());
                BinaryTree<Pair> result = new BinaryTree<>();
                result.makeRoot(sumOfNodes);
                first.setParent(result);
                second.setParent(result);
                result.setLeft(first);
                result.setRight(second);
                resultList.add(result);
                treeList.remove(0);
                treeList.remove(0);
            } else {
                if (treeList.size() > 1 && treeList.get(0).getData().getProb() < resultList.get(0).getData().getProb() &&
                        treeList.get(1).getData().getProb() < resultList.get(0).getData().getProb()) {
                    first = treeList.get(0);
                    second = treeList.get(1);
                    Pair sumOfNodes = new Pair(letter, first.getData().getProb() + second.getData().getProb());
                    BinaryTree<Pair> result = new BinaryTree<>();
                    result.makeRoot(sumOfNodes);
                    first.setParent(result);
                    second.setParent(result);
                    result.setLeft(first);
                    result.setRight(second);
                    resultList.add(result);
                    treeList.remove(0);
                    treeList.remove(0);

                } else if (treeList.size() > 1 && treeList.get(0).getData().getProb() < resultList.get(0).getData().getProb() &&
                        treeList.get(1).getData().getProb() > resultList.get(0).getData().getProb()) {
                    first = treeList.get(0);
                    second = resultList.get(0);
                    Pair sumOfNodes = new Pair(letter, first.getData().getProb() + second.getData().getProb());
                    BinaryTree<Pair> result = new BinaryTree<>();
                    result.makeRoot(sumOfNodes);
                    first.setParent(result);
                    second.setParent(result);
                    result.setLeft(first);
                    result.setRight(second);
                    resultList.set(0, result);
                    treeList.remove(0);

                } else if (!treeList.isEmpty() && treeList.get(0).getData().getProb() > resultList.get(0).getData().getProb()) {
                    first = resultList.get(0);
                    second = treeList.get(0);
                    Pair sumOfNodes = new Pair(letter, first.getData().getProb() + second.getData().getProb());
                    BinaryTree<Pair> result = new BinaryTree<>();
                    result.makeRoot(sumOfNodes);
                    first.setParent(result);
                    second.setParent(result);
                    result.setLeft(first);
                    result.setRight(second);
                    resultList.set(0, result);
                    treeList.remove(0);

                } else if (!treeList.isEmpty() && treeList.get(0).getData().getProb() < resultList.get(0).getData().getProb()) {
                    first = resultList.get(1);
                    second = treeList.get(0);
                    Pair sumOfNodes = new Pair(letter, first.getData().getProb() + second.getData().getProb());
                    BinaryTree<Pair> result = new BinaryTree<>();
                    result.makeRoot(sumOfNodes);
                    first.setParent(result);
                    second.setParent(result);
                    resultList.set(0, result);
                    result.setLeft(first);
                    result.setRight(second);
                    treeList.remove(0);
                }
            }
            if (resultList.size() > 1) {
                first = resultList.get(0);
                second = resultList.get(1);
                Pair sumOfNodes = new Pair(letter, first.getData().getProb() + second.getData().getProb());
                BinaryTree<Pair> result = new BinaryTree<>();
                result.makeRoot(sumOfNodes);
                first.setParent(result);
                second.setParent(result);
                result.setLeft(first);
                result.setRight(second);
                resultList.set(0, result);
                resultList.remove(1);
            }
        }

        //Encoding the text
        String[] encodedValues = findEncoding(resultList.get(0));
        System.out.print("Enter a line of text (uppercase letters only): ");
        String text = in.nextLine();
        for (int i = 0; i< text.length(); i++){
            if(text.charAt(i)==' '){
                System.out.print(" ");
            }else {
                System.out.print(encodedValues[text.charAt(i) - 65]);
            }
        }

    }

    private static String[] findEncoding(BinaryTree<Pair> bt) {
        String[] result = new String[26];
        findEncoding(bt, result, "");
        return result;
    }

    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix) {
        // test is node/tree is a leaf
            if (bt.getLeft() == null && bt.getRight() == null) {
                if(bt.getData().getValue()!='0'){
                    a[bt.getData().getValue() - 65] = prefix;
                }
            }

        // recursive calls
        else {
            findEncoding(bt.getLeft(), a, prefix + "0");
            findEncoding(bt.getRight(), a, prefix + "1");
        }
    }

}
