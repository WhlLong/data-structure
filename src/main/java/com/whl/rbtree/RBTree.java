package com.whl.rbtree;

public class RBTree {

    public Node root;


    /**
     * 新增节点
     *
     * @param newNode
     * @return
     */
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

    /**
     * 新增节点后维护平衡
     *
     * @param newNode
     */
    private void fixInsert(Node newNode) {
        //1.如果新节点为根节点，将根节点设置为黑色
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
     * 删除节点
     *
     * @param val
     */
    public void remove(int val) {

        //根据值获取指定节点
        Node removeNode = select(val);

        //1.如果待删除的节点有两个子节点
        if (removeNode.left != null && removeNode.right != null) {
            //获取后继节点
            Node successorNode = getSuccessorNode(removeNode);

            removeNode.val = successorNode.val;

            fixRemove(successorNode);

        } else {
            fixRemove(removeNode);
        }

    }


    /**
     * 删除节点后维护平衡
     *
     * @param removeNode
     */
    private void fixRemove(Node removeNode) {
        Node parent = removeNode.parent;
        if (parent == null) {
            //0.如果待删除节点的父节点为空，说明该节点是根节点
            if (removeNode.left != null) {
                // 这里的右节点一定为空，所以只需要判断左节点即可
                root = removeNode.left;
            } else {
                root = null;
            }

            return;
        }


        // 1.如果待删除的节点是红色节点
        if (!removeNode.isBlack) {
            if (removeNode.left == null && removeNode.right == null) {
                //1.1如果待删除的后继节点没有子节点，直接删除即可

                if (removeNode == parent.left) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }

            } else if (removeNode.left != null && removeNode.right != null) {
                //1.2 如果待删除节点左子树不为空，右子树为空
                if (removeNode == parent.left) {
                    parent.left = removeNode.left;
                } else {
                    parent.right = removeNode.left;
                }

            } else if (removeNode.left != null && removeNode.right != null) {
                //1.3 如果待删除节点左子树为空，右子树不为空
                if (removeNode == parent.left) {
                    parent.left = removeNode.right;
                } else {
                    parent.right = removeNode.right;
                }
            }

        } else {
            // 2.如果待删除的节点是黑色节点

            if (removeNode == parent.left) {
                // 2.1如果待删除节点是父节点的左节点

                if (parent.right != null) {
                    if (!parent.right.isBlack) {
                        //2.1.1 待删除节点的兄弟节点是红色节点
                        /**
                         * 将兄弟节点设置为黑色
                         * 将父节点设置为红色
                         * 对兄弟节点进行一次左旋操作
                         * 进行2.1.2.3步骤的操作
                         */
                        parent.right.isBlack = true;
                        parent.isBlack = false;
                        leftRotate(parent.right);
                        //进行2.1.2.3步骤的操作
                    } else {

                        //2.1.2 待删除节点的兄弟节点是黑色节点

                        if (parent.right.right != null && !parent.right.right.isBlack) {
                            //2.1.2.1 待删除节点的兄弟节点的右子节点为红色节点
                            /**
                             * 将兄弟节点的颜色设置为父节点的颜色
                             * 将父节点的颜色设置为黑色
                             * 将兄弟节点的右子节点颜色设置为黑色
                             * 对兄弟节点进行左旋
                             */
                            parent.right.isBlack = parent.isBlack;
                            parent.isBlack = true;
                            parent.right.right.isBlack = true;
                            leftRotate(parent.right);
                        } else if (parent.right.left != null && parent.right.right != null
                                && parent.right.left.isBlack == false && parent.right.right.isBlack == true) {
                            //2.1.2.2 待删除节点的兄弟节点的左子节点为红色，右子节点为黑色节点
                            /**
                             * 将兄弟节点的左子节点设置为黑色、
                             * 将兄弟节点设置为红色
                             * 对兄弟节点的左子节点尽心过一次右旋操作，得到情况2.1.2.1
                             * 按照情景2.1.2.1处理
                             */
                            parent.right.left.isBlack = true;
                            parent.right.isBlack = false;
                            rightRotate(parent.right.left);
                        }else{
                            //2.1.2.3 待删除节点的兄弟节点的左子节点为黑色，右子节点为黑色
                            //这里也可以考虑为兄弟节点及其左右子节点均不存在
                            /**
                             * 将兄弟节点设置为红色
                             */
                        }


                    }

                }

            }


        }


        //2.如果
    }

    /**
     * 获取后继节点
     *
     * @param node
     * @return
     */
    private Node getSuccessorNode(Node node) {

        if (node != null && node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }


        return null;
    }


    /**
     * 左旋
     */
    private Node leftRotate(Node node) {

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
    private Node rightRotate(Node node) {
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


    public Node select(int val) {

        Node node = root;
        while (node != null) {
            if (node.val > val) {
                node = node.left;
            } else if (node.val == val) {
                return node;
            } else {
                node = node.right;
            }
        }

        return null;
    }

}