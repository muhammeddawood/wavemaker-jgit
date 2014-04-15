package com.wavemaker.repo.controller;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wavemaker.repo.api.RepositoryService;

@Controller
@RequestMapping("/vc")
public class RepositoryController {

	@Autowired
	private ServletContext context;
	@Autowired
	private RepositoryService repositoryService;
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
	@ResponseStatus(HttpStatus.CREATED)
	public void clone(@RequestParam String url, @RequestParam(value="dir") String repoDir) {
		String realPath = appFolder + "/" + repoDir;
		repositoryService.clone(url, realPath);
	}
	
	@RequestMapping(value="/pull", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void pull(@RequestParam String url, @RequestParam(value="dir") String repoDir) {
		String realPath = appFolder + "/" + repoDir;
		repositoryService.pull(url, realPath);
	}
	
	@RequestMapping(value="/track", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void add(@RequestParam(value="dir") String repoDir, @RequestParam String[] files) {
		String realPath = appFolder + "/" + repoDir;
		repositoryService.add(realPath, files);
	}
	
	@RequestMapping(value="/commit", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void add(@RequestParam(value="dir") String repoDir, @RequestParam String message) {
		String realPath = appFolder + "/" + repoDir;
		repositoryService.commit(realPath, message);
	}
}
