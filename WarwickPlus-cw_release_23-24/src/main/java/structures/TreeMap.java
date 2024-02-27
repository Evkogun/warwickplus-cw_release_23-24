package structures;
import java.time.LocalDate;

public class TreeMap {
    private Node root;

    private class Node {
        LocalDate key;
        LinkedList<Integer> movieIds;
        Node left, right;
        int height;

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
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
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
        return node;
    }

    public LinkedList<Integer> take(LocalDate key) {
        MovieIdListHolder holder = new MovieIdListHolder();
        root = take(root, key, holder);
        return holder.movieIdList;
    }

    private class MovieIdListHolder {
        LinkedList<Integer> movieIdList;
    }

    private Node take(Node node, LocalDate key, MovieIdListHolder holder) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = take(node.left, key, holder);
        } else if (cmp > 0) {
            node.right = take(node.right, key, holder);
        } else {
            holder.movieIdList = node.movieIds;
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        return balanceNode(node);
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return balanceNode(node);
    }

    public LinkedList<Integer> getMovieIdsInRange(LocalDate start, LocalDate end) {
        LinkedList<Integer> result = new LinkedList<>();
        getMovieIdsInRange(root, start, end, result);
        if (result.next != null){
            result.next.size = result.size;
            return result.next;
        }
        return result;
    }

    private void getMovieIdsInRange(Node node, LocalDate start, LocalDate end, LinkedList<Integer> result) {
        if (node == null) return;
        if (node.key.isAfter(start) && node.key.isBefore(end)) {
            LinkedList<Integer> dummyHead = new LinkedList<>();
            dummyHead.appendList(node.movieIds);
            result.appendList(dummyHead.next);
        }
        if (start.isBefore(node.key) || start.equals(node.key)) {
            getMovieIdsInRange(node.left, start, end, result);
        }
        if (end.isAfter(node.key) || end.equals(node.key)) {
            getMovieIdsInRange(node.right, start, end, result);
        }
    }
}