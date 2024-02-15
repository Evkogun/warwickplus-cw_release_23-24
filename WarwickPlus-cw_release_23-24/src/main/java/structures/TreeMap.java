package structures;
import java.time.LocalDate;

public class TreeMap {
    private Node root;

    private class Node {
        LocalDate key;
        LinkedList<Integer> movieIds; // Parameterized LinkedList with Integer
        Node left, right;

        public Node(LocalDate key, Integer id) {
            this.key = key;
            this.movieIds = new LinkedList<>(id); // Corrected instantiation with parameterized type
        }
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
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    public LinkedList<Integer> getMovieIdsInRange(LocalDate start, LocalDate end) {
        LinkedList<Integer> result = new LinkedList<>(null); 
        getMovieIdsInRange(root, start, end, result);
        return result.next;
    }

    private void getMovieIdsInRange(Node node, LocalDate start, LocalDate end, LinkedList<Integer> result) { 
        if (node == null) return;
        if (node.key.isAfter(start) && node.key.isBefore(end)) {
            LinkedList<Integer> dummyHead = new LinkedList<>(null); 
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