package com.wavemaker.repo.api;


public interface RepositoryService {

	/**
	 * Clones the source from the given url to the repo directory
	 * @param url
	 * @param repoDir
	 */
	void clone(String url, String repoDir);

	/**
	 * To pull the source from the given url to the repo directory
	 * @param url
	 * @param repoDir
	 */
	void pull(String url, String repoDir);
	
	/**
	 * Track the listed files for next commit
	 * @param repoDir
	 * @param files
	 */
	void track(String repoDir, String... file);
	
	/**
	 * Untrack the given files from next commit 
	 * @param repoDir
	 * @param file
	 */
	void untrack(String repoDir, String... file);

	/**
	 * Commit all the tracked changes
	 * @param repoDir
	 * @param message
	 */
	void commit(String repoDir, String message);
	
	/**
	 * Revert to previous revision 
	 * @param repoDir
	 */
	void revert(String repoDir);
	
}
