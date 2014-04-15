package com.wavemaker.repo.api;


public interface RepositoryService {

	/**
	 * Create or initialize a Git repository in the given repo directory  
	 * @param repo
	 */
	void init(String repo);
	
	/**
	 * Clones the source from the given url to the repo directory
	 * @param url
	 * @param repo
	 */
	void clone(String url, String repo);

	/**
	 * To pull the source from the given url to the repo directory
	 * @param url
	 * @param repo
	 */
	void pull(String url, String repo);
	
	/**
	 * Add the specified (new / modified) files for next commit
	 * @param repo
	 * @param files
	 */
	void add(String repo, String... file);
	
	/**
	 * Remove the specified files from the repository 
	 * @param repo
	 * @param file
	 */
	void remove(String repo, String... file);
	
	/**
	 * Move / rename a given file  
	 * @param repo
	 * @param file
	 */
	void move(String repo, String fromFile, String toFile);

	/**
	 * Commit all the tracked changes
	 * @param repo
	 * @param message
	 */
	void commit(String repo, String message);
	
	/**
	 * Revert to previous revision 
	 * @param repo
	 */
	void revert(String repo);
	
}
