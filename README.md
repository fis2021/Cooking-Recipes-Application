# **Cooking-Recipes-Application**

## Table of Contents
- [General Description](#general-description)
- [Technologies Used](#technologies-used)
- [Registration](#registration)
- [Junior Chef](#junior-chef)
- [Head Chef](#head-chef)
- [Issue Tracking](#issue-tracking)

## General Description

This application aims to help users by giving them a list of recipes made by "Head Chefs".
They can search in this list by introducing words contained in the recipes name. Any "Chef"
(logged in user) can rate recipes, log out to the startup page and delete their account.

## Technologies Used
- [Java 16](https://www.oracle.com/java/technologies/javase-downloads.html)
- [JavaFX (as GUI)](https://openjfx.io/openjfx-docs/)
- [Maven (Build Tool)](https://maven.apache.org/)
- [Nitrite Java](https://www.dizitart.org/nitrite-database.html) (as Database)

## Registration
Anyone can search for recipes. In order to rate a recipe, the user needs to first register into the application by selecting one of the 2 roles:
- Junior Chef
- Head Chef

Both roles require a unique username, a password and the basic information
like full name and email address.

Afterwards, they can choose to log out to the start up page or they can delete their account.

## Junior Chef
A Junior Chef can save his favourite recipes to view them later.

## Head Chef
A Head Chef can create new recipes. They are added to the recipe list.

He can modify his own recipes but doing so resets the raiting.

He can also check his own recipes to see how well they are doing 
(by their ratings from other Chefs).

## Issue Tracking
For the purpose of demonstrating a complete Agile Workflow, we
created a Jira instance that you can find
[here]( https://the-night-heron.atlassian.net ).
