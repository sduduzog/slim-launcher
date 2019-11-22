# Contributing

## Create an issue

Check to see if the issue already exists and if it does, leave a comment to get our attention! And if the issue doesn't already exist, feel free to create a new one. We will disagree on some design descisions but we will also agree on most. Which is why creating an issue and a discussion around it is neccessary.

## How to contribute
Fork the project and clone it to your local machine. To avoid build issues please open the project with Android Studio 4.0 Canary 3. In the time of writting this, this version of the IDE came with something I really needed for the app development to move forward. In the near future, a stable channel must be created and maintained for this project.

## Create an issue

Check to see if the issue already exists and if it does, leave a comment to get our attention! And if the issue doesn't already exist, feel free to create a new one. We will disagree on some design descisions but we will also agree on most. Which is why creating an issue and a discussion around it is neccessary.

## Clean code with tests

Existing code is barely unit tested. This has to change, hence the introduction of Dagger to the project in making it easier to refactor and test. When contributing, test are encouraged but not mandatory.

## Create a pull request
### Create your PR against `develop/incoming`. Never create a Pull Request against `master` or any other branch except for the specified...unless I accidentally deleted the correct branch and haven't created a new one.
Try to keep the pull request small. Make sure you address a single concern. If a pull request does more that what it is described to do. I'm less likely going to trust that work and inevitably less likely going to accept it. This project doesn't have a code standard that I'm following so before you commit, do a Ctrl+Alt+L on the file you're working on or when using Android Studio to commit, make sure the **Reformat code** checkbox is checked.