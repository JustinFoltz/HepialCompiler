# HepialCompiler

**Réalisé par :** Quentin Leblanc, Justin Foltz

**Date :** 06.2019

## Le projet

Ce projet consiste à implémenter un compilateur d'un langage simplifié : Hepial.

## Pré-requis :

Le paquet *jflex* est nécessaire pour faire fonctionner ce compilateur.

## Lancement du compilateur

LE programme peut être lancé avec la commande suivante : 

```bash
make
```

Dans ce cas le compilateur utilisasera le fichier source *test.hepial* et generera le fichier bytecode *demo.class*.

Pour modifier ces parametres, il est possible de lancer le programme avec les arguments suivants :

```bash
make hepial FILEIN=<src> FILEOUT=<dst>
```

## Fonctionnement

![](./fonctionnement.jpg)

* *FLEX* : permet d'identifier les éléments du langage dans le programme source
* *CUP* : vérifie que la syntaxe des éléments envoyés par *FLEX* respecte les règles de grammaire spécifiques au langage *Hepial*;
* *JAVA* : analyse sémantique du programme source grâce à la lecture d'un arbre construit pendant la réduction des règles de *CUP*. Génère un programme *JASMIN* durant la lecture de l'arbre;
* *JASMIN* : génère l'image finale à partir du programme précédemment construit.

## Arbre abstrait

![](./arbre.jpg)