# OGResize

Lightweight Paper/ShreddedPaper plugin that allows players to dynamically change player size with a GUI.

### Features

- Changes player size with interactive GUI
- Moderation command to resize other players
- Fully configurable minimum, maximum, increment, and default sizes
- Persistent player sizes across sessions and respawn

### Installation

1. Download the latest `.jar` from Github
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
use `/resize reload` to apply config changes without restarting the server
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
- `/resize [username] [size]`: Set player size
### Admin Commands
- `/resize reload`: Reload plugin and config
- `/resise disable`: Disable plugin
- `/resize enable`: Enable plugin

### Permissions
- `ogresize.self`: Allows use of GUI and changing own player size (default: operators)
- `ogresize.others`: Allows player to resize other players (default: operators)
- `ogresize.admin`: Allows use of reload / enable / disable (default: operators)

## GUI
<img width="693" height="515" alt="image" src="https://github.com/user-attachments/assets/541932e1-afd6-4eb0-a681-8999c7bbf33b" />
Full GUI

<img width="718" height="115" alt="image" src="https://github.com/user-attachments/assets/9a403493-bd6d-4c1c-aa54-79a3ea9d3b25" />
Left side/smaller option. Minimum reflects value set in config. 

<img width="310" height="113" alt="image" src="https://github.com/user-attachments/assets/be0d51cb-2989-4202-94f8-1049a86b7f3e" />
Middle/default option. Default reflects value set in config. 

<img width="736" height="114" alt="image" src="https://github.com/user-attachments/assets/46371342-933e-4361-834c-f70cc6195a1f" />
Right side/bigger option. Maximum reflects value set in config.

### Requirements
- Paper 1.21+ or ShreddedPaper 1.21.11+

### Support
Found a bug or have a suggestion?
Open an issue on GitHub, open a ticket in the OGCraft discord, or message me directly on Discord @ OJCream

### License

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.





