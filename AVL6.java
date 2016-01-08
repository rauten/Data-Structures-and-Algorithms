import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author Riley Auten
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {

    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Empty data");
        } else {
            for (Object a : data) {
                add((T) a);
            }
        }
    }

    /**
     *@param current a pointer AVLNode
     */
    private void updateNode(AVLNode<T> current) {
        if (current != null) {
            if (current.getRight() == null && current.getLeft() == null) {
                current.setBalanceFactor(0);
                current.setHeight(0);
            } else if (current.getRight() == null) {
                current.setBalanceFactor(current.getLeft().getHeight() + 1);
                current.setHeight(current.getLeft().getHeight() + 1);
            } else if (current.getLeft() == null) {
                current.setBalanceFactor((-1) - current.getRight().getHeight());
                current.setHeight(current.getRight().getHeight() + 1);
            } else {
                current.setBalanceFactor(current.getLeft().getHeight()
                        - current.getRight().getHeight());
                current.setHeight(Math.max(current.getLeft().getHeight(),
                        current.getRight().getHeight()) + 1);
            }
        }
    }

    /**
     *
     * @param current a pointer node
     */
    private void rightRotation(AVLNode<T> current) {
        System.out.println("rightrot");
        T data = current.getData();
        current.setData(current.getLeft().getData());
        if (current.getRight() != null) {
            current.getRight().setRight(
                    new AVLNode<T>(current.getRight().getData())); //setLeft
            current.getRight().setData(data);
        } else {
            current.setRight(new AVLNode<T>(data));
        }
        if (current.getLeft().getRight() != null) {
            current.getRight().setLeft(current.getLeft().getRight());
        }
        current.setLeft(current.getLeft().getLeft());
        updateNode(current.getLeft());
        updateNode(current.getRight());
        updateNode(current);
    }

    /**
     *
     * @param current a pointer node
     */
    private void leftRotation(AVLNode<T> current) {
        System.out.println("leftrot");
        T data = current.getData();
        current.setData(current.getRight().getData());
        if (current.getLeft() != null) {
            current.getLeft().setLeft(
                    new AVLNode<T>(current.getLeft().getData()));
            current.getLeft().setData(data);
        } else {
            current.setLeft(new AVLNode<T>(data));
        }
        if (current.getRight().getLeft() != null) {
            current.getLeft().setRight(current.getRight().getLeft());
        }
        current.setRight(current.getRight().getRight());
        updateNode(current.getLeft());
        updateNode(current.getRight());
        updateNode(current);
    }

    /**
     * @param current a pointer node
     */
    private void rightLeftRotation(AVLNode<T> current) {
        System.out.println("rightleft");
        T data = current.getData();
        current.setData(current.getRight().getLeft().getData());
        if (current.getLeft() != null) {
            current.getLeft().setLeft(current.getLeft());
            current.getLeft().setData(data);
        } else {
            current.setLeft(new AVLNode<T>(data));
        }
        if (current.getRight().getLeft().getLeft() != null) {
            current.getLeft().setRight(current.getRight().getLeft().getLeft());
        } else if (current.getRight().getLeft().getRight() != null) {
            current.getRight().setLeft(current.getRight().getLeft().getRight());
        } else {
            current.getRight().setLeft(null);
        }
        updateNode(current.getLeft());
        updateNode(current.getRight());
        updateNode(current);
    }

    /**
     *
     * @param current a pointer node
     */
    private void leftRightRotation(AVLNode<T> current) {
        System.out.println("leftright");
        T data = current.getData();
        current.setData(current.getLeft().getRight().getData());
        if (current.getRight() != null) {
            current.getRight().setRight(current.getRight());
            current.getRight().setData(data);
        } else {
            current.setRight(new AVLNode<T>(data));
        }
        if (current.getLeft().getRight().getRight() != null) {
            current.getRight().setLeft(current.getLeft().getRight().getRight());
        } else if (current.getLeft().getRight().getLeft() != null) {
            current.getLeft().setRight(current.getLeft().getRight().getLeft());
        } else {
            current.getLeft().setRight(null);
        }
        updateNode(current.getLeft());
        updateNode(current.getRight());
        updateNode(current);
    }

    /**
     *
     * @param current a pointer node
     */
    private void rotate(AVLNode<T> current) {
        if (current != null) {
            if (current.getBalanceFactor() > 1) {
                if (current.getRight() != null && current.getLeft() != null) {
                    if (current.getRight().getBalanceFactor() >= 0
                            || current.getLeft().getBalanceFactor() >= 0) {
                        rightRotation(current);
                    } else if (current.getRight().getBalanceFactor() < 0
                            || current.getLeft().getBalanceFactor() < 0) {
                        leftRightRotation(current);
                    }
                } else if (current.getRight() == null) {
                    if (current.getLeft().getBalanceFactor() >= 0) {
                        rightRotation(current);
                    } else if (current.getLeft().getBalanceFactor() < 0) {
                        leftRightRotation(current);
                    }
                } else if (current.getLeft() == null) {
                    if (current.getRight().getBalanceFactor() >= 0) {
                        rightRotation(current);
                    } else if (current.getRight().getBalanceFactor() < 0) {
                        leftRightRotation(current);
                    }
                }
            } else if (current.getBalanceFactor() < -1) {
                if (current.getRight() != null && current.getLeft() != null) {
                    if (current.getRight().getBalanceFactor() <= 0
                            || current.getLeft().getBalanceFactor() <= 0) {
                        leftRotation(current);
                    } else if (current.getRight().getBalanceFactor() > 0
                            || current.getLeft().getBalanceFactor() > 0) {
                        rightLeftRotation(current);
                    }
                } else if (current.getRight() == null) {
                    if (current.getLeft().getBalanceFactor() <= 0) {
                        leftRotation(current);
                    } else if (current.getLeft().getBalanceFactor() > 0) {
                        rightLeftRotation(current);
                    }
                } else if (current.getLeft() == null) {
                    if (current.getRight().getBalanceFactor() <= 0) {
                        leftRotation(current);
                    } else if (current.getRight().getBalanceFactor() > 0) {
                        rightLeftRotation(current);
                    }
                }
            }
        }
    }

    /**
     *
     * @param current a pointer node
     * @param dt data that you want to add into your tree
     */
    private void realAdd(AVLNode<T> current, T dt) {
        if (root == null) {
            root = new AVLNode<T>(dt);
            current = root;
            size++;
        } else if (dt.compareTo(current.getData()) > 0) {
            if (current.getRight() != null) {
                realAdd(current.getRight(), dt);
            } else {
                current.setRight(new AVLNode<T>(dt));
                size++;
                updateNode(current.getRight());
            }
        } else if (dt.compareTo(current.getData()) < 0) {
            if (current.getLeft() != null) {
                realAdd(current.getLeft(), dt);
            } else {
                current.setLeft(new AVLNode<T>(dt));
                size++;
                updateNode(current.getLeft());
            }
        }
        updateNode(current);
        rotate(current);
        return;
    }

    /**
     *
     * @param current pointer node
     * @param theRealPO a list
     * @return a list that contains the entries of the AVL in preorder
     */
    private List<T> realPreOrder(AVLNode<T> current, List<T> theRealPO) {
        if (current == null) {
            return theRealPO;
        }
        theRealPO.add(current.getData());
        realPreOrder(current.getLeft(), theRealPO);
        realPreOrder(current.getRight(), theRealPO);
        return theRealPO;
    }

    /**
     *
     * @param current pointer node
     * @param realTalkPO a list
     * @return a list that contains the entries of the AVL in postorder
     */
    private List<T> realPostOrder(AVLNode<T> current, List<T> realTalkPO) {
        if (current == null) {
            return realTalkPO;
        }
        realPostOrder(current.getLeft(), realTalkPO);
        realPostOrder(current.getRight(), realTalkPO);
        realTalkPO.add(current.getData());
        return realTalkPO;
    }

    /**
     *
     * @param current pointer node
     * @param realTalkDoeIO a list
     * @return a list that contains the entries of the AVL inorder
     */
    private List<T> realInOrder(AVLNode<T> current, List<T> realTalkDoeIO) {
        if (current == null) {
            return realTalkDoeIO;
        }
        realInOrder(current.getLeft(), realTalkDoeIO);
        realTalkDoeIO.add(current.getData());
        realInOrder(current.getRight(), realTalkDoeIO);
        return realTalkDoeIO;
    }

    /**
     *
     * @param current pointer node
     * @param dt data that the user wants to test if exists in the tree
     * @return a boolean value; does the tree contain a certain data?
     */
    private boolean realContains(AVLNode<T> current, T dt) {
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

    /**
     *
     * @param current a pointer node
     * @param dt the data the user wants to get
     * @return data generic 'T', the data the user wants to get
     */
    private T realGet(AVLNode<T> current, T dt) {
        if (root == null) {
            throw new NoSuchElementException("Element not in AVL");
        } else if (current.getData().compareTo(dt) == 0) {
            return current.getData();
        } else if (current.getData().compareTo(dt) > 0) {
            if (current.getLeft() != null) {
                current = current.getLeft();
                //return realGet(current, dt);
            } else {
                throw new NoSuchElementException("Element not in AVL");
            }
        } else if (current.getData().compareTo(dt) < 0) {
            if (current.getRight() != null) {
                current = current.getRight();
                //return realGet(current, dt);
            } else {
                throw new NoSuchElementException("Element not in AVL");
            }
        }
        return realGet(current, dt);
    }

    /**
     *
     * @param data the data that a certain node contains
     * @param current a pointer node
     * @param level the current level you are on
     * @return an int representing the depth
     */
    private int getDepth(T data, AVLNode<T> current, int level) {
        if (data.compareTo(current.getData()) < 0) {
            if (current.getLeft() == null) {
                throw new NoSuchElementException("Element is not in AVL");
            } else {
                level++;
                return getDepth(data, current.getLeft(), level);
            }
        } else if (data.compareTo(current.getData()) > 0) {
            if (current.getRight() == null) {
                throw new NoSuchElementException("Element not in AVL");
            } else {
                level++;
                return getDepth(data, current.getRight(), level);
            }
        }
        return level;

    }

    /**
     *
     * @param current a pointer node
     * @param dt data the user wants to remove
     * @return T data, the data the user wants to remove
     */
    private T actuallyRemove(AVLNode<T> current, T dt) {
        AVLNode<T> removed = null;
        AVLNode<T> parent = null;
        T newData = null;
        if (root == null) {
            size++;
            throw new NoSuchElementException("Element not in AVL");
        }
        if (root.getData().compareTo(dt) == 0) {
            if (root.getRight() == null && root.getLeft() == null) {
                newData = root.getData();
                root = null;
                return newData;
            } else if (root.getRight() == null && root.getLeft() != null) {
                newData = root.getData();
                root = root.getLeft();
                updateNode(root);
                rotate(root);
                return newData;
            } else if (root.getLeft() == null && root.getRight() != null) {
                newData = root.getData();
                root = root.getRight();
                updateNode(root);
                rotate(root);
                return newData;
            } else {
                newData = root.getData();
                removed = root;
                current = root.getLeft();
                T data = getPredecessor(current, removed);
                removed.setData(data);
                updateNode(root.getLeft());
                rotate(root.getLeft());
                updateNode(root);
                rotate(root);
                return newData;
            }
        } else if (current.getRight() == null && current.getLeft()
                == null && current.getData() != dt) {
            throw new NoSuchElementException("Element not in AVL");
        } else if (current.getRight() != null
                && current.getRight().getData().compareTo(dt) == 0) {
            if (current.getRight().getLeft() == null
                    && current.getRight().getRight() == null) {
                newData = current.getRight().getData();
                current.setRight(null);
                updateNode(current);
                rotate(current);
                return newData;
            } else if (current.getRight().getLeft() != null
                    && current.getRight().getRight() != null) {
                removed = current.getRight();
                newData = removed.getData();
                T data = getPredecessor(current.getRight().getLeft(), removed);
                removed.setData(data);
                updateNode(current.getRight().getLeft());
                rotate(current.getRight().getLeft());
                updateNode(current.getRight());
                rotate(current.getRight());
                return newData;
            } else if (current.getRight().getLeft() != null
                    || current.getRight().getRight() != null) {
                newData = current.getRight().getData();
                if (current.getRight().getLeft() != null) {
                    current.setRight(current.getRight().getLeft());
                    updateNode(current.getRight());
                    rotate(current.getRight());
                    updateNode(current);
                    rotate(current);
                    return newData;
                } else {
                    current.setRight(current.getRight().getRight());
                    updateNode(current.getRight());
                    rotate(current.getRight());
                    updateNode(current);
                    rotate(current);
                    return newData;
                }
            }
        } else if (current.getLeft() != null
                && current.getLeft().getData().compareTo(dt) == 0) {
            if (current.getLeft().getLeft() == null
                    && current.getLeft().getRight() == null) {
                newData = current.getLeft().getData();
                current.setLeft(null);
                updateNode(current);
                System.out.println(current.getBalanceFactor());
                System.out.println(current.getHeight());
                rotate(current);
                return newData;
            } else if (current.getLeft().getRight() != null
                    && current.getLeft().getLeft() != null) {
                removed = current.getLeft();
                newData = removed.getData();
                T data = getPredecessor(current.getLeft().getLeft(), removed);
                removed.setData(data);
                updateNode(current.getLeft().getLeft());
                rotate(current.getLeft().getLeft());
                updateNode(current.getLeft());
                rotate(current.getLeft());
                return newData;
            } else if (current.getLeft().getLeft() != null
                    || current.getLeft().getRight() != null) {
                newData = current.getLeft().getData();
                if (current.getLeft().getLeft() != null) {
                    current.setLeft(current.getLeft().getLeft());
                    updateNode(current.getLeft());
                    rotate(current.getLeft());
                    updateNode(current);
                    rotate(current);
                    return newData;
                } else {
                    current.setLeft(current.getLeft().getRight());
                    updateNode(current.getLeft());
                    rotate(current.getLeft());
                    updateNode(current);
                    rotate(current);
                    return newData;
                }
            }
        } else if (dt.compareTo(current.getData()) < 0) {
            current = current.getLeft();
        } else if (dt.compareTo(current.getData()) > 0) {
            current = current.getRight();
        }
        updateNode(current);
        rotate(current);
        return actuallyRemove(current, dt);
    }

    /**
     *
     * @param current a pointer node
     * @param removed a AVLNode that points to the node you want removed
     * @return T data, the data the user wants removed
     */
    private T getPredecessor(AVLNode<T> current, AVLNode<T> removed) {
        T data = null;
        AVLNode<T> parent = null;
        if (current.getRight() == null) {
            data = current.getData();
            if (current.getLeft() != null) {
                removed.setLeft(current.getLeft());
                updateNode(current.getLeft());
                rotate(current.getLeft());
            } else {
                removed.setLeft(null);
            }
        } else if (current.getRight().getRight() != null) {
            getPredecessor(current.getRight(), removed);
            updateNode(current.getRight());
            rotate(current.getRight());
        } else if (current.getRight() != null) {
            data = current.getRight().getData();
            //parent = current;
            if (current.getRight().getLeft() != null) {
                current.setRight(current.getRight().getLeft());
            } else {
                current.setRight(null);
            }
            updateNode(current.getRight());
            rotate(current.getRight());
        }
        return data;
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        AVLNode<T> instance = root;
        realAdd(instance, data);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        AVLNode<T> instance = root;
        size--;
        return actuallyRemove(instance, data);
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        AVLNode<T> instance = root;
        return realGet(instance, data);
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        AVLNode<T> instance = root;
        return realContains(instance, data);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        AVLNode<T> instance = root;
        List<T> theRealPO = new ArrayList<T>(0);
        return realPreOrder(instance, theRealPO);
    }

    @Override
    public List<T> postorder() {
        AVLNode<T> instance = root;
        List<T> realTalkPO = new ArrayList<T>(0);
        return realPostOrder(instance, realTalkPO);
    }

    @Override
    public List<T> inorder() {
        AVLNode<T> instance = root;
        List<T> realTalkDoeIO = new ArrayList<T>(0);
        return realInOrder(instance, realTalkDoeIO);
    }

    @Override
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        List<AVLNode<T>> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode<T> removed = queue.remove(0);
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

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (size == 0) {
            throw new NoSuchElementException("Element not found");
        } else {
            int depth = getDepth(data, root, 1);
            return depth;
        }
    }



    /**
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }

}
