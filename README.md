# eBank - Application de Gestion Bancaire

Une application web complète de gestion bancaire développée avec **Spring Boot 3** pour le backend et **React TypeScript** pour le frontend.

## 🚨 IMPORTANT - Si vous obtenez "Login ou mot de passe erronés"

**Problème**: Cette erreur apparaît car le backend n'est pas démarré. Le backend nécessite Java 17 et Maven.

**Solution rapide**:
1. **Exécutez**: `install-prerequisites.bat` en tant qu'administrateur (clic droit → "Exécuter en tant qu'administrateur")
2. **Redémarrez** votre terminal après l'installation
3. **Suivez** les étapes "Installation Backend" ci-dessous

**Voir aussi**: `INSTALLATION_GUIDE.md` pour un guide détaillé en anglais

## 🏗️ Architecture

- **Backend**: Spring Boot 3 + Spring Security + JWT + MySQL
- **Frontend**: React 18 + TypeScript + Material-UI
- **Sécurité**: JWT avec expiration 1h, mots de passe cryptés BCrypt
- **Base de données**: MySQL 8
- **Communication**: API REST

## 👥 Fonctionnalités par Rôle

### 👨‍💼 AGENT_GUICHET
- ✅ Ajouter nouveau client (avec envoi d'email automatique)
- ✅ Créer nouveau compte bancaire
- ✅ Validation des données selon les règles métier

### 👤 CLIENT
- ✅ Consulter tableau de bord (comptes, soldes, opérations)
- ✅ Effectuer virements avec validation complète
- ✅ Pagination des opérations
- ✅ Sélection de compte source

### 🔐 Commun
- ✅ Authentification JWT sécurisée
- ✅ Changement de mot de passe
- ✅ Interface responsive et moderne

## 🛠️ Prérequis

### Backend
- **Java 17** ou supérieur
- **Maven 3.6+**
- **MySQL 8.0+**

### Frontend
- **Node.js 16+**
- **npm 7+**

## 📋 Installation

### 1. Cloner le projet
```bash
git clone [votre-repo]
cd examen-eBank
```

### 2. Configuration Base de Données

#### Installer MySQL
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# Windows: Télécharger depuis https://dev.mysql.com/downloads/mysql/
# macOS: brew install mysql
```

#### Créer la base de données
```sql
-- Se connecter à MySQL
mysql -u root -p

-- Créer la base de données
CREATE DATABASE ebank_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Créer un utilisateur (optionnel)
CREATE USER 'ebank_user'@'localhost' IDENTIFIED BY 'ebank_password';
GRANT ALL PRIVILEGES ON ebank_db.* TO 'ebank_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Installation Backend

#### Installer Java 17
```bash
# Ubuntu/Debian
sudo apt install openjdk-17-jdk

# Windows: Exécuter install-prerequisites.bat OU télécharger depuis https://adoptium.net/
# macOS: brew install openjdk@17
```

#### Installer Maven
```bash
# Ubuntu/Debian
sudo apt install maven

# Windows: Exécuter install-prerequisites.bat OU télécharger depuis https://maven.apache.org/download.cgi
# macOS: brew install maven
```

#### Configurer et lancer le backend
```bash
cd ebank-backend

# Modifier application.yml si nécessaire (utilisateur/mot de passe MySQL)
# Les valeurs par défaut sont : username: root, password: root

# Compiler et lancer
mvn clean install
mvn spring-boot:run
```

Le backend sera accessible sur `http://localhost:8080/api`

### 4. Installation Frontend

```bash
cd ebank-frontend

# Installer les dépendances
npm install

# Lancer l'application
npm start
```

Le frontend sera accessible sur `http://localhost:3000`

## 🎯 Test de l'Application

### Comptes de test disponibles

| Rôle | Login | Mot de passe | Description |
|------|-------|--------------|-------------|
| AGENT_GUICHET | `agent1` | `password` | Agent de guichet |
| CLIENT | `client1` | `password` | Client avec 2 comptes |
| CLIENT | `client2` | `password` | Client avec 1 compte |

### Données de test incluses

- **3 utilisateurs** (1 agent, 2 clients)
- **3 comptes bancaires** avec soldes différents
- **6 opérations** d'exemple (dépôts, virements, retraits)

## 🔍 Cas d'usage à tester

### 1. Authentification (UC-1)
1. Aller sur `http://localhost:3000`
2. Se connecter avec `agent1` / `password`
3. Vérifier l'accès aux fonctionnalités agent
4. Se déconnecter et tester avec `client1` / `password`

### 2. Ajouter Client (UC-2) - Agent seulement
1. Se connecter en tant qu'agent
2. Aller dans "Ajouter client"
3. Remplir le formulaire avec des données valides
4. Vérifier la création et l'envoi d'email

### 3. Nouveau Compte (UC-3) - Agent seulement
1. Utiliser "Nouveau compte"
2. Saisir un RIB valide et le numéro d'identité d'un client existant
3. Vérifier la création avec statut "Ouvert"

### 4. Tableau de Bord (UC-4) - Client seulement
1. Se connecter en tant que client
2. Voir le tableau de bord avec comptes et opérations
3. Tester la sélection de compte et pagination

### 5. Virement (UC-5) - Client seulement
1. Aller dans "Nouveau virement"
2. Sélectionner compte source
3. Saisir montant, RIB destinataire, motif
4. Vérifier les validations et l'exécution

## 🏗️ Structure du Projet

```
examen-eBank/
├── ebank-backend/                 # Backend Spring Boot
│   ├── src/main/java/com/ebank/
│   │   ├── entities/             # Entités JPA
│   │   ├── repositories/         # Repositories Spring Data
│   │   ├── services/            # Services métier
│   │   ├── controllers/         # Contrôleurs REST
│   │   ├── dto/                 # DTOs
│   │   ├── security/            # Configuration sécurité JWT
│   │   ├── config/              # Configuration Spring
│   │   └── exceptions/          # Gestion des erreurs
│   └── src/main/resources/
│       ├── application.yml      # Configuration
│       └── data.sql            # Données de test
├── ebank-frontend/               # Frontend React
│   ├── src/
│   │   ├── components/          # Composants React
│   │   ├── contexts/            # Contextes (Auth)
│   │   ├── services/            # Services API
│   │   └── types/              # Types TypeScript
│   └── package.json
├── install-prerequisites.bat    # Script d'installation Windows
├── INSTALLATION_GUIDE.md        # Guide détaillé en anglais
└── README.md
```

## 🔧 Configuration

### Backend (application.yml)
```yaml
# Configuration base de données
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ebank_db
    username: root
    password: root

# Configuration JWT
jwt:
  secret: [clé-secrète]
  expiration: 3600000  # 1 heure

# Configuration email
spring:
  mail:
    host: smtp.gmail.com
    username: your-email@gmail.com
    password: your-app-password
```

### Frontend (src/services/api.ts)
```typescript
// URL du backend
private baseURL = 'http://localhost:8080/api';
```

## 🛡️ Sécurité Implémentée

- **Authentification JWT** avec expiration 1h (RG_3)
- **Mots de passe cryptés** avec BCrypt (RG_1)
- **Contrôle d'accès** basé sur les rôles
- **Validation côté client et serveur**
- **Protection CORS** configurée
- **Gestion des erreurs** unifiée

## 🎨 UI/UX

- **Design moderne** avec Material-UI
- **Interface responsive** (mobile, tablet, desktop)
- **Navigation intuitive** basée sur les rôles
- **Feedback utilisateur** (loading, erreurs, succès)
- **Thème personnalisé** eBank

## 🚀 Déploiement Production

### Backend
```bash
# Construire le JAR
mvn clean package -DskipTests

# Lancer en production
java -jar target/ebank-backend-1.0.0.jar --spring.profiles.active=prod
```

### Frontend
```bash
# Construire pour production
npm run build

# Servir les fichiers statiques
npx serve -s build
```

## 📝 Notes Importantes

1. **Email Configuration**: Configurer les paramètres SMTP dans `application.yml` pour l'envoi d'emails
2. **Base de données**: Changer `ddl-auto` de `create-drop` à `update` en production
3. **Sécurité JWT**: Utiliser une clé secrète forte en production
4. **CORS**: Ajuster les origines autorisées selon l'environnement

## 🐛 Dépannage

### Backend ne démarre pas
- Vérifier que MySQL est démarré
- Vérifier les credentials dans `application.yml`
- Vérifier que Java 17+ est installé

### Frontend ne se connecte pas
- Vérifier que le backend est accessible sur port 8080
- Vérifier la configuration CORS
- Ouvrir les outils développeur pour voir les erreurs

### Erreurs de compilation
- Vérifier les versions Node.js et npm
- Supprimer `node_modules` et faire `npm install`
- Vérifier que toutes les dépendances sont installées

## 👨‍💻 Développement

### Lancer en mode développement
```bash
# Backend (avec rechargement auto)
cd ebank-backend
mvn spring-boot:run

# Frontend (avec rechargement auto)
cd ebank-frontend
npm start
```

### Tests
```bash
# Backend
mvn test

# Frontend
npm test
```

---

## 📞 Support

Pour toute question ou problème, vérifiez d'abord que toutes les étapes d'installation ont été suivies correctement. 