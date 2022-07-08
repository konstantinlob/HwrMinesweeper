# HWR OOP Lecture Project Template

[TODO]: # (Change README.md Headline to better fit to your project!)

This repository contains a student project created for an ongoing lecture on object-oriented programming with Java at HWR Berlin (summer term 2022).

> :warning: This code is for educational purpose only. Do not rely on it!

## Abstract
[TODO]: # (Write a short description of your project.)
A simple console based minesweeper game which takes commands (X Y)  to uncover cells on a field   

mapsize(0-99) and the difficultly (1, 2, 3)of the map are the main extra features of the program, which is to be used and played in the intellij terminal!

interesting problems which occurred during development:  
- finding out how the uncovering algorithm works for minesweeper, which was solved relatively simply by uncovering the center cells, finding its non-diagonal 
neighbours, and if these are not a bomb or already uncovered, recursively use the uncover method on those cells.

## Feature List

[TODO]: # (For each feature implemented, add a row to the table!)

| Number | Feature                                         | Tests |
|-----|-------------------------------------------------|-------|
| 1   | custom fieldsizes 0-99                          | yes   |
| 2   | chooseable gamemode (leicht, mittel, schwer)    | yes   |


## Additional Dependencies

| Number | Dependency Name | Dependency Description | Why is it necessary? |
|--------|--------------|------------------------|----------------------|
| 0      | None         | -                      | -                    |


