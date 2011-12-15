package org.schemingpanda.reddit;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Encapsulates and provides access to current user state in a thread safe manner
 * 
 * @author William Anthony Hunt
 */
public class UserState {
	
	private UserState(){}
	
	private static AtomicBoolean loggedIn = new AtomicBoolean(false);
	private static String username = "";
	private static String modHash = "";
	private static String cookie = "";
	
	/**
	 * A method to be invoked to set properties related to a user's logged-in state
	 * @param username the name of the user logged-in
	 * @param modHash the modhash of the user logged-in 
	 * @param cookie the cookie of the user logged-in
	 */
	public static synchronized void loggedIn(String username, String modHash, String cookie){
		UserState.loggedIn.set(true);
		UserState.cookie = cookie;
		UserState.modHash = modHash;
		UserState.username = username;
	}
	
	/**
	 * A method to be invoked when a user logs out
	 */
	public static synchronized void loggedOut(){
		UserState.loggedIn.set(false);
		UserState.cookie = "";
		UserState.modHash = "";
		UserState.username = "";
	}
	
	/**
	 * Returns a boolean indicating whether the user is logged-in
	 * @return a boolean: {@code true} if the user is logged-in, {@code false} otherwise
	 */
	public static synchronized boolean isLoggedIn(){
		return loggedIn.get();
	}
	
	/**
	 * Returns the name of the user currently logged-in, or an empty string is user is not logged in.
	 * @return the name of the user currently logged-in, or an empty string is user is not logged in.
	 */
	public static synchronized String getUsername(){
		return username;
	}
	
	/**
	 * Returns the cookie of the user currently logged-in, or an empty string is user is not logged in.
	 * @return the cookie of the user currently logged-in, or an empty string is user is not logged in.
	 */
	public static synchronized String getCookie(){
		return cookie;
	}
	
	/**
	 * Returns the modhash of the user currently logged-in, or an empty string is user is not logged in.
	 * @return the modhash of the user currently logged-in, or an empty string is user is not logged in.
	 */
	public static synchronized String getModHash(){
		return modHash;
	}

	
}
