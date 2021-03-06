package com.wavemaker.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wavemaker.repo.api.RepositoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RepositoryServiceTest {
	
	private final static String REMOTE_REPOSITORY = "https://github.com/Imaginea/inventory.git";
	
	@Autowired
	private RepositoryService repoService;
	
	@Test
	public void cloneRepository() throws IOException {
		File file = File.createTempFile("TestRepo", null);
		file.delete();
		
		repoService.clone(REMOTE_REPOSITORY, file.getAbsolutePath());
		
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
        
		Repository repository = builder.setGitDir(file)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();

		Assert.assertEquals(file.getAbsolutePath(), repository.getDirectory().toString());
		
		repository.close();
		FileUtils.deleteQuietly(file);
	}
	
	@Test
	public void pull() throws IOException {
		File file = File.createTempFile("TestRepo", null);
		file.delete();
		
		repoService.clone(REMOTE_REPOSITORY, file.getAbsolutePath());
		repoService.pull(REMOTE_REPOSITORY, file.getAbsolutePath());
		
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(file)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
		
		Assert.assertEquals(file.getAbsolutePath(), repository.getDirectory().toString());
		
		repository.close();
		FileUtils.deleteQuietly(file);
	}
	
	@Test
	public void addAndCommit() throws IOException {
		File gitDir = File.createTempFile("TestRepo", null);
		gitDir.delete();
		
		repoService.clone(REMOTE_REPOSITORY, gitDir.getAbsolutePath());

		// create the file
        createNewFile(gitDir, "testfile1", "content1");
        // create the file
        createNewFile(gitDir, "testfile2", "content2");
        
        File file = new File("Gemfile");
        file.renameTo(new File("Gemfile2"));
        
        repoService.add(gitDir.getAbsolutePath(), "testfile1", "testfile2", "Gemfile2", "config.ru");
        
        repoService.commit(gitDir.getAbsolutePath(), "First Commit");
		
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(gitDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
		
		//Assert.assertEquals(2, repository.getDirectory().listFiles().length);
		
		repository.close();
		FileUtils.deleteQuietly(gitDir);
	}
	
	private void createNewFile(File dir, String fileName, String content) throws IOException {
		File file = new File(dir, fileName);
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.print(content);
		writer.close();
	}
}
