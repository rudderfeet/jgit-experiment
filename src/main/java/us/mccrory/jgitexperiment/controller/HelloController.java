package us.mccrory.jgitexperiment.controller;

import java.io.File;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() throws IllegalStateException, GitAPIException {
    	
    	File repoDir = new File("/Users/smccrory/Applications/jGit/jgit-experiment");    	
    	Git git = Git.cloneRepository()
    			  .setURI("https://github.com/rudderfeet/jgit-experiment.git")
    			  .setDirectory(repoDir)
    			  .call();
    	
    	RevWalk walk = new RevWalk(git.getRepository());
    	
    	StringBuffer message = new StringBuffer();
		for (Iterator<RevCommit> revWalkIter = walk.iterator(); revWalkIter.hasNext();) {
			RevCommit revCommit = (RevCommit) revWalkIter.next();
			message.append(revCommit.getShortMessage());
			message.append("<BR/>");		
		}
		
		walk.close();
    	
        return message.toString();
    }

}