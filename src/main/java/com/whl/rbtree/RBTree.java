package com.whl.rbtree;

public class RBTree {

    public Node root;


    public Node insert(Node newNode) {
        if (newNode == null) {
            return root;
        }
        if (root == null) {
            root = newNode;
        } else {

            //寻找可插入的位置
            Node parent = root;
            Node node = parent;
            while (node != null) {
                if (newNode.val > parent.val) {
                    node = parent.right;
                    if (node != null) {
                        parent = parent.right;
                    }

                }
                if (newNode.val < parent.val) {
                    node = parent.left;
                    if (node != null) {
                        parent = parent.left;
                    }

                }
            }
            if (newNode.val > parent.val) {
                parent.right = newNode;
            } else if (newNode.val < parent.val) {
                parent.left = newNode;
            }
            newNode.parent = parent;
        }

        fixInsert(newNode);

        return root;
    }

    public void fixInsert(Node newNode) {
        //1.如果新节点为根节点
        if (newNode == root) {
            root.isBlack = true;
            return;
        }
        //2. 如果插入节点的父节点为黑色节点，直接插入即可
        if (newNode.parent.isBlack) {
            return;
        }

        //3. 如果插入节点的父节点为红色节点

        //3.1 如果父节点是祖父节点的左子树
        if (newNode.parent == newNode.parent.parent.left) {

            //3.1.1 如果插入节点的叔父节点存在并且为红色节点
            Node uncleNode = newNode.parent.parent.right;
            if (uncleNode != null && !uncleNode.isBlack) {
                newNode.parent.isBlack = true;
                uncleNode.isBlack = true;
                uncleNode.parent.isBlack = false;
                fixInsert(uncleNode.parent);

            } else {
                // 3.1.2 如果插入节点的叔父节点不存在或者为黑色

                if (newNode == newNode.parent.left) {
                    /**
                     * 3.1.2.1 如果插入节点是父节点的左子树
                     * 对父节点进行右旋操作
                     * 将父节点设置为黑色
                     * 将父节点的两个子节点设置为红色，这里因为刚插入的节点肯定是红色，所以只需要将父节点的右子节点设置为红色即可
                     */
                    Node node = rightRotate(newNode.parent);
                    node.isBlack = true;
                    node.right.isBlack = false;
                } else if (newNode == newNode.parent.right) {
                    /**
                     * 3.1.2.2 如果插入节点是父节点的右子树
                     * 对新节点进行左旋操作
                     * 对新节点进行右旋操作
                     * 将新节点设置为黑色
                     * 将新节点的右子节点设置成红色
                     */
                    Node node = leftRotate(newNode);
                    node = rightRotate(node);
                    node.isBlack = true;
                    node.right.isBlack = false;
                }
            }
        } else {
            //3.2 如果父节点是祖父节点的右子树
            //3.2.1 如果插入节点的叔父节点存在并且为红色节点
            Node uncleNode = newNode.parent.parent.left;
            if (uncleNode != null && !uncleNode.isBlack) {
                newNode.parent.isBlack = true;
                uncleNode.isBlack = true;
                uncleNode.parent.isBlack = false;
                fixInsert(uncleNode.parent);

            } else {
                // 3.2.2 如果插入节点的叔父节点不存在或者为黑色

                if (newNode == newNode.parent.right) {
                    //3.2.2.1如果新插入节点是祖父节点的右子节点
                    /**
                     * 对新插入节点的父节点进行左旋操作
                     * 将新插入节点的父节点设置为黑色
                     * 将新插入节点的父节点的左子节点设置为红色
                     */
                    Node node = leftRotate(newNode.parent);
                    node.isBlack = true;
                    node.left.isBlack = false;
                } else if (newNode == newNode.parent.left) {
                    //3.2.2.2 如果新插入节点是祖父节点的左子节点
                    /**
                     * 对新插入节点进行右旋操作
                     * 对新插入节点进行左旋操作
                     * 将新节点设置为黑色
                     * 将新节点的左子节点设置为红色
                     */
                    Node node = rightRotate(newNode);
                    node = leftRotate(node);
                    node.isBlack = true;
                    node.left.isBlack = false;
                }

            }
        }


        //将根节点设置为黑色
        root.isBlack = true;
    }


    /**
     * 左旋
     */
    public Node leftRotate(Node node) {

        Node parent = node.parent;
        Node pParent = parent.parent;

        if (pParent != null) {
            if (pParent.left == parent) {
                pParent.left = node;
            } else {
                pParent.right = node;
            }
        }

        node.parent = pParent;
        if (node.left != null) {
            parent.right = node.left;
        }
        node.left = parent;
        parent.parent = node;

        return node;
    }

    /**
     * 右旋
     */
    public Node rightRotate(Node node) {
        Node parent = node.parent;
        Node pParent = parent.parent;

        if (pParent != null) {
            if (pParent.left == parent) {
                pParent.left = node;
            } else {
                pParent.right = node;
            }
        }


        node.parent = pParent;
        if (node.right != null) {
            parent.left = node.right;
        }
        node.right = parent;
        parent.parent = node;
        return node;
    }


}