

# Burgerquizz

## Introduction

What I had in mind : 
This instance would run on a raspberrypi connected to a screen which will display the score, buzz results and jingles.

Two players and a game master will connect through any web browser.

## Setup

### Build

Build : mvn install

### Environment setup

You'll have to have a video repository like this :

* |-intro
  * |-videonameWithoutAnySpaces
  * |-...
* |-nuggets
  * |-...
* |-addition
  * |-...
* |-burger-de-la-mort
  * |-...
* |-menus
  * |-...
* |-sel-ou-poivre
  * |-...
* |-divers
  * |-...
* |-sounds
  * |-mayo-sound.mp3
  * |-ketchup-sound.mp3`

## Start 

Launch :

_java -jar [GENERATEDWAR] path-to-video-files_


## Play

then connect as player ex : 'http://rasperrypi.local:8080/player.html'
connect as game master 'http://rasperrypi.local:8080/master.html'

# Technically :

Quick and dirty, any improvment velcome ^^

A Springboot instance to manage websocket and a java swing app for the display because it's meant to run locally.

