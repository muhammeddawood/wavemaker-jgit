package com.wavemaker.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wavemaker.jgit.api.JGitService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JGitServiceTest {
	
	private final static String REMOTE_REPOSITORY = "https://github.com/Imaginea/thecodez.git";
	
	@Autowired
	private JGitService jgitService;
	
	@Test
	public void testCloneRepository() throws IOException {
		File file = File.createTempFile("TestJGit", null);
		file.delete();
		
		jgitService.cloneRemoteRepository(REMOTE_REPOSITORY, file.getAbsolutePath());
		
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
	public void testPullRemoteRepository() throws IOException {
		File file = File.createTempFile("TestJGit", null);
		file.delete();
		
		jgitService.cloneRemoteRepository(REMOTE_REPOSITORY, file.getAbsolutePath());
		jgitService.pullFromRemoteRepository(REMOTE_REPOSITORY, file.getAbsolutePath());
		
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
	public void testAddFilesAndCommit() throws IOException {
		File gitDir = File.createTempFile("TestJGit", null);
		gitDir.delete();
		
		jgitService.cloneRemoteRepository(REMOTE_REPOSITORY, gitDir.getAbsolutePath());

		// create the file
        createNewFile(gitDir, "testfile1", "content1");
        // create the file
        createNewFile(gitDir, "testfile2", "content2");
        
        jgitService.addFilesAndCommit(gitDir.getAbsolutePath(), Arrays.asList("testfile1", "testfile2"), "First commit");
		
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