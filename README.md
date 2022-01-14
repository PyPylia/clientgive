# clientgive
A simple clientside utility for spawning in any item within Creative mode.

## Installation
1. Download and run the [Fabric installer](https://fabricmc.net/use).
   - Click the "vanilla" button, leave the other settings as they are,
     and click "download installer".
   - Note: this step may vary if you aren't using the vanilla launcher
     or an old version of Minecraft.
2. Download the [Fabric API](https://minecraft.curseforge.com/projects/fabric)
   and move it to the mods folder (`.minecraft/mods`).
3. Download clientgive from the [releases page](https://github.com/PyPylia/clientgive/releases)
   and move it to the mods folder (`.minecraft/mods`).
   
## Usage
- Without any arguments
  ```
  /clientgive minecraft:stick
  ```
  - This doesn't stack items, if there are no empty slots in the hotbar it will return an error.

- With a specified amount
  ```
  /clientgive minecraft:stick 16
  ```
  - Limited to between 1 and 64.

- With NBT data
  ```
  /clientgive minecraft:stick{Enchantments:[{id:"minecraft:sharpness", lvl:10s}]}
  ```

- With the SlotID
  ```
  /clientgive minecraft:stick 1 36
  ```
  - This can bypass the empty slot restriction.
  - Note: this uses the slot system in line with `CreativeInventoryActionC2SPacket`, many slots have different IDs than normal.

## Contributing
1. Clone the repository
   ```
   git clone https://github.com/PyPylia/clientgive
   cd clientgive
   ```
2. Generate the Minecraft source code
   ```
   ./gradlew genSources
   ```
   - Note: inside Windows Command Prompt, use `gradlew` rather than `./gradlew`.
3. Import the project into your preferred IDE.
   - If you use IntelliJ, you can simply import the project as a Gradle project.
   - If you use Eclipse, you need to run `./gradlew eclipse` before importing the project as an Eclipse project.
   - If you use VSCode, you need to run `./gradlew vscode` before importing the project as an VSCode project.
4. Edit the code
5. After testing in the IDE, build a JAR to test whether it works outside the IDE too
   ```
   ./gradlew build
   ```
   The mod JAR may be found in the `build/libs` directory
6. [Create a pull request](https://help.github.com/en/articles/creating-a-pull-request)
   so that your changes can be integrated into clientgive
   - Note: for large contributions, create an issue before doing all that
     work, to ask whether your pull request is likely to be accepted
