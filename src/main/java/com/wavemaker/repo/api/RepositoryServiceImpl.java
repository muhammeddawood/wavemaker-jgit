package com.wavemaker.repo.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import com.wavemaker.repo.exception.RepositoryException;

@Service
public class RepositoryServiceImpl implements RepositoryService {

	@Override
	public void clone(String url, String repo) {
		try {
			File file = new File(repo);
			Git.cloneRepository().setURI(url).setDirectory(file).call();
		} catch (GitAPIException e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public void pull(String url, String repo) {
		Repository repository = null;
		try {
			repo = repo + "/.git";
			repository = openRepository(repo);
			
			Git git = new Git(repository);
			git.pull().call();
			repository.close();
		} catch (GitAPIException | IOException e) {
			throw new RepositoryException(e);
		} finally {
			if(repository != null) {
				repository.close();
			}
		}
	}

	@Override
	public void add(String repoDir, String... files) {
		Repository repo = null;
		try {
			repo = openRepository(repoDir);
			Git git = new Git(repo);
			
			AddCommand add = git.add();
			for(String file : files) {
				add.addFilepattern(file);
			}

			// run the add-call
			add.call();
			repo.close();
		} catch (IOException | GitAPIException e) {
			throw new RepositoryException(e);
		} finally {
			if(repo != null) {
				repo.close();
			}
		}
	}
	
	@Override
	public void commit(String repoDir, String message) {
		Repository repo = null;
		try {
			repo = openRepository(repoDir);
			Git git = new Git(repo);
			git.commit().setMessage(message).call();
			repo.close();
		} catch (IOException | GitAPIException e) {
			throw new RepositoryException(e);
		} finally {
			if(repo != null) {
				repo.close();
			}
		}
	}
	
	private Repository openRepository(String dir) throws IOException {
		File file = new File(dir);
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.readEnvironment() 
				.findGitDir(file) // scan up the file system tree
				.build();
		
		return repository;
	}

	@Override
	public void remove(String repo, String... file) {
		
	}

	@Override
	public void revert(String repo) {
		
	}

	@Override
	public void move(String repo, String fromFile, String toFile) {
		
	}

	@Override
	public void init(String repo) {
		
	}

}
