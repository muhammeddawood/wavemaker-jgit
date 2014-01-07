package com.wavemaker.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import com.wavemaker.jgit.exception.JGitAPIException;

@Service
public class JGitServiceImpl implements JGitService {

	@Override
	public void cloneRemoteRepository(String url, String local) {
		try {
			File file = new File(local);
			Git.cloneRepository().setURI(url).setDirectory(file).call();
		} catch (GitAPIException e) {
			throw new JGitAPIException(e);
		}
	}

	@Override
	public void pullFromRemoteRepository(String url, String local) {
		Repository repository = null;
		try {
			local = local + "/.git";
			repository = openRepository(local);
			
			Git git = new Git(repository);
			git.pull().call();
			repository.close();
		} catch (GitAPIException | IOException e) {
			throw new JGitAPIException(e);
		} finally {
			if(repository != null) {
				repository.close();
			}
		}
	}

	@Override
	public void addFilesAndCommit(String localRepo, List<String> files, String message) {
		Repository repo = null;
		try {
			repo = openRepository(localRepo);

			Git git = new Git(repo);

			AddCommand add = git.add();
			for(String file : files) {
				add.addFilepattern(file);
			}

			// run the add-call
			add.call();
			git.commit().setMessage(message).call();
			repo.close();
		} catch (IOException | GitAPIException e) {
			throw new JGitAPIException(e);
		} finally {
			if(repo != null) {
				repo.close();
			}
		}
	}
	
	private Repository openRepository(String local) throws IOException {
		File file = new File(local);
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.readEnvironment() 
				.findGitDir(file) // scan up the file system tree
				.build();
		
		return repository;
	}
}
