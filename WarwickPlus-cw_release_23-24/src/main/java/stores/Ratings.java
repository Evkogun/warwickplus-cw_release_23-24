package stores;

import java.time.LocalDateTime;

import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;
    private HashMap<Integer, HashMap<Integer, Float>> userRatingsMap;
    private HashMap<Integer, RatingInfo> movieRatingsMap;

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
        
        HashMap<Integer, Float> userRatings = userRatingsMap.get(userid);
        if (userRatings == null) {
            userRatings = new HashMap<>();
            userRatingsMap.put(userid, userRatings);
        }
        userRatings.put(movieid, rating);
        
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo == null) {
            movieRatingInfo = new RatingInfo();
            movieRatingsMap.put(movieid, movieRatingInfo);
        }
        movieRatingInfo.addRating(rating, userid);

        return true;
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
        HashMap<Integer, Float> userRatings = userRatingsMap.get(userid);
        if (userRatings != null) {
            Float rating = userRatings.take(movieid); 
            removedFromUserMap = (rating != null); 
    
            if (userRatings.isEmpty()) {
                userRatingsMap.take(userid);
            }
        }
    
        boolean removedFromMovieMap = false;
        RatingInfo movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo != null && removedFromUserMap) {

            removedFromMovieMap = movieRatingInfo.removeRating(userid);

            if (movieRatingInfo.isEmpty()) {
                movieRatingsMap.take(movieid);
            }
        }
    
        return removedFromUserMap && removedFromMovieMap; 
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
        RatingInfo  movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo.isEmpty()){
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
        HashMap<Integer, Float> userRatings = userRatingsMap.get(userid);
        if (userRatings == null || userRatings.isEmpty()) {
            return new float[0]; 
        }
        return userRatings.values();
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in ratings, but does exist in the movies store, return 0.0f. 
     *         If the film cannot be found in ratings or movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        RatingInfo  movieRatingInfo = movieRatingsMap.get(movieid);
        if (movieRatingInfo == null) {
            return -1.0f;
        } else if (movieRatingInfo.isEmpty()) {
            return 0.0f;
        }
        HashMap<Integer, Float> ratings = movieRatingInfo.getRatings();
        float[] ratingsArr = ratings.values();
        float avgRatings = 0;
        for (int i = 0; i < ratings.size(); i++){
            avgRatings += ratingsArr[i];
        }
        return avgRatings/ratings.size();
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
        HashMap<Integer, Float> userInfo = userRatingsMap.get(userid);
        if (userInfo == null || userInfo.isEmpty()) {
            return -1;  
        }
        float[] userArr = userInfo.values();
        float avgRatings = 0;
        for (int i = 0; i < userInfo.size(); i++){
            avgRatings += userArr[i];
        }
        return avgRatings/userInfo.size();
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
        // TODO Implement this function
        return null;
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
        // TODO Implement this function
        return null;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return userRatingsMap.size();
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
        return movieRatingsMap.get(movieid).getCount();
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
        // TODO Implement this function
        return null;
    }
}

class RatingInfo {
    private HashMap<Integer, Float> ratings;
    private float totalRating;
    private int count;

    public RatingInfo() {
        ratings = new HashMap<>();
        totalRating = 0;
        count = 0;
    }

    public void addRating(float rating, int userid) {
        Float previousRating = ratings.get(userid);
        if (previousRating != null) {
            totalRating -= previousRating;
        }
        ratings.put(userid, rating);
        totalRating += rating;
        count = ratings.size();
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
        return count == 0 ? 0 : totalRating / count;
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
    // Banging your head against a wall sometimes works
}

