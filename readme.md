This is a Star Craft: Brood War bot for CSE 40971. This bot is a copy of the Marine Hell bot from the course documnetation that we are building on.

# About Our Bot

Our bot makes use of behavior trees (one for strategy, one for economy, and one for research(coming soon)). The trees will be continuously executed throughout the game in a order that varies throughout gameplay depending on the situation. Our trees make use of a blackboard that keeps track of important up to date game data that determine what decisions will be made. Our trees will behave differently based on the race of our opponent, since good strategies drastically vary depending on the type of opponent. The trees consist of node objects that keep going down until an action(s) is executed. Once the action(s) is executed, the blackboard is informed that the tree has been ran and the tree will be ready to run again from the root. As we continue to grow our trees in detail and complexity we hope to have a bot that will make "BotBoi3000" a household name in the StarCraft universe.

If you would like to run this bot, we *highly* recommend simply downloading the BotBoi3000.jar file from the "executable jar file" folder, which will periodically be updated to our latest compiled build.

If, however, you are inclined to compile the .jar on your own, then please proceed with the following steps:

# Compiling the Bot

1. If you have a Mac, please get a Windows (Pro version or above pls)

2. Now that you have Windows, download [32-bit JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) (choose the Windows x86 version). Make sure the install location is listed as the default "Program Files (x86)/Java/name_of_JDK_version" because ~~we don't want to add support for changing from the default install location~~ it's super vitally important for technical reasons you wouldn't understand.

3. Now that you have successfully installed a 32-bit JDK, please clone our repository to some location on your computer.

4. Now you need to download Chocolatey (if you don't already have it). Open ~~Command Prompt~~ Powershell as administrator and type in `Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))` (What's that you say? you don't trust me enough to run any command I tell you with administrator privledges on your machine? Well you should)

5. Now you should ~~Give up and just download the jar file from our github~~ type `choco install make` to install the windows version of make so you can run a makefile from PowerShell. (I know what you're thinking. "But there's a Windows subsystem for Linux that already has that". Once again, have a little faith. There's a reason for everything I tell you to do. Probably. [But seriously, don't try to use the Linux subsytem to compile this project](https://a_completely_functional_link_that_brings_you_to_some_.edu_site_that_verifies_everything_I_just_said))

6. Now you'll need to make some changes to your Path. Go to your Windows start search bar and type "environment variables" and click on the top recommendation to edit your environment variables. Then click the "Environment Variables" button near the bottom. Add a new variable called JAVA_HOME and set it to `C:\Program Files (x86)\Java\jdk1.8.0_241` (change it if your 32-bit java version is different, this is just the install location of whatever Java you installed). Then find you Path variable and edit it, to add `%JAVA_HOME%\bin` to your path. Do this for both user and system variables because ~~I'm not sure which one actually matters~~ it's clearly the only way to do this.

5. Now you can open a non-administrative Powershell (mostly just to feel safer about it, but also so it has your updated Environment Variable changes) and navigate to the cloned repository. Go to the src folder and type in `Make`.

6.  Congratulations! You've successfully built our jar file! Now you can go install other stuff below.

# Necessary Installs

1. Install Docker using the tutorial found [here](https://github.com/Games-and-Simulations/sc-docker/blob/master/INSTALL.md)
**Note:** If you're having trouble try `py -3.6 -m pip install scbw` to make it install scbw with that python version (3.6 in this case). Some machines may default to using the system python 2 so specifying which python to use for the install helped it work for me.

2. After you've installed docker, open a powershell window as administrator and run `docker-machine create -d hyperv --hyperv-virtual-switch "Default Switch" default`. ~~We have no clue what it does, but it seems to work~~ For the sake of Readme brevity, we will ommit the thorough explanation of the command which we had prepared (we're super experienced college undergrads tho, so you can obviously trust us to run commands as adminstrator with no explanations needed. The chocolately install thing from earlier didn't break your computer, so this ~~will definitely finish it off~~ won't either).

# Running the Bot

1. Put the BotBoi3000.jar file that you either made or downloaded in the AI folder of the bots folder, which should be located at "Users/Your_User_Folder/AppData/Roaming/scbw/bots/". You will need to create a folder called BotBoi3000, as well as an AI folder, a Read folder, a Write folder, a json, and a BWAPI.dll file (follow the explanation in the Development section of [this github page](https://github.com/Games-and-Simulations/sc-docker/blob/master/USAGE.md#development))

2. `scbw.play --bots "BotBoi3000" "BotBoi3000" --show_all` is to get it to play itself, and `scbw.play --bots "BotBoi3000" --human --show_all` is to get it to play against yourself.

# Known limitations

1. It doesn't actually run if the bot's starting location is on the right or bottom side of the map. (Read as top left spawn location **defines** the current meta)
2. ~~We don't actually have a Makefile, so don't try to make it.~~ We now have a Makefile. Progress.


