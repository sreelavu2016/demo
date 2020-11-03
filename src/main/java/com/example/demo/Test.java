package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;

public class Test {

	public static void main(String[] args)
			throws IOException, InvalidRemoteException, TransportException, GitAPIException, URISyntaxException {

		// Local directory on this machine where we will clone remote repo.
		File localRepoDir = new File("D:\\Adobe\\raj1");
		File localRepoDirCheck = new File(localRepoDir + "\\.git");

		// Monitor to get git command progress printed on java System.out console
		TextProgressMonitor consoleProgressMonitor = new TextProgressMonitor(new PrintWriter(System.out));

		Repository repo;
		Git git = null;
		if (localRepoDirCheck.exists()) {
			repo = Git.open(localRepoDir).getRepository();
		} else {
			git = Git.cloneRepository().setURI("https://github.com/lavudirisala/sample.git")
					.setCredentialsProvider(
							new UsernamePasswordCredentialsProvider("lavanya.dirisala@gmail.com", "github@7733"))
					.setDirectory(localRepoDir).setCloneAllBranches(true).call();
			repo = git.getRepository();
		}

		/*
		 * Get list of all branches (including remote) & print
		 *
		 * Equivalent of --> $ git branch -a
		 *
		 */
		git = new Git(repo);
		boolean isBranchExit = existsBranch(localRepoDir, "Test5");
		if (isBranchExit) {
			git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider("lavanya.dirisala@gmail.com", "github@7733")).call();
			git.branchCreate().setForce(true).setName("Test5").setStartPoint("origin/" + "Test5").call();
			git.checkout().setName("Test5").call();
		} else {
			git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider("lavanya.dirisala@gmail.com", "github@7733")).call();
			git.branchCreate().setForce(true).setName("Test5").setStartPoint("origin/" + "sample").call();
			git.checkout().setName("Test5").call();
		}
		localRepoDir = repo.getWorkTree();
		try (Git git1 = new Git(repo)) {

			readFolder(localRepoDir, repo);
			// create the file
			/*File myFile = new File(repo.getDirectory().getParent(), "rasidwarner");
			if (!myFile.createNewFile()) {
				throw new IOException("Could not create file " + myFile);
			}
*/
			// Stage all files in the repo including new files
			git1.add().addFilepattern(".").call();

			// and then commit the changes.

			//git1.commit().setMessage("Commit all changes including additions").call();

			/*try (PrintWriter writer = new PrintWriter(myFile)) {
				writer.append("Hello, world!");
			}*/

			// Stage all changed files, omitting new files, and commit with one command
			git1.commit().setAll(true).setMessage("Commit changes to all files").call();

			RemoteAddCommand remoteAddCommand = git1.remoteAdd();
			remoteAddCommand.setName("origin");
			// remoteAddCommand.setUri(new URIish("https://github.com/.git"));//url
			// corretected
			remoteAddCommand.setUri(new URIish("https://github.com/lavudirisala/sample.git"));// url corretected
			// you can add more settings here if needed
			remoteAddCommand.call();
			// push to remote:
			PushCommand pushCommand = git1.push();
			pushCommand.setCredentialsProvider(
					new UsernamePasswordCredentialsProvider("lavanya.dirisala@gmail.com", "github@7733"));
			// you can add more settings here if needed
			pushCommand.call();

			System.out.println("Committed all changes to repository at " + repo.getDirectory());
		}

	}

	private static boolean existsBranch(File localRepoDir, String branchName) {
		try {
			Git git = Git.open(localRepoDir);
			ListBranchCommand listBranchCmd = git.branchList();
			listBranchCmd.setListMode(ListBranchCommand.ListMode.ALL);
			List<Ref> refs = listBranchCmd.call();
			for (Ref ref : refs) {
				if (ref.getName().equals("refs/heads/" + branchName)) {
					return true;
				}
			}
			return false;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (GitAPIException ex) {
			throw new RuntimeException(ex);
		}
	}




	private static void readFolder(File localRepoDir, Repository repo) {
		System.out.println(localRepoDir.getPath());
		for (final File fileEntry : localRepoDir.listFiles()) {
			if (fileEntry.isDirectory()) {
				File myfile = new File(repo.getDirectory().getParent(), fileEntry.getName());
			} else {
				readFolder(localRepoDir, repo);
			}
		}
	}
}
