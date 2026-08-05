# OGResize

Lightweight Paper/ShreddedPaper plugin that allows players to dynamically change player size with a GUI.

### Features

- Change player size with interactive GUI
- Moderation command to resize other players
- Fully configurable minimum, maximum, increment, and default sizes
- Persistent player sizes across sessions and respawn

### Installation

1. Download the latest `.jar`
2. Place it in your server's `/plugins/` folder
3. Configure `config.yml` if needed
4. Start your server

### Configuration

config.yml
```settings:
  min-size: 0.5
  max-size: 1.5
  step-size: 0.1
  default-size: 1.0
```
use `/resize reload` to apply config changes without restarting the server <br>
each 0.5 increment = one block
  
playersize.yml 
```players:
	<uuid>:
	  scale: 1.0
```
Saves player size to UUID. Should not be manually edited unless absolutely necessary.	
  
## Commands
### Player Commands

- `/resize`: Open GUI
- `/resize help`: Show help menu
- `/resize info`: Show plugin info
### Moderation Command
- `/resize [username] [size]`: Manually set any player's size
### Admin Commands
- `/resize reload`: Reload plugin and config
- `/resise disable`: Disable plugin
- `/resize enable`: Enable plugin

### Permissions
- `ogresize.self`: Allows use of GUI and changing own player size (default: operators)
- `ogresize.others`: Allows player to resize other players (default: operators)
- `ogresize.admin`: Allows use of reload / enable / disable (default: operators)

## GUI
<img width="702" height="523" alt="image" src="https://github.com/user-attachments/assets/8c1f1c7b-ba3d-4b74-ac5b-c9158ad92a4b" /> <br>
Full GUI
<br>
<br>
<img width="950" height="117" alt="image" src="https://github.com/user-attachments/assets/03a41e3d-4483-442e-87a4-dba72583baf6" /> <br>
Instantly set player to minimum size. Minimum reflects value set in config.
<br>
<br>
<img width="692" height="113" alt="image" src="https://github.com/user-attachments/assets/9bcfdc96-c219-4009-9a47-011f601b75a6" /> <br>
Increment player size smaller. Increment reflects step size value in config. 
<br>
<br>
<img width="344" height="117" alt="image" src="https://github.com/user-attachments/assets/b6a4843b-75ed-46f2-8813-177271e6f281" /> <br>
Return player size to default. Default reflects value set in config. 
<br>
<br>
<img width="668" height="116" alt="image" src="https://github.com/user-attachments/assets/209456eb-d77a-4c3e-9efc-8b63af526006" /> <br>
Increment player size bigger. Increment reflects step-size value in config.
<br>
<br>
<img width="950" height="118" alt="image" src="https://github.com/user-attachments/assets/5bebc46f-fe4a-4952-a938-9e02eca80b24" /> <br>
Instantly set player to maximum size. Maximum reflects value set in config.
<br>

### Requirements
- Paper 26.1+ or ShreddedPaper 26.1+
- Java 25

### Support
Found a bug or have a suggestion?
Open an issue on GitHub, open a ticket in the OGCraft discord, or message me directly on Discord @ OJCream

