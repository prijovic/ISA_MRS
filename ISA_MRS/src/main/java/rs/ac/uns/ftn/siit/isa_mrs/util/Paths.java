package rs.ac.uns.ftn.siit.isa_mrs.util;

public class Paths {
    public static final String CROSS_ORIGIN = "http://localhost:3000";
    public static final String TOKEN_CONTROLLER = "/";
    public static final String LOGIN = "/login";
    public static final String REFRESH_TOKEN = "/refreshToken";
    public static final String USER_CONTROLLER = "/Users";
    public static  final String REQUEST_CONTROLLER = "/Requests";
    public static final String PASSWORD_CHANGE = USER_CONTROLLER + "/passChange";
    public static final String DELETE_USER = USER_CONTROLLER + "/del";
    public static final String RENTAL_OBJECT_CONTROLLER = "/RentalObjects";
    public static final String GET_VACATION_RENTAL = RENTAL_OBJECT_CONTROLLER + "/getVacationRental";
    public static final String GET_VACATION_RENTALS = RENTAL_OBJECT_CONTROLLER + "/getVacationRentals";
    public static final String GET_BOAT = RENTAL_OBJECT_CONTROLLER + "/getBoat";
    public static final String GET_BOATS = RENTAL_OBJECT_CONTROLLER + "/getBoats";
    public static final String GET_ADVENTURE = RENTAL_OBJECT_CONTROLLER + "/getAdventure";
    public static final String GET_ADVENTURES = RENTAL_OBJECT_CONTROLLER + "/getAdventures";
    public static final String DELETE_ACCOUNT = "/delAcc";
}
