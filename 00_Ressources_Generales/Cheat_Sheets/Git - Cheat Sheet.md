# Cheat Sheets Git Complètes

## Table des Matières

1. [Configuration et Initialisation](#1-configuration-et-initialisation)
2. [Commandes de Base](#2-commandes-de-base)
3. [Branches et Merging](#3-branches-et-merging)
4. [Repositories Distants](#4-repositories-distants)
5. [Historique et Inspection](#5-historique-et-inspection)
6. [Stash et Nettoyage](#6-stash-et-nettoyage)
7. [Tags et Releases](#7-tags-et-releases)
8. [SSH et Authentification Multiple](#8-ssh-et-authentification-multiple)
9. [Workflows Avancés](#9-workflows-avancés)
10. [Résolution de Conflits](#10-résolution-de-conflits)
11. [Git Hooks](#11-git-hooks)
12. [Sous-modules](#12-sous-modules)
13. [Rebase et Cherry-pick Avancés](#13-rebase-et-cherry-pick-avancés)
14. [Best Practices et Optimisation](#14-best-practices-et-optimisation)
15. [Troubleshooting](#15-troubleshooting)

---

## 1. Configuration et Initialisation

### Configuration globale
```bash
# Configuration utilisateur
git config --global user.name "Votre Nom"
git config --global user.email "votre.email@example.com"

# Configuration d'éditeur
git config --global core.editor "code --wait"  # VS Code
git config --global core.editor "vim"          # Vim

# Configuration des fins de ligne
git config --global core.autocrlf true         # Windows
git config --global core.autocrlf input        # macOS/Linux

# Configuration des couleurs
git config --global color.ui auto

# Voir toute la configuration
git config --list
git config --global --list
```

### Initialisation de repository
```bash
# Nouveau repository local
git init
git init nom-projet

# Cloner un repository existant
git clone https://github.com/user/repo.git
git clone https://github.com/user/repo.git nom-dossier

# Cloner une branche spécifique
git clone -b develop https://github.com/user/repo.git

# Cloner avec profondeur limitée (shallow clone)
git clone --depth 1 https://github.com/user/repo.git
```

---

## 2. Commandes de Base

### Status et Information
```bash
# Statut du repository
git status
git status -s                    # Format court
git status --porcelain           # Format machine-readable

# Différences
git diff                         # Working directory vs staging
git diff --staged                # Staging vs dernier commit
git diff HEAD                    # Working directory vs dernier commit
git diff branch1 branch2         # Entre deux branches
git diff commit1 commit2         # Entre deux commits

# Information sur le repository
git remote -v                    # Repositories distants
git branch -a                    # Toutes les branches
git log --oneline -10            # 10 derniers commits
```

### Staging et Commits
```bash
# Staging (ajout à l'index)
git add file.txt                 # Fichier spécifique
git add .                        # Tous les fichiers modifiés
git add -A                       # Tous les fichiers (même les supprimés)
git add -p                       # Staging interactif par hunks

# Unstaging
git reset file.txt               # Retirer du staging
git reset                        # Tout retirer du staging

# Commits
git commit -m "Message de commit"
git commit -am "Message"         # Add + commit pour fichiers trackés
git commit --amend               # Modifier le dernier commit
git commit --amend -m "Nouveau message"

# Commits avec templates
git commit -t template.txt       # Utiliser un template
git config commit.template ~/.gitmessage.txt
```

### Annulation et Restauration
```bash
# Annuler des modifications
git checkout -- file.txt        # Annuler modifications working directory
git restore file.txt            # Nouvelle syntaxe (Git 2.23+)
git restore --staged file.txt   # Unstage un fichier

# Reset (attention : peut perdre des données)
git reset --soft HEAD~1         # Annule le commit, garde staging
git reset --mixed HEAD~1        # Annule commit + staging (par défaut)
git reset --hard HEAD~1         # Annule tout (DANGER!)

# Revert (création d'un nouveau commit)
git revert commit-hash           # Annule un commit spécifique
git revert HEAD                  # Annule le dernier commit
git revert --no-commit commit-hash  # Prépare le revert sans commit
```

---

## 3. Branches et Merging

### Gestion des Branches
```bash
# Créer et naviguer
git branch feature-branch        # Créer une branche
git checkout feature-branch     # Changer de branche
git checkout -b feature-branch  # Créer et changer
git switch feature-branch       # Nouvelle syntaxe (Git 2.23+)
git switch -c feature-branch    # Créer et changer

# Lister les branches
git branch                       # Branches locales
git branch -r                    # Branches distantes
git branch -a                    # Toutes les branches
git branch -v                    # Avec dernier commit
git branch --merged              # Branches mergées
git branch --no-merged           # Branches non mergées

# Supprimer des branches
git branch -d feature-branch     # Supprimer (safe)
git branch -D feature-branch     # Forcer la suppression
git push origin --delete feature-branch  # Supprimer branche distante
```

### Merging
```bash
# Merge classique
git checkout main
git merge feature-branch

# Merge sans fast-forward (garde l'historique)
git merge --no-ff feature-branch

# Merge avec message personnalisé
git merge -m "Merge message" feature-branch

# Annuler un merge en cours
git merge --abort

# Squash merge (combine tous les commits en un)
git merge --squash feature-branch
git commit -m "Feature: description"
```

### Rebase
```bash
# Rebase simple
git checkout feature-branch
git rebase main

# Rebase interactif
git rebase -i HEAD~3             # 3 derniers commits
git rebase -i main               # Depuis la branche main

# Résoudre les conflits pendant rebase
git rebase --continue            # Continuer après résolution
git rebase --skip                # Ignorer le commit
git rebase --abort               # Abandonner le rebase

# Rebase avec preservation des merges
git rebase -p main               # Preserve les merge commits
git rebase --rebase-merges main  # Nouvelle syntaxe
```

---

## 4. Repositories Distants

### Configuration des Remotes
```bash
# Gérer les remotes
git remote add origin https://github.com/user/repo.git
git remote add upstream https://github.com/original/repo.git
git remote rename origin new-origin
git remote remove origin
git remote set-url origin https://github.com/user/new-repo.git

# Informations sur les remotes
git remote show origin
git remote -v
git ls-remote origin
```

### Push et Pull
```bash
# Push
git push origin main             # Push vers branche spécifique
git push -u origin main          # Push et set upstream
git push --all origin            # Push toutes les branches
git push --tags origin           # Push tous les tags
git push --force                 # Force push (DANGER!)
git push --force-with-lease      # Force push plus sûr

# Pull et Fetch
git fetch origin                 # Récupérer les changements
git fetch --all                  # Fetch tous les remotes
git pull origin main             # Fetch + merge
git pull --rebase origin main    # Fetch + rebase
git pull --ff-only origin main   # Pull seulement si fast-forward

# Tracking branches
git branch --set-upstream-to=origin/main main
git branch -u origin/main main  # Version courte
git branch --unset-upstream main # Supprimer le tracking
```

### Synchronisation avancée
```bash
# Synchroniser avec upstream (fork workflow)
git fetch upstream
git checkout main
git merge upstream/main
git push origin main

# Prune (supprimer références branches supprimées)
git fetch --prune origin
git remote prune origin

# Mirror (miroir exact)
git clone --mirror https://github.com/user/repo.git
```

---

## 5. Historique et Inspection

### Log et Historique
```bash
# Log basique
git log
git log --oneline                # Format compact
git log --graph                  # Graphique ASCII
git log --decorate               # Montrer les références
git log --all                    # Toutes les branches

# Log avancé
git log -10                      # 10 derniers commits
git log --since="2 weeks ago"    # Depuis 2 semaines
git log --until="2023-01-01"     # Jusqu'à une date
git log --author="John"          # Par auteur
git log --grep="fix"             # Par message de commit
git log -S "function_name"       # Pickaxe search (contenu)
git log -p file.txt              # Historique d'un fichier avec diff

# Log formaté
git log --pretty=format:"%h - %an, %ar : %s"
git log --pretty=oneline
git log --graph --pretty=format:"%C(yellow)%h%C(reset) -%C(red)%d%C(reset) %s %C(green)(%cr) %C(bold blue)<%an>%C(reset)" --abbrev-commit
```

### Inspection détaillée
```bash
# Show (détails d'un commit)
git show commit-hash
git show HEAD                    # Dernier commit
git show HEAD~2                  # Il y a 2 commits
git show branch-name:file.txt    # Fichier à un moment donné

# Blame (qui a modifié quoi)
git blame file.txt
git blame -L 10,20 file.txt      # Lignes 10 à 20
git blame -C file.txt            # Détecte les copies/moves

# Statistiques
git shortlog                     # Résumé par auteur
git shortlog -sn                 # Nombre de commits par auteur
git log --stat                   # Statistiques des fichiers
git log --numstat                # Format numérique
```

---

## 6. Stash et Nettoyage

### Git Stash
```bash
# Stash basique
git stash                        # Sauvegarder les changements
git stash push -m "Work in progress"  # Avec message
git stash -u                     # Inclure les fichiers untracked
git stash --include-untracked    # Version longue

# Gestion du stash
git stash list                   # Lister les stash
git stash show                   # Montrer le dernier stash
git stash show stash@{1}         # Montrer un stash spécifique
git stash show -p                # Avec diff

# Appliquer et supprimer
git stash pop                    # Appliquer et supprimer
git stash apply                  # Appliquer sans supprimer
git stash apply stash@{1}        # Appliquer un stash spécifique
git stash drop                   # Supprimer le dernier stash
git stash drop stash@{1}         # Supprimer un stash spécifique
git stash clear                  # Vider tous les stash

# Stash avancé
git stash branch new-branch stash@{1}  # Créer branche depuis stash
git stash create                 # Créer stash sans l'appliquer
```

### Nettoyage
```bash
# Clean (supprimer fichiers non trackés)
git clean -n                     # Dry run (simulation)
git clean -f                     # Forcer la suppression
git clean -fd                    # Inclure les dossiers
git clean -fx                    # Inclure les fichiers ignorés
git clean -i                     # Mode interactif

# Maintenance
git gc                           # Garbage collection
git gc --aggressive              # GC aggressif
git gc --prune=now               # Prune immédiat
git fsck                         # Vérifier l'intégrité
git count-objects -v             # Statistiques objets
```

---

## 7. Tags et Releases

### Gestion des Tags
```bash
# Créer des tags
git tag v1.0.0                   # Tag léger
git tag -a v1.0.0 -m "Version 1.0.0"  # Tag annoté
git tag -a v1.0.0 commit-hash    # Tag sur commit spécifique

# Lister et inspecter
git tag                          # Lister tous les tags
git tag -l "v1.*"                # Pattern matching
git show v1.0.0                  # Details du tag
git tag -n                       # Tags avec messages

# Push et partage
git push origin v1.0.0           # Push un tag spécifique
git push origin --tags           # Push tous les tags
git push --follow-tags           # Push commits + tags

# Supprimer des tags
git tag -d v1.0.0                # Supprimer localement
git push origin --delete v1.0.0  # Supprimer sur remote
git push origin :refs/tags/v1.0.0  # Syntaxe alternative
```

### Releases et versioning
```bash
# Tags sémantiques
git tag -a v1.0.0 -m "Initial release"
git tag -a v1.1.0 -m "Minor update"
git tag -a v2.0.0 -m "Major release"

# Pre-releases
git tag -a v1.0.0-alpha -m "Alpha version"
git tag -a v1.0.0-beta.1 -m "Beta version"
git tag -a v1.0.0-rc.1 -m "Release candidate"

# Checkout sur tag
git checkout v1.0.0              # Detached HEAD
git checkout -b hotfix-v1.0.1 v1.0.0  # Nouvelle branche depuis tag
```

---

## 8. SSH et Authentification Multiple

### Configuration SSH de base
```bash
# Générer une clé SSH
ssh-keygen -t ed25519 -C "votre.email@example.com"
ssh-keygen -t rsa -b 4096 -C "votre.email@example.com"  # Si ed25519 non supporté

# Démarrer ssh-agent
eval "$(ssh-agent -s)"          # Linux/macOS
eval $(ssh-agent)               # Windows Git Bash

# Ajouter les clés
ssh-add ~/.ssh/id_ed25519
ssh-add -l                      # Lister les clés chargées
ssh-add -D                      # Supprimer toutes les clés
```

### Configuration multi-comptes
```bash
# Structure des clés
~/.ssh/
├── id_rsa_personnel             # Clé compte personnel
├── id_rsa_personnel.pub
├── id_rsa_travail               # Clé compte travail  
├── id_rsa_travail.pub
└── config                       # Configuration SSH
```

**Configuration ~/.ssh/config :**
```bash
# Compte personnel
Host github.com
    HostName github.com
    User git
    IdentityFile ~/.ssh/id_rsa_personnel
    IdentitiesOnly yes

# Compte travail
Host github-work
    HostName github.com
    User git
    Port 22
    IdentityFile ~/.ssh/id_rsa_travail
    IdentitiesOnly yes

# Configuration avancée
Host github-work
    HostName ssh.github.com
    Port 443
    User git
    IdentityFile ~/.ssh/id_rsa_travail
    IdentitiesOnly yes
```

### Utilisation avec multi-comptes
```bash
# Clone avec compte personnel (défaut)
git clone git@github.com:user/repo.git

# Clone avec compte travail
git clone git@github-work:company/repo.git

# Changer remote pour utiliser compte travail
git remote set-url origin git@github-work:company/repo.git

# Configuration par projet
cd projet-travail
git config user.name "Nom Professionnel"
git config user.email "nom.professionnel@entreprise.com"

# Test des connexions
ssh -T git@github.com            # Test compte personnel
ssh -T git@github-work           # Test compte travail
```

---

## 9. Workflows Avancés

### Git Flow
```bash
# Installation (si pas intégré)
# Linux: apt install git-flow
# macOS: brew install git-flow
# Windows: Git Flow extension

# Initialisation
git flow init

# Feature branches
git flow feature start nouvelle-feature
git flow feature finish nouvelle-feature
git flow feature publish nouvelle-feature    # Push vers remote
git flow feature pull origin nouvelle-feature # Pull depuis remote

# Release branches  
git flow release start v1.2.0
git flow release finish v1.2.0

# Hotfix branches
git flow hotfix start v1.2.1
git flow hotfix finish v1.2.1
```

### GitHub Flow (simplifié)
```bash
# 1. Créer une branche feature
git checkout -b feature/nouvelle-fonctionnalite

# 2. Développer et commiter
git add .
git commit -m "Ajout nouvelle fonctionnalité"

# 3. Push et créer PR
git push -u origin feature/nouvelle-fonctionnalite

# 4. Après review et merge, nettoyer
git checkout main
git pull origin main
git branch -d feature/nouvelle-fonctionnalite
```

### Forking Workflow
```bash
# 1. Fork le repository sur GitHub

# 2. Clone votre fork
git clone git@github.com:votre-nom/projet.git

# 3. Ajouter upstream
git remote add upstream git@github.com:original/projet.git

# 4. Créer feature branch
git checkout -b feature-branch

# 5. Développer et push vers votre fork
git push origin feature-branch

# 6. Synchroniser avec upstream
git fetch upstream
git checkout main
git merge upstream/main
git push origin main
```

### Semantic Versioning avec Conventional Commits
```bash
# Format des messages de commit
feat: ajout nouvelle fonctionnalité     # MINOR version
fix: correction de bug                  # PATCH version  
BREAKING CHANGE: modification majeure   # MAJOR version

# Exemples
git commit -m "feat(auth): ajout login OAuth"
git commit -m "fix(ui): correction bouton responsive"  
git commit -m "docs: mise à jour README"
git commit -m "style: formatage code selon ESLint"
git commit -m "refactor: restructuration modules"
git commit -m "test: ajout tests unitaires"
git commit -m "chore: mise à jour dépendances"

# Breaking changes
git commit -m "feat!: nouvelle API incompatible

BREAKING CHANGE: la fonction authenticate() 
nécessite maintenant un paramètre token"
```

---

## 10. Résolution de Conflits

### Types de conflits
```bash
# Conflits de merge
git merge feature-branch
# Auto-merging file.txt
# CONFLICT (content): Merge conflict in file.txt
# Automatic merge failed; fix conflicts and then commit the result.

# Conflits de rebase
git rebase main
# error: could not apply commit... fix conflicts and run "git rebase --continue"
```

### Résolution manuelle
```bash
# 1. Voir les fichiers en conflit
git status

# 2. Éditer les fichiers (chercher les marqueurs)
<<<<<<< HEAD
Code de la branche actuelle
=======
Code de la branche mergée
>>>>>>> feature-branch

# 3. Après résolution
git add fichier-resolu.txt

# 4. Finaliser
git commit                      # Pour merge
git rebase --continue           # Pour rebase
```

### Outils de résolution
```bash
# Configurer un outil de merge
git config --global merge.tool vimdiff      # Vim
git config --global merge.tool code         # VS Code
git config --global merge.tool meld         # Meld

# Lancer l'outil
git mergetool

# Voir les stratégies de merge
git merge -X ours feature-branch     # Privilégier notre version
git merge -X theirs feature-branch   # Privilégier leur version
git merge --no-commit feature-branch # Merge sans commit automatique
```

### Prévention des conflits
```bash
# Synchronisation régulière
git fetch origin
git rebase origin/main           # Maintenir à jour

# Petits commits fréquents
git commit -m "WIP: fonction A"
git commit -m "WIP: fonction B"  

# Communication équipe sur les zones modifiées
# Utiliser .gitattributes pour stratégies spécifiques
```

---

## 11. Git Hooks

### Types de hooks
```bash
# Client-side hooks (dans .git/hooks/)
pre-commit          # Avant commit
prepare-commit-msg  # Préparer message commit
commit-msg          # Valider message commit
post-commit         # Après commit
pre-rebase          # Avant rebase
post-checkout       # Après checkout
post-merge          # Après merge
pre-push            # Avant push

# Server-side hooks
pre-receive         # Avant réception
update             # Par branche mise à jour
post-receive       # Après réception
```

### Exemples de hooks

**Pre-commit (vérifications avant commit) :**
```bash
#!/bin/sh
# .git/hooks/pre-commit

# Vérifier le style de code
npm run lint
if [ $? -ne 0 ]; then
    echo "Lint failed, commit aborted"
    exit 1
fi

# Vérifier les tests
npm test
if [ $? -ne 0 ]; then
    echo "Tests failed, commit aborted"  
    exit 1
fi

# Vérifier les secrets
if grep -r "password\|secret\|key" --include="*.js" --include="*.py" .; then
    echo "Potential secrets found, commit aborted"
    exit 1
fi
```

**Commit-msg (format des messages) :**
```bash
#!/bin/sh
# .git/hooks/commit-msg

commit_regex='^(feat|fix|docs|style|refactor|test|chore)(\(.+\))?: .{1,50}'

if ! grep -qE "$commit_regex" "$1"; then
    echo "Invalid commit message format!"
    echo "Format: type(scope): description"
    echo "Example: feat(auth): add login functionality"
    exit 1
fi
```

**Post-commit (notifications) :**
```bash
#!/bin/sh
# .git/hooks/post-commit

# Envoyer notification
commit_hash=$(git rev-parse HEAD)
commit_msg=$(git log -1 --pretty=%B)
author=$(git log -1 --pretty=%an)

curl -X POST "https://hooks.slack.com/services/..." \
  -H 'Content-type: application/json' \
  --data "{\"text\":\"New commit by $author: $commit_msg ($commit_hash)\"}"
```

### Installation et gestion des hooks
```bash
# Rendre exécutable
chmod +x .git/hooks/pre-commit

# Partager les hooks (dans le projet)
mkdir .githooks
cp .git/hooks/* .githooks/
git config core.hooksPath .githooks

# Framework pre-commit (Python)
pip install pre-commit
# Créer .pre-commit-config.yaml
pre-commit install
pre-commit run --all-files
```

---

## 12. Sous-modules

### Gestion de base
```bash
# Ajouter un sous-module
git submodule add https://github.com/user/library.git libs/library
git commit -m "Ajout sous-module library"

# Cloner avec sous-modules
git clone --recursive https://github.com/user/project.git
# ou
git clone https://github.com/user/project.git
git submodule init
git submodule update

# Alternative avec une commande
git submodule update --init --recursive
```

### Mise à jour des sous-modules
```bash
# Mettre à jour tous les sous-modules
git submodule update --remote

# Mettre à jour un sous-module spécifique  
git submodule update --remote libs/library

# Mettre à jour vers une version spécifique
cd libs/library
git checkout v2.1.0
cd ../..
git add libs/library
git commit -m "Update library to v2.1.0"

# Automatiser les mises à jour
git config submodule.libs/library.update rebase
git submodule update --remote --rebase
```

### Configuration avancée
```bash
# Configurer la branche à suivre
git config -f .gitmodules submodule.libs/library.branch develop

# Push récursif (inclut les sous-modules)
git push --recurse-submodules=check      # Vérification
git push --recurse-submodules=on-demand  # Push automatique

# Foreach (exécuter commande dans tous les sous-modules)
git submodule foreach 'git pull origin master'
git submodule foreach 'git checkout -b feature-branch'

# Status des sous-modules
git submodule status
git submodule summary
```

### Suppression de sous-modules
```bash
# 1. Deinitialiser
git submodule deinit -f libs/library

# 2. Supprimer du git
git rm -f libs/library

# 3. Nettoyer les références
rm -rf .git/modules/libs/library

# 4. Commiter
git commit -m "Remove library submodule"
```

### Alternatives aux sous-modules
```bash
# Git Subtree (plus simple)
git subtree add --prefix=libs/library https://github.com/user/library.git master
git subtree pull --prefix=libs/library https://github.com/user/library.git master
git subtree push --prefix=libs/library https://github.com/user/library.git master

# Package managers (npm, composer, etc.)
npm install library
pip install library
```

---

## 13. Rebase et Cherry-pick Avancés

### Rebase Interactif
```bash
# Rebase interactif des 5 derniers commits
git rebase -i HEAD~5

# Options dans l'éditeur :
pick    # Utiliser le commit
reword  # Modifier le message
edit    # Modifier le commit  
squash  # Fusionner avec le commit précédent
fixup   # Comme squash mais ignore le message
drop    # Supprimer le commit
```

**Exemple de session interactive :**
```bash
pick 1234567 Add feature A
reword 2345678 Fix bug in feature B  
squash 3456789 Add tests for feature A
fixup 4567890 Fix typo
drop 5678901 Debug commit
```

### Rebase avec autosquash
```bash
# Créer commits avec marqueurs
git commit -m "feat: add new function"
git commit --fixup HEAD           # Fixup du commit précédent  
git commit --squash HEAD~1        # Squash avec commit spécifique

# Rebase automatique avec autosquash
git rebase -i --autosquash HEAD~5

# Configuration par défaut
git config --global rebase.autoSquash true
```

### Cherry-pick avancé
```bash
# Cherry-pick basique
git cherry-pick commit-hash

# Cherry-pick multiple commits
git cherry-pick commit1 commit2 commit3
git cherry-pick commit1..commit3     # Range (exclut commit1)
git cherry-pick commit1^..commit3    # Range (inclut commit1)

# Cherry-pick sans commit
git cherry-pick -n commit-hash       # --no-commit
git cherry-pick --no-commit commit-hash

# Cherry-pick avec modification
git cherry-pick -e commit-hash       # --edit (modifier message)
git cherry-pick -x commit-hash       # Ajouter référence origine

# Résoudre conflits durant cherry-pick
git cherry-pick --continue           # Continuer après résolution
git cherry-pick --skip               # Ignorer ce commit
git cherry-pick --abort              # Abandonner le cherry-pick
```

### Strategies de rebase
```bash
# Rebase avec stratégie
git rebase -X ours main              # Privilégier notre version
git rebase -X theirs main            # Privilégier leur version

# Rebase en préservant merges
git rebase --preserve-merges main    # Ancienne syntaxe
git rebase --rebase-merges main      # Nouvelle syntaxe

# Rebase avec signature
git rebase --committer-date-is-author-date main
git rebase --ignore-date main
```

---

## 14. Best Practices et Optimisation

### Messages de commit
```bash
# Format recommandé
<type>(<scope>): <subject>

<body>

<footer>

# Types courants
feat     # Nouvelle fonctionnalité
fix      # Correction de bug
docs     # Documentation
style    # Formatage, pas de changement de code
refactor # Refactoring
test     # Ajout/modification de tests
chore    # Maintenance, build, etc.

# Exemples
git commit -m "feat(auth): add OAuth2 integration

- Add Google OAuth2 provider
- Update user registration flow
- Add OAuth2 configuration

Closes #123"
```

### Structure de branches
```bash
# Nommage des branches
feature/nom-fonctionnalite
bugfix/description-bug
hotfix/correction-urgente
release/v1.2.0
experimental/nouvelle-approche

# Branches de longue durée
main/master    # Production
develop        # Intégration
staging        # Tests

# Workflow recommandé
git checkout develop
git pull origin develop
git checkout -b feature/nouvelle-fonctionnalite
# ... développement ...
git push -u origin feature/nouvelle-fonctionnalite
# ... Pull Request/Merge Request ...
git checkout develop
git pull origin develop
git branch -d feature/nouvelle-fonctionnalite
```

### Configuration optimisée
```bash
# Performance
git config --global core.preloadindex true
git config --global core.fscache true
git config --global gc.auto 256

# Sécurité
git config --global user.useConfigOnly true
git config --global push.default simple
git config --global pull.rebase true

# Interface
git config --global color.ui auto
git config --global core.editor "code --wait"
git config --global init.defaultBranch main

# Alias utiles
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.unstage 'reset HEAD --'
git config --global alias.last 'log -1 HEAD'
git config --global alias.visual '!gitk'
git config --global alias.graph 'log --graph --oneline --decorate --all'
git config --global alias.aliases 'config --get-regexp alias'
```

### .gitignore efficace
```bash
# Structure par catégorie
# OS
.DS_Store
Thumbs.db
*.swp
*.swo

# IDE
.vscode/
.idea/
*.sublime-*

# Languages
node_modules/
*.pyc
__pycache__/
target/
*.class

# Build
dist/
build/
*.o
*.so

# Secrets
.env
*.key
*.pem

# Logs
*.log
logs/

# Temporary
tmp/
temp/
```

### Maintenance du repository
```bash
# Nettoyage régulier
git gc --aggressive --prune=now
git remote prune origin
git branch --merged | grep -v "\*\|main\|develop" | xargs -n 1 git branch -d

# Vérification intégrité
git fsck --full
git count-objects -v

# Statistiques
git shortlog -sn
git log --since="1 month ago" --oneline | wc -l
git diff --stat HEAD~10..HEAD
```

---

## 15. Troubleshooting

### Problèmes courants
```bash
# Annuler le dernier commit (plusieurs options)
git reset --soft HEAD~1      # Garde les changements stagés
git reset HEAD~1             # Garde les changements non stagés  
git reset --hard HEAD~1      # SUPPRIME tout

# Récupérer un commit "perdu"
git reflog                   # Voir tous les changements
git checkout commit-hash     # Revenir à un commit
git branch recovery commit-hash  # Créer branche de récupération

# Changer l'auteur du dernier commit
git commit --amend --author="Nom <email@example.com>"

# Changer plusieurs commits
git rebase -i HEAD~5
# Marquer commits avec 'edit', puis pour chaque :
git commit --amend --author="Nom <email@example.com>"
git rebase --continue
```

### Problèmes de push
```bash
# Push rejeté (non fast-forward)
git pull --rebase origin main   # Rebase local
git push origin main

# Forcer le push (ATTENTION!)
git push --force-with-lease origin main  # Plus sûr
git push --force origin main             # DANGEREUX

# Certificat SSL
git config --global http.sslVerify false  # TEMPORAIRE uniquement
git config --global http.sslCAInfo /path/to/certificate
```

### Problèmes de performance
```bash
# Repository lent
git gc --aggressive
git repack -Ad
git prune

# Fichiers volumineux
git filter-branch --tree-filter 'rm -f huge-file' HEAD
git filter-repo --path huge-file --invert-paths  # Outil moderne

# Clones partiels (Git 2.19+)
git clone --filter=blob:none <url>      # Pas les blobs
git clone --filter=tree:0 <url>         # Juste les commits
```

### Récupération d'urgence
```bash
# Récupérer fichier supprimé
git ls-files --deleted
git checkout HEAD -- fichier-supprime.txt

# Récupérer branche supprimée
git reflog --all | grep "checkout.*branch-name"
git branch branch-name commit-hash

# Récupérer depuis stash supprimé
git fsck --unreachable | grep commit | cut -d\  -f3 | xargs git log --merges --no-walk --grep=WIP
```

### Debug avancé
```bash
# Bisect (chercher commit problématique)
git bisect start
git bisect bad HEAD
git bisect good v1.0
# Git checkout un commit, tester
git bisect good    # ou bad
# Répéter jusqu'à trouver
git bisect reset

# Blame avancé
git blame -w file.txt           # Ignore whitespace
git blame -M file.txt           # Détecte moves
git blame -C file.txt           # Détecte copies

# Log de debug
git log --walk-reflogs
git log --follow file.txt       # Suit renommages
git log --grep="pattern"
git log -S "code-pattern" --source --all
```

---

## Alias Git Recommandés

Ajoutez ces alias à votre configuration Git pour une utilisation plus efficace :

```bash
# Alias de base
git config --global alias.co checkout
git config --global alias.br branch  
git config --global alias.ci commit
git config --global alias.st status

# Alias avancés
git config --global alias.unstage 'reset HEAD --'
git config --global alias.last 'log -1 HEAD'
git config --global alias.graph 'log --graph --pretty=format:"%C(yellow)%h%C(reset) -%C(red)%d%C(reset) %s %C(green)(%cr) %C(bold blue)<%an>%C(reset)" --abbrev-commit'
git config --global alias.branches 'branch -a'
git config --global alias.tags 'tag -l'
git config --global alias.remotes 'remote -v'

# Alias workflow
git config --global alias.up 'pull --rebase --autostash'
git config --global alias.save 'stash push -u'
git config --global alias.wip 'commit -am "WIP"'
git config --global alias.undo 'reset HEAD~1 --mixed'
git config --global alias.amend 'commit -a --amend'
```

---

## Références et Ressources

Cette cheat sheet Git complète couvre les aspects essentiels et avancés de Git pour les développeurs. Pour approfondir :

1. **Documentation officielle Git** - https://git-scm.com/doc
2. **Pro Git Book** - https://git-scm.com/book (gratuit)
3. **GitHub Guides** - https://guides.github.com/
4. **Atlassian Git Tutorials** - https://www.atlassian.com/git/tutorials
5. **Git Flow** - https://nvie.com/posts/a-successful-git-branching-model/
6. **Conventional Commits** - https://www.conventionalcommits.org/
7. **Git Hooks** - https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks
8. **Git Internals** - Pour comprendre le fonctionnement interne
9. **Oh My Zsh Git Plugin** - Aliases et helpers
10. **GitKraken/SourceTree** - Interfaces graphiques

---

*Dernière mise à jour : Septembre 2025*