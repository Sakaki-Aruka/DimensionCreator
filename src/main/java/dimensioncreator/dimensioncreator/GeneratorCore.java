package dimensioncreator.dimensioncreator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GeneratorCore implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        /*
        args description

        (generate)
        /dc [0] [1] [2] [3]

        [0]:worldName
        [1]:environment (normal,nether,end,custom)
        [2]:generate structures (true,false)
        [3]:world type (amplified,flat,large_biomes,normal)

        (unload)
        /dc unload [1] [2]

        [1]:worldName
        [2]:save (true,false)

         */
        Player player = (Player) sender;
        if(args.length != 4){
            if(args[0].equalsIgnoreCase("unload") && args.length ==3){
                for(World world : Bukkit.getWorlds()){
                    if(args[1].equalsIgnoreCase(world.getName())){
                        Bukkit.unloadWorld(world,Boolean.valueOf(args[2]));
                        player.sendMessage("§a[Dimension Creator]:"+world.getName()+" unload successful.");
                    }
                }
                player.sendMessage("§c[Dimension Creator]:"+args[1]+" unload failed.");
                return false;

            }else {
                player.sendMessage("§c[Dimension Creator]:Invalid options.Fail to create a world.");
                return false;
            }
        }
        try{
            String worldName = args[0];
            World.Environment environment = World.Environment.valueOf(args[1].toUpperCase(Locale.ROOT));
            WorldCreator worldCreator = new WorldCreator(worldName).generateStructures(Boolean.valueOf(args[2]));
            WorldType worldType = WorldType.valueOf(args[3].toUpperCase(Locale.ROOT));

            worldCreator.environment(environment);
            worldCreator.type(worldType);

            player.sendMessage("§2[Dimension Creator]:Creating the dimension.\n[Dimension Creator]:options:"+worldCreator);
            Bukkit.createWorld(worldCreator);
            player.sendMessage("§a[Dimension Creator]:Finish to create the dimension.");
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            player.sendMessage("§c[Dimension Creator]:Invalid options.Fail to load settings.");
            return false;
        }

    }

    public List<String> onTabComplete(CommandSender sender,Command command,String label,String[] args){
        if(args.length == 1){
            return null;
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("unload")){
                ArrayList<String> worlds = new ArrayList<>();
                for(World world : Bukkit.getWorlds()){
                    worlds.add(world.getName());
                }
                return tabCompleterSupport(worlds,args[1].toUpperCase(Locale.ROOT));

            }else{
                return tabCompleterSupport(new ArrayList<>(Arrays.asList("NORMAL","NETHER","THE_END","CUSTOM")),args[1].toUpperCase(Locale.ROOT));
            }

        }else if(args.length == 3){
            return tabCompleterSupport(new ArrayList<>(Arrays.asList("true","false")),args[2].toUpperCase(Locale.ROOT));
        }else if(args.length == 4 && !(args[0].equalsIgnoreCase("unload"))){
            return tabCompleterSupport(new ArrayList<>(Arrays.asList("AMPLIFIED","FLAT","LARGE_BIOMES","NORMAL")),args[3].toUpperCase(Locale.ROOT));
        }
        return null;
    }

    public ArrayList<String> tabCompleterSupport(ArrayList<String> array,String input){
        ArrayList<String> settings = new ArrayList<>();
        for(String loop : array){
            if(loop.contains(input)){
                settings.add(loop);
            }
        }
        if(settings.size() != 0){
            return settings;
        }else{
            return null;
        }
    }
}
