# payroll-system
 A somewhat payroll system simulation in Java

## Features
- Employee ID Input
- Employee worked days Input

## Goals
- Utilize ArrayList
    > Used as employee ID and daily rate storage, basically a 2D ArrayList
- Used the GUI library of Java
    > - Accomplish without the aid of Designers (Netbeans Designer, JForm Designer, etc.)
    > - Provide decent UI/UX
    > - Spotify-like color theme
- Provide error feedback
    > - Handle invalid Input
    >   - Employee ID input contains non-numeric characters
    >   - Days Worked input contains non-numeric characters
    > - Handle unregistered Employee ID
    >   - Employee ID is not registered on the 2D ArrayList

## Valid Employee IDs
- 123 456 789
- 123 456 788
- 123 456 777
- 123 456 666
- 123 455 555
- 123 444 444
- 123 333 333
- 122 222 222
- 111 111 111
- 999 999 999

## Screenshots

Initial Interface
![](src/main/resources/screenshots/Initial%20Interface.png)

Verified ID Interface
![](src/main/resources/screenshots/Verified%20ID%20Interface.png)

Assessed Interface
![](src/main/resources/screenshots/Assessed%20Interface.png)

## Shortcomings

- Improper way of handling display errors
    > Prevented unwanted input but still improper by-the-book

- Glitch on rounded panels and text fields
    > When modifying dimensions, the verificationContainerPanel breaks

- Some errors are unhandled
    > Too lazy to do them
