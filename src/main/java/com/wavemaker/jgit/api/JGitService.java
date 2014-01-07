package com.wavemaker.jgit.api;

import java.util.List;

public interface JGitService {

	void cloneRemoteRepository(String url, String localPath);

	void pullFromRemoteRepository(String url, String local);
	
	void addFilesAndCommit(String localRepo, List<String> files, String message);
}
