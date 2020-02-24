This is a Star Craft: Brood War bot for CSE 40971. This bot is a copy of the Marine Hell bot from the course documnetation that we are building on.

If you would like to run this bot, we *highly* recommend simply downloading the BotBoi3000.jar file from the "executable jar file" folder, which will periodically be updated to our latest compiled build.

If, however, you are inclined to compile the .jar on your own, then please proceed with the following steps:

# Compiling the Bot

1. If you have a Mac, please get a Windows (Pro version or above pls)

2. Now that you have Windows, download [32-bit JRE](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) (choose the Windows x86 online version). Make sure the install location is listed as the default "Program Files (x86)/Java/name_of_JRE_version" because ~~we don't want to add support for changing from the default install location~~ it's super vitally important for technical reasons you wouldn't understand.

3. Now that you have successfully installed a 32-bit JRE, please clone our repository to some location on your computer.

4. Open ~~Command Prompt~~ Powershell and navigate to the cloned repository.

5. Type make into the terminal.

6. ~~Give up and just download the jar file from our github~~ Congratulations! You've successfully built our jar file!

# Necessary Installs

1. Install Docker using the tutorial found [here](https://github.com/Games-and-Simulations/sc-docker/blob/master/INSTALL.md)
Note: If you're having trouble try `py -3.6 -m pip install scbw` to make it install scbw with that python version (3.6 in this case) (some machines may default to using the system python 2 so specifying which python to use for the install helped it work for me)

2. After you've installed docker, open a powershell window as administrator and run `docker-machine create -d hyperv --hyperv-virtual-switch "Default Switch" default`. ~~We have no clue what it does, but it seems to work~~ For the sake of Readme brevity, we will ommit the thorough explanation of the command which we had prepared (we're super experienced college undergrads tho, so you can obviously trust us to run commands as adminstrator with no explanations needed).

# Running the Bot

1. Put the .jar file in the AI folder of the bots folder, which should be located at "Users/Your_User_Folder/AppData/Roaming/scbw/bots/". You will need to create a folder called BotBoi3000, as well as an AI folder, a Read folder, a Write folder, a json, and a BWAPI.dll file (follow the explanation in the Development section of [this github page](https://github.com/Games-and-Simulations/sc-docker/blob/master/USAGE.md#development))

2. `scbw.play --bots "BotBoi3000" "BotBoi3000" --show_all` is to get it to play itself, and `scbw.play --bots "BotBoi3000" --human --show_all` is to get it to play against yourself.

# Known limitations

1. It doesn't actually run if the bot's starting location is on the right or bottom side of the map.
2. We don't actually have a Makefile, so don't actually try to make it.


