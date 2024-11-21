# NOTE APPLICATION

Application permettant de gérer des notes par catégories

## FEATURES 

- Authentification par jwt
- Création de notes
- Création de catégories
- Suppression notes
- Suppression catégories
- Ajout des catégories au notes
- List des notes par catégories
- List des notes par utilisateur

## DIAGRAMME 

Les diagrammes ont été réalisés avec Draw.io et 
sont placé a la racine du projet dans le 
fichier .drawio

## Installation 

Projet gérer via docker

se mettre à la racine du projet et lancer
```bash
    docker compose up --build
```

le projet se lance sur localhost:8080

le serveur est configuré par defaut pour se relancer à chaque changement dans le code