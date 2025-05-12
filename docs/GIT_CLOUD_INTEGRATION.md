# Git Integration Guide

This guide provides step-by-step instructions for setting up a Git repository for your Appium Cucumber Framework and publishing it to GitHub.

## Setting Up Git Repository

### Step 1: Initialize Git in Your Project

```bash
# Navigate to your project directory
cd path/to/AppiumCucumberFramework

# Initialize Git repository
git init
```

### Step 2: Create .gitignore File

Create a `.gitignore` file in the project root with the following content:

```
# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties

# IntelliJ IDEA
.idea/
*.iml
*.iws
*.ipr
out/

# Eclipse
.classpath
.project
.settings/

# Mac
.DS_Store

# Windows
Thumbs.db
Desktop.ini

# Logs
logs/
*.log

# Appium logs
*.log

# Local configuration
*.local
```

### Step 3: Add and Commit Files

```bash
# Add all files to the repository (except those in .gitignore)
git add .

# Commit files with a meaningful message
git commit -m "Initial commit of Appium Cucumber Framework"
```

### Step 4: Create a Repository on GitHub

1. Go to [GitHub](https://github.com/)
2. Log in to your account
3. Click on the "+" in the top right corner and select "New repository"
4. Name your repository (e.g., "AppiumCucumberFramework")
5. Add a description (optional)
6. Choose public or private visibility
7. Skip the initialization options (README, .gitignore, license)
8. Click "Create repository"

### Step 5: Link Local Repository to GitHub

```bash
# Add the remote repository URL
git remote add origin https://github.com/YOUR_USERNAME/AppiumCucumberFramework.git

# Push your code to GitHub
git push -u origin master
```

## Basic Git Commands and Workflow

### Status and Differences

```bash
# Check repository status
git status

# View changes
git diff
```

### Branches

```bash
# Create a new branch
git branch feature/new-feature

# Switch to branch
git checkout feature/new-feature

# Create and switch to branch in one command
git checkout -b feature/new-feature

# List branches
git branch
```

### Working with Changes

```bash
# Add specific files
git add file1.java file2.java

# Add all changes
git add .

# Commit changes
git commit -m "Description of changes"

# Amend last commit
git commit --amend -m "New commit message"
```

### Remote Operations

```bash
# Push changes to remote
git push origin branch-name

# Pull changes from remote
git pull origin branch-name

# Fetch remote branches
git fetch origin
```

### Merge and Rebase

```bash
# Merge a branch into current branch
git merge branch-name

# Rebase current branch onto another
git rebase branch-name
```

### Viewing History

```bash
# View commit history
git log

# View commit history with graph
git log --graph --oneline --all
```

## Git Workflow for Team Development

1. Always pull before starting work:
   ```bash
   git pull origin master
   ```

2. Create a feature branch for your work:
   ```bash
   git checkout -b feature/my-feature
   ```

3. Make changes and commit frequently:
   ```bash
   git add .
   git commit -m "Meaningful description of changes"
   ```

4. Push your branch to GitHub:
   ```bash
   git push origin feature/my-feature
   ```

5. Create a Pull Request on GitHub to merge your changes

6. After approval, merge the Pull Request on GitHub

7. Update your local master:
   ```bash
   git checkout master
   git pull origin master
   ```

## Best Practices

1. **Commit Messages**: Write clear, concise commit messages that explain what changes were made and why

2. **Small Commits**: Make small, focused commits rather than large ones with many unrelated changes

3. **Feature Branches**: Use feature branches for developing new features or fixing bugs

4. **Regular Updates**: Regularly pull from the master branch to avoid large merge conflicts

5. **Meaningful Branch Names**: Use descriptive branch names like `feature/login-tests` or `fix/hotel-search-issue`

6. **No Sensitive Data**: Never commit sensitive data like passwords, API keys or personal information

7. **Use Pull Requests**: Use pull requests for code review before merging to master

## Troubleshooting

### Merge Conflicts

If you encounter merge conflicts:

```bash
# During a merge
git status              # See which files have conflicts
# Edit files to resolve conflicts
git add .               # Mark conflicts as resolved
git commit              # Complete the merge

# If you need to abort a merge
git merge --abort
```

### Undoing Changes

```bash
# Discard changes in working directory
git restore filename

# Unstage a file
git restore --staged filename

# Undo last commit but keep changes
git reset HEAD~1

# Discard last commit and changes
git reset --hard HEAD~1
```

### Authentication Issues

If you're having trouble with authentication:

1. Use a personal access token instead of password
2. Set up SSH keys for authentication
3. Use GitHub CLI for authentication