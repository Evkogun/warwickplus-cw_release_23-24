package stores;
import structures.*;
import java.time.LocalDate;


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
    private double voteAverage = -1;
    private int voteCount = -1;
    private int collectionID = -1;
    private String imdbID = null;
    private double popularity = 0;
    private LinkedList<Company> productionCompanyList;
    private LinkedList<String> productionCountryList;

    
    public MovieInfoData(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.tagline = tagline; 
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

    }
    
    public boolean setVote(double voteAverageReplacement, int voteCountReplacement){
        this.voteAverage = voteAverageReplacement;
        this.voteCount = voteCountReplacement;
        return true;
    }

    public void productionCompanyAdd(Company company) {
        if (productionCompanyList == null) productionCompanyList = new LinkedList<>(); 
        if (!productionCompanyList.contains(company)) productionCompanyList.add(company);
    }

    public void productionCountryAdd(String country){
        if (productionCountryList == null) productionCountryList = new LinkedList<>(); 
        if (!productionCountryList.contains(country)) productionCountryList.add(country);
    }

    public Company[] getProductionCompanies(){
        if (productionCompanyList == null) return new Company[0];
        return productionCompanyList.getValuesCompany();
    }

    public String[] getProductionCountries(){
        if (productionCountryList == null) return new String[0];
        return productionCountryList.getValuesCountry();
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public LocalDate getRelease() {
        return release;
    }

    public long getBudget() {
        return budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public double getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public boolean isAdult() {
        return adult;
    }

    public boolean isVideo() {
        return video;
    }

    public String getPoster() {
        return poster;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public double getPopularity() {
        return popularity;
    }

    public LinkedList<Company> getProductionCompanyList() {
        return productionCompanyList;
    }

    public LinkedList<String> getProductionCountryList() {
        return productionCountryList;
    }

    public void setImbdID(String imbdid){
        this.imdbID = imbdid;
    }

    public void setCollectionID(int collectionid){
        this.collectionID = collectionid;
    }

    public void setPopularity(double popularityreplacement){
        this.popularity = popularityreplacement;
    }
}