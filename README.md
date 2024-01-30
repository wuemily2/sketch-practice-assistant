# Sketch Practice Assistant
An app intended to replicate the functionality of websites like [Line of Action](https://line-of-action.com/), but in desktop form and allowing users to select their own photos.

A simple wireframe has been created for the concept in Figma, see [here](https://www.figma.com/proto/KsqvHIprREEpnSY3ejOm1P/Sketching-Practice-Aid?node-id=1-28&starting-point-node-id=1%3A28&mode=design&t=2RGNBRtHmll5LizH-1). It is still recommended to check a site like [Line of Action](https://line-of-action.com/) to understand intended functionality.

## Why make this app?
I can't find an equivalent app online that has the features I would like as a practicing artist, so I decided to develop this app on my own.

## Is this app completed yet?
The app is tentatively completed, but I've given up on trying to make it runnable outside of intelliJ for now.
JavaFx is quite difficult to work with, from cluttered documentation, 
to the lack of dynamic resizing functionalities. If I will release this app, it will be
probably by using other frameworks or libraries, possibly in other languages.

For now, I am busy with things in life, so this won't happen in near future.

## What version of Java and JavaFX is used?
[OpenJDK 21](https://jdk.java.net/21/) and [OpenJFX 21](https://gluonhq.com/products/javafx/)

## How would I set this up on my own computer and try to run it?
I have not figured out how to make this distributable.

The following is my personal setup:

### My untested setup steps (on Windows, did not verify for other platforms)
1. Clone the repository
2. Open in intelliJ; install intelliJ if you haven't already. My version is IntelliJ IDEA 2023.2.1 (Ultimate Edition). 
But you can probably use the free community edition.
3. Project Structure > Project Settings > Project, set sdk to open openjdk 21 if it isn't already set
4. right click pom.xml file, go to "Maven" and reload project if project isn't loaded based on that already
5. Right click SketchPractice class and run SketchPractice.main()
