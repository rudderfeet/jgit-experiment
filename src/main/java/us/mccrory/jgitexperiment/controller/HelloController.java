package us.mccrory.jgitexperiment.controller;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() throws IllegalStateException, GitAPIException, RevisionSyntaxException, MissingObjectException, IncorrectObjectTypeException, AmbiguousObjectException, IOException {

		File repoDir = new File("/Users/smccrory/Applications/jGit/jgit-experiment");
		Git git = Git.cloneRepository().setURI("https://github.com/rudderfeet/jgit-experiment.git")
				.setDirectory(repoDir).call();
		Repository repository = git.getRepository();

		StringBuffer message = new StringBuffer();
		Iterable<RevCommit> logs = git.log().call();

		int count = 0;
		for (RevCommit rev : logs) {
			// System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id:
			// " + rev.getId().getName() */);
			count++;
		}
		System.out.println("Had " + count + " commits overall on current branch");

		logs = git.log().add(repository.resolve(git.getRepository().getFullBranch())).call();
		count = 0;
		for (RevCommit rev : logs) {
			System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
			count++;
		}
		System.out.println("Had " + count + " commits overall on " + git.getRepository().getFullBranch());

		logs = git.log().all().call();
		count = 0;
		for (RevCommit rev : logs) {
			// System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id:
			// " + rev.getId().getName() */);
			count++;
		}
		System.out.println("Had " + count + " commits overall in repository");

		logs = git.log()
				// for all log.all()
				.addPath("README.md").call();
		count = 0;
		for (RevCommit rev : logs) {
			// System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id:
			// " + rev.getId().getName() */);
			count++;
		}
		System.out.println("Had " + count + " commits on README.md");

		logs = git.log()
				// for all log.all()
				.addPath("pom.xml").call();
		count = 0;
		for (RevCommit rev : logs) {
			// System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id:
			// " + rev.getId().getName() */);
			count++;
		}
		System.out.println("Had " + count + " commits on pom.xml");

		return message.toString();

	}

}