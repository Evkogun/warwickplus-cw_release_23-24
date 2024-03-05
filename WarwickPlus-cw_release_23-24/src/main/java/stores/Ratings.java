package stores;

import java.time.LocalDateTime;
import java.util.Comparator;
import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;
    private HashMap<Integer, RatingInfo> userRatingsMap;
    private HashMap<Integer, RatingInfo> movieRatingsMap;
    private RatingInfo[] movieRatings;
    private RatingInfo[] userRatings;
    private int size;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        this.stores = stores;
        userRatingsMap = new HashMap<>();
        movieRatingsMap = new HashMap<>();
        movieRatings = new RatingInfo[0];
        userRatings = new RatingInfo[0];
        size = 0;
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */

    @Override
    public boolean add(int userid, int movieid, float rating, LocalDateTime timestamp) {    
        RatingInfo userRatingInfo = userRatingsMap.get(userid);
        if (userRatingInfo == null) {
            userRatingInfo = new RatingInfo();
            userRatingsMap.put(userid, userRatingInfo);
            userRatingsMap.get(userid).setMovieID(userid);
        }
        if(userRatingInfo.addRating(rating, movieid) == true) size++;
       
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo == null) {
            movieRatingInfo = new RatingInfo();
            movieRatingsMap.put(movieid, movieRatingInfo);
            movieRatingsMap.get(movieid).setMovieID(movieid);
        }
        return movieRatingInfo.addRating(rating, userid);
    }

    

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        boolean removedFromUserMap = false;
        RatingInfo userRatingsInfo = userRatingsMap.get(userid);
        if (userRatingsInfo != null && !userRatingsInfo.isEmpty()) {
            removedFromUserMap = userRatingsInfo.removeRating(movieid);
    
            if (userRatingsInfo.isEmpty()) {
                userRatingsMap.take(userid);
            }
        }
    
        boolean removedFromMovieMap = false;
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo != null && !movieRatingInfo.isEmpty()) {
            removedFromMovieMap = movieRatingInfo.removeRating(userid);
    
            if (movieRatingInfo.isEmpty()) {
                movieRatingsMap.take(movieid);
            }
        }
        if (removedFromUserMap && removedFromMovieMap){
            size++;
            return true;
        }
        else return false;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo.ratedByUsers(userid)){
            remove(userid, movieid);
        }
        add(userid, movieid, rating, timestamp);
        return true;
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieid) {
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo == null || movieRatingInfo.isEmpty()) {
            return new float[0];
        }
        return movieRatingInfo.getRatings().values();
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        RatingInfo userRatingsInfo = userRatingsMap.get(userid);
        if (userRatingsInfo == null || userRatingsInfo.isEmpty()) {
            return new float[0]; 
        }
        return userRatingsInfo.getRatings().values();
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in ratings, but does exist in the movies store, return 0.0f. 
     *         If the film cannot be found in ratings or movies stores, return -1.0f.
     */

    public float getMovieAverageRating(int movieid) {
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo == null || movieRatingInfo.isEmpty()) {
            return movieRatingInfo == null ? -1.0f : 0.0f;
        }
        return movieRatingInfo.getAverageRating();
    }
    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found, or there are no rating, return -1
     */
    @Override
    public float getUserAverageRating(int userid) {
        RatingInfo userRatingInfo = userRatingsMap.get(userid);
        if (userRatingInfo == null || userRatingInfo.isEmpty()) {
            return userRatingInfo == null ? -1.0f : 0.0f;
        }
        return userRatingInfo.getAverageRating();
    }

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies
     */
    @Override
    public int[] getMostRatedMovies(int num) {
        if (movieRatingsMap == null || movieRatingsMap.isEmpty()) return new int[0];
        int[] keyListStore = movieRatingsMap.keyList();
        if (keyListStore == null) return new int[0];
        if (movieRatings.length != keyListStore.length){
            this.movieRatings = new RatingInfo[keyListStore.length];
        }
        for (int i = 0; i < keyListStore.length; i++){
            movieRatings[i] = movieRatingsMap.get(keyListStore[i]);
        }  
        Comparator<RatingInfo> movieRatingCountComparator = (o1, o2) -> Float.compare(o1.getCount(), o2.getCount());
        Sort.genericSort(movieRatings, movieRatingCountComparator);

        int[] returnArr = new int[num];
        for (int i = 0; i < num; i++){
            returnArr[i] = movieRatings[i].getMovieID();
        }

        return returnArr;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        if (userRatingsMap == null || userRatingsMap.isEmpty()) return new int[0];
        int[] keyListStore = userRatingsMap.keyList();
        if (keyListStore == null) return new int[0];
        if (userRatings.length != keyListStore.length){
            this.userRatings = new RatingInfo[keyListStore.length];
        }
        for (int i = 0; i < keyListStore.length; i++){
            userRatings[i] = userRatingsMap.get(keyListStore[i]);
        }  
        Comparator<RatingInfo> userRatingCountComparator = (o1, o2) -> Float.compare(o1.getCount(), o2.getCount());
        Sort.genericSort(userRatings, userRatingCountComparator);

        int[] returnArr = new int[num];
        for (int i = 0; i < num; i++){
            returnArr[i] = userRatings[i].getMovieID();
        }

        return returnArr;
    }


    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the movies store, but there
     *         are no ratings for it, then return 0. If the movie
     *         does not exist in the ratings or movies store, then
     *         return -1
     */
    @Override
    public int getNumRatings(int movieid) {
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo == null) {
            return -1;
        }
        return movieRatingInfo.getCount();
    }

    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        if (movieRatings == null) return new int[0];
        int[] keyListStore = movieRatingsMap.keyList();
        if (keyListStore == null) return new int[0];
        if (movieRatings.length != keyListStore.length){
            this.movieRatings = new RatingInfo[keyListStore.length];
        }
        for (int i = 0; i < keyListStore.length; i++){
            movieRatings[i] = movieRatingsMap.get(keyListStore[i]);
        }  
        
        Comparator<RatingInfo> averageMovieRationsCountComparator = (o1, o2) -> Float.compare(o1.getAverageRating(), o2.getAverageRating());
        Sort.genericSort(movieRatings, averageMovieRationsCountComparator);

        int[] returnArr = new int[numResults];
        for (int i = 0; i < numResults; i++){
            returnArr[i] = movieRatings[i].getMovieID();
        }

        return returnArr;
    }
}

class RatingInfo {
    private HashMap<Integer, Float> ratings;
    private float totalRating;
    private int count;
    private int movieid;

    public RatingInfo() {
        ratings = new HashMap<>();
        totalRating = 0;
        count = 0;
    }

    public boolean addRating(float rating, int userid) {
        if (!ratings.put(userid, rating)) return false;
        totalRating += rating;
        count = ratings.size();
        return true;
    }

    public boolean removeRating(int userid) {
        Float ratingToRemove = ratings.take(userid);
        if (ratingToRemove != null) {
            totalRating -= ratingToRemove;
            count = ratings.size();
            return true;
        }
        return false;
    }

    public float getAverageRating() {
        return count > 0 ? totalRating / count : 0.0f;
    }

    public HashMap<Integer, Float> getRatings() {
        return ratings;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean ratedByUsers(int userid) {
        return ratings.containsKey(userid);
    }

    public int getCount(){
        return count;
    }

    public int getMovieID(){
        return movieid;
    }

    public void setMovieID(int movieidinput){
        this.movieid = movieidinput;
    }
}



class Sort {
    @SuppressWarnings("unchecked")
    public static <T> void genericSort(T[] array, Comparator<T> comparator) {
        if (array.length <= 1) {
            return;
        }

        T[] leftHalf = (T[]) new Object[array.length / 2];
        T[] rightHalf = (T[]) new Object[array.length - leftHalf.length];

        System.arraycopy(array, 0, leftHalf, 0, leftHalf.length);
        System.arraycopy(array, leftHalf.length, rightHalf, 0, rightHalf.length);

        genericSort(leftHalf, comparator);
        genericSort(rightHalf, comparator);

        genericMerge(array, leftHalf, rightHalf, comparator);
    }

    private static <T> void genericMerge(T[] outputArray, T[] leftHalf, T[] rightHalf, Comparator<T> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < leftHalf.length && rightIndex < rightHalf.length) {
            if (comparator.compare(leftHalf[leftIndex], rightHalf[rightIndex]) >= 0) {
                outputArray[mergeIndex++] = leftHalf[leftIndex++];
            } else {
                outputArray[mergeIndex++] = rightHalf[rightIndex++];
            }
        }

        System.arraycopy(leftHalf, leftIndex, outputArray, mergeIndex, leftHalf.length - leftIndex);
        System.arraycopy(rightHalf, rightIndex, outputArray, mergeIndex, rightHalf.length - rightIndex);
    }
    
}


