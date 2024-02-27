package stores;

import java.time.LocalDate;


import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies{
    Stores stores;
    private HashMap<Integer, MovieInfoData> movieInfo;
    private TreeMap timeTreeMap;
    private HashMap<Integer, CollectionData> collectionInfo;
    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        movieInfo = new HashMap<>();
        timeTreeMap = new TreeMap();
        collectionInfo = new HashMap<>();
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        MovieInfoData movieInfoTemp = new MovieInfoData(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);
        if (!movieInfo.put(id, movieInfoTemp)) return false;
        if (release != null) timeTreeMap.put(release, id);

        return true;
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        if (movieInfo.containsKey(id)) {
            LocalDate releaseDate = movieInfo.get(id).release;
            LinkedList<Integer> movieIds = timeTreeMap.take(releaseDate);
            if (movieIds != null && movieIds.size > 0) {
                movieIds.remove(id); 
            }
            movieInfo.take(id); 
            return true;
        }
        return false;
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        return movieInfo.keyList();
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {
        if (timeTreeMap == null) {
            return new int[0];
        }
        LinkedList<Integer> movieIdList = timeTreeMap.getMovieIdsInRange(start, end);
        // LinkedList<Integer> temp = movieIdList;
        // System.out.println(movieIdList.size);
        // while (temp.next != null){
        //     System.out.println(temp.element);
        //     temp = temp.next;
        // }
        if (movieIdList == null || movieIdList.size == 0) {
            return new int[0];
        }
        return movieIdList.getValues();
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).title : null;
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).originalTitle : null;
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).overview : null;
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).tagline : null;
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).status : null;
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).genres : null;
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).release : null;
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).budget : -1;
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).revenue : -1;
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).languages : null;
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).originalLanguage : null;
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public double getRuntime(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).runtime : -1;
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).homepage : null;
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).adult : false;
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).video : false;
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        return movieInfo.get(id) != null ? movieInfo.get(id).poster : null;
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        MovieInfoData setVote = movieInfo.get(id);
        if (setVote == null) return false;
        setVote.voteAverage = voteAverage;
        setVote.voteCount = voteCount;
        return true;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public double getVoteAverage(int id) {
        if (movieInfo.get(id) == null) return -1;
        return movieInfo.get(id).voteAverage;
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        if (movieInfo.get(id) == null) return -1;
        return movieInfo.get(id).voteCount;
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        MovieInfoData movie = movieInfo.get(filmID);
        if (movie == null) {
            return false; 
        }
        CollectionData collection = collectionInfo.get(collectionID);
        if (collection == null) {
            collection = new CollectionData(filmID, collectionName, collectionPosterPath, collectionBackdropPath);
            collectionInfo.put(collectionID, collection);
        } else {
            LinkedList<Integer> filmList = collection.filmStore;
            if (filmList == null) {
                collection.filmAdd(filmID);
            }
        }
        movie.collectionID = collectionID; 
        return true;
    }
    

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        CollectionData collectionData = collectionInfo.get(collectionID);
        if (collectionData == null || collectionData.filmStore == null || collectionData.filmStore.size == 0) {
            return new int[0];
        }
        return collectionData.filmStore.getValues();
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        if (collectionInfo.get(collectionID) == null) return null;
        return collectionInfo.get(collectionID).collectionName;
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        if (collectionInfo.get(collectionID) == null) return null;
        return collectionInfo.get(collectionID).collectionPosterPath;
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        if (collectionInfo.get(collectionID) == null) return null;
        return collectionInfo.get(collectionID).collectionBackdropPath;
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        if (movieInfo.get(filmID) == null) return -1;
        return movieInfo.get(filmID).collectionID;
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        if (movieInfo.get(filmID) == null) return false;
        movieInfo.get(filmID).imdbID = imdbID;
        return true;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        if (movieInfo.get(filmID) == null) return null;
        return movieInfo.get(filmID).imdbID;
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        if (movieInfo.get(id) == null) return false;
        movieInfo.get(id).popularity = popularity;
        return true;
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        if (movieInfo.get(id) == null) return -1;
        return movieInfo.get(id).popularity;
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        if (movieInfo.get(id) == null) return false;
        movieInfo.get(id).productionCompanyAdd(company);
        return true;
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        if (movieInfo.get(id) == null) return false;
        movieInfo.get(id).productionCountryAdd(country);
        return true;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        if (movieInfo.get(id) == null) return null;
        return movieInfo.get(id).productionCompanyList.getValuesCompany();
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        if (movieInfo.get(id) == null) return null;
        return movieInfo.get(id).productionCountryList.getValuesCountry();
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return movieInfo.size();
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        MovieInfoData[] movieStore = movieInfo.movieInfoList();
        LinkedList<Integer> matchingMovieIds = new LinkedList<>();
        
        for (int i = 0; i < movieStore.length; i++){
            if (movieStore[i].title != null && movieStore[i].title.toLowerCase().contains(searchTerm.toLowerCase())){
                matchingMovieIds.add(movieStore[i].id);
            }
        }

        if (matchingMovieIds.size == 0) {
            return new int[0];
        }

        if (matchingMovieIds.element == 0) {
            matchingMovieIds = matchingMovieIds.next;
        }
    
        int[] foundFilms = new int[matchingMovieIds.size];
        int index = 0;
        LinkedList<Integer> current = matchingMovieIds;
        while (current != null) {
            foundFilms[index++] = current.element;
            current = current.next;
        }
    
        return foundFilms;
    }

    public class MovieInfoData {
        private int id;
        private String title;
        private String originalTitle;
        private String overview;
        private String tagline;
        private String status;
        private Genre[] genres; 
        private LocalDate release;
        private long budget;
        private long revenue;
        private String[] languages; 
        private String originalLanguage; 
        private double runtime; 
        private String homepage;
        private boolean adult;
        private boolean video;
        private String poster; 
        private double voteAverage;
        private int voteCount;
        private int collectionID;
        private String imdbID;
        private double popularity;
        private LinkedList<Company> productionCompanyList;
        private LinkedList<String> productionCountryList;
    
        
        public MovieInfoData(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
            this.id = id;
            this.title = title;
            this.originalTitle = originalTitle;
            this.overview = overview;
            this.tagline = tagline.isEmpty() ? null : tagline; 
            this.status = status;
            this.genres = genres;
            this.release = release;
            this.budget = budget;
            this.revenue = revenue;
            this.languages = languages;
            this.originalLanguage = originalLanguage;
            this.runtime = runtime;
            this.homepage = homepage;
            this.adult = adult;
            this.video = video;
            this.poster = poster;
            this.voteAverage = -1;
            this.voteCount = -1;
            this.collectionID = -1;
            this.imdbID = null;
            this.popularity = 0;
        }
        

        public void productionCompanyAdd(Company company){
            if (productionCompanyList == null) {
                productionCompanyList = new LinkedList<>();
                productionCompanyList.add(company);
            } 
            else {
                LinkedList<Company> current = productionCompanyList;
                boolean found = false;
                while (current != null) {
                    if (current.element.equals(company)) {
                        found = true;
                        break;
                    }
                    current = current.next;
                }
                if (!found) {
                    productionCompanyList.add(company);
                }
            }
        }

        public void productionCountryAdd(String country){
            if (productionCountryList == null) {
                productionCountryList = new LinkedList<>();
                productionCountryList.add(country);
            } 
            else {
                LinkedList<String> current = productionCountryList;
                boolean found = false;
                while (current != null) {
                    if (current.element.equals(country)) {
                        found = true;
                        break;
                    }
                    current = current.next;
                }
                if (!found) {
                    productionCountryList.add(country);
                }
            }
        }
    }
    
    public class CollectionData {
        private LinkedList<Integer> filmStore;
        private String collectionName;
        private String collectionPosterPath;
        private String collectionBackdropPath;
    
        public CollectionData(int filmID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
            this.filmStore = new LinkedList<>();
            filmStore.add(filmID);
            this.collectionName = collectionName;
            this.collectionPosterPath = collectionPosterPath;
            this.collectionBackdropPath = collectionBackdropPath;
        }

        public boolean filmAdd(int filmID){
            return filmStore.add(filmID);
        }
    }
    
}
