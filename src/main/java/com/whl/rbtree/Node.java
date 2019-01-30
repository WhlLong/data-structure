package com.whl.rbtree;

public class Node {

    public int val;
    public Node left;    //左子节点
    public Node right;   //右子节点
    public Node parent;  //父节点
    public boolean isBlack; //是否红色节点,默认false,即默认为红色节点

}