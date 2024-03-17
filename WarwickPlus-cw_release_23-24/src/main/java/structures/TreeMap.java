package structures;
import java.time.LocalDate;

public class TreeMap {
    private Node root;

    private class Node {
        LocalDate key;
        LinkedList<Integer> movieIds;
        Node left, right;
        int height = 0;

        public Node(LocalDate key, Integer id) {
            this.key = key;
            this.movieIds = new LinkedList<>();
            movieIds.add(id);
            this.height = 1;
        }
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y; // Move x to the "top", moves xs right node to ys left node.
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1; // Recalculates heights.
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
        ///////////////////////////
        //      rightRotate      //
        //                       //
        //      y           x    //
        //     / \         / \   //
        //    x   T3  ->  T1  y  //
        //   / \         / \     //
        //  T1  T2      T2  T3   //
        //                       //
        ///////////////////////////

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x; // Opposite of rightRotate.
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1; // Recalculates Heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    public void put(LocalDate releaseDate, Integer movieId) {
        root = put(root, releaseDate, movieId);
    }

    private Node put(Node node, LocalDate key, Integer movieId) {
        if (node == null) {
            return new Node(key, movieId);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, movieId);
        } else if (cmp > 0) {
            node.right = put(node.right, key, movieId);
        } else {
            node.movieIds.add(movieId);
            return node;
        }
        return balanceNode(node);
    }

    private Node balanceNode(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node; // This rotates the nodes based off of the length of the left and right node.
    }

    public void take(LocalDate key, Integer movieId) {
        root = take(root, key, movieId);
    }

    private Node take(Node node, LocalDate key, Integer movieId) {
        if (node == null) return null;
        // Travels through the node, using compareTo to judge needed direction.
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = take(node.left, key, movieId);
        } else if (cmp > 0) {
            node.right = take(node.right, key, movieId);
        } else {
            node.movieIds.remove(movieId); // Removes value from stored linkedlist.

            if (node.movieIds.getSize() == 0) { // If that was the last value...
                if (node.left == null) return node.right;
                // Case: No left child, replace node with right child.
                if (node.right == null) return node.left;
                // Case: No right child, replace node with left child.
                
                Node t = node;
                node = min(t.right); 
                node.right = deleteMin(t.right);
                node.left = t.left;
                // Case: Two children, find the successor and delete the minimum.
            }
        }
        return balanceNode(node); 
    }

    private Node min(Node node) {
        if (node.left == null) return node; // Helper methods for other methods.
        else return min(node.left);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return balanceNode(node);
    }

    public LinkedList<Integer> getMovieIdsInRange(LocalDate start, LocalDate end) {
        LinkedList<Integer> result = new LinkedList<>();
        getMovieIdsInRange(root, start, end, result); // Begins recursive search for root
        return result;
    }
    
    private void getMovieIdsInRange(Node node, LocalDate start, LocalDate end, LinkedList<Integer> result) {
        if (node == null) return;
        // If the current node's key (date) is within the [start, end] range, add all its movie IDs to the result list.
        if (!node.key.isBefore(start) && !node.key.isAfter(end)) for (int movieId : node.movieIds.getValues()) result.add(movieId);
        // Recursively search in the left subtree if the start date is before or equal to the current node's date.
        if (start.isBefore(node.key)) getMovieIdsInRange(node.left, start, end, result);
        // Recursively search in the right subtree if the end date is after or equal to the current node's date.
        if (end.isAfter(node.key)) getMovieIdsInRange(node.right, start, end, result);
        
    }
}