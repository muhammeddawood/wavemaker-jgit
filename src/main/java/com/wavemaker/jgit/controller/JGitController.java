package com.wavemaker.jgit.controller;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wavemaker.jgit.api.JGitService;

@Controller("/jgit")
public class JGitController {

	@Autowired
	private ServletContext context;
	@Autowired
	private JGitService jgitService;
	@Value("#{appProperties['app.folder']}")
	private String appFolder;
	
	@PostConstruct
	public void afterPropertiesSet() {
		File file = new File(appFolder);
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	@RequestMapping(value="/clone", method=RequestMethod.POST)
	public void cloneRemoteRepository(@RequestParam String url, @RequestParam String path) {
		String realPath = appFolder + "/" + path;
		jgitService.cloneRemoteRepository(url, realPath);
	}
	
	@RequestMapping(value="/pull", method=RequestMethod.POST)
	public void pullFromRemoteRepository(@RequestParam String url, @RequestParam String path) {
		String realPath = appFolder + "/" + path;
		jgitService.pullFromRemoteRepository(url, realPath);
	}
	
	@RequestMapping(value="/addAndCommit", method=RequestMethod.POST)
	public void addFilesAndCommit(@RequestParam String localRepo, @RequestParam List<String> files,
			@RequestParam String message) {
		String realPath = appFolder + "/" + localRepo;
		jgitService.addFilesAndCommit(realPath, files, message);
	}
}
