package com.whl.rbtree;


public class Main {

    public static void main(String[] args) {
       Node root =  insertNode();
    }

    public static Node insertNode(){
        RBTree rbTree = new RBTree();

        Node node1 = new Node();
        node1.val = 11;

        Node node2 = new Node();
        node2.val = 5;

        Node node3 = new Node();
        node3.val = 14;

        Node node4 = new Node();
        node4.val = 3;

        Node node5 = new Node();
        node5.val = 17;

        rbTree.insert(node1);
        rbTree.insert(node2);
        rbTree.insert(node3);
        rbTree.insert(node4);
       return rbTree.insert(node5);
    }


}