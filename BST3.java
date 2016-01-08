import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Collection;

public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element
     * in data is null
     */
    public BST(Collection<T> data) {
        try {
            for (T dt : data) {
                add(dt);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid data");
        }
    }
    /**
    *@param current node
    *@param dt data
    **/
    private void realAdd(BSTNode<T> current, T dt) {
        if (root == null) {
            root = new BSTNode<T>(dt);
            size++;
        } else if (dt.compareTo(current.getData()) > 0) {
            if (current.getRight() != null) {
                current = current.getRight();
                realAdd(current, dt);
            } else {
                current.setRight(new BSTNode<T>(dt));
                size++;
            }
        } else if (dt.compareTo(current.getData()) < 0) {
            if (current.getLeft() != null) {
                current = current.getLeft();
                realAdd(current, dt);
            } else {
                current.setLeft(new BSTNode<T>(dt));
                size++;
            }
        }
    }
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        BSTNode<T> instance = root;
        realAdd(instance, data);
    }
    /**
    *@return T data
    *@param current : a BSTNode
    *@param dt : data
    **/
    private T actuallyRemove(BSTNode<T> current, T dt) {
        BSTNode<T> removed = null;
        BSTNode<T> parent = null;
        T newData = null;
        if (root == null) {
            size++;
            throw new NoSuchElementException("Element not in BST");
        }
        if (root.getData().compareTo(dt) == 0) {
            if (root.getRight() == null && root.getLeft() == null) {
                newData = root.getData();
                root = null;
                return newData;
            } else if (root.getRight() == null && root.getLeft() != null) {
                newData = root.getData();
                root = root.getLeft();
                return newData;
            } else if (root.getLeft() == null && root.getRight() != null) {
                newData = root.getData();
                root = root.getRight();
                return newData;
            } else {
                newData = root.getData();
                parent = root;
                current = current.getLeft();
                if (current.getRight() == null) {
                    root.setData(current.getData());
                    if (current.getLeft() != null) {
                        root.setLeft(current.getLeft());
                    } else {
                        root.setLeft(null);
                    }
                } else {
                    while (current.getRight() != null) {
                        parent = current;
                        current = current.getRight();
                    }
                    root.setData(current.getData());
                    if (current.getLeft() == null) {
                        parent.setRight(null);
                    } else {
                        parent.setRight(current.getLeft());
                    }
                }
                return newData;
            }
        } else if (current.getRight() == null && current.getLeft()
            == null && current.getData() != dt) {
            throw new NoSuchElementException("Element not in BST");
        } else if (current.getRight() != null
            && current.getRight().getData().compareTo(dt) == 0) {
            if (current.getRight().getLeft() == null
                && current.getRight().getRight() == null) {
                newData = current.getRight().getData();
                current.setRight(null);
                return newData;
            } else if (current.getRight().getLeft() != null
                && current.getRight().getRight() != null) {
                current = current.getRight();
                removed = current;
                newData = removed.getData();
                current = current.getLeft();
                if (current.getRight() == null) {
                    removed.setData(current.getData());
                    if (current.getLeft() != null) {
                        removed.setLeft(current.getLeft());
                    } else {
                        removed.setLeft(null);
                    }
                } else {
                    while (current.getRight() != null) {
                        parent = current;
                        current = current.getRight();
                    }
                    removed.setData(current.getData());
                    if (current.getLeft() != null) {
                        parent.setRight(current.getLeft());
                    } else {
                        parent.setRight(null);
                    }
                }
                return newData;
            } else if (current.getRight().getLeft() != null
                || current.getRight().getRight() != null) {
                newData = current.getRight().getData();
                if (current.getRight().getLeft() != null) {
                    current.setRight(current.getRight().getLeft());
                    return newData;
                } else {
                    current.setRight(current.getRight().getRight());
                    return newData;
                }
            }
        } else if (current.getLeft() != null
            && current.getLeft().getData().compareTo(dt) == 0) {
            if (current.getLeft().getLeft() == null
                && current.getLeft().getRight() == null) {
                newData = current.getLeft().getData();
                current.setLeft(null);
                return newData;
            } else if (current.getLeft().getRight() != null
                && current.getLeft().getLeft() != null) {
                current = current.getLeft();
                removed = current;
                current = current.getLeft();
                if (current.getRight() == null) {
                    newData = removed.getData();
                    removed.setData(current.getData());
                    if (current.getLeft() != null) {
                        removed.setLeft(current.getLeft());
                    } else {
                        removed.setLeft(null);
                    }
                } else {
                    while (current.getRight() != null) {
                        parent = current;
                        current = current.getRight();
                    }
                    newData = current.getData();
                    removed.setData(newData);
                    if (current.getLeft() == null) {
                        parent.setRight(null);
                    } else {
                        parent.setRight(parent.getRight().getLeft());
                    }
                }
                return newData;
            } else if (current.getLeft().getLeft() != null
                || current.getLeft().getRight() != null) {
                newData = current.getLeft().getData();
                if (current.getLeft().getLeft() != null) {
                    current.setLeft(current.getLeft().getLeft());
                    return newData;
                } else {
                    current.setLeft(current.getLeft().getRight());
                    return newData;
                }
            }
        } else if (dt.compareTo(current.getData()) < 0) {
            current = current.getLeft();
        } else if (dt.compareTo(current.getData()) > 0) {
            current = current.getRight();
        }
        return actuallyRemove(current, dt);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        BSTNode<T> instance = root;
        size--;
        return actuallyRemove(instance, data);
    }
    /**
    *@return T : data of a node
    *@param current : a BSTNode with data T
    *@param dt : data
    **/
    private T realGet(BSTNode<T> current, T dt) {
        if (root == null) {
            throw new NoSuchElementException("Element not in BST");
        } else if (current.getData().compareTo(dt) == 0) {
            return current.getData();
        } else if (current.getData().compareTo(dt) > 0) {
            if (current.getLeft() != null) {
                current = current.getLeft();
                //return realGet(current, dt);
            } else {
                throw new NoSuchElementException("Element not in BST");
            }
        } else if (current.getData().compareTo(dt) < 0) {
            if (current.getRight() != null) {
                current = current.getRight();
                //return realGet(current, dt);
            } else {
                throw new NoSuchElementException("Element not in BST");
            }
        }
        return realGet(current, dt);
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        BSTNode<T> instance = root;
        return realGet(instance, data);
    }
    /**
    *@return boolean : does the BST contain data?
    *@param current : BSTNode with data T
    *@param dt : data
    **/
    private boolean realContains(BSTNode<T> current, T dt) {
        if (current == null) {
            return false;
        } else if (current.getData().equals(dt)) {
            return true;
        } else if (dt.compareTo(current.getData()) < 0) {
            return realContains(current.getLeft(), dt);
        } else if (dt.compareTo(current.getData()) > 0) {
            return realContains(current.getRight(), dt);
        }
        return false;
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        BSTNode<T> instance = root;
        return realContains(instance, data);
    }

    @Override
    public int size() {
        return size;
    }
    /**
    *@return List : a list of type T
    *@param current : a BSTNode with data type T
    *@param theRealPO : a list with elements from the BST
    **/
    private List<T> realPreOrder(BSTNode<T> current, List<T> theRealPO) {
        //List<T> theRealPO = new ArrayList<T>();
        if (current == null) {
            return theRealPO;
        }
        theRealPO.add(current.getData());
        realPreOrder(current.getLeft(), theRealPO);
        realPreOrder(current.getRight(), theRealPO);
        return theRealPO;
    }

    @Override
    public List<T> preorder() {
        BSTNode<T> instance = root;
        List<T> theRealPO = new ArrayList<T>(0);
        return realPreOrder(instance, theRealPO);
    }
    /**
    *@return List : a list with data type T
    *@param current : a BSTNode with data type T
    *@param realTalkPO : a list with elements from the BST
    **/
    private List<T> realPostOrder(BSTNode<T> current, List<T> realTalkPO) {
        if (current == null) {
            return realTalkPO;
        }
        realPostOrder(current.getLeft(), realTalkPO);
        realPostOrder(current.getRight(), realTalkPO);
        realTalkPO.add(current.getData());
        return realTalkPO;
    }

    @Override
    public List<T> postorder() {
        BSTNode<T> instance = root;
        List<T> realTalkPO = new ArrayList<T>(0);
        return realPostOrder(instance, realTalkPO);
    }
    /**
    *@return List : a list with data type T
    *@param current : a BSTNode with data type T
    *@param realTalkDoeIO : a list with elements from the BST
    **/
    private List<T> realInOrder(BSTNode<T> current, List<T> realTalkDoeIO) {
        if (current == null) {
            return realTalkDoeIO;
        }
        realInOrder(current.getLeft(), realTalkDoeIO);
        realTalkDoeIO.add(current.getData());
        realInOrder(current.getRight(), realTalkDoeIO);
        return realTalkDoeIO;
    }

    @Override
    public List<T> inorder() {
        BSTNode<T> instance = root;
        List<T> realTalkDoeIO = new ArrayList<T>(0);
        return realInOrder(instance, realTalkDoeIO);
    }

    @Override
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        List<BSTNode<T>> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> removed = queue.remove(0);
            if (removed != null) {
                list.add(removed.getData());
                queue.add(removed.getLeft());
                queue.add(removed.getRight());
            }
        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    /**
    *@return int : the height of the BST
    *@param current : a BSTNode with data type T
    **/
    private int realHeight(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return Math.max(realHeight(current.getRight()),
                realHeight(current.getLeft())) + 1;
        }
    }
    
    /**
     * @return the height of the tree
     */
    @Override
    public int height() {
        return realHeight(root);
    }

    /**
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}
