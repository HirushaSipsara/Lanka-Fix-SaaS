# Git Feature Branch Workflow for LankaFIX Team

## 1. Start a New Feature
**Always start from the latest code.**
```bash
# Switch to main and get latest changes
git checkout main
git pull origin main

# Create your feature branch
# Naming: feature/<your-feature-name>
# Example: feature/worker-profiles
git checkout -b feature/worker-profiles
```

## 2. Work & Commit
Make your changes in your branch. Commit often!
```bash
git add .
git commit -m "Added worker profile model and repository"
```

## 3. Push to GitHub
Upload your branch so the team can see it.
```bash
# The first time you push a new branch:
git push -u origin feature/worker-profiles

# Afterwards, just:
git push
```

## 4. Merge (Pull Request)
**Do not merge manually on your laptop!**
1. Go to GitHub.
2. You will see a "Compare & pull request" button. Click it.
3. Review the changes.
4. **Ask a team member to approve it.** (Code Review).
5. Click "Merge".

## 5. Sync & Continue
Once your branch is merged, delete it locally and start the next one.
```bash
git checkout main
git pull origin main
git branch -d feature/worker-profiles
```
