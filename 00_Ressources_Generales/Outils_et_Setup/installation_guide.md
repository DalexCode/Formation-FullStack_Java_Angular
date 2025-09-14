# 🛠️ Guide d'Installation - Environnement de Développement Full Stack

## 🎯 Vue d'Ensemble

Ce guide vous accompagne dans l'installation de tous les outils nécessaires pour développer des applications Full Stack Java/Angular.

## ✅ Prérequis Système

- **OS** : Windows 10+, macOS 10.14+, ou Linux Ubuntu 18.04+
- **RAM** : Minimum 8GB, recommandé 16GB
- **Espace disque** : Minimum 20GB libres
- **Connexion internet** : Pour les téléchargements

## 1. Java Development Kit (JDK)

### Installation Windows

Télécharger depuis https://adoptium.net/
Ou utiliser Chocolatey
choco install temurin17

### Installation macOS

Avec Homebrew
brew install openjdk@17

Ajouter au PATH
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc

### Installation Linux

Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

CentOS/RHEL
sudo yum install java-17-openjdk-devel

### Vérification

java -version
javac -version

## 2. IDE - IntelliJ IDEA

### Installation

1. Télécharger depuis https://www.jetbrains.com/idea/download/
2. Choisir **Community Edition** (gratuite)
3. Installer avec les paramètres par défaut

### Configuration Recommandée

1. **File → Settings → Plugins**
2. Installer :
   - Spring Boot
   - Angular and TypeScript
   - Docker
   - GitToolBox
   - Rainbow Brackets
   - Key Promoter X

### Thème et Police

Settings → Appearance & Behavior → Appearance
Theme: Darcula ou IntelliJ Light
Settings → Editor → Font
Font: JetBrains Mono, Size: 14

## 3. Node.js et npm

### Installation

Windows (avec Chocolatey)
choco install nodejs

macOS (avec Homebrew)
brew install node

Linux
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt-get install -y nodejs

### Vérification

node --version
npm --version

### Configuration npm

Configurer un registry rapide
npm config set registry https://registry.npmjs.org/

Installer Angular CLI globalement
npm install -g @angular/cli@latest

Vérifier Angular CLI
ng version

## 4. Base de Données

### H2 Database (Développement)

Aucune installation requise - embarquée dans Spring Boot

### PostgreSQL (Production)

#### Windows

choco install postgresql

#### macOS

brew install postgresql
brew services start postgresql

#### Linux

sudo apt update
sudo apt install postgresql postgresql-contrib

Démarrer le service
sudo systemctl start postgresql
sudo systemctl enable postgresql

#### Configuration PostgreSQL

-- Se connecter à PostgreSQL
sudo -u postgres psql

-- Créer un utilisateur
CREATE USER devuser WITH PASSWORD 'devpassword';

-- Créer une base de données
CREATE DATABASE formation_db OWNER devuser;

-- Donner les privilèges
GRANT ALL PRIVILEGES ON DATABASE formation_db TO devuser;

## 5. Contrôle de Version - Git

### Installation

Windows
choco install git

macOS
brew install git

Linux
sudo apt install git

### Configuration Globale

git config --global user.name "Votre Nom"
git config --global user.email "votre.email@example.com"
git config --global init.defaultBranch main
git config --global core.autocrlf input # Linux/Mac
git config --global core.autocrlf true # Windows

### Configuration SSH (Recommandé)

Générer une clé SSH
ssh-keygen -t ed25519 -C "votre.email@example.com"

Ajouter la clé à l'agent SSH
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

Copier la clé publique
cat ~/.ssh/id_ed25519.pub

Coller dans GitHub → Settings → SSH Keys

## 6. Docker (Optionnel - Phases Avancées)

### Installation Docker Desktop

1. Télécharger depuis https://www.docker.com/products/docker-desktop
2. Installer avec les paramètres par défaut
3. Redémarrer si demandé

### Vérification Docker

docker --version
docker-compose --version
docker run hello-world

## 🔧 Configuration d'Environnement

### Variables d'Environnement Windows

Ouvrir PowerShell en administrateur
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot", "Machine")
[Environment]::SetEnvironmentVariable("PATH", $env:PATH + ";%JAVA_HOME%\bin", "Machine")

### Variables d'Environnement macOS/Linux

Ajouter à ~/.bashrc ou ~/.zshrc
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 # Linux
export JAVA_HOME=$(/usr/libexec/java_home -v 17) # macOS
export PATH=$JAVA_HOME/bin:$PATH

Maven (si installé)
export MAVEN_HOME=/opt/maven
export PATH=$MAVEN_HOME/bin:$PATH

Recharger le fichier
source ~/.bashrc # ou ~/.zshrc

## ✅ Script de Vérification

### Créer le fichier `verify_installation.sh`

#!/bin/bash

echo "🔍 Vérification de l'environnement de développement..."
echo "================================================"

Java
echo -n "☕ Java: "
if command -v java &> /dev/null; then
java -version 2>&1 | head -1
else
echo "❌ Non installé"
fi

Node.js
echo -n "🟢 Node.js: "
if command -v node &> /dev/null; then
echo "✅ $(node --version)"
else
echo "❌ Non installé"
fi

npm
echo -n "📦 npm: "
if command -v npm &> /dev/null; then
echo "✅ $(npm --version)"
else
echo "❌ Non installé"
fi

Angular CLI
echo -n "🅰️ Angular CLI: "
if command -v ng &> /dev/null; then
echo "✅ $(ng version --json | grep '"version"' | head -1 | cut -d'"' -f4)"
else
echo "❌ Non installé"
fi

Git
echo -n "🐙 Git: "
if command -v git &> /dev/null; then
echo "✅ $(git --version)"
else
echo "❌ Non installé"
fi

PostgreSQL
echo -n "🐘 PostgreSQL: "
if command -v psql &> /dev/null; then
echo "✅ $(psql --version)"
else
echo "❌ Non installé"
fi

Docker
echo -n "🐳 Docker: "
if command -v docker &> /dev/null; then
echo "✅ $(docker --version)"
else
echo "❌ Non installé"
fi

echo "================================================"
echo "✅ Vérification terminée!"

### Exécuter le script

chmod +x verify_installation.sh
./verify_installation.sh

## 🏗️ Workspace Recommandé

### Structure de dossiers

~/Development/
├── Formation_FullStack_Java_Angular/ # Votre formation
├── Projects/ # Vos projets
│ ├── java-projects/
│ ├── angular-projects/
│ └── full-stack-projects/
├── Tools/ # Outils portables
└── Learning/ # Ressources d'apprentissage
├── books/
├── tutorials/
└── documentation/

### Configuration IntelliJ

1. **File → Project Structure**
2. **Project Settings → Project** : Java 17
3. **Platform Settings → SDKs** : Ajouter le JDK 17
4. **File → Settings → Build → Build Tools → Maven** : Vérifier la configuration

## 🔧 Outils Additionnels Recommandés

### Extensions VS Code (Alternative à IntelliJ)

Si vous préférez VS Code
code --install-extension ms-vscode.vscode-java-pack
code --install-extension angular.ng-template
code --install-extension ms-azuretools.vscode-docker
code --install-extension eamodio.gitlens

### Outils en ligne de commande

HTTPie pour tester les APIs
pip install httpie

Tree pour visualiser l'arborescence
Windows: choco install tree
macOS: brew install tree
Linux: sudo apt install tree

## 🚨 Résolution des Problèmes Courants

### Java : "JAVA_HOME not set"

Vérifier JAVA_HOME
echo $JAVA_HOME

Si vide, définir manuellement
export JAVA_HOME=/path/to/jdk

### npm : Erreurs de permissions

Configurer npm pour éviter sudo
mkdir ~/.npm-global
npm config set prefix '~/.npm-global'
echo 'export PATH=~/.npm-global/bin:$PATH' >> ~/.bashrc

### Angular CLI : Command not found

Réinstaller globalement
npm uninstall -g @angular/cli
npm cache clean --force
npm install -g @angular/cli@latest

### PostgreSQL : Connection refused

Démarrer le service
sudo systemctl start postgresql # Linux
brew services start postgresql # macOS

## 🎯 Test Final

### Créer un projet test complet

1. Créer un projet Spring Boot
   mkdir test-environment && cd test-environment
   curl https://start.spring.io/starter.zip
   -d dependencies=web,data-jpa,postgresql
   -d groupId=com.test
   -d artifactId=demo
   -o demo.zip
   unzip demo.zip && cd demo

2. Créer un projet Angular
   ng new frontend --routing --style=scss
   cd frontend && ng serve

3. Test Docker
   docker run hello-world

## 🏆 Validation Complète

Si tous les outils s'installent et fonctionnent correctement :

- ✅ **Java** compile et exécute
- ✅ **Angular** crée et sert une application
- ✅ **PostgreSQL** accepte les connexions
- ✅ **Git** peut commit et push
- ✅ **Docker** peut lancer des conteneurs

**🎉 Votre environnement est prêt pour le développement Full Stack !**

## 📚 Ressources Supplémentaires

- [Documentation Spring Boot](https://spring.io/projects/spring-boot)
- [Guide Angular](https://angular.io/guide/setup-local)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Git Handbook](https://guides.github.com/introduction/git-handbook/)

---

**Date de dernière mise à jour** : $(date)
**Version des outils** : Java 17, Node.js LTS, Angular 17+
