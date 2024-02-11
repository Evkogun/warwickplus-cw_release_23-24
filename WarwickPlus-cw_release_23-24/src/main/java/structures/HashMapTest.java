package structures;


public class HashMapTest {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        int[] counts = new int[map.capacity()];  // Using a method to get the capacity

        // Generate a large number of keys and add them to the map
        for (int i = 0; i < 10000; i++) {
            int key = generateRandomKey();  // Implement this method to generate diverse keys
            map.add(key, "Value" + i);
            int index = map.hash(key);  // Directly call the hash function if it's accessible
            counts[index]++;
        }

        // Check the distribution
        for (int count : counts) {
            System.out.println(count);  // Ideally, counts should be roughly the same
        }

        int collisions = 0;
        for (int count : counts) {
            if (count > 1) {
                collisions += count - 1;  // Every entry in a bucket after the first is a collision
            }
        }
        double collisionRate = (double) collisions / 10000;
        System.out.println("Collision Rate: " + collisionRate);

        int[] edgeCases = {Integer.MIN_VALUE, Integer.MAX_VALUE, -1, 0};
        for (int key : edgeCases) {
            int index = map.hash(key);
            if (index < 0 || index >= map.capacity()) {
                System.out.println("Edge case failed for key: " + key);
            } else {
                System.out.println("Edge case passed for key: " + key);
            }
        }

        int key = generateRandomKey();
        int firstHash = map.hash(key);
        boolean consistent = true;
        for (int i = 0; i < 1000; i++) {
            if (map.hash(key) != firstHash) {
                consistent = false;
                break;
            }
        }
        System.out.println("Consistency: " + consistent);
    }

    // Placeholder for generateRandomKey method
    private static int generateRandomKey() {
        // Implement this method to generate random keys
        // For example, using random integers:
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}

