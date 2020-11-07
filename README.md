# HepialCompiler

**Developed by:** Quentin Leblanc, Justin Foltz

**Date:** 06.2019

## The project

HepialCompiler is a compiler of the simple invented Hepial language. It is based on grammar rules reduction to generate `.class` files using Java.

## Technologies

* *FLEX* (allows you to identify the language elements in the source program);
* *CUP* (verifies that the syntax of the elements sent by *FLEX* respects the grammar rules specific to the *Hepial* language);
* *JAVA* (semantic analysis of the source program thanks to the reading of a tree built during the reduction of *CUP* rules. Generates a *JASMIN* program while reading the tree);
* *JASMIN* : (generates the final image from the previously built program).

## How to run the project ?

### Prerequisites

Package *jflex* must be installed.

### Running the project

1. Clone the repository

2. In the root folder, type one of the following command :

   * Compilation of default program in the `test` file into the `demo.class` output file

     ```bash
     make
     ```

   * Compilation of a user defined program

     ```bash
     make hepial FILEIN=<src> FILEOUT=<dst>
     ```

The program `test` provided display a list of different simple programs implemented as demo :

- *pyramid.hepial*: displays a pyramid of ''*'' whose size depends on the number entered by the user;
- *game.hepial* : game where the user has to find a number in 5 attempts with the indications "too big" or "too small";
- *errors.hepial* : program containing errors. The location and cause of errors are specified by the compiler.

### Cleaning the project

The command below allows you to clean the compiled files :

```bash
make clean
```
