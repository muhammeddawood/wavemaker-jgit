package com.wavemaker.repo.api;

import java.util.List;

public interface RepositoryService {

	void clone(String url, String repoDir);

	void pull(String url, String repoDir);
	
	void add(String repoDir, List<String> files);

	void commit(String repoDir, String message);
}
