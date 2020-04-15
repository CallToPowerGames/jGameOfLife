# jGameOfLife

Conway's Game of Life implementation.

## Rules

### Active cells

* Each cell with one or no neighbors dies
* Each cell with four or more neighbors dies
* Each cell with two or three neighbors survives.

### Inactice cells

* Each cell with three neighbors becomes populated.

## Copyright

(C) 2016-2020 Denis Meyer

## Features

* "Live" update cells via mouse click while game is running
* Different generation speeds
* Manual next generation
* Configurable field size
* Import & Export patterns
* Example patterns (under "patterns")

## Prerequisites

* Java 8
* Ant

## Screenshot

![Screenshot](img/screenshot.jpg?raw=true)

![Screenshot - xkcd Conway](img/screenshot_xkcd_conway.jpg?raw=true)

## Build

There are different ant tasks configured:

* clean
* compile
* jar
* run-jar
