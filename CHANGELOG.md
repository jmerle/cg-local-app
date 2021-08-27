# 1.3.0 - August 27th, 2021
- Added support for sending HTTP GET requests to [http://localhost:53135/play](http://localhost:53135/play) to trigger "Play All Testcases" on CodinGame. This works regardless of what Auto Play is set to in CG Local's settings.
- Fixed the "Open CodinGame" button on Linux systems that don't have the GNOME libraries installed.

# 1.2.0 - May 5th, 2020
- Moved the UI from JavaFX with TornadoFX to Swing. This change makes the application work with Java versions newer than 8 and means it is no longer required to run the application with a JRE or JDK with JavaFX available.
- Fixed a bug where the server would sometimes not be able to bind to its port if the application was quit a short time ago.

# 1.1.1 - October 8th, 2019
- Made the window resizable to fix issues on KDE

# 1.1.0 - October 6th, 2018
- Removed the FXLauncher dependency, which previously handled the auto-updating. Since the application is considered stable, this is no longer necessary. By removing FXLauncher, I am not required to keep up a webhost to host the files on, and the application will launch faster because it doesn't have to do an up-to-date check.
- Fixed font rendering issues on Linux
- Fixed the "Open CodinGame" button on Linux

# 1.0.1 - January 4th, 2018
- Fixed an issue where changes in vim and gedit were not detected properly

# 1.0.0 - January 3rd, 2018
- Initial release
