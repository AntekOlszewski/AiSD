package pl.edu.pw.ee;

import static pl.edu.pw.ee.Color.BLACK;
import static pl.edu.pw.ee.Color.RED;

public class RedBlackTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int putRecursionCounter;

    public RedBlackTree() {
        putRecursionCounter = 0;
    }

    public int getRecursionCounter() {
        return putRecursionCounter;
    }

    public void resetRecursionCounter() {
        putRecursionCounter = 0;
    }

    public V get(K key) {
        validateKey(key);
        Node<K, V> node = root;

        V result = null;

        while (node != null) {

            if (shouldCheckOnTheLeft(key, node)) {
                node = node.getLeft();

            } else if (shouldCheckOnTheRight(key, node)) {
                node = node.getRight();

            } else {
                result = node.getValue();
                break;
            }
        }
        return result;
    }

    public void put(K key, V value) {
        validateParams(key, value);
        root = put(root, key, value);
        root.setColor(BLACK);
    }

    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
    }

    private boolean shouldCheckOnTheLeft(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private boolean shouldCheckOnTheRight(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    private void validateParams(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Input params (key, value) cannot be null.");
        }
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        putRecursionCounter++;
        if (node == null) {
            return new Node<>(key, value);
        }

        if (isKeyBiggerThanNode(key, node)) {
            putOnTheRight(node, key, value);

        } else if (isKeySmallerThanNode(key, node)) {
            putOnTheLeft(node, key, value);

        } else {
            node.setValue(value);
        }

        node = reorganizeTree(node);

        return node;
    }

    private boolean isKeyBiggerThanNode(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    private void putOnTheRight(Node<K, V> node, K key, V value) {
        Node<K, V> rightChild = put(node.getRight(), key, value);
        node.setRight(rightChild);
    }

    private boolean isKeySmallerThanNode(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private void putOnTheLeft(Node<K, V> node, K key, V value) {
        Node<K, V> leftChild = put(node.getLeft(), key, value);
        node.setLeft(leftChild);
    }

    private Node<K, V> reorganizeTree(Node<K, V> node) {
        node = rotateLeftIfNeeded(node);
        node = rotateRightIfNeeded(node);
        changeColorsIfNeeded(node);

        return node;
    }

    private Node<K, V> rotateLeftIfNeeded(Node<K, V> node) {
        if (isBlack(node.getLeft()) && isRed(node.getRight())) {
            node = rotateLeft(node);
        }
        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        Node<K, V> head = node.getRight();
        node.setRight(head.getLeft());
        head.setLeft(node);
        head.setColor(node.getColor());
        node.setColor(RED);

        return head;
    }

    private Node<K, V> rotateRightIfNeeded(Node<K, V> node) {
        if (isRed(node.getLeft()) && isRed(node.getLeft().getLeft())) {
            node = rotateRight(node);
        }
        return node;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        Node<K, V> head = node.getLeft();
        node.setLeft(head.getRight());
        head.setRight(node);
        head.setColor(node.getColor());
        node.setColor(RED);

        return head;
    }

    private void changeColorsIfNeeded(Node<K, V> node) {
        if (isRed(node.getLeft()) && isRed(node.getRight())) {
            changeColors(node);
        }
    }

    private void changeColors(Node<K, V> node) {
        if (node != null) {
            if (node.isRed()) {
                node.setColor(BLACK);
            } else {
                node.setColor(RED);
            }
        }

        if (node.getLeft() != null) {
            if (node.getLeft().isRed()) {
                node.getLeft().setColor(BLACK);
            } else {
                node.getLeft().setColor(RED);
            }
        }

        if (node.getRight() != null) {
            if (node.getRight().isRed()) {
                node.getRight().setColor(BLACK);
            } else {
                node.getRight().setColor(RED);
            }
        }
    }

    private boolean isBlack(Node<K, V> node) {
        return !isRed(node);
    }

    private boolean isRed(Node<K, V> node) {
        return node == null
                ? false
                : node.isRed();
    }

    public String getPreOrder() {
        if (root == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        preOrder(root, result);
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    private void preOrder(Node<K, V> node, StringBuilder string) {
        if (node == null) {
            return;
        }
        string.append(node.getKey() + ":" + node.getValue() + " ");
        preOrder(node.getLeft(), string);
        preOrder(node.getRight(), string);
    }

    public String getInOrder() {
        if (root == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        inOrder(root, result);
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    private void inOrder(Node<K, V> node, StringBuilder string) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft(), string);
        string.append(node.getKey() + ":" + node.getValue() + " ");
        inOrder(node.getRight(), string);
    }

    public String getPostOrder() {
        if (root == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        postOrder(root, result);
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    private void postOrder(Node<K, V> node, StringBuilder string) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeft(), string);
        postOrder(node.getRight(), string);
        string.append(node.getKey() + ":" + node.getValue() + " ");
    }

    public int getNElems() {
        if (root != null) {
            int i = 1;
            return nElems(root, i);
        }
        return 0;
    }

    private int nElems(Node<K, V> node, int i) {
        if (node == null) {
            return 0;
        }
        i += nElems(node.getLeft(), i);
        i += nElems(node.getRight(), i);
        return i;
    }

    public void deleteMax() {
        if (root == null) {
            return;
        }
        root = deleteMax(root);
    }

    private Node<K, V> deleteMax(Node<K, V> node) {
        if (node.getLeft() != null && node.getLeft().getColor() == RED) {
            node = rotateRight(node);
        }
        if (node.getRight() == null) {
            return null;
        }
        if (node.getRight() != null && node.getRight().getLeft() != null && !node.getRight().isRed()
                && !node.getRight().getLeft().isRed()) {
            changeColors(node);
            if (node.getLeft() != null && node.getLeft().getLeft() != null) {
                if (node.getLeft().getLeft().isRed()) {
                    node = rotateRight(node);
                }
            }
        }
        node.setRight(deleteMax(node.getRight()));
        return reorganizeTree(node);
    }
}
