# **Cooking-Recipes-Application**

## _Table of Contents_
- [General Description](#_general-description_)
- [Technologies Used](#_technologies-used_)
- [Registration](#_registration_)
- [Junior Chef](#_junior-chef_)
- [Head Chef](#_head-chef_)
- [Issue Tracking](#_issue-tracking_)

## _General Description_

This application aims to help users by giving them a list of recipes made by "Head Chefs".
They can search in this list by introducing words contained in the recipes name. Any "Chef"
(logged in user) can rate and add comments on recipes.

## _Technologies Used_
- [Java 16](https://www.oracle.com/java/technologies/javase-downloads.html)
- [JavaFX (as GUI)](https://openjfx.io/openjfx-docs/)
- [Maven (Build Tool)](https://maven.apache.org/)
- [SQLite (as Database)](https://www.sqlite.org/index.html)

## _Registration_
Anyone can search for recipes. In order to rate and comment on a recipe, the user needs to first register
into the application by selecting one of the 2 roles:
- Junior Chef
- Head Chef

Both roles require a unique username, a password and the basic information
like full name and email address.

## _Junior Chef_
After the Junior Chef logs in, besides the benefits of a logged in user, he can save
his favourite recipes to use them later.

Also, after logging in, he can see his list of saved recipes. He can add or remove recipes from it.

## _Head Chef_
A Head Chef can create new recipes or add changes to his recipes.

Also, a logged in Head Chef can check his list of recipes to see how well they are doing (by their rating and comments of other Chefs).

## _Issue Tracking_
For the purpose of demonstrating a complete Agile Workflow, we
created a Jira instance that you can find
[here]( https://the-night-heron.atlassian.net/jira/software/projects/CRA/boards/1 ).